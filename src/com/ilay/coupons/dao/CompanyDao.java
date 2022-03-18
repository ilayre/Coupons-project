package com.ilay.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ilay.coupons.beans.Company;
import com.ilay.coupons.beans.Coupon;
import com.ilay.coupons.utils.JdbcUtils;
import com.ilay.coupons.enums.CouponType;
import com.ilay.coupons.enums.ErrorType;
import com.ilay.coupons.exception.ApplicationException;

public class CompanyDao {
	
	public long createCompany(Company company) throws ApplicationException{
		
		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = null;
		
		try {
			// Getting a connection to the DB
			connection = JdbcUtils.getConnection();
			
			// Creating a string which will contain the query
			String sql = "insert into company (COMPANY_NAME, COMPANY_PASSWORD, COMPANY_EMAIL) values (?,?,?)";

			preparedStatement= connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

			preparedStatement.setString(1, company.getCompanyName());
			preparedStatement.setString(2, company.getCompanyPassword());
			preparedStatement.setString(3, company.getCompanyEmail());

			preparedStatement.executeUpdate();	//update changes in the DB
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			resultSet.next();
			long companyId = resultSet.getLong(1);
			return companyId;
			
		} 
		
		catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException( ErrorType.SYSTEM_ERROR,"Error in CompanyDao, creatCompany(); FAILED");
		} 
		finally {
			JdbcUtils.closeResources(connection, preparedStatement);  //closing connection resourced
			
		}
	}
	
	public Company getCompanyByComapnyId(long companyId) throws ApplicationException { //getting company information by ID

		Connection connection = null;  // Getting a connection to the DB
		PreparedStatement preparedStatement = null; 
		ResultSet resultSet = null;  //initial the result set
		Company company = null;	//initial Company Object for the final result

		try {
			connection = JdbcUtils.getConnection();   //connecting to the DB
			String sql = "SELECT * FROM company WHERE COMPANY_ID = ? ";	//SQL quarry
			preparedStatement = connection.prepareStatement(sql);  
			preparedStatement.setLong(1, companyId);
			resultSet = preparedStatement.executeQuery();	//getting the SQL result as a resultSet
			
			if (!resultSet.next()) {		//if the result is empty, return null
				return null;
			}
			company = extractCompanyFromResultSet(resultSet);	//using external method for converting from resultSet to company
		}

		catch (SQLException e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR,"Error in CompanyDao, getCompanyByComapnyId(); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);	//closing resources
		}
		return company;	//return company as a result
	}
	
	public Company getCompanyByComapnyName(String companyName) throws ApplicationException { //getting company information by ID

		Connection connection = null;  // Getting a connection to the DB
		PreparedStatement preparedStatement = null; 
		ResultSet resultSet = null;  //initial the result set
		Company company = null;	//initial Company Object for the final result

		try {
			connection = JdbcUtils.getConnection();   //connecting to the DB
			String sql = "SELECT * FROM company WHERE COMPANY_NAME = ? ";	//SQL quarry
			preparedStatement = connection.prepareStatement(sql);  
			preparedStatement.setString(1, companyName);
			resultSet = preparedStatement.executeQuery();	//getting the SQL result as a resultSet
			
			if (!resultSet.next()) {		//if the result is empty, return null
				return null;
			}
			company = extractCompanyFromResultSet(resultSet);	//using external method for converting from resultSet to company
		}

		catch (SQLException e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR,"Error in CompanyDao, getCompanyByComapnyName(); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);	//closing resources
		}
		return company;	//return company as a result
	}
	
	public Company extractCompanyFromResultSet(ResultSet resultSet) throws SQLException {  //Extract company's data by parameters from the database
	
		Company company = new Company();
		company.setCompanyId(resultSet.getLong("COMPANY_ID"));
		company.setCompanyName(resultSet.getString("COMPANY_NAME"));
		company.setCompanyPassword(resultSet.getString("COMPANY_PASSWORD"));
		company.setCompanyEmail(resultSet.getString("COMPANY_EMAIL"));

		return company;
	}
	
	public void deleteCompany(long companyId) throws ApplicationException {  // deleting company from the DB
		
		java.sql.PreparedStatement preparedStatement = null; 
		Connection connection = null;
		
		try {
			connection = JdbcUtils.getConnection();  
			
			String sql = "DELETE FROM company WHERE COMPANY_ID = ?"; 
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, companyId); 
			
			preparedStatement.executeUpdate(); 
		}
		
		catch (SQLException e) { 
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CompanyDao, deleteCompany(); FAILED");
		}
		
		finally {
			JdbcUtils.closeResources(connection, preparedStatement); 
		}
	}

	public void deleteCompanyCoupons(long companyId) throws ApplicationException {  // deleting company from the DB
		
		java.sql.PreparedStatement preparedStatement = null; 
		Connection connection = null;
		
		try {
			connection = JdbcUtils.getConnection();  
			
			String sql = "DELETE FROM coupon WHERE COMPANY_ID = ?"; 
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, companyId); 
			
			preparedStatement.executeUpdate(); 
		}
		
		catch (SQLException e) { 
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CompanyDao, deleteCompanyCoupons(); FAILED");
		}
		
		finally {
			JdbcUtils.closeResources(connection, preparedStatement); 
		}
	}
	
	public void updateCompany(Company company) throws ApplicationException { //update company parameters in DB 
		
		java.sql.PreparedStatement preparedStatement = null; 
		Connection connection = null; 
		
		try {
			connection = JdbcUtils.getConnection();
			String sql = "UPDATE company SET COMPANY_NAME = ?, COMPANY_PASSWORD = ?, COMPANY_EMAIL = ? WHERE COMPANY_ID = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, company.getCompanyName());
			preparedStatement.setString(2, company.getCompanyPassword());
			preparedStatement.setString(3, company.getCompanyEmail());
			preparedStatement.setLong(4, company.getCompanyId());
			
			preparedStatement.executeUpdate();
	}
		
		catch (SQLException e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CompanyDao, updateCompany(); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
}

	public List<Company> getCompanyList() throws ApplicationException{  //getting a List of all company's in the DB
		
		//initial all uses parameters needed for the method 
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		ResultSet resultSet = null;
		Company company = null;
		List<Company> allCompany = new ArrayList<Company>();
		 
		try {
			connection = JdbcUtils.getConnection();
			String sql = "SELECT * FROM company";
			preparedStatement = connection.prepareStatement(sql);
			
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				company = extractCompanyFromResultSet(resultSet);
				allCompany.add(company);
			}
		}	
		catch (SQLException e){
			throw new ApplicationException(e,ErrorType.SYSTEM_ERROR, "Error in CompanyDao, getCompanyList(); FAILED");
		}
		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
		return allCompany;
	}
	
	public long checkLogin(String companyEmail, String companyPassword) throws ApplicationException {
		
		java.sql.PreparedStatement preparedStatement =null;
		Connection connection = null;
		ResultSet resultSet = null;
		
		try {
			connection = JdbcUtils.getConnection();
			
			String sql = "SELECT COMPANY_ID FROM company WHERE COMPANY_EMAIL = ? AND COMPANY_PASSWORD = ?";
			
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, companyEmail);
			preparedStatement.setString(2, companyPassword);
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				long companyId = resultSet.getLong(1);
				return companyId;	//return 0 when there is no data
			}else return 0;		 
		}
		
		catch (SQLException e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CompanyDao, checkLogin(); FAILED");
		}
		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}

	public List<Coupon> getAllCompanyCoupons(long companyId) throws ApplicationException {
	
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;
		Coupon coupon = null;
		List<Coupon> companyCouponsList = new ArrayList<Coupon>();
		
		
		try {
			connection = JdbcUtils.getConnection();
			String sql = "SELECT * FROM coupon WHERE COMPANY_ID = ?";	
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, companyId);
			resultSet = preparedStatement.executeQuery(); 

			if(!resultSet.next()) {
				return null;		
			}
			while (resultSet.next()) {
				coupon = extractCouponFromResultSet(resultSet);
				companyCouponsList.add(coupon);
			}
			
		}
		catch(SQLException e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CouponDao, getCouponsByMaximumPrice(); FAILED");
		}
		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
		return companyCouponsList;
	}
	
	private Coupon extractCouponFromResultSet(ResultSet resultSet) throws SQLException {
		
		Coupon coupon = new Coupon();
		coupon.setCouponId(resultSet.getLong("ID"));
		coupon.setTitle(resultSet.getString("TITLE"));
		coupon.setStartDate(resultSet.getString("START_DATE"));
		coupon.setEndDate(resultSet.getString("END_DATE"));
		coupon.setAmount(resultSet.getInt("AMOUNT"));
		coupon.setType(CouponType.valueOf(resultSet.getString("type")));
		coupon.setMessage(resultSet.getString("MESSAGE"));
		coupon.setPrice(resultSet.getDouble("PRICE"));
		coupon.setImage(resultSet.getString("IMAGE"));
		coupon.setCompanyId(resultSet.getLong("COMPANY_ID"));
		
		return coupon;
	}

	
}