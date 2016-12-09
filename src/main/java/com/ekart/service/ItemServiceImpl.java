package com.ekart.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ekart.controller.CustomerController;
import com.ekart.dao.ItemDao;
import com.ekart.model.Item;

@Service
public class ItemServiceImpl implements ItemService {
	private Logger logger=Logger.getLogger(CustomerController.class);
	@Autowired
	ItemDao itemDao;
	public void addItem(Item item) {
		itemDao.addItem(item);
		logger.info("you are in addItem() service");	
	}
	public List<Item> viewItems() {
		return itemDao.viewItems();
	}
	public void deleteItem(int id) {
		itemDao.deleteItem(id);
		
	}
	public Item getItembyid(int id) {
		return itemDao.getItembyid(id);
	}
	public void update(Item item) {
		itemDao.update(item);
		
	}

}
