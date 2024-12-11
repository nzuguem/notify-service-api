package me.nzuguem.notify.configurations;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
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
    GenericContainer<?> mailpitContainer(DynamicPropertyRegistry propertyRegistry) {

        var mailpitContainer = new GenericContainer<>("axllent/mailpit")
                .withNetwork(NETWORK)
                .withNetworkAliases("mailpit")
                .withExposedPorts(1025, 8025)
                .withEnv("MP_MAX_MESSAGES", "5000")
                .withEnv("MP_SMTP_AUTH_ACCEPT_ANY", "1")
                .withEnv("MP_SMTP_AUTH_ALLOW_INSECURE", "1");

        propertyRegistry.add("spring.mail.host", () -> "localhost");
        propertyRegistry.add("spring.mail.port", () -> mailpitContainer.getMappedPort(1025));

        return mailpitContainer;
    }

}
