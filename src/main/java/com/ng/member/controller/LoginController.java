package com.ng.member.controller;

import com.ng.member.dto.request.LoginRequest;
import com.ng.member.dto.response.GenericResponse;
import com.ng.member.dto.response.LoginResponse;
import com.ng.member.service.UserService;
import com.ng.member.util.constant.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录
 * @author niuguang
 * @date 18-1-17
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SuppressWarnings("Duplicates")
@RequestMapping("/member")
@Api(tags = "login", description = "登录")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ApiOperation("登录")
    public GenericResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        log.debug("用户登录，参数：{}",loginRequest);
        GenericResponse resp;
        try{
            resp = userService.login(loginRequest);
        } catch (Exception e){
            e.printStackTrace();
            log.error("用户登录，参数：{}，异常：{}", e.getMessage());
            resp = GenericResponse.builder().code(Constants.CommonCode.FAIL).msg("用户登录异常！").build();
        }
        return resp;
    }
}
