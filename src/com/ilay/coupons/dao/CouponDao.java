package com.ilay.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.ilay.coupons.beans.Coupon;
import com.ilay.coupons.beans.CustomerCoupon;
import com.ilay.coupons.enums.CouponType;
import com.ilay.coupons.enums.ErrorType;
import com.ilay.coupons.exception.ApplicationException;
import com.ilay.coupons.utils.DateUtils;
import com.ilay.coupons.utils.JdbcUtils;



public class CouponDao {
	

	
		
	public long createCoupon (Coupon coupon) throws ApplicationException{  //creating a new coupon in the DB 

		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = null;
	
	try {
		connection = JdbcUtils.getConnection();
		
		String sql = "insert into coupon (TITLE, START_DATE, END_DATE, AMOUNT, TYPE, MESSAGE, PRICE, IMAGE, COMPANY_ID) values (?,?,?,?,?,?,?,?,?)";
		
		preparedStatement = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
		
		preparedStatement.setString(1, coupon.getTitle());
		preparedStatement.setString(2, coupon.getStartDate());
		preparedStatement.setString(3, coupon.getEndDate());
		preparedStatement.setInt(4, coupon.getAmount());
		preparedStatement.setString(5, coupon.getType().getName()); 
		preparedStatement.setString(6, coupon.getMessage());
		preparedStatement.setDouble(7, coupon.getPrice());
		preparedStatement.setString(8, coupon.getImage());
		preparedStatement.setLong(9, coupon.getCompanyId());
		
		preparedStatement.executeUpdate();
		ResultSet resultSet = preparedStatement.getGeneratedKeys();
		resultSet.next();
		long couponId = resultSet.getLong(1);
		return couponId;
	}
	catch (SQLException e) {
		e.printStackTrace();
		throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CouponDao, createCoupon(); FAILED");
	}	
	finally {
		JdbcUtils.closeResources(connection, preparedStatement);
	}                       
}

	public Coupon getCouponByCouponId(long couponId) throws ApplicationException{
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Coupon coupon = null;
		
		try {
			connection = JdbcUtils.getConnection();
			String sql = "SELECT * FROM coupon WHERE ID = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, couponId);
			resultSet = preparedStatement.executeQuery();
			
			if(!resultSet.next()) {
				return null;
			}
			coupon = extractCouponFromResultSet(resultSet);
		}
		catch(SQLException e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CouponDao, getCouponByCouponId(); FAILED");
		}
		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
		return coupon;	
	}
		
	private Coupon extractCouponFromResultSet(ResultSet resultSet) throws SQLException {
	
			Coupon coupon = new Coupon();
			coupon.setCouponId(resultSet.getLong("ID"));
			coupon.setTitle(resultSet.getString("TITLE"));
			coupon.setStartDate(resultSet.getString("START_DATE"));
			coupon.setEndDate(resultSet.getString("END_DATE"));
			coupon.setAmount(resultSet.getInt("AMOUNT"));
			coupon.setType(CouponType.valueOf(resultSet.getString("TYPE")));
			coupon.setMessage(resultSet.getString("MESSAGE"));
			coupon.setPrice(resultSet.getDouble("PRICE"));
			coupon.setImage(resultSet.getString("IMAGE"));
			coupon.setCompanyId(resultSet.getLong("COMPANY_ID"));
			
			return coupon;
		}
	
	public void deleteCoupon(long couponId) throws ApplicationException{

		PreparedStatement preparedStatement = null;
		Connection connection = null;
		
		try {
			connection = JdbcUtils.getConnection();
			
			String sql = "delete FROM coupon WHERE ID = ?";	
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, couponId);
			preparedStatement.executeUpdate();
		}
		
		catch (SQLException e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CouponDao, deleteCoupon(); FAILED");	
		}
		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public void deleteCouponFromFkTable(long couponId) throws ApplicationException{

		PreparedStatement preparedStatement = null;
		Connection connection = null;
		
		try {
			connection = JdbcUtils.getConnection();
			
			String sql = "delete FROM customer_coupon WHERE COUPON_ID = ?";	
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, couponId);
			preparedStatement.executeUpdate();
		}
		
