// ***** EMAIL UTIL *****
package com.example.pokeschool.util;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class Email {

    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String EMAIL_ORIGEM = "miniInter2026@gmail.com";
    private static final String SENHA_APP = "aervxcapckkuknor";
    public static boolean enviarTokenRecuperacao(String emailDestino, String token) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", SMTP_HOST);
            props.put("mail.smtp.port", SMTP_PORT);

            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(EMAIL_ORIGEM, SENHA_APP);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_ORIGEM));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDestino));
            message.setSubject("Código de Recuperação de Senha - Remember");

            String corpoEmail =
                    "<!DOCTYPE html>" +
                            "<html>" +
                            "<head>" +
                            "  <style>" +
                            "    body { font-family: Arial, sans-serif; background-color: #f4f4f4; }" +
                            "    .container { max-width: 500px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; }" +
                            "    .token { font-size: 32px; letter-spacing: 5px; color: #333; font-weight: bold; text-align: center; margin: 20px 0; }" +
                            "    .aviso { color: #666; font-size: 14px; }" +
                            "  </style>" +
                            "</head>" +
                            "<body>" +
                            "  <div class='container'>" +
                            "    <h2>Recuperação de Senha</h2>" +
                            "    <p>Você solicitou a recuperação de senha.</p>" +
                            "    <p>Seu código de verificação é:</p>" +
                            "    <div class='token'>" + token + "</div>" +
                            "    <p class='aviso'>Este código expira em 2 horas.</p>" +
                            "    <p class='aviso'>Se você não solicitou isso, ignore este email.</p>" +
                            "  </div>" +
                            "</body>" +
                            "</html>";

            message.setContent(corpoEmail, "text/html; charset=utf-8");
            Transport.send(message);

            System.out.println("Email enviado para: " + emailDestino);
            return true;

        } catch (MessagingException e) {
            System.out.println("Erro ao enviar email!");
            e.printStackTrace();
            return false;
        }
    }
}