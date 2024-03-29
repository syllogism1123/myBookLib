package com.xin.bookbackend.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequest (
        @NotBlank
        @Size(min = 3, max = 32)
        String oldPassword,
        @NotBlank
        @Size(min = 3, max = 32)
        String newPassword) {

}
