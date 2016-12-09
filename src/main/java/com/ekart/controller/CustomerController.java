package com.ekart.controller;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ekart.model.Customer;
import com.ekart.service.CustomerService;
import com.ekart.service.EmailService;

@Controller
public class CustomerController {
	@Autowired
	EmailService emailService;
	@Autowired
	CustomerService customerService;
	private Logger logger=Logger.getLogger(CustomerController.class);
@RequestMapping("/")
public ModelAndView home()
{	
	logger.info("home() method called from the CustomerController");
	return new ModelAndView("home");
}
@RequestMapping("/account")
public ModelAndView account()
{	
	logger.info("account() method called from the CustomerController");
	return new ModelAndView("account");
}

@RequestMapping("/about")
public ModelAndView about()
{	
	logger.info("about() method called from the CustomerController");
	return new ModelAndView("about");
}
@RequestMapping("/register")
public ModelAndView register()
{
	logger.info("register() method called from the CustomerController");
	return new ModelAndView("register","user",new Customer());
}

@RequestMapping("/addCustomer")
public ModelAndView addCustomer(@Valid @ModelAttribute("user") Customer customer,BindingResult bindingResult)
{
	if(bindingResult.hasErrors())
{
	return new ModelAndView("register");
}
	logger.info("addCustomer() method called from the CustomerController");
	customerService.addCustomer(customer);
	logger.info("after adding the customer");
	String subject="Thanks for registering with ekart.com";
	String body="Dear "+customer.getFirstName()+" ,  Thanks for registering with eyekart.com. You will receive a gift soon,  Thanks, Sai Kumar";
   try {
	emailService.send(customer, subject, body);
	logger.info("after sending the mail from the controller");
} catch (MessagingException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}	
	return new ModelAndView("register","message","Congrats "+customer.getFirstName()+", You have Successsfully Registered!!");
}
}
