package me.nzuguem.notify.models.context;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class Context {

    protected  List<String> variables = List.of();

    public boolean validate(Map<String, String> values) {

        if (Objects.isNull(this.variables()) || this.variables().isEmpty()) {
            return true;
        }
        
        if (Objects.isNull(values)) {
            return false;           
        }

        if (!Objects.equals(Set.of(this.variables()), values.keySet())) {
            return false;
        }

        return !values.values()
                .stream()
                .anyMatch(String::isBlank) && this.validateInternal(values);
    }

    public static PromotionContext promotion() {
        return new PromotionContext();
    }

    public static CartAbandonmentContext cartAbandonment() {
        return new CartAbandonmentContext();
    }

    public static OrderConfirmationContext orderConfirmation() {
        return new OrderConfirmationContext();
    }

    public static NoopContext noop() {
        return new NoopContext();
    }

    public List<String> variables() {
        return this.variables;
    }

    protected abstract boolean validateInternal(Map<String, String> values);
    
}