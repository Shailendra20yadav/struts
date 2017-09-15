package test;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;  
import javax.mail.*;  
import javax.mail.internet.*;

public class MailUtility {
	
	public static String USER_NAME="m20yadav@gmail.com";
	public static String PASSWORD="skkyaadav";
	private static String toAddress ="shailendra20yadav@gmail.com";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//sendMailByGmail();
		
		 String subject = "New email with attachments";
	     String message = "I have some attachments for you.";
		String[] attachFiles = new String[3];
        attachFiles[0] = "C:/shailendra/amex/photo.jpg";
        attachFiles[1] = "C:/shailendra/amex/offerLetterFirstPage.pdf";
        attachFiles[2] = "C:/shailendra/amex/Shailendra kumar-Resume_Java.docx";
 
        try {
            sendEmailWithAttachments(toAddress,subject, message, attachFiles);
            System.out.println("Email sent.");
        } catch (Exception ex) {
            System.out.println("Could not send email.");
            ex.printStackTrace();
        }
		

	}
	public static Session getSession(String host, String port,final String userName, final String password){
		Session session = null;
			Properties props = new Properties();  
		  props.put("mail.smtp.host", host);  
		  props.put("mail.smtp.socketFactory.port", port);  
		  props.put("mail.smtp.socketFactory.class",  "javax.net.ssl.SSLSocketFactory");  
		  props.put("mail.smtp.auth", "true");  
		  props.put("mail.smtp.port", port); 
		  props.put("mail.smtp.starttls.enable", "true");
		  props.put("mail.user", userName);
		  props.put("mail.password", password);
		// creates a new session with an authenticator
	        Authenticator auth = new Authenticator() {
	            public PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(userName, password);
	            }
	        };
	         session = Session.getInstance(props, auth);
		return session;
	}
	public static Session getSession(final String userName, final String password){
		return getSession("smtp.gmail.com", "465", userName, password);
	}
	public static void sendMailByGmail(){
		
		  
		  //Get the session object  
		  Properties props = new Properties();  
		  props.put("mail.smtp.host", "smtp.gmail.com");  
		  props.put("mail.smtp.socketFactory.port", "465");  
		  props.put("mail.smtp.socketFactory.class",  
		            "javax.net.ssl.SSLSocketFactory");  
		  props.put("mail.smtp.auth", "true");  
		  props.put("mail.smtp.port", "465");  
		   
		  Session session = Session.getDefaultInstance(props,  
		   new javax.mail.Authenticator() {  
		   protected PasswordAuthentication getPasswordAuthentication() {  
		   return new PasswordAuthentication(USER_NAME,PASSWORD);//change accordingly  
		   }  
		  });  
		   
		  //compose message  
		  try {  
		   MimeMessage message = new MimeMessage(session);  
		   message.setFrom(new InternetAddress(USER_NAME));//change accordingly  
		   message.addRecipient(Message.RecipientType.TO,new InternetAddress(toAddress));  
		   message.setSubject("Hello");  
		   message.setText("Testing.......");
		     
		   //send message  
		   Transport.send(message);  
		  
		   System.out.println("message sent successfully");  
		   
		  } catch (MessagingException e) {
			  e.printStackTrace();
			  throw new RuntimeException(e);
		  }  
	}
	public static void sendEmailWithAttachments(String toAddress,
            String subject, String message, String[] attachFiles)
            throws AddressException, MessagingException {
        // sets SMTP server properties
        
        Session session = getSession(USER_NAME, PASSWORD);
 
        // creates a new e-mail message
        Message msg = new MimeMessage(session);
 
        msg.setFrom(new InternetAddress(USER_NAME));
        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
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
        if (attachFiles != null && attachFiles.length > 0) {
            for (String filePath : attachFiles) {
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

}
