package com.example.studyapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.studyapp.entity.UserEntity;
import com.example.studyapp.form.RegisterForm;
import com.example.studyapp.service.RegisterService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @GetMapping("/register")
    public String viewRegister(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "register";
    }

    @PostMapping("/register")
    public String register(Model model, @Valid RegisterForm registerForm, BindingResult bindingResult, HttpSession session) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("registerForm", registerForm);
            return "register";
        }

        // ユーザー名の重複チェック
        if (registerService.checkUserName(registerForm.getUserName())) {
            model.addAttribute("registerForm", registerForm);
            model.addAttribute("errorMessage", "⚠️このユーザー名は既に使われています。");
            return "register";
        } else {
            registerService.registUserInfo(registerForm);
            UserEntity userEntity = registerService.findByUserName(registerForm.getUserName());
            if (userEntity != null && userEntity.getUserPassword().equals(registerForm.getUserPassword())) {
                session.setAttribute("userEntity", userEntity);
                return "redirect:/taskList/" + userEntity.getUserId();
            }
            model.addAttribute("errorMessage", "ログインに失敗しました。");
            return "login";
        }
    }

}
