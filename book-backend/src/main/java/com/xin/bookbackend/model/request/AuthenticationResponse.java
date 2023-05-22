package com.xin.bookbackend.model.request;

import lombok.Builder;

@Builder
public record AuthenticationResponse(String token) {
}
