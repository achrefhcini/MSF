package util;

import java.util.Date;
import java.util.Properties;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 * Session Bean implementation class Email
 */
@Stateless
@LocalBean
public class Email {
	private static int port = 587;
	private static String host = "smtp.gmail.com";
	private static String from = "mohamed95.akoum95@gmail.com";
	private static boolean auth = true;
	private static String username = "mohamed95.akoum95@gmail.com";
	private static String password = "zmmmaa24466205";
	private static boolean debug = true;
	public static void generateAndSendEmail(String to , String Subject, String body) throws AddressException, MessagingException {
		 
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		//props.put("mail.smtp.ssl.enable", true);
		props.put("mail.smtp.starttls.enable", true);
		Authenticator authenticator = null;
		if (auth) {
		    props.put("mail.smtp.auth", true);
		    authenticator = new Authenticator() {
		        private PasswordAuthentication pa = new PasswordAuthentication(username, password);
		        @Override
		        public PasswordAuthentication getPasswordAuthentication() {
		            return pa;
		        }
		    };
		}
		Session session = Session.getInstance(props, authenticator);
		session.setDebug(debug);
		MimeMessage message = new MimeMessage(session);
		try {
		    message.setFrom(new InternetAddress(from));
		    InternetAddress[] address = {new InternetAddress(to)};
		    message.setRecipients(Message.RecipientType.TO, address);
		    message.setSubject(Subject);
		    message.setSentDate(new Date());
		    message.setText(body);
		    Transport.send(message);
		} catch (MessagingException ex) {
		    ex.printStackTrace();
		}
	}

}
