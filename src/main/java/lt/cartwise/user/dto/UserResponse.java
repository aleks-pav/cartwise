package lt.cartwise.user.dto;

import lt.cartwise.enums.Role;

public record UserResponse(Long id, String email, String name, Role role, boolean isActive) {

}
