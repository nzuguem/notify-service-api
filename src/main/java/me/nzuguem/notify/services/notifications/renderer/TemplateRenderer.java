package me.nzuguem.notify.services.notifications.renderer;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.output.StringOutput;

@Component
public class TemplateRenderer {

    private final TemplateEngine templateEngine;

    public  TemplateRenderer(
        @Value("${notify.jte.packageName}") String jtePackageName) {
        this.templateEngine = TemplateEngine.createPrecompiled(null, ContentType.Html, null, jtePackageName);
    }

    public String render(Map<String, String> context, String templateName) {

        var output = new StringOutput();
        this.templateEngine.render(templateName, Collections.<String, Object>unmodifiableMap(context), output);

        return output.toString();
    }
}
