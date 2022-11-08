package com.itck.gateway.config;


public class MyConfig {
    private String key;
    private String value;

    public MyConfig(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public MyConfig() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
