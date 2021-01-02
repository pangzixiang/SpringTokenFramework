package com.pangzixiang.springtokenframework.security.service;

import com.pangzixiang.springtokenframework.security.Dao.SecurityDaoImpl;
import com.pangzixiang.springtokenframework.security.encryption.BCryptPasswordEncoder;
import com.pangzixiang.springtokenframework.security.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private SecurityDaoImpl securityDao;

    public User getUser(String username) {
        return securityDao.getUserByName(username);
    }

    public boolean check(User user, String password) {
        if (user == null) {
            return false;
        }else {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(-1,null);

            return bCryptPasswordEncoder.matches(password,user.getPassword());
        }
    }

}
