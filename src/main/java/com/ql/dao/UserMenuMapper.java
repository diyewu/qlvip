package com.ql.dao;

import com.ql.entity.UserMenu;

public interface UserMenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserMenu record);

    int insertSelective(UserMenu record);

    UserMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserMenu record);

    int updateByPrimaryKey(UserMenu record);
}