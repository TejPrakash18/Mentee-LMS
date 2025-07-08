package com.tej.smart_lms.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ForgotPasswordRequest {
    private String email;
    private String newPassword;
}
