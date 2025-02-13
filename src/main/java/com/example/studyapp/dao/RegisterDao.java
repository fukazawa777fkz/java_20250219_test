package com.example.studyapp.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.studyapp.entity.UserEntity;
import com.example.studyapp.form.RegisterForm;

@Mapper
public interface RegisterDao {

    int existByUserName(String userName);

    void insertUserInfo(RegisterForm registerForm);

    UserEntity findByUserName(String userName);

}
