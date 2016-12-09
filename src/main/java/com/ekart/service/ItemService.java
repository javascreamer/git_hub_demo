package com.ekart.service;

import java.util.List;

import com.ekart.model.Item;

public interface ItemService {
	void addItem(Item item);
	List<Item> viewItems();
	void deleteItem(int id);
	Item getItembyid(int id);
	public void update(Item item);

}
