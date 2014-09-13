package transporter;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailTranporter {

    private static final String HOST = "smtp.gmail.com";
    private static final String PORT = "587";
    private static String       USERNAME;
    private static String       PASSWORD;
    private String              toAdress;
    private String              subject;
    private String              message;
    private String[]            attachFilePaths;
    private File[]              attachFiles;

    public void sendEmailWithAttachments() throws AddressException, MessagingException {
        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", HOST);
        properties.put("mail.smtp.port", PORT);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.user", USERNAME);
        properties.put("mail.password", PASSWORD);

        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        };
        Session session = Session.getInstance(properties, auth);

        // creates a new e-mail message
        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(USERNAME));
        InternetAddress[] toAddresses = { new InternetAddress(toAdress) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());

        // creates message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(message, "text/html");

        // creates multi-part
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        // adds attachments
        if (attachFilePaths != null && attachFilePaths.length > 0) {
            for (String filePath : attachFilePaths) {
                MimeBodyPart attachPart = new MimeBodyPart();
                try {
                    attachPart.attachFile(filePath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                multipart.addBodyPart(attachPart);
            }
        } else {
            for (File filePath : attachFiles) {
                MimeBodyPart attachPart = new MimeBodyPart();
                try {
                    attachPart.attachFile(filePath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                multipart.addBodyPart(attachPart);
            }
        }

        // sets the multi-part as e-mail's content
        msg.setContent(multipart);

        // sends the e-mail
        Transport.send(msg);

    }

    public EmailTranporter(String toAdress, String subject, String message, String[] attachFilePaths, File[] attachFiles) {
        super();
        this.toAdress = toAdress;
        this.subject = subject;
        this.message = message;
        this.attachFilePaths = attachFilePaths;
        this.attachFiles = attachFiles;
    }

    public static String getUSERNAME() {
        return USERNAME;
    }

    public static void setUSERNAME(String uSERNAME) {
        USERNAME = uSERNAME;
    }

    public static String getPASSWORD() {
        return PASSWORD;
    }

    public static void setPASSWORD(String pASSWORD) {
        PASSWORD = pASSWORD;
    }

    public String getToAdress() {
        return toAdress;
    }

    public void setToAdress(String toAdress) {
        this.toAdress = toAdress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String[] getAttachFilePaths() {
        return attachFilePaths;
    }

    public void setAttachFilePaths(String[] attachFilePaths) {
        this.attachFilePaths = attachFilePaths;
    }

    public File[] getAttachFiles() {
        return attachFiles;
    }

    public void setAttachFiles(File[] attachFiles) {
        this.attachFiles = attachFiles;
    }

    public static String getHost() {
        return HOST;
    }

    public static String getPort() {
        return PORT;
    }
}
