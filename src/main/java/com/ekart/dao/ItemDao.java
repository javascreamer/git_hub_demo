package com.ekart.dao;

import java.util.List;

import com.ekart.model.Item;

public interface ItemDao {
	void addItem(Item item);
	List<Item> viewItems();
	void deleteItem(int id);
	Item getItembyid(int id);
	void update(Item item);

}
