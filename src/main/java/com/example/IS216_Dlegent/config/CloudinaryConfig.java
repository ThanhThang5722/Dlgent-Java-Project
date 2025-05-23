package com.example.IS216_Dlegent.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cloudinary")
public class CloudinaryConfig {
    private String cloudName;
    private String apiKey;
    private String apiSecret;

    // Getter & Setter
    public String getCloudName() {
        return cloudName;
    }
    public void setCloudName(String cloudName) {
        this.cloudName = cloudName;
    }
    public String getApiKey() {
        return apiKey;
    }
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
    public String getApiSecret() {
        return apiSecret;
    }
    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }
}