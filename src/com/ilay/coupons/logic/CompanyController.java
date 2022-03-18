package com.ilay.coupons.logic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ilay.coupons.beans.Company;
import com.ilay.coupons.beans.Coupon;
import com.ilay.coupons.dao.CompanyDao;
import com.ilay.coupons.enums.ErrorType;
import com.ilay.coupons.exception.ApplicationException;
import com.ilay.coupons.utils.ValidationUtils;

public class CompanyController {
	
	private CompanyDao companyDao;
	private ValidationUtils validationUtils;
	
	public CompanyController() {
		super();
		this.companyDao = new CompanyDao();
		this.validationUtils = new ValidationUtils();
	}
	
	public long createCompany(Company company) throws ApplicationException{
		
		if(companyDao.getCompanyByComapnyName(company.getCompanyName())!=null) {	//checks if company name already exist
			throw new ApplicationException(ErrorType.ID_IS_ALREADY_EXIST, "Company Name is already exist");
		}
		if(!validationUtils.isEmailAddressValid(company.getCompanyEmail())) {	//if return false, email is not valid.
			throw new ApplicationException(ErrorType.INVALID_PARAMETER, "Email Address format is not valid");	//throws the specific exception.
		}
		if(!validationUtils.ispasswordValid(company.getCompanyPassword())) {	//checks for password validation.
			throw new ApplicationException(ErrorType.INVALID_PARAMETER, "Invalid email or password, notice: password must have at least 8 chars and one upeprcase letter");
		}
		return companyDao.createCompany(company);
	}
	
	public Company getCompanyByComapnyId(long companyId) throws ApplicationException {
		if(companyDao.getCompanyByComapnyId(companyId)==null) {		//if getCompanyByCompanyId return nothing it means the id dont exist.
			throw new ApplicationException(ErrorType.INVALID_PARAMETER, "Company ID not exist");
		} return companyDao.getCompanyByComapnyId(companyId);
	}
	
	public Company getCompanyByCompanyName(String companyName) throws ApplicationException {
		if(companyDao.getCompanyByComapnyName(companyName)!=null) {
			return companyDao.getCompanyByComapnyName(companyName);
		}else throw new ApplicationException(ErrorType.INVALID_PARAMETER, "Company Name not exist");
	}
		
	public void deleteCompany (long companyId) throws ApplicationException {
		if(companyDao.getCompanyByComapnyId(companyId)==null) {		//if getCompanyByCompanyId return nothing it means the method works fine.
			throw new ApplicationException(ErrorType.INVALID_PARAMETER, "Company ID not exist");
		}	
			companyDao.deleteCompany(companyId);
			companyDao.deleteCompanyCoupons(companyId);
		}
	
	public void updateCompany (Company company) throws ApplicationException {
	
		if(companyDao.getCompanyByComapnyId(company.getCompanyId())==null) {		//check if company id exist.	
			throw new ApplicationException(ErrorType.INVALID_PARAMETER, "Company ID not exist");
		}
		if(companyDao.getCompanyByComapnyName(company.getCompanyName())!=null) {	//checks if company name already exist
			throw new ApplicationException(ErrorType.NAME_IS_ALREADY_EXIST, "Company Name is already exist in the database");
		}
		
		if(!validationUtils.isEmailAddressValid(company.getCompanyEmail())) {	//check if email address is valid.
			throw new ApplicationException(ErrorType.INVALID_PARAMETER, "Email Address format is not valid");
		}
		if(!validationUtils.ispasswordValid(company.getCompanyPassword())) {		//check if password is valid.
			throw new ApplicationException(ErrorType.INVALID_PARAMETER, "Invalid email or password");
		}
		companyDao.updateCompany(company);
		}

	public void extractCompanyFromResultSet(ResultSet resultSet) throws SQLException {
		companyDao.extractCompanyFromResultSet(resultSet);
	}
	
	public List<Company> getCompanyList() throws ApplicationException {
		return companyDao.getCompanyList();
	}
	
	public long checkLogin(String companyName, String companyPassword) throws ApplicationException {
		
		long logedInId = companyDao.checkLogin(companyName, companyPassword);	
		
		if(logedInId!=0) {
			return logedInId;
		}else {
			throw new ApplicationException(ErrorType.INVALID_LOGIN, "Wrong Email or Password");
		}
		
	
	}

	public List<Coupon> getAllCompanyCoupons(long companyId) throws ApplicationException {
		if(companyDao.getAllCompanyCoupons(companyId)==null) {
			throw new ApplicationException(ErrorType.SYSTEM_MESSAGE,"There are no coupons available, List is empty");
		}
		return companyDao.getAllCompanyCoupons(companyId);
	}
}