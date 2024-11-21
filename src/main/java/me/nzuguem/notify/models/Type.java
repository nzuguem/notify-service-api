package me.nzuguem.notify.models;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.Map;

import me.nzuguem.notify.models.context.Context;

public enum Type {
    
    ORDER_CONFIRMATION("Thank you for your order", Context.orderConfirmation()), 
    PROMOTION("Get -${percentage}% off your favourite items", Context.promotion()), 
    CART_ABANDONMENT("Your basket is waiting for you", Context.cartAbandonment());

    private String subject;
    private Context context;

    Type(String subject, Context context) {
        this.subject = subject;
        this.context = context;
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

    public boolean validate(Map<String, String> context) {
        return this.context.validate(context);
    }

    public List<String> getContextKeys() {
        return this.context.variables();
    }
}
