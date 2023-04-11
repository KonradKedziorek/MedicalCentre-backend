package pl.kedziorek.medicalcentreapplication.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.kedziorek.medicalcentreapplication.service.EmailService;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;

    @Override
    public void sendMail(SimpleMailMessage msg) {
        if (msg != null) {
            javaMailSender.send(msg);
            log.info("Mail has been send");
        } else {
            log.info("Failed to send mail");
        }
    }

    @Override
    public SimpleMailMessage prepareInfoMailAboutCreatedAccount(
            String email,
            String name,
            String password,
            String createdBy) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);

        msg.setFrom("medicalCentre@outlook.com");

        msg.setSubject("Medical centre account creating");

        msg.setText("Welcome in Medical Centre Application!\n\n" +
                "Your account has been successfully created!. \n\n" +
                "Your username is your current email address: " + email + ". \n" +
                "Your password: " + password + " \n\n" +
                "In case some troubles with logging in, contact our staff (for example on email: " + createdBy + ").\n\n" +
                "With regards, \n" +
                "Medical Centre");

        return msg;
    }
}
