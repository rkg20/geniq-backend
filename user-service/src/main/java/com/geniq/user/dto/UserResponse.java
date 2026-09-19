package com.geniq.user.dto;

import com.geniq.user.model.User;

public class UserResponse {
    public Long id;
    public String email;
    public String name;
    public String role;

    public UserResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.role = user.getRole();
    }
}
