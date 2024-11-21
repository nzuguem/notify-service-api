package me.nzuguem.notify.models;

import java.util.Map;

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

        public void validate() {
            if (!this.notificationType.validate(this.context)) {
                throw new ContextNoMatchTypeException("Parameters %s are mandatory for the notification type %s".formatted(this.notificationType.getContextKeys(), notificationType));
            }
        }
     }
