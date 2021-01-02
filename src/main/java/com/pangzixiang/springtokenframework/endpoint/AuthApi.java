package com.pangzixiang.springtokenframework.endpoint;

import com.pangzixiang.springtokenframework.model.Response;
import com.pangzixiang.springtokenframework.model.ResponseUtil;
import com.pangzixiang.springtokenframework.security.model.User;
import com.pangzixiang.springtokenframework.security.service.LoginService;
import com.pangzixiang.springtokenframework.security.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Api(value = "Authentication", tags = "Authentication API")
public class AuthApi {

    @Autowired
    LoginService loginService;

    @Autowired
    TokenService tokenService;

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "get token by login")
    public Response login(
            @ApiParam(value = "Username", required = true, defaultValue = "user1") @RequestParam("username") String username,
            @ApiParam(value = "password", required = true, defaultValue = "user1234") @RequestParam("password") String password) {
        User user = loginService.getUser(username);
        if (loginService.check(user,password)){
            return ResponseUtil.success(tokenService.genToken(user));
        }
        else{
            return ResponseUtil.error(HttpStatus.NON_AUTHORITATIVE_INFORMATION.value(), "fail to login");
        }
    }

    @GetMapping(value = "/checkToken", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "check token")
    public Response checkToken(@RequestHeader("Authorization") String token) {
        if (tokenService.checkToken(token))
            return ResponseUtil.success();
        return ResponseUtil.error(HttpStatus.BAD_GATEWAY.value());
    }

    @ExceptionHandler
    public Response exception(Exception e){
        return ResponseUtil.error(HttpStatus.BAD_REQUEST.value(),e.getMessage());
    }
}
