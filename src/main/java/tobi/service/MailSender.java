package tobi.service;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.MessagingException;

public interface MailSender {
    void send(SimpleMailMessage simpleMailMessage) throws MailException;
    void send(SimpleMailMessage[] simpleMailMessages) throws MailException;
}
