package me.nzuguem.notify.models;

import java.util.Locale;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("customer")
public record Customer(@Id String id, String lastName, String firstName, String email) {

    public String fullName() {
        return String.format("%s %s", firstName, lastName.toUpperCase(Locale.ROOT));
    }
}
