package com.pangzixiang.springtokenframework.security.workflow;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.pangzixiang.springtokenframework.security.Dao.SecurityDaoImpl;
import com.pangzixiang.springtokenframework.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Autowired
    SecurityDaoImpl securityDao;

    private String TOKEN_PREFIX = "Bearer ";

    public String genToken(User user){

        String token = TOKEN_PREFIX + JWT.create()
                .withAudience(user.getUsername())
                .withExpiresAt(new Date())
                .sign(Algorithm.HMAC256(user.getUuid()));
        return token;
    }

}
