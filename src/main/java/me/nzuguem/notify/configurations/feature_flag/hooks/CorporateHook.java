package me.nzuguem.notify.configurations.feature_flag.hooks;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.openfeature.sdk.Hook;
import dev.openfeature.sdk.HookContext;

public class CorporateHook implements Hook<Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CorporateHook.class);

    @Override
    public void error(HookContext<Object> ctx, Exception error, Map<String, Object> hints) {
        LOGGER.error("Error Evaluation... {}", ctx.getFlagKey(), error);
    }

    public static CorporateHook getInstance() {
        return new CorporateHook();
    }

}
