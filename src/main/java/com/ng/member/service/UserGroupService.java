package com.ng.member.service;

import com.ng.member.dto.request.UserGroupRequest;
import com.ng.member.dto.request.UserToGroupRequest;
import com.ng.member.dto.response.GenericResponse;
import com.ng.member.dto.response.UserToGroupResponse;
import com.ng.member.entity.UserDetail;
import com.ng.member.entity.UserGroup;
import com.ng.member.entity.UserToGroup;
import com.ng.member.mapper.UserGroupMapper;
import com.ng.member.mapper.UserMapper;
import com.ng.member.mapper.UserToGroupMapper;
import com.ng.member.util.WordEncode;
import com.ng.member.util.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author niuguang
 * @date 18-1-16
 */
@Service
@Slf4j
public class UserGroupService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserGroupMapper userGroupMapper;

    @Autowired
    private UserToGroupMapper userToGroupMapper;

    @Autowired
    private UserService userService;


    /**
     * 创建用户组
     * @param request
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public GenericResponse addUserGroup(UserGroupRequest request) {
        GenericResponse commonResp = GenericResponse.builder().code(Constants.CommonCode.SUCCESS).build();
        Long currUserId = userService.queryCurrentUserId();
        // 同一个用户下相同组名的用户组只能有一个
        if(!checkGroupNameUsable(request.getGroupName(),currUserId)){
            log.error("用户名下已存在该用户组");
            commonResp.setCode(Constants.CommonCode.FAIL);
            commonResp.setMsg("您名下已存在该用户组");
            return commonResp;
        }

        // 插入用户组
        UserGroup userGroup = new UserGroup();
        userGroup.setGroupName(request.getGroupName());
        userGroup.setAdminUserId(currUserId);
        userGroup.setGroupDesc(request.getGroupDesc());
        userGroup.setCreateTime(new Date());
        // 用户ID+组名生成组邀请CODE
        SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        userGroup.setGroupCode(WordEncode.EncoderByMd5(currUserId+request.getGroupName()+tempDate.format(new Date())));
        userGroupMapper.insertSelective(userGroup);

        // 初始化管理员到用户组
        UserToGroup userToGroup = new UserToGroup();
        userToGroup.setGroupId(userGroup.getId());
        userToGroup.setUserId(currUserId);
        userToGroup.setGroupRole(Constants.UserGroupRoleConstants.ADMIN);
        userToGroup.setCreateTime(new Date());
        userToGroupMapper.insertSelective(userToGroup);

        return commonResp;
    }


    /**
     * 查询当前用户用户组
     * @return
     */
    public GenericResponse<List<UserGroup>> queryUserGroup(){
        GenericResponse commonResp = GenericResponse.builder().code(Constants.CommonCode.SUCCESS).build();
        Example queryUserGroupExam = new Example(UserGroup.class);
        queryUserGroupExam.createCriteria().andEqualTo("adminUserId",userService.queryCurrentUserId())
                .andEqualTo("deleteFlag",Constants.DeleteFlagConstants.DELETE_FALSE);
        commonResp.setData(userGroupMapper.selectByExample(queryUserGroupExam));
        return commonResp;

    }

    /**
     * 查询当前用户用户组
     * @return
     */
    public GenericResponse<List<UserGroup>> queryUserGroupByUserId(Long userId){
        GenericResponse commonResp = GenericResponse.builder().code(Constants.CommonCode.SUCCESS).build();
        Example queryUserGroupExam = new Example(UserGroup.class);
        queryUserGroupExam.createCriteria().andEqualTo("adminUserId",userId)
                .andEqualTo("deleteFlag",Constants.DeleteFlagConstants.DELETE_FALSE);
        commonResp.setData(userGroupMapper.selectByExample(queryUserGroupExam));
        return commonResp;

    }

    /**
     * 查询当前用户用户组
     * @return
     */
    public GenericResponse<List<Long>> queryUserListByGroupId(List<Long> userGroupId){
        GenericResponse commonResp = GenericResponse.builder().code(Constants.CommonCode.SUCCESS).build();
        commonResp.setData(userToGroupMapper.queryUserIdByGroupList(userGroupId));
        return commonResp;
    }

    /**
     * 删除用户组，同时删除组成员
     * @param userGroupId
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public GenericResponse delUserGroup(Long userGroupId){
        GenericResponse commonResp = GenericResponse.builder().code(Constants.CommonCode.SUCCESS).build();
        if(null == userGroupId){
            log.error("删除用户组入参错误");
            commonResp.setCode(Constants.CommonCode.FAIL);
            commonResp.setMsg("删除用户组入参错误");
            return commonResp;
        }
        // todo 校验用户组是否在做任务

        // 删除组
        if(userGroupMapper.delUserGroup(userGroupId,userService.queryCurrentUserId()) == 0){
            commonResp.setCode(Constants.CommonCode.FAIL);
            commonResp.setMsg("删除用户组失败！");
            return commonResp;
        }

        // 删除组成员关系
        userToGroupMapper.delUserToGroup(userGroupId);

        return commonResp;
    }

    /**
     * 修改用户组
     * @param request
     * @return
     */
    public GenericResponse updateUserGroup(UserGroupRequest request){
        GenericResponse commonResp = GenericResponse.builder().code(Constants.CommonCode.SUCCESS).build();
        if(null == request.getId()){
            log.error("更新用户组入参错误");
            commonResp.setCode(Constants.CommonCode.FAIL);
            commonResp.setMsg("编辑用户组入参错误");
            return commonResp;
        }
        Long currUserId = userService.queryCurrentUserId();
        UserGroup userGroup = new UserGroup();
        userGroup.setGroupName(request.getGroupName());
        userGroup.setAdminUserId(currUserId);
        userGroup.setGroupDesc(request.getGroupDesc());
        userGroup.setGroupCode(WordEncode.EncoderByMd5(currUserId+request.getGroupName()));
        userGroup.setUpdateTime(new Date());

        Example updateUserGroupExam = new Example(UserGroup.class);
        updateUserGroupExam.createCriteria().andEqualTo("id",request.getId()).andEqualTo("adminUserId",userService.queryCurrentUserId())
                .andEqualTo("deleteFlag",Constants.DeleteFlagConstants.DELETE_FALSE);;
        if(userGroupMapper.updateByExampleSelective(userGroup,updateUserGroupExam)== 0){
            log.error("更新用户组失败");
            commonResp.setCode(Constants.CommonCode.FAIL);
            commonResp.setMsg("用户组不存在");
            return commonResp;
        }

        return commonResp;
    }

    /**
     * 查询用户组用户
     * @param userGroupId
     * @return
     */
    public GenericResponse<UserToGroupResponse> queryUserToGroup(Long userGroupId){
        GenericResponse commonResp = GenericResponse.<UserToGroupResponse>builder().code(Constants.CommonCode.SUCCESS).build();
        UserToGroupResponse userToGroupResponse = UserToGroupResponse.builder().groupId(userGroupId).build();
        Example queryUserGroupExam = new Example(UserGroup.class);
        queryUserGroupExam.createCriteria().andEqualTo("id",userGroupId).andEqualTo("adminUserId",userService.queryCurrentUserId())
                .andEqualTo("deleteFlag",Constants.DeleteFlagConstants.DELETE_FALSE);
        List<UserGroup> userGroupList = userGroupMapper.selectByExample(queryUserGroupExam);
        if(CollectionUtils.isEmpty(userGroupList)){
            commonResp.setCode(Constants.CommonCode.FAIL);
            commonResp.setMsg("查询用户组成员失败");
            return commonResp;
        }
        userToGroupResponse.setGroupName(userGroupList.get(0).getGroupName());

        // 查询组成员详细信息
        userToGroupResponse.setGroupUserList(userToGroupMapper.queryUserToGroupByUserName(userGroupId,null));
        commonResp.setData(userToGroupResponse);

        return commonResp;
    }

    /**
     * 按用户名模糊查询用户组用户
     * @param groupId
     * @param userName
     * @return
     */
    public GenericResponse<UserToGroupResponse> queryUserToGroupByUserName(Long groupId,String userName){
        GenericResponse commonResp = GenericResponse.<UserToGroupResponse>builder().code(Constants.CommonCode.SUCCESS).build();
        UserToGroupResponse userToGroupResponse = UserToGroupResponse.builder().groupId(groupId).build();
        Example queryUserGroupExam = new Example(UserGroup.class);
        queryUserGroupExam.createCriteria().andEqualTo("id",groupId).andEqualTo("adminUserId",userService.queryCurrentUserId())
                .andEqualTo("deleteFlag",Constants.DeleteFlagConstants.DELETE_FALSE);
        List<UserGroup> userGroupList = userGroupMapper.selectByExample(queryUserGroupExam);
        if(CollectionUtils.isEmpty(userGroupList)){
            commonResp.setCode(Constants.CommonCode.FAIL);
            commonResp.setMsg("查询用户组成员失败");
            return commonResp;
        }
        userToGroupResponse.setGroupName(userGroupList.get(0).getGroupName());

        // 查询组成员详细信息
        userToGroupResponse.setGroupUserList(userToGroupMapper.queryUserToGroupByUserName(groupId,userName));
        commonResp.setData(userToGroupResponse);

        return commonResp;
    }

    /**
     * 添加用户到用户组
     * @param request
     * @return
     */
    public GenericResponse addUserToGroup(UserToGroupRequest request){
        GenericResponse commonResp = GenericResponse.builder().code(Constants.CommonCode.SUCCESS).build();
        // 查询用户名对应的用户信息
        UserDetail userDetail = userMapper.selectUserInfo(request.getUserName());
        if(null == userDetail){
            // 当前用户不存在，后续邀请注册
            log.debug("用户不存在");
            commonResp.setCode(Constants.CommonCode.FAIL);
            commonResp.setMsg("用户不存在");
            return commonResp;
        }

        // 判断要添加的用户是否已经在组中
        if(!checkUserNameGroupUsable(request.getGroupId(),userDetail.getId())){
            log.debug("用户在该组已存在");
            commonResp.setCode(Constants.CommonCode.FAIL);
            commonResp.setMsg("用户在该组已存在");
            return commonResp;
        }
        UserToGroup userToGroup = new UserToGroup();
        userToGroup.setGroupId(request.getGroupId());
        userToGroup.setUserId(userDetail.getId());
        userToGroup.setCreateTime(new Date());
        userToGroupMapper.insertSelective(userToGroup);

        return commonResp;
    }

    /**
     * 用户组删除用户
     * @param groupId
     * @param userId
     * @return
     */
    public GenericResponse delUserToGroupUser(Long groupId,Long userId){
        GenericResponse commonResp = GenericResponse.builder().code(Constants.CommonCode.SUCCESS).build();
        if(null == groupId || null == userId){
            log.error("用户组删除用户入参错误");
            commonResp.setCode(Constants.CommonCode.FAIL);
            commonResp.setMsg("删除用户组成员入参错误");
            return commonResp;
        }
        // 检查当前用户是不是操作组的管理员
        Long currUserId = userService.queryCurrentUserId();
        UserGroup userGroupParam = new UserGroup();
        userGroupParam.setId(groupId);
        userGroupParam.setAdminUserId(currUserId);
        userGroupParam.setDeleteFlag(Constants.DeleteFlagConstants.DELETE_FALSE);
        UserGroup userGroup = userGroupMapper.selectOne(userGroupParam);
        if(null == userGroup){
            log.debug("当前用户不是所操作用户组的管理员");
            commonResp.setCode(Constants.CommonCode.FAIL);
            commonResp.setMsg("您不是该用户组的管理员");
            return commonResp;
        }

        // 删除用户组成员
        userToGroupMapper.delUserToGroupUser(groupId,userId);
        return commonResp;
    }

    /**
     * 用户接收邀请加入组
     * @param groupId
     * @return
     */
    public GenericResponse agreeToAddInGroup(Long groupId){
        GenericResponse commonResp = GenericResponse.builder().code(Constants.CommonCode.SUCCESS).build();
        if(null == groupId){
            log.error("用户加入组入参错误");
            commonResp.setCode(Constants.CommonCode.FAIL);
            commonResp.setMsg("用户加入组入参错误");
            return commonResp;
        }

        // 判断用户组是否还存在
        UserGroup userGroup = userGroupMapper.selectByPrimaryKey(groupId);
        if(null == userGroup){
            log.error("用户组已解散");
            commonResp.setCode(Constants.CommonCode.FAIL);
            commonResp.setMsg("用户组已解散");
            return commonResp;
        }

        UserToGroup userToGroup = new UserToGroup();
        userToGroup.setGroupId(groupId);
        userToGroup.setUserId(userService.queryCurrentUserId());
        userToGroup.setCreateTime(new Date());
        userToGroupMapper.insertSelective(userToGroup);

        return commonResp;
    }

    /**
     * 校验组名名是否可用
     * @param groupName
     * @param userId
     * @return
     */
    private boolean checkGroupNameUsable(String groupName,Long userId){
        Example example = new Example(UserGroup.class);
        example.createCriteria().andEqualTo("groupName", groupName).andEqualTo("adminUserId",userId)
                .andEqualTo("deleteFlag", Constants.DeleteFlagConstants.DELETE_FALSE);
        if(userGroupMapper.selectCountByExample(example) > 0){
            return false;
        }
        return true;
    }

    /**
     * 校验用户是否在组中
     * @param groupId
     * @param userId
     * @return
     */
    private boolean checkUserNameGroupUsable(Long groupId,Long userId){
        Example example = new Example(UserToGroup.class);
        example.createCriteria().andEqualTo("groupId", groupId).andEqualTo("userId",userId)
                .andEqualTo("deleteFlag", Constants.DeleteFlagConstants.DELETE_FALSE);
        if(userToGroupMapper.selectCountByExample(example) > 0){
            return false;
        }
        return true;
    }


}
