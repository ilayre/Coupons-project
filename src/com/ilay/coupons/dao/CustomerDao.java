package com.ilay.coupons.dao;
import com.ilay.coupons.beans.Customer;
import com.ilay.coupons.enums.ErrorType;
import com.ilay.coupons.exception.ApplicationException;
import com.ilay.coupons.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao {
	
	public long createCustomer(Customer customer) throws ApplicationException {
		
		java.sql.PreparedStatement preparedStatement =null;
		Connection connection = null;
		
		try {
			connection = JdbcUtils.getConnection();
			String sql = "INSERT INTO customer (CUSTOMER_NAME, CUSTOMER_PASSWORD, CUSTOMER_EMAIL) VALUES (?,?,?)";
			preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			
			preparedStatement.setString(1, customer.getCustomerName());
			preparedStatement.setString(2, customer.getCustomerPassword());
			preparedStatement.setString(3, customer.getCustomerEmail());
			
			preparedStatement.executeUpdate();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			resultSet.next();
			long customerId = resultSet.getLong(1);
			return customerId;
		}
		catch (SQLException e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CouponDao, createCustomer(); FAILED");
		}
		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
	
	public Customer getCustomerByCustomerId(long customerId) throws ApplicationException {
		
		Connection connection =null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Customer customer = null;
		
		try {
			connection = JdbcUtils.getConnection();
			String sql = "SELECT * FROM customer WHERE CUSTOMER_ID = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, customerId);
			resultSet = preparedStatement.executeQuery();
			
			if(!resultSet.next()) {
				return null;
			}
			customer = extractCustomerFromResultSet(resultSet);
		}
		
		catch (SQLException e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CouponDao, getCustomerByCustomerId(); FAILED");
		}
		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
		return customer;
	}
	
	public Customer extractCustomerFromResultSet(ResultSet resultSet) throws SQLException {
		Customer customer = new Customer();
		customer.setCustomerId(resultSet.getInt("CUSTOMER_ID"));
		customer.setCustomerName(resultSet.getString("CUSTOMER_NAME"));
		customer.setCustomerPassword(resultSet.getString("CUSTOMER_PASSWORD"));
		customer.setCustomerEmail(resultSet.getString("CUSTOMER_EMAIL"));
		
		return customer;
	}
	
	public void deleteCustomer(Long customerId) throws ApplicationException{
		
		java.sql.PreparedStatement preparedStatement = null;  
		Connection connection = null; 
		
		try {
			connection = JdbcUtils.getConnection();  
			
			String sql = "DELETE FROM customer WHERE ID = ?"; 
			preparedStatement = connection.prepareStatement(sql); 
			preparedStatement.setLong(1, customerId); 
			
			preparedStatement.executeUpdate(); 
		}
		
		catch (SQLException e) {  
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CouponDao, deleteCustomer(); FAILED");
		}
		
		finally {
			JdbcUtils.closeResources(connection, preparedStatement); 
		}
	}

	public void deleteCustomerFromFkTable(Long customerId) throws ApplicationException{
		
		java.sql.PreparedStatement preparedStatement = null;  
		Connection connection = null; 
		
		try {
			connection = JdbcUtils.getConnection();  
			
			String sql = "DELETE FROM customer_coupon WHERE CUSTOMER_ID = ?"; 
			preparedStatement = connection.prepareStatement(sql); 
			preparedStatement.setLong(1, customerId); 
			
			preparedStatement.executeUpdate(); 
		}
		
		catch (SQLException e) {  
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CouponDao, deleteCustomerFromeFkTable(); FAILED");
		}
		
		finally {
			JdbcUtils.closeResources(connection, preparedStatement); 
		}
	}
	
	public void updateCustomer(Customer customer) throws ApplicationException{
		
		java.sql.PreparedStatement preparedStatement = null; 
		Connection connection = null; 
		
		try {
			connection = JdbcUtils.getConnection();
			String sql = "UPDATE customer SET CUSTOMER_NAME = ?, CUSTOMER_PASSWORD = ?, CUSTOMER_EMAIL = ? WHERE CUSTOMER_ID = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, customer.getCustomerName());
			preparedStatement.setString(2, customer.getCustomerPassword());
			preparedStatement.setString(3, customer.getCustomerEmail());
			preparedStatement.setLong(4, customer.getCustomerId());
			
			preparedStatement.executeUpdate();
		}
		catch (SQLException e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in customerDao, updateCustomer(); FAILED");
		}
		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public List<Customer> getCustomerList() throws ApplicationException{
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		ResultSet resultSet = null;
		Customer customer = null;
		List<Customer> allCustomer = new ArrayList<Customer>();
		 
		try {
			connection = JdbcUtils.getConnection();
			String sql = "SELECT * FROM customer";
			preparedStatement = connection.prepareStatement(sql);
			
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				customer = extractCustomerFromResultSet(resultSet);
				allCustomer.add(customer);
			}
		}
		
		catch (SQLException e){
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CouponDao, getCustomerList(); FAILED");
		}
		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
		return allCustomer;
	}
		
	public long checkLogin(String customerEmail, String customerPassword) throws ApplicationException {
		
		java.sql.PreparedStatement preparedStatement =null;
		Connection connection = null;
		ResultSet resultSet = null;
	
		
		try {
			connection = JdbcUtils.getConnection();
			
			String sql = "SELECT CUSTOMER_ID FROM customer WHERE CUSTOMER_EMAIL = ? AND CUSTOMER_PASSWORD = ?";
			
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, customerEmail);
			preparedStatement.setString(2, customerPassword);
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				long customerId = resultSet.getLong(1);
				return customerId;	//return 0 when there is no data 	
			}else return 0;
		}
		
		catch (SQLException e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CustomerDao, checkLogin(); FAILED");
		}
		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}
	
	public boolean isCustomerExistByName(String customerName) throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = JdbcUtils.getConnection();
			String sql = "SELECT * FROM customer WHERE CUSTOMER_NAME = ?";	//select coupon from the DB by coupon name.
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, customerName);
			resultSet = preparedStatement.executeQuery();	//extract the result to resultSet.
			
			if(!resultSet.next()) {
				return false;		//return false if the result set is empty, which means there is not customer name like this in the DB.
			}else {
				return true;		//return true if the result set isn't empty, which means there is a customer with that name in the DB.
			}
		}
			catch(SQLException e) {
				throw new ApplicationException(ErrorType.SYSTEM_ERROR, "Error in CustomerDao, isCustomerExistByName(); FAILED");
			}
		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}

	
}
