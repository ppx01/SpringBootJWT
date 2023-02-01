package com.jwt.service.impl;

import com.jwt.dao.UserDao;
import com.jwt.entity.User;
import com.jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jingdeyue
 * @date 2023/1/31 18:03
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User login(User user) {
        User login = userDao.login(user);
        if(login != null)
            return login;
        throw new RuntimeException("登录失败");
    }
}