		catch (SQLException e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CouponDao, deleteCouponFromFkTable(); FAILED");	
		}
		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
	
	public void updateCoupon(Coupon couopn) throws ApplicationException{
		
		java.sql.PreparedStatement preparedStatement = null; 
		Connection connection = null; 
		
		try {
			connection = JdbcUtils.getConnection();
			String sql = "UPDATE coupon SET TITLE = ?, START_DATE = ?, END_DATE = ?, AMOUNT = ?, TYPE = ?, MESSAGE = ?, PRICE = ?, IMAGE = ?, COMPANY_ID = ? WHERE ID = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, couopn.getTitle());
			preparedStatement.setString(2, couopn.getStartDate());
			preparedStatement.setString(3, couopn.getEndDate());
			preparedStatement.setLong(4, couopn.getAmount());
			preparedStatement.setString(5, couopn.getType().getName());
			preparedStatement.setString(6, couopn.getMessage());
			preparedStatement.setDouble(7, couopn.getPrice());
			preparedStatement.setString(8, couopn.getImage());
			preparedStatement.setLong(9, couopn.getCompanyId());
			preparedStatement.setLong(10, couopn.getCouponId());
			
			preparedStatement.executeUpdate();
	}
		
		catch (SQLException e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CouponDao, updateCoupon(); FAILED");
		}
		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
}	
		
	public List<Coupon> getCouponList() throws ApplicationException{
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		ResultSet resultSet = null;
		Coupon coupon = null;
		List<Coupon> allCoupons = new ArrayList<Coupon>();
		 
		try {
			connection = JdbcUtils.getConnection();
			String sql = "SELECT * FROM coupon";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			
			if(!resultSet.next()) {		
				return null;
			}
			
			while (resultSet.next()) {
				coupon = extractCouponFromResultSet(resultSet);
				allCoupons.add(coupon);
			}
		}
		catch (SQLException e){
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CouponDao, getCouponList(); FAILED");
		}
		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
		return allCoupons;
	}			

	public boolean checkCouponDate (long couponId) throws ApplicationException {
		
		Connection connection = null;		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Coupon coupon = null;
		
		try {			//connecting to DB and extracting the selected coupon
			connection = JdbcUtils.getConnection();
			String sql = "SELECT * FROM coupon WHERE ID = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, couponId);
			resultSet = preparedStatement.executeQuery();
			
			if(!resultSet.next()) {		//check if the coupon exist
				throw new ApplicationException(ErrorType.INVALID_PARAMETER, "Error in CouponDao, couponID not exist");
			}
			
			coupon = extractCouponFromResultSet(resultSet);
			
			if(DateUtils.isCurrentDateAfterEndDate(coupon.getEndDate())) {  	//compare between the coupon end date and current date
				return false;		//coupon is out of date
			}else {
				return true;		//coupon date is OK
			}	
		}
			
		catch(SQLException e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CouponDao, checkCouponDate(); FAILED");
		}
		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}
	
