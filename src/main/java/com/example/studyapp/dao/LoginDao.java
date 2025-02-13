package com.example.studyapp.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.studyapp.entity.UserEntity;

@Mapper
public interface LoginDao {

    UserEntity findByUserName(String userName);

}
