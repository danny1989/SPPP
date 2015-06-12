/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package www.fitcoders.com.util;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author CJAVAPERU
 */
public class EmailSenderService {

    private Properties properties;

    private String password = "PASSS****************";
    private String titleMessage = "CLIENTE POTENCIAL";
    private String contentHTML = "<h1>HELLO</h1>";
    private String[] emails = {"info@cjavaperu.com"};
    private Session session;

    private void init() {
        properties = new Properties();
        properties.put("mail.smtp.host", "HOST***********");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", 465);
        properties.put("mail.smtp.starttls.enable", false);
        properties.put("mail.smtp.mail.sender", "EMAIL CREDENCIAL********************");
        properties.put("mail.smtp.user", "EMAIL CREDENCIAL*********************");
        properties.put("mail.smtp.auth", "true");

        session = Session.getDefaultInstance(properties);
    }

    /**
     * Correos a los que serán enviado el mensaje.
     *
     * @param emails
     */
    public void setEmails(String[] emails) {
        this.emails = emails;
    }

    private Address[] getAddresses() throws AddressException {
        Address[] add = new Address[this.emails.length];
        for (int i = 0; i < add.length; i++) {
            add[i] = new InternetAddress(this.emails[i]);
        }
        return add;
    }

    public void setTitleMessage(String titleMessage) {
        this.titleMessage = titleMessage;
    }
     
    public void setContentHTML(String contentHTML) {
        this.contentHTML = contentHTML;
    }

    public void sendEmail() {
        System.out.println("Enviando Correo");
        init();
        System.out.println("Inicializado");
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress((String) properties.get("mail.smtp.mail.sender")));
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress("dcondor.cjavaperu@gmail.com"));  
            message.addRecipients(Message.RecipientType.TO, getAddresses());
            message.setSubject(titleMessage);
            message.setContent(contentHTML, "text/html");
            Transport t = session.getTransport("smtp");
            t.connect((String) properties.get("mail.smtp.user"), password);
            t.sendMessage(message, message.getAllRecipients());
            t.close();
            System.out.println("FinalizandoEnivo");
        } catch (MessagingException me) {
            System.out.println("Error");
            me.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {

        EmailSenderService e = new EmailSenderService();
        String []em = {"dcondor.cjavaperu@gmail.com"};
        e.setEmails(em);
        e.sendEmail();

    }

}