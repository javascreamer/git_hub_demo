package com.ekart.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ekart.model.Item;
import com.ekart.service.ItemService;
@Controller
public class AdminController {
	@Autowired
	ItemService itemService;
	
	private Logger logger=Logger.getLogger(CustomerController.class);
	@RequestMapping("/addItem")
	public ModelAndView addItem()
	{
		logger.info("inside addItem()");
	return new ModelAndView("addItem","item",new com.ekart.model.Item());
	}
	
	@RequestMapping("/deleteItem")
	public String deleteItem(@RequestParam("id") int id)
	{
		logger.info("id:"+id);
	itemService.deleteItem(id);	
	logger.info("item deleted");
	return "redirect:/viewItems";
	}
	@RequestMapping("/editItem")
	public ModelAndView editItem(@RequestParam("id") int id)
	{
		   Item i=itemService.getItembyid(id);
		return new ModelAndView("editItem","item",i);
	}
	
	@RequestMapping("editedItem")
	public String editedItem(@RequestParam("itemId") String itemId,@RequestParam("itemName") String itemName,@RequestParam("category") String category,@RequestParam("itemPrice") String itemPrice,@RequestParam("itemDescription") String itemDescription)
	{
		Item item=itemService.getItembyid(Integer.parseInt(itemId));
	 item.setCategory(category);
	 item.setItemDescription(itemDescription);
	 item.setItemPrice(Float.parseFloat(itemPrice));
	 item.setItemName(itemName);
	 itemService.update(item);
	 return "redirect:/viewItems";
	}
	
	@RequestMapping("/viewItems")
	public ModelAndView viewItems()
	{
		logger.info("inside ViewItem()");	
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
	return new ModelAndView("viewItems","items",jsonData);
	}
	@RequestMapping("/storeItem")
	public ModelAndView storeItem(@RequestParam("file")MultipartFile file,@Valid @ModelAttribute("item") Item item,BindingResult result)
	{
		if(result.hasErrors())
		{
			return  new ModelAndView("addItem");
		}
		else
		{
			itemService.addItem(item);
			MultipartFile itemImage=item.getFile();
			Path path=Paths.get("H://spring framework//ekart//ekart//src//main//webapp//resources//images//"+item.getItemId()+".jpg");
			if(itemImage!=null)
			{
				try {
					itemImage.transferTo(new File(path.toString()));
					logger.info("AdminController.storeItem() executed");
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		return new ModelAndView("addItem","info","Item Added Successfully");
	}
	
	@RequestMapping("viewDetails")
	public ModelAndView viewDetails(@RequestParam("id") String id)
	{
		Item item=itemService.getItembyid(Integer.parseInt(id));
		return new ModelAndView("viewDetails","item",item);
	}
	
	
}
