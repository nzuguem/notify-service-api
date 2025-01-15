package me.nzuguem.notify.configurations.telemetry;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import io.opentelemetry.instrumentation.logback.appender.v1_0.OpenTelemetryAppender;
import me.nzuguem.notify.configurations.ApplicationContextProvider;

// https://docs.spring.io/spring-boot/reference/actuator/loggers.html
// https://github.com/open-telemetry/opentelemetry-java-instrumentation/tree/main/instrumentation/logback/logback-appender-1.0/library
@Component
public class OpenTelemetryAppenderInitializer implements InitializingBean {

	@Override
	public void afterPropertiesSet() {
		OpenTelemetryAppender.install(ApplicationContextProvider.getOpenTelemetryBean());
	}

}
