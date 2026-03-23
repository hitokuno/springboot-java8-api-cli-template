package com.example.app.common.service;

import com.example.app.common.domain.User;
import com.example.app.primarydb.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    public User findById(String id) {
        return userMapper.findById(id);
    }
}
