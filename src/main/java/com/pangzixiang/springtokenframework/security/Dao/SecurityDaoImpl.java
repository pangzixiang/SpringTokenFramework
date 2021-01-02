package com.pangzixiang.springtokenframework.security.Dao;

import com.pangzixiang.springtokenframework.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SecurityDaoImpl implements SecurityDao{

    @Autowired
    SecurityDao securityDao;

    @Override
    public User getUserByName(String username) {
        return this.securityDao.getUserByName(username);
    }
}
