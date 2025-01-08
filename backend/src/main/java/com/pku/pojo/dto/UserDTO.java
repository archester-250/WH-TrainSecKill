package com.pku.pojo.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String password;
    private String email;
    private String mobile;
    private boolean isAdminLogin = false;
}
