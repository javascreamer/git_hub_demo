package com.ekart.service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ekart.controller.CustomerController;
import com.ekart.model.Customer;
@Service
public class EmailService {
	public static final String REPLY_TO_ADDRESS="donotreply@ekart.com";
	public static final String FROM_ADDRESS="registration@ekart.com";
	private Logger logger=Logger.getLogger(CustomerController.class);
	@Autowired
	public JavaMailSender javaMailSender;
public void send(Customer customer, String subject,String body) throws MessagingException
{
	logger.info("Inside the send method");
	 MimeMessage mail=javaMailSender.createMimeMessage();
	 MimeMessageHelper helper=new MimeMessageHelper(mail,true);
	 helper.setTo(customer.getEmailAddress());
	 helper.setReplyTo(REPLY_TO_ADDRESS);
	 helper.setFrom(FROM_ADDRESS);
	 helper.setSubject(subject);
		helper.setText(body);
		javaMailSender.send(mail);
		logger.info("email has been sent");
		
	 
}
	
	

}
