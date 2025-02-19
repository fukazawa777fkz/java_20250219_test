package com.example.studyapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.studyapp.dao.RegisterDao;
import com.example.studyapp.entity.UserEntity;
import com.example.studyapp.entity.UserInfo;
import com.example.studyapp.entity.UserInfoExample;
import com.example.studyapp.form.RegisterForm;
import com.example.studyapp.mapper.UserInfoMapper;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private RegisterDao registerDao;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public boolean checkUserName(String userName) {
        // if (registerDao.existByUserName(userName) != 0) {
        //     return true;
        // } else {
        //     return false;
        // }

        UserInfoExample example = new UserInfoExample();
        example.createCriteria().andUserNameEqualTo(userName);
        if (userInfoMapper.countByExample(example) != 0) {
            return true;
        }
        return false;
        
    }

    @Override
    public void registUserInfo(RegisterForm registerForm) {

        UserInfo record = new UserInfo();
        record.setUserName(registerForm.getUserName());
        record.setUserPassword(registerForm.getUserPassword());
        userInfoMapper.insert(record);
        // registerDao.insertUserInfo(registerForm);
    }

    @Override
    public UserEntity findByUserName(String userName) {
        return registerDao.findByUserName(userName);
    }

}
