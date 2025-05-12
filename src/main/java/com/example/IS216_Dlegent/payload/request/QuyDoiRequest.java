package com.example.IS216_Dlegent.payload.request;

public class QuyDoiRequest {
    private Long userId;

    public QuyDoiRequest() {
    }

    public QuyDoiRequest(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
