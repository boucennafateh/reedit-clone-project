package org.fate7.redditproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fate7.redditproject.exceptions.SpringRedditException;
import org.fate7.redditproject.model.NotificationEmail;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private final MailContextBuilder mailContextBuilder;

    public void sendMail(NotificationEmail notificationEmail) throws SpringRedditException {

        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("clone-reddit@gmail.com");
            mimeMessageHelper.setTo(notificationEmail.getRecipient());
            mimeMessageHelper.setSubject(notificationEmail.getSubject());
            mimeMessageHelper.setText(mailContextBuilder.build(notificationEmail.getBody()));

        };

        try {
            mailSender.send(mimeMessagePreparator);
            log.info("Activation email sent !");
        } catch (MailException e) {
            log.error(e.getMessage());
            throw new SpringRedditException("Exception occurred when sending email to " + notificationEmail.getRecipient());
        }

    }
}
