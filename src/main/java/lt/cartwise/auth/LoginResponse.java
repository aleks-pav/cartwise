package lt.cartwise.auth;

import lt.cartwise.user.dto.UserDto;

public record LoginResponse(String token, String refreshToken, UserDto user){}
