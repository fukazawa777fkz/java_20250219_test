package com.example.studyapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.studyapp.entity.UserEntity;
import com.example.studyapp.form.LoginForm;
import com.example.studyapp.service.LoginService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    // ログイン画面を表示
    @GetMapping("/login")
    public String viewLogin(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    // ログイン処理
    @PostMapping("/login")
    public String login(Model model, @Valid LoginForm loginForm, BindingResult bindingResult, HttpSession session) {
        // バリデーションエラーがある場合はログイン画面に戻る
        if (bindingResult.hasErrors()) {
            model.addAttribute("loginForm", loginForm);
            return "login";
        }

        UserEntity userEntity = loginService.findByUserName(loginForm.getUserName());
        if (userEntity != null && userEntity.getUserPassword().equals(loginForm.getUserPassword())) {
            session.setAttribute("userEntity", userEntity);
            return "redirect:/taskList/" + userEntity.getUserId();
        } else {
            model.addAttribute("loginForm", loginForm);
            model.addAttribute("errorMessage", "⚠︎ ユーザー名またはパスワードに誤りがあります。");
            return "login";
        }
    }

}
