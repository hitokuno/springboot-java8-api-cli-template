package com.example.app.primarydb.mapper;

import com.example.app.common.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User findById(String id);
}
