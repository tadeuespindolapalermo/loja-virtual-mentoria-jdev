package br.com.tadeudeveloper.lojavirtual.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Service
public class ServiceSendEmail {

    private String userName = "tadeupalermoti@gmail.com";
    private String password = "ctthaietitegsejc";

    @Async
    public void enviarEmailHtml(String assunto, String textoMensagem, String emailDestino) throws MessagingException, UnsupportedEncodingException {
        Properties properties = new Properties();

        // Alex porque o envio dele parou de funcionar
        properties.put("mail.smtp.ssl.trust", "*");

        // Tadeu Sonar Lint por causa do "mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"
        properties.put("mail.smtp.ssl.checkserveridentity", "true");

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls", "true"); // Alex usou false
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        });

        session.setDebug(true);

        Address[] toUser = InternetAddress.parse(emailDestino);

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(userName, "Tadeu CobraDev", "UTF-8"));
        message.setRecipients(Message.RecipientType.TO, toUser);
        message.setSubject(assunto);
        message.setContent(textoMensagem, "text/html; charset=utf-8");

        Transport.send(message);
    }
}