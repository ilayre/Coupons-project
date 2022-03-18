package com.ilay.coupons.api;

import javax.ws.rs.Consumes;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ilay.coupons.beans.Company;
import com.ilay.coupons.beans.Customer;
import com.ilay.coupons.beans.User;
import com.ilay.coupons.enums.UserType;
import com.ilay.coupons.exception.ApplicationException;
import com.ilay.coupons.logic.CompanyController;
import com.ilay.coupons.logic.CustomerController;

@Path("/site")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserApi {

	private CompanyController companyController;
	private CustomerController customerController;

	public UserApi() {
		super();
		this.companyController = new CompanyController();
		this.customerController = new CustomerController();
	}

	@POST
	@Path("/register")
	public Response registerUser(@Context HttpServletRequest request, @Context HttpServletResponse response, User user)
			throws ApplicationException, IOException {
		

		if (user != null) {
			if (user.getUserType() == UserType.COMPANY) {
				Company company = new Company(user.getName(), user.getPassword(), user.getEmail());
				companyController.createCompany(company);
			}
			if (user.getUserType() == UserType.CUSTOMER) {
				Customer customer = new Customer(user.getName(), user.getPassword(), user.getEmail());
				customerController.createCustomer(customer);
			}
			
		}

		
		response.setStatus(200);
		return Response.status(200).build();
	}

	@POST
	@Path("/login")
	public Response loginUser(@Context HttpServletRequest request, @Context HttpServletResponse response, User user)
			throws ApplicationException, IOException {
		
		
		
		long logedInId;

		if (user.getEmail() != null && user.getPassword() != null) {
			if (user.getUserType() == UserType.COMPANY) {
				logedInId = companyController.checkLogin(user.getEmail(), user.getPassword());
				request.getSession();
				Cookie cookie = new Cookie("loginSuccessUserId", Long.toString(logedInId));
				cookie.setPath("/");
				response.addCookie(cookie);
				return Response.status(200).build();
			}

			if (user.getUserType() == UserType.CUSTOMER) {
				logedInId = customerController.checkLogin(user.getEmail(), user.getPassword());
				request.getSession();
				Cookie cookie = new Cookie("loginSuccessUserId", Long.toString(logedInId));
				cookie.setPath("/");
				response.addCookie(cookie);
				return Response.status(200).build();
			}

		}
		return Response.status(401).build();

	}

}