package com.springmvc.app.beans;

import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.lookup.StrLookup;

@Plugin(name = "contextLookUp", category = StrLookup.CATEGORY)
public class contextLookUp implements StrLookup {

    @Override
    public String lookup(String key) {
        if(key.equals("requestId")) {
            if(!ThreadContext.containsKey(key)) {
                return "got lookup";
            }
            else {
                return ThreadContext.get(key);
            }
        }
        return ThreadContext.get(key);
    }

    @Override
    public String lookup(LogEvent event, String key) {
        return lookup(key);
    }
}
