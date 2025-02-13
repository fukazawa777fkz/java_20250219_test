package com.example.studyapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.studyapp.dao.RegisterDao;
import com.example.studyapp.entity.UserEntity;
import com.example.studyapp.form.RegisterForm;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private RegisterDao registerDao;

    @Override
    public boolean checkUserName(String userName) {
        if (registerDao.existByUserName(userName) != 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void registUserInfo(RegisterForm registerForm) {
        registerDao.insertUserInfo(registerForm);
    }

    @Override
    public UserEntity findByUserName(String userName) {
        return registerDao.findByUserName(userName);
    }

}
