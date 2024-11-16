package me.nzuguem.notify.resources;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("slow")
public class SlowResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(SlowResource.class);

    @GetMapping("{seconds:[0-9]+}")
    public String slowResource(
        @PathVariable
        @NotNull
        @Min(0) Long seconds) throws InterruptedException {

        LOGGER.info("⏳ Begin slow processing...");

        Thread.currentThread()
            .sleep(Duration.ofSeconds(seconds));

        return "I was slow 🦥, i waited %d seconds".formatted(seconds);
    }

}
