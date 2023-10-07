package com.adera.commonTypes;

import java.util.UUID;

public class Config {
    private String userId;

    @Override
    public String toString() {
        return "Config{" +
                "userId='" + userId + '\'' +
                '}';
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
