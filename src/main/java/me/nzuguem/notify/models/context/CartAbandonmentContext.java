package me.nzuguem.notify.models.context;

import java.util.List;
import java.util.Map;

public class CartAbandonmentContext extends Context {

    public CartAbandonmentContext() {
        this.variables = List.of("products", "cartLink");
    }

    @Override
    public boolean validateInternal(Map<String, String> values) {
        return true;
    }
    
}