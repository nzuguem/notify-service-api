package me.nzuguem.notify.configurations.feature_flag;

import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.openfeature.contrib.hooks.otel.MetricsHook;
import dev.openfeature.contrib.hooks.otel.TracesHook;
import dev.openfeature.contrib.providers.gofeatureflag.GoFeatureFlagProvider;
import dev.openfeature.contrib.providers.gofeatureflag.GoFeatureFlagProviderOptions;
import dev.openfeature.contrib.providers.gofeatureflag.exception.InvalidOptions;
import dev.openfeature.contrib.providers.ofrep.OfrepProvider;
import dev.openfeature.contrib.providers.ofrep.OfrepProviderOptions;
import dev.openfeature.sdk.FeatureProvider;
import dev.openfeature.sdk.ImmutableContext;
import dev.openfeature.sdk.OpenFeatureAPI;
import dev.openfeature.sdk.Value;
import dev.openfeature.sdk.exceptions.OpenFeatureError;
import io.opentelemetry.api.OpenTelemetry;
import me.nzuguem.notify.configurations.feature_flag.hooks.CorporateHook;

@Configuration
public class OpenFeatureConfiguration {

    public static final String GOFF_PROVIDER = "go-feature-flag-provider";
    public static final String OFREP_PROVIDER = "ofrep-provider";

    @Bean(name=GOFF_PROVIDER)
    public FeatureProvider goFeatureFlagProvider(
        @org.springframework.beans.factory.annotation.Value("${notify.goff.url}") String goffUrl
    ) throws InvalidOptions {

        var goFeatureFlagProviderOptions = GoFeatureFlagProviderOptions
                .builder()
                .endpoint(goffUrl)
                .timeout(1000)
                .flagChangePollingIntervalMs(5000L)
                .build();

        return  new GoFeatureFlagProvider(goFeatureFlagProviderOptions);
    }

    @Bean(name=OFREP_PROVIDER)
    public FeatureProvider ofrepProvider(
        @org.springframework.beans.factory.annotation.Value("${notify.goff.url}") String goffUrl
    ) throws InvalidOptions {

        var ofrepProviderOptions = OfrepProviderOptions
                .builder()
                .baseUrl(goffUrl)
                .connectTimeout(Duration.ofSeconds(10))
                .executor(Executors.newVirtualThreadPerTaskExecutor())
                .build();

        return  OfrepProvider.constructProvider(ofrepProviderOptions);
    }

    @Bean
    public OpenFeatureAPI openFeatureAPI(
        OpenTelemetry opentelemetry,
        @Qualifier(GOFF_PROVIDER) FeatureProvider goFeatureFlagProvider,
        @Qualifier(OFREP_PROVIDER) FeatureProvider ofrepProvider
    ) {
        var openFeatureAPI = OpenFeatureAPI.getInstance();

        try {

            openFeatureAPI.setProviderAndWait(GOFF_PROVIDER, goFeatureFlagProvider);
            openFeatureAPI.setProviderAndWait(OFREP_PROVIDER, ofrepProvider);

            var apiAttrs = new HashMap<String, Value>();
            apiAttrs.put("app", Value.objectToValue("notify-service-api"));
            var apiCtx = new ImmutableContext(apiAttrs);
            openFeatureAPI.setEvaluationContext(apiCtx);

        } catch (OpenFeatureError e) {
            throw new RuntimeException("Failed to set OpenFeature provider", e);
        }

        openFeatureAPI.addHooks(
            this.buildMetricsHook(opentelemetry),
            this.buildTracesHook(),
            CorporateHook.getInstance()
        );

        return openFeatureAPI;
    }

    private TracesHook buildTracesHook() {
        return new TracesHook();
    }

    private MetricsHook buildMetricsHook(OpenTelemetry opentelemetry) {
        return new MetricsHook(opentelemetry);
    }

}
