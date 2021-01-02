package com.pangzixiang.springtokenframework.security.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.pangzixiang.springtokenframework.annotation.NeedAuth;
import com.pangzixiang.springtokenframework.security.model.User;
import com.pangzixiang.springtokenframework.security.workflow.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        String token = request.getHeader("Authorization");
        if (!(handler instanceof HandlerMethod))
            return true;

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        if (method.isAnnotationPresent(NeedAuth.class)) {
            NeedAuth tokenAuth = method.getAnnotation(NeedAuth.class);
            if (tokenAuth.required()) {
                if (token == null)
                    throw new RuntimeException("Please Log in");
                if (!token.contains("Bearer "))
                    throw new RuntimeException("Please add prefix 'Bearer '");
                token = token.split(" ")[1];
                String username;
                try {
                    username = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException e) {
                    throw new RuntimeException("invalid Token");
                }

                User user = loginService.getUser(username);
                if (user == null)
                    throw new RuntimeException("Please register");

                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getUuid())).acceptExpiresAt(300).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    throw new RuntimeException("invalid Token");
                }
                return true;
            }
        } else {
            return true;
        }


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
