package me.nzuguem.notify.models.context;

import java.util.List;
import java.util.Map;

public class OrderConfirmationContext extends  Context {

    public OrderConfirmationContext() {
        this.variables = List.of("orderNumber", "trackingLink");
    }

    @Override
    public boolean validateInternal(Map<String, String> values) {
        return true;
    }
}