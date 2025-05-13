package com.example.IS216_Dlegent.payload.respsonse;

public class LoginResponse {
    private String message;
    private String token;
    private Long roleId;

    public LoginResponse(String message, String token, Long roleId) {
        this.message = message;
        this.token = token;
        this.roleId = roleId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
