package me.nzuguem.notify.configurations.feature_flag;

import java.util.HashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.openfeature.contrib.hooks.otel.MetricsHook;
import dev.openfeature.contrib.hooks.otel.TracesHook;
import dev.openfeature.contrib.providers.gofeatureflag.GoFeatureFlagProvider;
import dev.openfeature.contrib.providers.gofeatureflag.GoFeatureFlagProviderOptions;
import dev.openfeature.contrib.providers.gofeatureflag.exception.InvalidOptions;
import dev.openfeature.sdk.FeatureProvider;
import dev.openfeature.sdk.ImmutableContext;
import dev.openfeature.sdk.OpenFeatureAPI;
import dev.openfeature.sdk.Value;
import dev.openfeature.sdk.exceptions.OpenFeatureError;
import io.opentelemetry.api.OpenTelemetry;

@Configuration
public class OpenFeatureConfiguration {

    private final OpenTelemetry opentelemetry;

    public OpenFeatureConfiguration(OpenTelemetry opentelemetry) {
        this.opentelemetry = opentelemetry;
    }

    @Bean
    public FeatureProvider goFeatureFlagProvider(
        @org.springframework.beans.factory.annotation.Value("${notify.goff.url}") String goffUrl
    ) throws InvalidOptions {

        var goFeatureFlagProviderOptions = GoFeatureFlagProviderOptions
                .builder()
                .endpoint(goffUrl)
                .timeout(1000)
                .build();

        return  new GoFeatureFlagProvider(goFeatureFlagProviderOptions);
    }

    @Bean
    public OpenFeatureAPI openFeatureAPI(
        FeatureProvider goFeatureFlagProvider
    ) {
        var openFeatureAPI = OpenFeatureAPI.getInstance();

        try {

            openFeatureAPI.setProviderAndWait(goFeatureFlagProvider);

            var apiAttrs = new HashMap<String, Value>();
            apiAttrs.put("app", Value.objectToValue("notify-service-api"));
            var apiCtx = new ImmutableContext(apiAttrs);
            openFeatureAPI.setEvaluationContext(apiCtx);

        } catch (OpenFeatureError e) {
            throw new RuntimeException("Failed to set OpenFeature provider", e);
        }

        openFeatureAPI.addHooks(this.buildMetricsHook(), this.buildTracesHook());

        return openFeatureAPI;
    }

    private TracesHook buildTracesHook() {
        return new TracesHook();
    }

    private MetricsHook buildMetricsHook() {
        return new MetricsHook(this.opentelemetry);
    }

}
