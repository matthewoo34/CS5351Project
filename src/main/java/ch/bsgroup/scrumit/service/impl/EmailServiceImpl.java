package ch.bsgroup.scrumit.service.impl;

import ch.bsgroup.scrumit.service.IEmailService;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public class EmailServiceImpl implements IEmailService{

	  private final String senderEmail = "453db543b7a72d";//change with your sender email
	  private final String senderPassword = "2c03bf8eb1fb1b";//change with your sender password
	
	@Override
	public void send(String to, String subject, String content) {
		try {
			System.out.println("email to"+to+",title:"+subject+",content:"+content);
			Session session = createSession();
			
			MimeMessage message = new MimeMessage(session);
			prepareEmailMessage(message,to,subject,content);
			Transport.send(message);
		} catch (Exception ex) {
			System.out.println("send email error");
			ex.printStackTrace();
		}
	}
	
	private void prepareEmailMessage(MimeMessage message, String to, String title, String content)
	          throws MessagingException {
	      message.setFrom(new InternetAddress(senderEmail));
	      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
	      message.setSubject(title);
	      message.setText(content);
	  }

	private Session createSession() {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");//Outgoing server requires authentication
		props.put("mail.smtp.starttls.enable", "true");//TLS must be activated
		props.put("mail.smtp.host", "smtp.mailtrap.io"); //Outgoing server (SMTP) - change it to your SMTP server
		props.put("mail.smtp.port", "2525");//Outgoing port
		
		Session session = Session.getInstance(props,new javax.mail.Authenticator() {
	          protected PasswordAuthentication getPasswordAuthentication() {
	              return new PasswordAuthentication(senderEmail, senderPassword);
	          }
		});
		
		return session;
	}
}
