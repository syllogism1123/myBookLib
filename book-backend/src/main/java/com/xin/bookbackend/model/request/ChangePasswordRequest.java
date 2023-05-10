package com.xin.bookbackend.model.request;

public record ChangePasswordRequest (String oldPassword, String newPassword) {

}
