package org.fate7.redditproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class MailContextBuilder {

    private final TemplateEngine templateEngine;

    public String build(String msg){

        Context context = new Context();
        context.setVariable("message", msg);
        return templateEngine.process("mailTemplate", context);
    }
}
