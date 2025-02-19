package com.example.studyapp.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterForm {

    @NotBlank
    private String userName;

    @NotBlank
    private String userPassword;

}
