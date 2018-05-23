package edu.northeastern.cs5500.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component("MailSender")
public class MailSender {

    /**
     * JavaMailSender reference
     */
    @Autowired
    JavaMailSender javaMailSender;

    private static final String FROM = "webdevproject2017@gmail.com";
    //private static final String TO = "bajaj.sho@husky.neu.edu";
    
    private static final String SUBJECT = "Plagiarism App Alert";
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     *
     * @param body Body of mail
     */
	public void sendMail(String body, String toAddress) {
		
		SimpleMailMessage mail = new SimpleMailMessage();
		
		mail.setFrom(FROM);
		//mail.setTo(TO);
		mail.setTo(toAddress);
		mail.setSubject(SUBJECT);
		mail.setText(body);
		
		logger.info("Sending...");
		
		javaMailSender.send(mail);
		
		logger.info("Done!");
	}
}