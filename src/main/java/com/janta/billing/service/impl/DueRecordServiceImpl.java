package com.janta.billing.service.impl;

import com.janta.billing.configuration.EmailService;
import com.janta.billing.dto.BillGenerateDto;
import com.janta.billing.dto.MailServiceDTO;
import com.janta.billing.entity.EmailTemplates;
import com.janta.billing.exception.SystemException;
import com.janta.billing.repository.EmailTemplateRepository;
import com.janta.billing.service.DueRecordService;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DueRecordServiceImpl implements DueRecordService {
    private static Logger logger = LoggerFactory.getLogger(DueRecordServiceImpl.class);
    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailTemplateRepository emailTemplateRepository;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public String sendDueNotification(MailServiceDTO mailServiceDTO)  {
        String to = mailServiceDTO.getTo();
        String subject = mailServiceDTO.getSubject();
        String templateName = mailServiceDTO.getTemplateName();
        Map<String,Object> model = mailServiceDTO.getModel();
        // Find template keys
        Optional<EmailTemplates> emailMetaData = emailTemplateRepository.findByTemplateName(templateName);
    logger.error(mailServiceDTO.toString());
        // Prepare email template
        emailMetaData.ifPresentOrElse((e)->{
           String htmlBody = createDueHtmlEmailBody(e.getDynamicKeys(),model,e.getTemplateName());
            try {
                emailService.sendMimeEmail(to,subject,htmlBody);
            } catch (MessagingException ex) {
                throw new RuntimeException(ex);
            }

        }, ()->{
            throw new SystemException("There is no email template");
        });
        logger.error("Mail send succesfully");
        // send mail using service
        return "successfully mail sent";
    }

    public String createDueHtmlEmailBody(String keys,Map<String,Object> content,String templateName) {
        Context context = new Context();
        String[] keyArray = keys.split(",");
        for(String key : keyArray) {
            context.setVariable(key,content.get(key));
        }

        return templateEngine.process(templateName, context);
    }

}
