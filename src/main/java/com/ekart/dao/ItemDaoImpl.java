package com.ekart.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ekart.controller.CustomerController;
import com.ekart.model.Item;

@Repository
public class ItemDaoImpl implements ItemDao{
	private Logger logger=Logger.getLogger(CustomerController.class);
	@Autowired
	SessionFactory sessionFactory;
	public void addItem(Item item) {
		Session session=sessionFactory.getCurrentSession();
		Transaction transaction=session.beginTransaction();
		session.save(item);
		logger.info("you are in addItem () dao Impl");
		transaction.commit();
	}
	public List<Item> viewItems() {
		logger.info("you are in viewItems() dao Impl");
		Session session=sessionFactory.getCurrentSession();
		Transaction transaction=session.beginTransaction();
		List<Item> items=session.createCriteria(Item.class).list();
		transaction.commit();
		return items;
		
	}
	public void deleteItem(int id) {
		logger.info("you are in deleteItem() dao Impl");
		Session session=sessionFactory.getCurrentSession();
		Transaction transaction=session.beginTransaction();
		Query query=session.createQuery("delete from Item where itemId=:status");
		query.setInteger("status",id);
		query.executeUpdate();
		 transaction.commit();
	}
	public Item getItembyid(int id) {
		Session session=sessionFactory.getCurrentSession();
		Transaction transaction=session.beginTransaction();
		Item i=session.load(Item.class,new Integer(id));
	  logger.info("get category:"+i.getCategory());
		transaction.commit();
		return i;
	}
	public void update(Item item) {
		Session session=sessionFactory.getCurrentSession();
		Transaction transaction=session.beginTransaction();
		session.update(item);
		transaction.commit();
	}
	

}
