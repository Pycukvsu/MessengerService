package com.relex.security.verivication.sender;

import com.relex.models.User;
import com.relex.security.verivication.sender.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class DefaultEmailService implements EmailSender {

    private final JavaMailSenderImpl emailSender;

    @Autowired
    public DefaultEmailService(JavaMailSenderImpl emailSender) {
        this.emailSender = emailSender;
    }

    @Value("${site.base.url.https}")
    private String baseURL;

    @Override
    public void sendSimpleEmail(User user, String token) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = null;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(user.getMail());
            mimeMessageHelper.setFrom("kha4etlov.ruslan@yandex.ru");
            mimeMessageHelper.setSubject("subject");
            mimeMessageHelper.setText(buildVerificationUrl(baseURL, token));
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        emailSender.send(mimeMessage);
    }

    public String buildVerificationUrl(String baseURL, String token) {
        return UriComponentsBuilder.fromHttpUrl(baseURL)
                .path("/registration/verify").queryParam("token", token).toUriString();
    }
}
