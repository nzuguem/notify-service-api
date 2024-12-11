package me.nzuguem.notify.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public record SenderRequest(Customer customer, Type type, Channel channel, Map<String, String> context) {

    public SenderRequest {
        context = !Objects.isNull(context) ? context : new HashMap<>();
        context.put("fullName", customer.fullName());
        context = Map.copyOf(context);
    }
}
