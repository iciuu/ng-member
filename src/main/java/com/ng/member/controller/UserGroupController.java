package com.ng.member.controller;

import com.ng.member.dto.request.UserGroupRequest;
import com.ng.member.dto.request.UserToGroupRequest;
import com.ng.member.dto.response.GenericResponse;
import com.ng.member.dto.response.LoginResponse;
import com.ng.member.entity.UserGroup;
import com.ng.member.service.UserGroupService;
import com.ng.member.util.constant.Constants;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户组
 * @author niuguang
 * @date 18-1-18
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SuppressWarnings("Duplicates")
@RequestMapping("/member")
@Api(tags = "userGroup", description = "用户组")
public class UserGroupController {

    @Autowired
    private UserGroupService userGroupService;

    @PostMapping("/addUserGroup")
    @ApiOperation("创建用户组")
    public GenericResponse<LoginResponse> addUserGroup(@RequestBody UserGroupRequest request) {
        log.debug("创建用户组，参数：{}",request);
        GenericResponse resp;
        try{
            resp = userGroupService.addUserGroup(request);
        } catch (Exception e){
            e.printStackTrace();
            log.error("创建用户组，参数:{},异常:{}", request, e.getMessage());
            resp = GenericResponse.builder().code(Constants.CommonCode.FAIL).msg("创建用户组异常！").build();
        }
        log.debug("创建用户组，返回:{}", resp);
        return resp;
    }

    @GetMapping("/queryUserGroup")
    @ApiOperation("查询用户组")
    public GenericResponse<List<UserGroup>> queryUserGroup() {
        log.debug("查询用户组");
        GenericResponse resp;
        try{
            resp = userGroupService.queryUserGroup();
        } catch (Exception e){
            e.printStackTrace();
            log.error("查询用户组，异常:{}", e.getMessage());
            resp = GenericResponse.builder().code(Constants.CommonCode.FAIL).msg("查询用户组异常！").build();
        }
        log.debug("查询用户组，返回:{}", resp);
        return resp;
    }

    @GetMapping("/queryUserGroupByUserId")
    @ApiOperation("查询用户组")
    public GenericResponse<List<UserGroup>> queryUserGroupByUserId(@RequestParam("userId") Long userId) {
        log.debug("查询用户组");
        GenericResponse resp;
        try{
            resp = userGroupService.queryUserGroupByUserId(userId);
        } catch (Exception e){
            e.printStackTrace();
            log.error("查询用户组，异常:{}", e.getMessage());
            resp = GenericResponse.builder().code(Constants.CommonCode.FAIL).msg("查询用户组异常！").build();
        }
        log.debug("查询用户组，返回:{}", resp);
        return resp;
    }

    @GetMapping("/queryUserListByGroupId")
    @ApiOperation("查询用户组用户")
    public GenericResponse<List<Long>> queryUserListByGroupId(@RequestParam("userGroupIdList") String userGroupIdListStr) {
        log.debug("查询用户组用户");
        GenericResponse resp;
        try{
            List<Long> userGroupIdList = JSON.parseArray(userGroupIdListStr,Long.class);
            resp = userGroupService.queryUserListByGroupId(userGroupIdList);
        } catch (Exception e){
            e.printStackTrace();
            log.error("查询用户组用户，异常:{}", e.getMessage());
            resp = GenericResponse.builder().code(Constants.CommonCode.FAIL).msg("查询用户组用户异常！").build();
        }
        log.debug("查询用户组用户，返回:{}", resp);
        return resp;
    }

    @DeleteMapping("/delUserGroup")
    @ApiOperation("删除用户组")
    public GenericResponse delUserGroup(@RequestParam Long userGroupId) {
        log.debug("删除用户组");
        GenericResponse resp;
        try{
            resp = userGroupService.delUserGroup(userGroupId);
        } catch (Exception e){
            e.printStackTrace();
            log.error("删除用户组，参数：{}，异常:{}", userGroupId, e.getMessage());
            resp = GenericResponse.builder().code(Constants.CommonCode.FAIL).msg("删除用户组异常！").build();
        }
        log.debug("删除用户组，返回:{}", resp);
        return resp;
    }

