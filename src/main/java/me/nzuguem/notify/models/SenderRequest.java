package me.nzuguem.notify.models;

import java.util.Map;
import java.util.Objects;

public record SenderRequest(Customer customer, Type type, Channel channel, Map<String, String> context) {

    @Override
    public Map<String, String> context() {
        return !Objects.isNull(this.context) ? this.context : Map.of();
    }
}
