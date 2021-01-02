package com.pangzixiang.springtokenframework.endpoint;

import com.pangzixiang.springtokenframework.annotation.NeedAuth;
import com.pangzixiang.springtokenframework.model.Response;
import com.pangzixiang.springtokenframework.model.ResponseUtil;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("api")
@Api(value = "TEST-API", tags = "TEST API")
public class TestApi {

    @GetMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
    @NeedAuth
    public Response test(){
        return ResponseUtil.success();
    }

    @ExceptionHandler
    public Response exception(Exception e){
        return ResponseUtil.error(HttpStatus.BAD_REQUEST.value(),e.getMessage());
    }

}
