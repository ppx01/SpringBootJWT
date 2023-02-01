package com.jwt.dao;

import com.jwt.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author jingdeyue
 * @date 2023/1/31 18:03
 */
@Mapper
public interface UserDao {
    User login(@Param("user") User user);
}
