package com.ng.member.controller;

import com.ng.member.dto.response.GenericResponse;
import com.ng.member.entity.User;
import com.ng.member.entity.UserDetail;
import com.ng.member.service.UserService;
import com.ng.member.util.constant.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户信息
 * @author niuguang
 * @date 18-1-17
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SuppressWarnings("Duplicates")
@RequestMapping("/member")
@Api(tags = "user", description = "用户相关操作")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/queryUserInfo")
    @ApiOperation("查询用户信息")
    public GenericResponse<UserDetail> queryUserInfo(@RequestParam String userName) {
        log.debug("查询用户信息，参数：{}",userName);
        GenericResponse resp;
        try{
            resp = userService.queryUserInfo(userName);
        } catch (Exception e){
            e.printStackTrace();
            log.error("查询用户信息，参数：{}，异常：{}", e.getMessage());
            resp = GenericResponse.builder().code(Constants.CommonCode.FAIL).msg("查询用户信息异常！").build();
        }
        return resp;
    }

    @GetMapping("/queryUserInfoList")
    @ApiOperation("查询用户信息")
    public GenericResponse<List<User>> queryUserInfoList(@RequestParam List<Long> userIdList) {
        log.debug("查询用户信息，参数：{}",userIdList);
        GenericResponse resp;
        try{
            resp = userService.queryUserInfoList(userIdList);
        } catch (Exception e){
            e.printStackTrace();
            log.error("查询用户信息，参数：{}，异常：{}", e.getMessage());
            resp = GenericResponse.builder().code(Constants.CommonCode.FAIL).msg("查询用户信息异常！").build();
        }
        return resp;
    }

    @GetMapping("/queryCurrentUserInfo")
    @ApiOperation("查询用户信息")
    public GenericResponse<UserDetail> queryCurrentUserInfo() {
        log.debug("查询当前用户信息");
        GenericResponse resp;
        try{
            resp = userService.queryCurrentUserInfo();
        } catch (Exception e){
            e.printStackTrace();
            log.error("查询当前用户信息，异常：{}", e.getMessage());
            resp = GenericResponse.builder().code(Constants.CommonCode.FAIL).msg("查询当前用户信息异常！").build();
        }
        return resp;
    }
}
