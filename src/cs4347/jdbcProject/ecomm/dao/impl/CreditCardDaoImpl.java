package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cs4347.jdbcProject.ecomm.dao.CreditCardDAO;
import cs4347.jdbcProject.ecomm.entity.Address;
import cs4347.jdbcProject.ecomm.entity.CreditCard;
import cs4347.jdbcProject.ecomm.util.DAOException;


public class CreditCardDaoImpl implements CreditCardDAO
{

	@Override
	public CreditCard create(Connection connection, CreditCard creditCard, Long customerID)
			throws SQLException, DAOException {
		final String createQuery = "INSERT INTO Address (name, ccNumber, expDate, securityCode, customerID) VALUES (?,?,?,?,?);";
		if(customerID == null || creditCard == null){
			throw new DAOException("Attempting to insert an address with a null customer id or null address");
		}
		
		PreparedStatement ps = null;
		try{
			ps = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, creditCard.getName());
			ps.setString(2, creditCard.getCcNumber());
			ps.setString(3, creditCard.getExpDate());
			ps.setString(4, creditCard.getSecurityCode());
			ps.setLong(5, customerID);
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
		return null;
	}

	@Override
	public CreditCard retrieveForCustomerID(Connection connection, Long customerID) throws SQLException, DAOException {
		if (customerID == null){
			throw new DAOException("Trying to retrieve an address from a null customer id");
		}
		final String retrieveQuery = "SELECT name, ccNumber, expDate, securityCode WHERE customerID = ?";
		PreparedStatement ps = null;
		try{
			ps=connection.prepareStatement(retrieveQuery);
			ps.setLong(1, customerID);
			ResultSet rs = ps.executeQuery();
			
			if(!rs.next()){
				return null;
			}
			
			CreditCard cc = new CreditCard();
			cc.setName(rs.getString("name"));
			cc.setCcNumber(rs.getString("ccNumber"));
			cc.setExpDate(rs.getString("expDate"));
			cc.setSecurityCode(rs.getString("securityCode"));
			return cc;
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
	}

	@Override
	public void deleteForCustomerID(Connection connection, Long customerID) throws SQLException, DAOException {
		if(customerID == null) throw new DAOException("Trying to delete a credit card for a null customer id");
		final String deleteQuery = "DELETE FROM CreditCard WHERE customerID = ?";
		PreparedStatement ps = null;
		try{
			ps = connection.prepareStatement(deleteQuery);
			ps.setLong(1, customerID);
			ps.executeUpdate();
		}
		finally 
		{
			if (ps != null && !ps.isClosed()) 
			{
				ps.close();
			}
			if (connection != null && !connection.isClosed()) 
			{
				connection.close();
			}
		}
	}

}
