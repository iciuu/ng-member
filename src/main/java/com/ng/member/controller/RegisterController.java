package com.ng.member.controller;

import com.ng.member.dto.request.RegisterRequest;
import com.ng.member.dto.response.GenericResponse;
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
 * 注册
 * @author niuguang
 * @date 18-1-17
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SuppressWarnings("Duplicates")
@RequestMapping("/member")
@Api(tags = "register", description = "注册")
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ApiOperation("注册")
    public GenericResponse register(@RequestBody RegisterRequest request) {
        log.debug("用户注册，参数:{}", request);
        GenericResponse resp;
        try{
            resp = userService.register(request);
            // 注册成功登录
            if(resp.getCode() == Constants.CommonCode.SUCCESS){
                resp.setData(userService.registerToLogin(request));
            }
        } catch (Exception e){
            e.printStackTrace();
            log.error("用户注册异常，参数:{},异常:{}", request, e.getMessage());
            resp = GenericResponse.builder().code(Constants.CommonCode.FAIL).msg("用户注册异常！").build();
        }
        log.debug("用户注册，返回:{}", resp);
        return resp;
    }
}
