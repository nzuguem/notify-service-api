package me.nzuguem.notify.configurations;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id="env-infos")
public class CorporateEndpoint {

    @Value("${notify.env}")
    private String env;

    @ReadOperation
    public Map<String, String> getEnvInfos() {
        return Map.of("env", this.env);
    }
    
}
