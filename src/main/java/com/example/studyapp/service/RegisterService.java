package com.example.studyapp.service;

import com.example.studyapp.entity.UserEntity;
import com.example.studyapp.form.RegisterForm;

public interface RegisterService {

    // ユーザー名の重複チェック
    boolean checkUserName(String userName);

    // ユーザーの登録
    void registUserInfo(RegisterForm registerForm);

    // ログイン処理
    UserEntity findByUserName(String userName);

}
