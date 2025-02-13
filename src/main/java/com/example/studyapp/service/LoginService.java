package com.example.studyapp.service;

import com.example.studyapp.entity.UserEntity;

public interface LoginService {

    UserEntity findByUserName(String userName);

}
