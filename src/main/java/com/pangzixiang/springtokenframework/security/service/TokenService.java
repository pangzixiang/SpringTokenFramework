package com.pangzixiang.springtokenframework.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.pangzixiang.springtokenframework.security.Dao.SecurityDaoImpl;
import com.pangzixiang.springtokenframework.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Autowired
    SecurityDaoImpl securityDao;

    @Autowired
    LoginService loginService;

    private String TOKEN_PREFIX = "Bearer ";

    public String genToken(User user){

        String token = TOKEN_PREFIX + JWT.create()
                .withAudience(user.getUsername())
                .withExpiresAt(new Date())
                .sign(Algorithm.HMAC256(user.getUuid()));
        return token;
    }

    public Boolean checkToken(String token) {
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

}
