package me.nzuguem.notify.configurations.feature_flag.hooks;

import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.openfeature.sdk.EvaluationContext;
import dev.openfeature.sdk.FlagEvaluationDetails;
import dev.openfeature.sdk.Hook;
import dev.openfeature.sdk.HookContext;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import me.nzuguem.notify.configurations.ApplicationContextProvider;

public class CorporateHook implements Hook<Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CorporateHook.class);

    private final Tracer otelTracer;

    private Span otelSpan;

    public CorporateHook() {
        var openTelemetry = ApplicationContextProvider.getBean(OpenTelemetry.class);
        this.otelTracer = openTelemetry.getTracer("feature-flag-tracer");
    }

    @Override
    public Optional<EvaluationContext> before(HookContext<Object> ctx, Map<String, Object> hints) {

        LOGGER.info("Before Evaluation... {}", ctx.getFlagKey());

        this.otelSpan = this.otelTracer.spanBuilder("feature-glag-span-" + ctx.getFlagKey())
                            .startSpan();

        return Optional.empty();
    }

    @Override
    public void after(HookContext<Object> ctx, FlagEvaluationDetails<Object> details, Map<String, Object> hints) {
        LOGGER.info("After Evaluation... {}", ctx.getFlagKey());
    }

    @Override
    public void error(HookContext<Object> ctx, Exception error, Map<String, Object> hints) {
        LOGGER.error("Error Evaluation... {}", ctx.getFlagKey(), error);
    }

    @Override
    public void finallyAfter(HookContext<Object> ctx, FlagEvaluationDetails<Object> details, Map<String, Object> hints) {
        LOGGER.info("Finally After Evaluation... {}", ctx.getFlagKey());

        this.otelSpan.end();
    }

    public static CorporateHook get() {
        return new CorporateHook();
    }

}
