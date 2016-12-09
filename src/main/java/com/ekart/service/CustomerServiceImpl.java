package com.ekart.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ekart.dao.CustomerDao;
import com.ekart.dao.CustomerDaoImpl;
import com.ekart.model.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {
	private Logger logger=Logger.getLogger(CustomerServiceImpl.class);
	@Autowired
	CustomerDao customerDao;
	public void addCustomer(Customer customer) {
		logger.info("You are in addCustomerServiceImpl  and before");
		customerDao.addCustomer(customer);	
		logger.info("You are in addCustomerServiceImpl and after ");
	}
	public Customer getCustomerById(int id) {
		
		return null;
	}

	public Customer getCustomerByName(String name) {
		return customerDao.getCustomerByName(name);
	}

	public void deleteCustomer(int id) {
		// TODO Auto-generated method stub
		
	}

	public Customer forgotPassword(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
