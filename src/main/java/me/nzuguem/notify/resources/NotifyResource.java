package me.nzuguem.notify.resources;

import java.time.Duration;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.lang.Thread;
import me.nzuguem.notify.business.NotifyBusiness;
import me.nzuguem.notify.models.NotifyRequest;

@RestController
@RequestMapping("notify")
public class NotifyResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotifyResource.class);

    private final NotifyBusiness notifyBusiness;

    public NotifyResource(NotifyBusiness notifyBusiness) {
        this.notifyBusiness = notifyBusiness;
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody NotifyRequest notifyRequest) {

        notifyRequest.validateContext();

        this.notifyBusiness.notify(notifyRequest);

        return ResponseEntity.accepted()
            .build();
	}

    @PostMapping("slow/{seconds}")
    public ResponseEntity<Void> createSlow(
        @Valid @RequestBody NotifyRequest notifyRequest,
        @PathVariable @NotNull @Min(0) Long seconds) throws InterruptedException {

        notifyRequest.validateContext();

        this.notifyBusiness.notifyWithTransactionalAnnotation(notifyRequest);

        this.slow(seconds);

        return ResponseEntity.accepted()
            .build();
	}

    private void slow(Long seconds) throws InterruptedException {

        if (!Objects.isNull(seconds)) {
            LOGGER.info("⏳ Begin slow processing...");
            Thread.sleep(Duration.ofSeconds(seconds));
            LOGGER.info("I was slow 🦥, i waited {} seconds", seconds);
        }

    }
}
