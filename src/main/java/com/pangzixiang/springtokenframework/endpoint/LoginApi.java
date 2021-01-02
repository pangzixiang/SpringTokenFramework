package com.pangzixiang.springtokenframework.endpoint;

import com.pangzixiang.springtokenframework.model.Response;
import com.pangzixiang.springtokenframework.model.ResponseUtil;
import com.pangzixiang.springtokenframework.security.model.User;
import com.pangzixiang.springtokenframework.security.workflow.LoginService;
import com.pangzixiang.springtokenframework.security.workflow.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Api(value = "Authentication", tags = "Authentication API")
public class LoginApi {

    @Autowired
    LoginService loginService;

    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    @ApiOperation(value = "get token by login")
    public Response login(
            @ApiParam(value = "Username", required = true) @RequestParam("username") String username,
            @ApiParam(value = "password", required = true) @RequestParam("password") String password) {
        User user = loginService.getUser(username);
        if (loginService.check(user,password)){
            return ResponseUtil.success(tokenService.genToken(user));
        }
        else{
            return ResponseUtil.error(HttpStatus.NON_AUTHORITATIVE_INFORMATION.value(), "fail to login");
        }
    }

    @ExceptionHandler
    public Response exception(Exception e){
        return ResponseUtil.error(HttpStatus.BAD_REQUEST.value(),e.getMessage());
    }
}
