package com.example.studyapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.studyapp.dao.LoginDao;
import com.example.studyapp.entity.UserEntity;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginDao loginDao;

    @Override
    public UserEntity findByUserName(String userName) {
        return loginDao.findByUserName(userName);
    }

}
