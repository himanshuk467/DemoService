package com.springmvc.app.beans;

import java.io.Serializable;
import java.util.Map;

public class JsonMessage implements Serializable {
    private String content;
    private Map<String, Object> map;

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public String getContent() {
        return content;
    }

    public  JsonMessage() {

    }

    @Override
    public String toString() {
        return String.format("content : " + content +  "\n" + "map : " + map);
    }
}
