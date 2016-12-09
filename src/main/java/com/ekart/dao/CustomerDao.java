package com.ekart.dao;

import com.ekart.model.Customer;

public interface CustomerDao {
	void addCustomer(Customer customer);
	Customer getCustomerById(int id);
	Customer getCustomerByName(String name);
	void deleteCustomer(int id);
	Customer forgotPassword(String name);
}
