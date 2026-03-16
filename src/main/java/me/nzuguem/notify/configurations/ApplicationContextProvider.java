package me.nzuguem.notify.configurations;


import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullUnmarked;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import io.opentelemetry.api.OpenTelemetry;


@Component
@NullUnmarked
public  class ApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        ApplicationContextProvider.applicationContext = applicationContext;
    }


    public static <T> T getBean(Class<T> beanClass) {
        return ApplicationContextProvider.applicationContext.getBean(beanClass);
    }

    public static Object getBean(String beanName) {
        return ApplicationContextProvider.applicationContext.getBean(beanName);
    }

    public static OpenTelemetry getOpenTelemetryBean() {
        return ApplicationContextProvider.getBean(OpenTelemetry.class);
    }
}
