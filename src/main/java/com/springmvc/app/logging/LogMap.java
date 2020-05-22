package com.springmvc.app.logging;

import java.util.HashMap;
import java.util.Map;

public class LogMap {
    private Map<String, Object> map;

    private LogMap(Map<String, Object> map) {
        this.map = map;
    }


    public Map<String, Object> getmap() {
        return this.map;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static class Builder {

        private Map<String, Object> map;

        public Builder() {
            this.map = new HashMap<>();
        }

        public Map<String, Object> getMap() {
            return this.map;
        }

        public LogMap.Builder withKey(String key, Object value) {
            validateKey(key);
            this.map.put(key, value);
            return this;
        }

        public void validateKey(String key) {
            if(key.matches(".*[-%;<>&#_,.].*")) {
                throw new IllegalArgumentException("Key cannot contain special character" + key);
            }
        }

        public LogMap build() {
            LogMap logMap = new LogMap(this.map);
            return logMap;
        }
    }
}
