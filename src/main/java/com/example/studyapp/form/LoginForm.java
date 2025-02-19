package com.example.studyapp.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginForm {

    @NotBlank
    private String userName;

    @NotBlank
    private String userPassword;

}
