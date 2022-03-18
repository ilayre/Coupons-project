package com.ilay.coupons.api;


import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ilay.coupons.beans.Customer;
import com.ilay.coupons.exception.ApplicationException;
import com.ilay.coupons.logic.CustomerController;


@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerApi {

	private CustomerController customerController;
	
	public CustomerApi() {
		super();
		this.customerController = new CustomerController();
	}
	
	@POST
	public void createCustomer(Customer customer) throws ApplicationException {
		customerController.createCustomer(customer);
	}
	
	@GET
	@Path("/{customerId}")
	public Customer getCustomerByCustomerId(@PathParam("customerId")long customerId) throws ApplicationException {
		return customerController.getCustomerByCustomerId(customerId);
	}
	
	@DELETE
	@Path("/delete/{customerId}")
	public void deleteCustomer(@PathParam("customerId")Long customerId) throws ApplicationException {
		customerController.deleteCustomer(customerId);
	}
	
	@PUT
	@Path("/update")
	public void updateCustomer(Customer customer) throws ApplicationException {
		customerController.updateCustomer(customer);
	}
	
	@GET
	@Path("/getList")
	public List<Customer> getCustomerList() throws ApplicationException {
		return customerController.getCustomerList();
	}

	
	
}
