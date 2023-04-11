package pl.kedziorek.medicalcentreapplication.service;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    void sendMail(SimpleMailMessage msg);

    SimpleMailMessage prepareInfoMailAboutCreatedAccount(
            String email,
            String name,
            String password,
            String createdBy);
}