	public boolean isCouponExistByName(Coupon coupon) throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = JdbcUtils.getConnection();
			String sql = "SELECT * FROM coupon WHERE TITLE = ?";	//select coupon from the DB by coupon name.
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, coupon.getTitle());
			resultSet = preparedStatement.executeQuery();	//extract the result to resultSet.
			
			if(!resultSet.next()) {
				return false;		//return false if the result set is empty, which means the coupon not exist in the DB.
			}else {
				return true;		//return true if the result set isn't empty, which means there is a coupon with that name in the DB.
			}
		}
			catch(SQLException e) {
				throw new ApplicationException(ErrorType.SYSTEM_ERROR, "Error in CouponDao, isCouponExistByName(); FAILED");
			}
		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}
	
	public static void deleteExpiredCoupon() throws ApplicationException {
		
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		
		try {
			String currentDate = DateUtils.getCurrentDate();
			connection = JdbcUtils.getConnection();
			
			String sql = "delete FROM coupon WHERE END_DATE < ?";	
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, currentDate);
			preparedStatement.executeUpdate();
		}
		
		catch (SQLException e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CouponDao, deleteExpiredCoupon(); FAILED");	
		}
		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public List<Coupon> getCouponsByMinimumPrice(double price) throws ApplicationException {
		
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;
		Coupon coupon = null;
		List<Coupon> minPriceSortedCouponsList = new ArrayList<Coupon>();
		
		
		try {
			connection = JdbcUtils.getConnection();
			String sql = "SELECT * FROM coupon WHERE PRICE >= ?";	//select coupon from the DB by coupon name.
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setDouble(1, price);
			resultSet = preparedStatement.executeQuery(); 

			if(!resultSet.next()) {
				return null;		//לכסות ברקונטרולר
			}
			while (resultSet.next()) {
				coupon = extractCouponFromResultSet(resultSet);
				minPriceSortedCouponsList.add(coupon);
			}
			
		}
		catch(SQLException e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CouponDao, getCouponsByMinimumPrice(); FAILED");
		}
		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
		return minPriceSortedCouponsList;	
	}
	
	public List<Coupon> getCouponsByMaximumPrice(double price) throws ApplicationException {
		
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;
		Coupon coupon = null;
		List<Coupon> maxPriceSortedCouponsList = new ArrayList<Coupon>();
		
		
		try {
			connection = JdbcUtils.getConnection();
			String sql = "SELECT * FROM coupon WHERE PRICE <= ?";	//select coupon from the DB by coupon name.
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setDouble(1, price);
			resultSet = preparedStatement.executeQuery(); 

			if(!resultSet.next()) {
				return null;		//לכסות ברקונטרולר
			}
			while (resultSet.next()) {
				coupon = extractCouponFromResultSet(resultSet);
				maxPriceSortedCouponsList.add(coupon);
			}
			
		}
		catch(SQLException e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CouponDao, getCouponsByMaximumPrice(); FAILED");
		}
		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
		return maxPriceSortedCouponsList;	
	}
	
	public List<Coupon> getCouponsByType(CouponType couponType) throws ApplicationException {
			
			PreparedStatement preparedStatement = null;
			Connection connection = null;
			ResultSet resultSet = null;
			Coupon coupon = null;
			List<Coupon> typeSortedCouponsList = new ArrayList<Coupon>();
			
			
			try {
				connection = JdbcUtils.getConnection();
				String sql = "SELECT * FROM coupon WHERE TYPE = ?";	//select coupon from the DB by coupon name.
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, couponType.name());
				resultSet = preparedStatement.executeQuery(); 

				if(!resultSet.next()) {
					return null;		 
				}
				while (resultSet.next()) {
					coupon = extractCouponFromResultSet(resultSet);
					typeSortedCouponsList.add(coupon);
				}
				
			}
			catch(SQLException e) {
				throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CouponDao, getCouponsByType(); FAILED");
			}
			finally {
				JdbcUtils.closeResources(connection, preparedStatement, resultSet);
			}
			return typeSortedCouponsList;	
		}

	public void updateCouponAmount(long couponId, int amount) throws ApplicationException{
		
		java.sql.PreparedStatement preparedStatement = null; 
		Connection connection = null; 
		
		try {
			connection = JdbcUtils.getConnection();
			String sql = "UPDATE coupon SET AMOUNT = ? WHERE ID = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, amount);
			preparedStatement.setLong(2, couponId);
		
			preparedStatement.executeUpdate();
		}
		
		catch (SQLException e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CouponDao, updateCouponAmount(); FAILED");
		}
		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}	


	public void purchaseCoupon(CustomerCoupon customerCoupon) throws ApplicationException{
		
		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = null;
		
		try {
			connection = JdbcUtils.getConnection();
			String sql = "insert into customer_coupon (CUSTOMER_ID, COUPON_ID) values(?,?)";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, customerCoupon.getCustomerId());
			preparedStatement.setLong(2, customerCoupon.getCouponId());
			
			preparedStatement.executeUpdate();
		}
		catch (SQLException e) {
			//throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CouponDao, purchaseCoupon(); FAILED");
			e.printStackTrace();
		}
		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}


}
