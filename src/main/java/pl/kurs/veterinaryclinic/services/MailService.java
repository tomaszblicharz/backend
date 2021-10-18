package pl.kurs.veterinaryclinic.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pl.kurs.veterinaryclinic.exceptions.VisitException;

@Service
public class MailService {

    private final static String TITLE_EMAIL = "Registration Visit";
    private final static String BODY_EMAIL = "Hi, there's a token for active your visit: http://localhost:8080/visit/confirm/%s";
    private static final boolean IS_HTML_CONTENT = true;
    private JavaMailSender javaMailSender;

    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMailWithActivationToken(String email, String token) throws VisitException {
        String body = String.format(BODY_EMAIL, token);
        sendMail(email, TITLE_EMAIL, body);
    }

    private void sendMail(String to, String subject, String text) throws VisitException {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(text, IS_HTML_CONTENT);
            javaMailSender.send(mimeMessage);
        } catch (MailException | MessagingException e) {
            throw new VisitException("No internet connection");
        }
    }

}