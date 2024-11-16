package me.nzuguem.notify;

import org.springframework.boot.SpringApplication;

import me.nzuguem.notify.configurations.TestcontainersConfiguration;


public class TestNotifyServiceApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(NotifyServiceApiApplication::main)
            .with(TestcontainersConfiguration.class)
            .run(args);
	}

}
