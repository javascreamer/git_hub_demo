package com.ekart.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ekart.model.Item;
import com.ekart.service.ItemService;

@Controller
public class LoginController {
	private Logger logger=Logger.getLogger(CustomerController.class);
	@Autowired
	ItemService itemService;
	@RequestMapping("/login")
	public String loginMethod()
	{
		return "login";
	}	
	@RequestMapping("/logout")
	public String logoutMethod()
	{
		return "logout";
	}	
	@RequestMapping("/userLogin")
	public ModelAndView userLogin(Principal principal)
	{
		 
		System.out.println("name:"+principal.getName());
		logger.info("inside userLogin()");	
		List<Item> items=itemService.viewItems();
		ObjectMapper mapper=new ObjectMapper();
		String jsonData="";
		
		try {
			jsonData=mapper.writeValueAsString(items);
			logger.info(jsonData);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ModelAndView("customerHome","data",jsonData);
	}
	@RequestMapping("/adminLogin")
	public ModelAndView adminLogin(Principal principal)
	{
		return new ModelAndView("adminHome");
	}
	

}
