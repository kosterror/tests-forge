package ru.kosterror.testsforge.mailservice.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import ru.kosterror.testsforge.commonmodel.mail.SendMailDto;
import ru.kosterror.testsforge.mailservice.entity.MailStatus;
import ru.kosterror.testsforge.mailservice.mapper.MailDetailsMapper;
import ru.kosterror.testsforge.mailservice.repository.MailDetailsRepository;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final SpringTemplateEngine templateEngine;
    private final JavaMailSender mailSender;
    private final MailDetailsRepository mailDetailsRepository;
    private final MailDetailsMapper mailDetailsMapper;

    public void sendEmail(SendMailDto sendMailDto) {
        try {
            sendEmailInternal(sendMailDto);
            var mailDetails = mailDetailsMapper.toEntity(sendMailDto);
            mailDetails.setDate(LocalDateTime.now());
            mailDetails.setStatus(MailStatus.SENT);
            mailDetailsRepository.insert(mailDetails);
            log.info("Saved successfully mail details {}", mailDetails);
        } catch (Exception exception) {
            log.error("Failed to send mail {}", sendMailDto, exception);
            var mailDetails = mailDetailsMapper.toEntity(sendMailDto);
            mailDetails.setDate(LocalDateTime.now());
            mailDetails.setStatus(MailStatus.ERROR);
            mailDetailsRepository.insert(mailDetails);
            log.info("Saved not successfully mail details {}", mailDetails);
        }
    }

    private void sendEmailInternal(SendMailDto sendMailDto) throws MessagingException {
        var htmlBody = getHtml(sendMailDto);

        var mimeMessage = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(mimeMessage);

        if (sendMailDto.receivers() != null) {
            messageHelper.setTo(sendMailDto.receivers().toArray(new String[0]));
        }

        if (sendMailDto.copies() != null) {
            messageHelper.setCc(sendMailDto.copies().toArray(new String[0]));
        }

        if (sendMailDto.hiddenCopies() != null) {
            messageHelper.setBcc(sendMailDto.hiddenCopies().toArray(new String[0]));
        }

        messageHelper.setSubject(sendMailDto.subject());
        messageHelper.setText(htmlBody, true);

        mailSender.send(mimeMessage);
        log.info("Mail {} sent", sendMailDto);
    }

    private String getHtml(SendMailDto sendMailDto) {
        var context = new Context();
        context.setVariable("body", sendMailDto.body());
        return templateEngine.process("default-template", context);
    }
}
