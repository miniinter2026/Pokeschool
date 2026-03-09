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
            message.setSubject("Código de Recuperação de Senha - PokeSchool");

            String corpoEmail =
                    "<!DOCTYPE html>" +
                            "<html>" +
                            "<head>" +
                            "  <meta charset='UTF-8'>" +
                            "  <style>" +
                            "    body {" +
                            "      font-family: Arial, sans-serif;" +
                            "      background-color: #f4f4f4;" +
                            "      margin: 0;" +
                            "      padding: 0;" +
                            "    }" +

                            "    .header {" +
                            "      background-color: #b30000;" +
                            "      color: white;" +
                            "      text-align: center;" +
                            "      padding: 20px;" +
                            "      font-size: 22px;" +
                            "      font-weight: bold;" +
                            "    }" +

                            "    .container {" +
                            "      max-width: 500px;" +
                            "      margin: 30px auto;" +
                            "      background: white;" +
                            "      padding: 30px;" +
                            "      border-radius: 10px;" +
                            "      box-shadow: 0 4px 10px rgba(0,0,0,0.1);" +
                            "      text-align: center;" +
                            "    }" +

                            "    h2 {" +
                            "      color: #111;" +
                            "      margin-bottom: 10px;" +
                            "    }" +

                            "    p {" +
                            "      color: #444;" +
                            "      font-size: 15px;" +
                            "    }" +

                            "    .token {" +
                            "      font-size: 34px;" +
                            "      letter-spacing: 6px;" +
                            "      background-color: #111;" +
                            "      color: white;" +
                            "      padding: 15px;" +
                            "      border-radius: 8px;" +
                            "      margin: 25px auto;" +
                            "      width: fit-content;" +
                            "      font-weight: bold;" +
                            "    }" +

                            "    .aviso {" +
                            "      color: #777;" +
                            "      font-size: 13px;" +
                            "      margin-top: 15px;" +
                            "    }" +

                            "    .footer {" +
                            "      text-align: center;" +
                            "      font-size: 12px;" +
                            "      color: #999;" +
                            "      margin-top: 20px;" +
                            "    }" +
                            "  </style>" +
                            "</head>" +

                            "<body>" +

                            "  <div class='header'>" +
                            "    Plataforma Escolar" +
                            "  </div>" +

                            "  <div class='container'>" +
                            "    <h2>Recuperação de Senha</h2>" +

                            "    <p>Recebemos uma solicitação para redefinir sua senha.</p>" +
                            "    <p>Utilize o código abaixo para continuar:</p>" +

                            "    <div class='token'>" + token + "</div>" +

                            "    <p class='aviso'>Este código expira em <b>2 horas</b>.</p>" +
                            "    <p class='aviso'>Se você não solicitou a recuperação de senha, ignore este e-mail.</p>" +
                            "  </div>" +

                            "  <div class='footer'>" +
                            "    © Plataforma Escolar - Sistema acadêmico" +
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