package com.client.ws.rasmooplus.integration.impl;

import com.client.ws.rasmooplus.integration.MailIntegration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class MailIntegrationImpl implements MailIntegration {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void send(String mailTo, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(mailTo);
        simpleMailMessage.setSubject("Acesso Liberado!");
        simpleMailMessage.setText("Login :"+mailTo+" Senha: alunorasmoo");
        javaMailSender.send(simpleMailMessage);
    }
}
