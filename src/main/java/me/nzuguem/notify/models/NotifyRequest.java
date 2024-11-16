package me.nzuguem.notify.models;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import me.nzuguem.notify.exceptions.ContextNoMatchTypeException;

public record NotifyRequest(
    @NotBlank(message = "customerId is mandatory")
    String customerId,
    @NotNull(message = "notificationType is mandatory")
    Type notificationType,
    Map<String, String> context,
    @NotNull(message = "channel is mandatory")
    Channel channel) {  

        public void validateContext() {

            var contextKeysType = new HashSet<>(this.notificationType.getContextKeys());

            if (contextKeysType.isEmpty()) {
                return;
            }

            if (
                Objects.isNull(this.context) ||
                !Objects.equals(contextKeysType, this.context.keySet())
                ) {
                throw new ContextNoMatchTypeException("Parameters %s are mandatory for the notification type %s".formatted(contextKeysType, notificationType));
            }

            this.context.values()
                .stream()
                .filter(String::isBlank)
                .findAny()
                .ifPresent(__ -> {throw new ContextNoMatchTypeException("Parameters %s are mandatory for the notification type %s".formatted(contextKeysType, notificationType));});
        }
}
