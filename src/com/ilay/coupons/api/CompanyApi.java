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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.ilay.coupons.beans.Company;
import com.ilay.coupons.beans.Coupon;
import com.ilay.coupons.exception.ApplicationException;
import com.ilay.coupons.logic.CompanyController;


@Path("/companies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CompanyApi {
	
	private CompanyController companyController;
	
	public CompanyApi() {
		super();
		this.companyController = new CompanyController();
	}
	
	@POST
	public long CrateCompany(Company company) throws ApplicationException {
		return companyController.createCompany(company);
	}
	
	@GET
	@Path("/{comapnyId}")
	public Company getCompanyByComapnyId(@PathParam("comapnyId") long companyId) throws ApplicationException {
		return companyController.getCompanyByComapnyId(companyId);
	}
	
	@GET
	@Path("/byname/{companyName}")
	public Company getCompanyByCompanyName(@QueryParam("companyName")String companyName) throws ApplicationException {
		return companyController.getCompanyByCompanyName(companyName);
	}
	
	@DELETE
	@Path("/delete/{companyId}")
	public void deleteCompany (@PathParam("companyId")long companyId) throws ApplicationException {
		companyController.deleteCompany(companyId);
	}
	
	@PUT
	@Path("/update")
	public void updateCompany (Company company) throws ApplicationException {
		companyController.updateCompany(company);
	}
	
	@GET
	@Path("/getlist")
	public List<Company> getCompanyList() throws ApplicationException {
		return companyController.getCompanyList();
	}
	
	@GET
	@Path("/getCompanyCoupons/{companyId}")
	public List<Coupon> getAllCompanyCoupons(@PathParam("companyId")long companyId) throws ApplicationException {
		return companyController.getAllCompanyCoupons(companyId);
	}
	
}
