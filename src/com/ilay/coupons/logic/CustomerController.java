package com.ilay.coupons.logic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ilay.coupons.beans.Customer;
import com.ilay.coupons.dao.CustomerDao;
import com.ilay.coupons.enums.ErrorType;
import com.ilay.coupons.exception.ApplicationException;
import com.ilay.coupons.utils.ValidationUtils;

public class CustomerController {
	
	private CustomerDao customerDao;
	private ValidationUtils validationUtils;
	
	
	public CustomerController() {
		super();
		this.customerDao = new CustomerDao();
		this.validationUtils = new ValidationUtils();
		
	}

	public void createCustomer(Customer customer) throws ApplicationException {
		
		if(customerDao.isCustomerExistByName(customer.getCustomerName())==true) { 	//check if customer name is already exist
			throw new ApplicationException(ErrorType.NAME_IS_ALREADY_EXIST, "Create customer has failed, customer is already exist");
		}
		if(!validationUtils.isEmailAddressValid(customer.getCustomerEmail())) {	//if return false, email is not valid.
			throw new ApplicationException(ErrorType.INVALID_PARAMETER, "Email Address format is not valid");	//throws the specific exception.
		}
		if(!validationUtils.ispasswordValid(customer.getCustomerPassword())) {		//checks for password validation
			throw new ApplicationException(ErrorType.INVALID_PARAMETER, "Password must have at least 8 charcters and at least one uppercase");
		}else {
			customerDao.createCustomer(customer);
		}	
	}
	
	public Customer getCustomerByCustomerId(long customerId) throws ApplicationException {
		
		if(customerDao.getCustomerByCustomerId(customerId)!=null) {	//checks if Customer ID exist in the database
			return customerDao.getCustomerByCustomerId(customerId);
		}else {
		throw new ApplicationException(ErrorType.INVALID_PARAMETER, "Customer ID not exist");
		}
	}
	
	public Customer extractCustomerFromResultSet(ResultSet resultSet) throws SQLException {
	
		return customerDao.extractCustomerFromResultSet(resultSet);
	}
	
	public void deleteCustomer(Long customerId) throws ApplicationException {
		if(customerDao.getCustomerByCustomerId(customerId)==null) {		//check if customer id exist.
			throw new ApplicationException(ErrorType.INVALID_PARAMETER, "Customer ID not exsit");
		}
		customerDao.deleteCustomerFromFkTable(customerId);
		customerDao.deleteCustomer(customerId);
		
	}
	
	public void updateCustomer(Customer customer) throws ApplicationException {
		
		if(customerDao.getCustomerByCustomerId(customer.getCustomerId())==null) {		//checks for ID existent
			throw new ApplicationException(ErrorType.INVALID_PARAMETER, "Customer ID not exist");
		}
			
		if(!validationUtils.ispasswordValid(customer.getCustomerPassword())) {
			throw new ApplicationException(ErrorType.INVALID_PARAMETER, "Password must have at least 8 charcters and at least one uppercase");
		}
		customerDao.updateCustomer(customer);
	}
	
	public List<Customer> getCustomerList() throws ApplicationException {
		return customerDao.getCustomerList();
	}
	
	public long checkLogin(String customerName, String customerPassword) throws ApplicationException {

		long logedInId = customerDao.checkLogin(customerName, customerPassword);	
		
		if(logedInId!=0) {
			return logedInId;
		}else {
			throw new ApplicationException(ErrorType.INVALID_LOGIN, "Wrong Email or Password");
		}
		
	}
}
