package me.nzuguem.notify.models;

import java.util.Locale;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("customer")
public record Customer(@Id String id, String lastName, String firstName, String email, String phoneNumber) {

    public String fullName() {
        return "%s %s".formatted(firstName, lastName.toUpperCase(Locale.ROOT));
    }
}
