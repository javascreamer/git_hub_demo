package com.ekart.dao;


import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ekart.controller.CustomerController;
import com.ekart.model.Cart;
import com.ekart.model.Customer;
import com.ekart.model.UserRole;

/*
 The @Repository annotation is a specialization
  of the @Component annotation with similar use and functionality. 
  In addition to importing the DAOs into the DI container, 
  it also makes the unchecked exceptions (thrown from DAO methods) 
  eligible for translation into Spring DataAccessException.
 */
@Repository
public class CustomerDaoImpl implements CustomerDao {
	
	private Logger logger=Logger.getLogger(CustomerDaoImpl.class);
	/*  link to understand autowiring:   https://www.mkyong.com/spring/spring-auto-scanning-components/  */
	@Autowired
	SessionFactory sessionFactory;
	public void addCustomer(Customer customer) {
		logger.info("You are in addCustomer(Customer customer)!!!");
		Session session=sessionFactory.getCurrentSession();
		Transaction transaction=session.beginTransaction();
		customer.setEnabled(true);
		session.save(customer);    
		UserRole role=new UserRole();
		role.setUserId(customer.getCustomerId());
		role.setAuthority("ROLE_USER");
		session.save(role);
		Cart newCart=new Cart();
		newCart.setCustomer(customer);
		customer.setCart(newCart);
		session.save(customer);
		session.save(newCart);
		logger.info("customer has been successfully signed up!!!");
		transaction.commit();	
	}
	public Customer getCustomerById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	public Customer getCustomerByName(String name) {
		Session session = sessionFactory.openSession();
		Transaction tx=session.beginTransaction();
        Query query = session.createQuery("from Customer where firstName = ?");
        query.setString(0, name);
        tx.commit();
        return (Customer) query.uniqueResult();
	}

	public void deleteCustomer(int id) {
		// TODO Auto-generated method stub
		
	}

	public Customer forgotPassword(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
