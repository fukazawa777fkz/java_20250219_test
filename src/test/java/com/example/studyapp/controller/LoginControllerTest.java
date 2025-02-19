package com.example.studyapp.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.studyapp.entity.UserEntity;
import com.example.studyapp.service.LoginService;

@WebMvcTest(LoginController.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginService loginService;

    private UserEntity testUser;

    @BeforeEach
    void setUp() {
        testUser = new UserEntity();
        testUser.setUserName("testUser");
        testUser.setUserPassword("password");
    }

    @Test
    void viewLogin_正常系() throws Exception {
        mockMvc.perform(get("/login"))
               .andExpect(status().isOk())
               .andExpect(view().name("login"))
               .andExpect(model().attributeExists("loginForm"));
    }

    @Test
    void login_正常系_ログイン成功() throws Exception {
        when(loginService.findByUserName("testUser")).thenReturn(testUser);

        mockMvc.perform(post("/login")
               .param("userName", "testUser")
               .param("userPassword", "password"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/taskList/" + testUser.getUserId()));
        
        verify(loginService, times(1)).findByUserName("testUser");
    }

    @Test
    void login_異常系_ユーザー名未入力() throws Exception {
        mockMvc.perform(post("/login")
               .param("userName", "")
               .param("userPassword", "password"))
               .andExpect(status().isOk())
               .andExpect(view().name("login"))
               .andExpect(model().hasErrors());
        
        verify(loginService, never()).findByUserName(any());
    }

    @Test
    void login_異常系_パスワード不一致() throws Exception {
        when(loginService.findByUserName("testUser")).thenReturn(testUser);

        mockMvc.perform(post("/login")
               .param("userName", "testUser")
               .param("userPassword", "wrongPassword"))
               .andExpect(status().isOk())
               .andExpect(view().name("login"))
               .andExpect(model().attribute("errorMessage", "⚠︎ ユーザー名またはパスワードに誤りがあります。"));
        
        verify(loginService, times(1)).findByUserName("testUser");
    }

    @Test
    void login_異常系_パスワード不一致2() throws Exception {
        when(loginService.findByUserName("testUser")).thenReturn(null);

        mockMvc.perform(post("/login")
               .param("userName", "testUser")
               .param("userPassword", "wrongPassword"))
               .andExpect(status().isOk())
               .andExpect(view().name("login"))
               .andExpect(model().attribute("errorMessage", "⚠︎ ユーザー名またはパスワードに誤りがあります。"));
        
        verify(loginService, times(1)).findByUserName("testUser");
    }

    @Test
    void login_異常系_ユーザー名空白() throws Exception {
        mockMvc.perform(post("/login")
               .param("userName", "   ")  // スペースのみ
               .param("userPassword", "password"))
               .andExpect(status().isOk())
               .andExpect(view().name("login"))
               .andExpect(model().attributeHasFieldErrors("loginForm", "userName"))
               .andExpect(model().attributeHasFieldErrorCode("loginForm", "userName", "NotBlank"))
               .andExpect(model().attribute("loginForm", hasProperty("userName", is("   "))));
    }

    @Test
    void login_異常系_パスワード空白() throws Exception {
        mockMvc.perform(post("/login")
               .param("userName", "testUser")
               .param("userPassword", "   "))  // スペースのみ
               .andExpect(status().isOk())
               .andExpect(view().name("login"))
               .andExpect(model().attributeHasFieldErrors("loginForm", "userPassword"))
               .andExpect(model().attributeHasFieldErrorCode("loginForm", "userPassword", "NotBlank"))
               .andExpect(model().attribute("loginForm", hasProperty("userPassword", is("   "))));
    }    
}