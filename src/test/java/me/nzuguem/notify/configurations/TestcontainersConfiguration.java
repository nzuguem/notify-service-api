package me.nzuguem.notify.configurations;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistrar;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

    private static final Network NETWORK = Network.newNetwork();

    @Bean
    TestcontainersLogsBeanPostProcessor containerLogsBeanPostProcessor() {
        return new TestcontainersLogsBeanPostProcessor();
    }

    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> postgreSQLContainer() {

        return new PostgreSQLContainer<>("postgres:16.5-alpine")
                .withDatabaseName("customers")
                .withUsername("customer")
                .withPassword("customer")
                .withNetwork(NETWORK)
                .withCopyFileToContainer(
                        MountableFile.forHostPath("./external-dependencies/database/init/customer.sql"),
                        "/docker-entrypoint-initdb.d/1-customer-init.sql"
                );
    }

    @Bean
    @ServiceConnection
    RabbitMQContainer rabbitMQContainer() {

        return new RabbitMQContainer(DockerImageName.parse("rabbitmq:management-alpine"))
                .withNetwork(NETWORK);
    }

    @Bean
    GenericContainer<?> mailpitContainer() {

        return new GenericContainer<>("axllent/mailpit")
                .withNetwork(NETWORK)
                .withNetworkAliases("mailpit")
                .withExposedPorts(1025, 8025)
                .withEnv("MP_MAX_MESSAGES", "5000")
                .withEnv("MP_SMTP_AUTH_ACCEPT_ANY", "1")
                .withEnv("MP_SMTP_AUTH_ALLOW_INSECURE", "1");
    }

    // https://github.com/spring-projects/spring-framework/issues/33501
    // https://docs.spring.io/spring-framework/reference/testing/testcontext-framework/ctx-management/dynamic-property-sources.html

    // Container beans are injected as arguments, because containers would have to be started before accessing their properties.
    // And their lifecycle is managed by Spring Boot TestContainers and the ApplicationContext.
    @Bean
	DynamicPropertyRegistrar propertiesRegistrar(
        GenericContainer<?> mailpitContainer
        ) {
		return registry -> {
            // Spring Mail
            registry.add("spring.mail.host", () -> "localhost");
            registry.add("spring.mail.port", () -> mailpitContainer.getMappedPort(1025));
        };
	}

}
