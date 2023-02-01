package com.jwt.service;

import com.jwt.entity.User;

/**
 * @author jingdeyue
 * @date 2023/1/31 18:02
 */
public interface UserService {
    User login(User user);
}
