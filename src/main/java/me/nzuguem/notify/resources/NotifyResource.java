package me.nzuguem.notify.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import me.nzuguem.notify.business.NotifyBusiness;
import me.nzuguem.notify.models.NotifyRequest;

@RestController
@RequestMapping("notify")
public class NotifyResource {
    
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
}
