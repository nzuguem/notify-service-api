package me.nzuguem.notify.configurations.feature_flag.hooks;

import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.openfeature.sdk.EvaluationContext;
import dev.openfeature.sdk.FlagEvaluationDetails;
import dev.openfeature.sdk.Hook;
import dev.openfeature.sdk.HookContext;

public class CorporateHook implements Hook<Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CorporateHook.class);

    @Override
    public Optional<EvaluationContext> before(HookContext<Object> ctx, Map<String, Object> hints) {

        LOGGER.info("Before Evaluation... {}", ctx.getFlagKey());

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
    public void finallyAfter(HookContext<Object> ctx, Map<String, Object> hints) {
        LOGGER.info("Finally After Evaluation... {}", ctx.getFlagKey());
    }

    public static CorporateHook getInstance() {
        return new CorporateHook();
    }

}