    @PostMapping("/updateUserGroup")
    @ApiOperation("编辑用户组")
    public GenericResponse updateUserGroup(@RequestBody UserGroupRequest request) {
        log.debug("编辑用户组，参数：{}",request);
        GenericResponse resp;
        try{
            resp = userGroupService.updateUserGroup(request);
        } catch (Exception e){
            e.printStackTrace();
            log.error("编辑用户组，参数:{},异常:{}", request, e.getMessage());
            resp = GenericResponse.builder().code(Constants.CommonCode.FAIL).msg("编辑用户组异常！").build();
        }
        log.debug("编辑用户组，返回:{}", resp);
        return resp;
    }

    @GetMapping("/queryUserToGroup")
    @ApiOperation("查询用户组成员")
    public GenericResponse queryUserToGroup(@RequestParam Long userGroupId) {
        log.debug("查询用户组成员，参数：{}",userGroupId);
        GenericResponse resp;
        try{
            resp = userGroupService.queryUserToGroup(userGroupId);
        } catch (Exception e){
            e.printStackTrace();
            log.error("查询用户组成员，参数:{},异常:{}", userGroupId, e.getMessage());
            resp = GenericResponse.builder().code(Constants.CommonCode.FAIL).msg("查询用户组成员异常！").build();
        }
        log.debug("查询用户组成员，返回:{}", resp);
        return resp;
    }

    @GetMapping("/queryUserToGroupByUserName")
    @ApiOperation("根据用户名模糊查询用户组成员")
    public GenericResponse queryUserToGroup(@RequestParam Long groupId,@RequestParam String userName) {
        log.debug("根据用户名模糊查询用户组成员，参数：组ID{},用户名{}",groupId,userName);
        GenericResponse resp;
        try{
            resp = userGroupService.queryUserToGroupByUserName(groupId,userName);
        } catch (Exception e){
            e.printStackTrace();
            log.error("根据用户名模糊查询用户组成员，组ID{}，用户名{},异常:{}", groupId, userName, e.getMessage());
            resp = GenericResponse.builder().code(Constants.CommonCode.FAIL).msg("查询用户组成员异常！").build();
        }
        log.debug("根据用户名模糊查询用户组成员，返回:{}", resp);
        return resp;
    }

    @PostMapping("/addUserToGroup")
    @ApiOperation("用户组添加成员")
    public GenericResponse<LoginResponse> addUserToGroup(@RequestBody UserToGroupRequest request) {
        log.debug("用户组添加成员，参数：{}",request);
        GenericResponse resp;
        try{
            resp = userGroupService.addUserToGroup(request);
        } catch (Exception e){
            e.printStackTrace();
            log.error("用户组添加成员，参数:{},异常:{}", request, e.getMessage());
            resp = GenericResponse.builder().code(Constants.CommonCode.FAIL).msg("用户组添加成员异常！").build();
        }
        log.debug("用户组添加成员，返回:{}", resp);
        return resp;
    }

    @DeleteMapping("/delUserToGroup")
    @ApiOperation("删除用户组成员")
    public GenericResponse delUserGroupUser(@RequestParam Long groupId,@RequestParam Long userId) {
        log.debug("删除用户组成员，参数：组ID{}，用户名{}",groupId,userId);
        GenericResponse resp;
        try{
            resp = userGroupService.delUserToGroupUser(groupId,userId);
        } catch (Exception e){
            e.printStackTrace();
            log.error("删除用户组成员，参数：组ID{}，用户名{}，异常:{}", groupId,userId, e.getMessage());
            resp = GenericResponse.builder().code(Constants.CommonCode.FAIL).msg("删除用户组成员异常！").build();
        }
        log.debug("删除用户组成员，返回:{}", resp);
        return resp;
    }

    @PostMapping("/agreeToAddInGroup")
    @ApiOperation("同意加入用户组")
    public GenericResponse agreeToAddInGroup(@RequestParam Long groupId) {
        log.debug("同意加入用户组，参数：{}",groupId);
        GenericResponse resp;
        try{
            resp = userGroupService.agreeToAddInGroup(groupId);
        } catch (Exception e){
            e.printStackTrace();
            log.error("同意加入用户组，参数：{}，异常:{}", groupId, e.getMessage());
            resp = GenericResponse.builder().code(Constants.CommonCode.FAIL).msg("同意加入用户组异常！").build();
        }
        log.debug("同意加入用户组，返回:{}", resp);
        return resp;
    }
}
