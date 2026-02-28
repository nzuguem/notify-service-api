package me.nzuguem.notify.services.notifications.factory;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.stereotype.Component;

import dev.openfeature.sdk.Client;
import dev.openfeature.sdk.MutableContext;
import dev.openfeature.sdk.OpenFeatureAPI;
import dev.openfeature.sdk.Value;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Tracer;
import me.nzuguem.notify.configurations.feature_flag.OpenFeatureConfiguration;
import me.nzuguem.notify.models.Channel;
import me.nzuguem.notify.models.Type;
import me.nzuguem.notify.services.notifications.Sender;
import me.nzuguem.notify.services.notifications.sms.SmsSender;
import me.nzuguem.notify.services.notifications.smtp.SmtpSender;

@Component
public class SenderFactory {

    private final SmsSender smsSender;
    private final SmtpSender smtpSender;
    private final Client openFeatureClient;
    private final Tracer otelTracer;


    public SenderFactory(
        SmsSender smsSender,
        SmtpSender smtpSender,
        OpenFeatureAPI openFeatureAPI,
        Tracer otelTracer) {
            this.smsSender = smsSender;
            this.smtpSender = smtpSender;
            this.openFeatureClient = openFeatureAPI.getClient(OpenFeatureConfiguration.OFREP_PROVIDER);
            this.otelTracer = otelTracer;
    }

    public Sender getSender(Channel channel, Type type) {

        return switch (channel) {
            case SMS -> this.toggleRouterSms(type);
            case SMTP -> smtpSender;
        };
    }

    private Sender toggleRouterSms(Type type) {
        var requestAttrs = new HashMap<String, Value>();
        requestAttrs.put("type", Value.objectToValue(type.name()));
        var requestCtx = new MutableContext(UUID.randomUUID().toString(), requestAttrs);

        var span = this.otelTracer.spanBuilder("get-flags")
            .setSpanKind(SpanKind.CLIENT)
            .setAttribute("notification.type", type.name())
            .startSpan();
        try(var _ = span.makeCurrent()){
            var on = this.openFeatureClient.getBooleanValue("send-sms", false, requestCtx);

            if(on) {
                return this.smsSender;
            }
        } finally {
            span.end();
        }

        throw new UnsupportedOperationException("Unimplemented method 'send' for SMS");
    }

}
