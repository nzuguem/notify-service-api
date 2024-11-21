package me.nzuguem.notify.models.context;

import java.util.List;
import java.util.Map;

public class PromotionContext extends Context {

    public PromotionContext() {
        this.variables = List.of("deadline", "promotionLink", "percentage");
    }

    @Override
    public boolean validateInternal(Map<String, String> values) {
        return true;
    }

}