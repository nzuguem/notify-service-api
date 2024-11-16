package me.nzuguem.notify.models;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.Map;

public enum Type {
    
    ORDER_CONFIRMATION("Thank you for your order", List.of("orderNumber", "trackingLink")), 
    PROMOTION("Get -${percentage}% off your favourite items", List.of("deadline", "promotionLink", "percentage")), 
    CART_ABANDONMENT("Your basket is waiting for you", List.of("products", "cartLink"));

    private String subject;
    private List<String> contextKeys;

    Type(String subject, List<String> contextKeys) {
        this.subject = subject;
        this.contextKeys = contextKeys;
    }

    private List<String> getContextKeysInSubject() {

        var contextKeys = new ArrayList<String>();

        var regex = "\\$\\{(.*?)\\}";
        var pattern = Pattern.compile(regex);
        var matcher = pattern.matcher(this.subject);

        while (matcher.find()) {
            String mot = matcher.group(1).trim();
            contextKeys.add(mot);
        }

        return contextKeys;
    }

    public String getSubject(Map<String, String> context) {

        var subject = this.subject;

        for (var key : this.getContextKeysInSubject()) {
            subject = subject.replace("${%s}".formatted(key), context.get(key));
        }

        return subject;
    }

    public List<String> getContextKeys() {
        return this.contextKeys;
    }
}
