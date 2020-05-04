package com.springmvc.app.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class JsonMessage implements Serializable {
    private String content;
    private Map<String, String> map;
    private Object object;

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public Object getObject() {
        return object;
    }

    public String getContent() {
        return content;
    }

    public  JsonMessage() {
        map = new HashMap<>();
    }

    public JsonMessage(String content, Object object) {
        this.content = content;
        this.object = object;
        map = new HashMap<>();
    }
}
