package com.ng.member.service;

import com.alibaba.fastjson.JSON;
import com.ng.member.config.web.security.JwtTokenUtil;
import com.ng.member.dto.request.LoginRequest;
import com.ng.member.dto.request.RegisterRequest;
import com.ng.member.dto.response.GenericResponse;
import com.ng.member.dto.response.LoginResponse;
import com.ng.member.entity.*;
import com.ng.member.mapper.*;
import com.ng.member.util.WordEncode;
import com.ng.member.util.constant.Constants;
import com.ng.member.util.constant.RedisConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author niuguang
 * @date 18-1-16
 */
@Service
@Slf4j
public class UserService {

    @Autowired(required = false)
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private RoleAuthMapper roleAuthMapper;

    @Autowired
    private UserGroupMapper userGroupMapper;

    @Autowired
    private UserToGroupMapper userToGroupMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 用户登录
     * @param loginRequest
     * @return
     */
    public GenericResponse<LoginResponse> login(LoginRequest loginRequest) {
        GenericResponse<LoginResponse> commonResp = GenericResponse.<LoginResponse>builder()
                .code(Constants.CommonCode.SUCCESS).build();
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest, loginRequest.getPassword());

        try {
            authentication = authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            JwtUser principal = (JwtUser) authentication.getPrincipal();
            LoginResponse loginResponse = LoginResponse.builder()
                    .token(jwtTokenUtil.generateToken(principal)).build();
            UserDetail userDetail = principal.getUserInfo().getUserDetail();
            loginResponse.setUserId(userDetail.getId());
            loginResponse.setUserName(userDetail.getUserName());
            commonResp.setData(loginResponse);

            // 维护最后登录时间
            User user = new User();
            user.setId(userDetail.getId());
            user.setLastLoginTime(new Date());
            userMapper.updateByPrimaryKeySelective(user);

            // 用户数据放入缓存
            UserDetail currUser = queryUserDetail(loginRequest.getUserName());
            stringRedisTemplate.opsForValue().set(RedisConstants.CURR_USER_KEY + userDetail.getId(), JSON.toJSONString(currUser)
                   ,60*72, TimeUnit.MINUTES);
        } catch (AuthenticationException e) {
            commonResp.setCode(Constants.CommonCode.FAIL);
            commonResp.setMsg(Optional.of(e.getMessage()).orElse("验证失败"));
            log.error("AuthenticationException", e);
        }
        return commonResp;
    }

    /**
     * 用户注册
     * @param registerRequest
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public GenericResponse register(RegisterRequest registerRequest){
        GenericResponse commonResp = GenericResponse.builder().code(Constants.CommonCode.SUCCESS).build();

        // 校验两次输入密码是否一致
        if(!registerRequest.getPassword().equals(registerRequest.getRePassword())){
            log.error("用户注册，两次输入密码不一致，参数：{}",registerRequest);
            commonResp.setCode(Constants.CommonCode.FAIL);
            commonResp.setMsg("两次输入密码不一致！");
            return commonResp;
        }
        // 校验用户名
        if(!checkUserNameUsable(registerRequest.getUserName())){
            log.error("用户注册，用户名已存在，参数：{}",registerRequest);
            commonResp.setCode(Constants.CommonCode.FAIL);
            commonResp.setMsg("用户名已存在！");
            return commonResp;
        }

        // 插入用户表
        User user = new User();
        user.setCreateTime(new Date());
        user.setUserName(registerRequest.getUserName());
        user.setPassword(WordEncode.EncoderByMd5(registerRequest.getPassword()));
        userMapper.insertSelective(user);

        // 插入用户角色表
        UserRole userRole = new UserRole();
        userRole.setCreateTime(new Date());
        userRole.setUserId(user.getId());
        userRole.setRoleId(Constants.UserRoleConstants.USER);
        userRoleMapper.insertSelective(userRole);

        // 如果为接受邀请注册
        if(!StringUtils.isEmpty(registerRequest.getGroupCode())){
            addToUserGroup(registerRequest.getGroupCode(),user.getId());
        }

        return commonResp;
    }


    /**
     * 注册成功后自动登录
     * @param userRegister
     * @return
     */
    public GenericResponse<LoginResponse> registerToLogin(RegisterRequest userRegister) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName(userRegister.getUserName());
        loginRequest.setPassword(userRegister.getPassword());
        return login(loginRequest);
    }

    public GenericResponse<UserDetail> queryUserInfo(String userName){
        GenericResponse response = GenericResponse.<UserDetail>builder().code(Constants.CommonCode.SUCCESS).build();
        UserDetail userDetail = queryUserDetail(userName);
        if(null == userDetail){
            response.setCode(Constants.CommonCode.FAIL);
            response.setMsg("用户信息查询失败");
            return response;
        }
        response.setData(userDetail);
        return response;
    }

    public GenericResponse<User> queryUserInfoList(List<Long> userIdList){
        GenericResponse response = GenericResponse.<User>builder().code(Constants.CommonCode.SUCCESS).build();
        Example example = new Example(User.class);
        example.createCriteria().andIn("id",userIdList);
        List<User> userList = userMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(userList)){
            response.setCode(Constants.CommonCode.FAIL);
            response.setMsg("用户信息查询失败");
            return response;
        }
        response.setData(userList);
        return response;
    }

    public GenericResponse<UserDetail> queryCurrentUserInfo(){
        GenericResponse response = GenericResponse.<UserDetail>builder().code(Constants.CommonCode.SUCCESS).build();
        String loginUserName = (String) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        if(StringUtils.isEmpty(loginUserName)){
            response.setCode(Constants.CommonCode.FAIL);
            response.setMsg("用户信息查询失败");
            return response;
        }
        response.setData(queryUserDetail(loginUserName));
        return response;
    }

    /**
     * 查询当前用户ID
     * @return
     */
    public Long queryCurrentUserId(){
        String loginUserName = (String) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        UserDetail userDetail = userMapper.selectUserInfo(loginUserName);
        return userDetail.getId();
    }

    /**
     * 查询用户权限
     * @param userName
     * @return
     */
    private UserDetail queryUserDetail(String userName){

        UserDetail userDetail = userMapper.selectUserInfo(userName);
        if(null != userDetail){
            // 角色
            Example queryUserRoleExam = new Example(UserRole.class);
            queryUserRoleExam.createCriteria().andEqualTo("userId",userDetail.getId())
                    .andEqualTo("deleteFlag",Constants.DeleteFlagConstants.DELETE_FALSE);
            List<UserRole> userRoleList = userRoleMapper.selectByExample(queryUserRoleExam);
            // 目前只有一个角色
            userDetail.setRoleId(userRoleList.get(0).getRoleId());
            userDetail.setRoleName(roleMapper.selectByPrimaryKey(userRoleList.get(0).getRoleId()).getRoleName());

            // 角色权限
            Example queryRoleAuthExam = new Example(RoleAuth.class);
            queryRoleAuthExam.createCriteria().andEqualTo("roleId",userDetail.getRoleId())
                    .andEqualTo("deleteFlag",Constants.DeleteFlagConstants.DELETE_FALSE);
            List<Long> authIdList = roleAuthMapper.selectByExample(queryRoleAuthExam).stream().map(RoleAuth::getAuthId).collect(Collectors.toList());

            Example authExam = new Example(Auth.class);
            authExam.createCriteria().andIn("id",authIdList).andEqualTo("deleteFlag",Constants.DeleteFlagConstants.DELETE_FALSE);
            userDetail.setAuthList(authMapper.selectByExample(authExam));
            userDetail.setPassword(null);
        }

        return userDetail;
    }


    /**
     * 校验用户名是否可用
     * @param userName
     * @return
     */
    private boolean checkUserNameUsable(String userName){
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("userName", userName)
                .andEqualTo("deleteFlag", Constants.DeleteFlagConstants.DELETE_FALSE);
        if(userMapper.selectCountByExample(example) > 0){
            return false;
        }
        return true;
    }

    /**
     * 添加注册用户到用户组
     * @param groupCode
     * @param userId
     */
    private void addToUserGroup(String groupCode,Long userId){
        UserGroup userGroupParam = new UserGroup();
        userGroupParam.setGroupCode(groupCode);
        userGroupParam.setDeleteFlag(Constants.DeleteFlagConstants.DELETE_FALSE);
        UserGroup userGroup = userGroupMapper.selectOne(userGroupParam);
        if(null != userGroup){
            UserToGroup userToGroup = new UserToGroup();
            userToGroup.setGroupId(userGroup.getId());
            userToGroup.setUserId(userId);
            userToGroup.setCreateTime(new Date());
            userToGroupMapper.insertSelective(userToGroup);
        }
    }

}
