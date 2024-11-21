package me.nzuguem.notify.models.context;

import java.util.Map;

public class NoopContext extends Context {

    @Override
    public boolean validateInternal(Map<String, String> values) {
        return true;
    }
    
}