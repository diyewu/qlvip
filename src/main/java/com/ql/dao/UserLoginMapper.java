package com.ql.dao;

import com.ql.entity.UserLogin;

public interface UserLoginMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserLogin record);

    int insertSelective(UserLogin record);

    UserLogin selectByPrimaryKey(String id);

    UserLogin checkUserExist(UserLogin userLogin);
    
    int updateByPrimaryKeySelective(UserLogin record);

    int updateByPrimaryKey(UserLogin record);
}