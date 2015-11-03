package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cs4347.jdbcProject.ecomm.dao.AddressDAO;
import cs4347.jdbcProject.ecomm.entity.Address;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class AddressDaoImpl implements AddressDAO
{

	@Override
	public Address create(Connection connection, Address address, Long customerID) throws SQLException, DAOException {
		final String createQuery = "INSERT INTO Address (address1, address2, city, state, zipcode, customerID) VALUES (?,?,?,?,?,?);";
		if(customerID == null || address == null){
			throw new DAOException("Attempting to insert an address with a null customer id or null address");
		}
		
		PreparedStatement ps = null;
		try{
			ps = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);
			System.out.println("Here we are!!!");
			ps.setString(1, address.getAddress1());
			ps.setString(2, address.getAddress2());
			ps.setString(3, address.getCity());
			ps.setString(4, address.getState());
			ps.setString(5, address.getZipcode());
			ps.setLong(6, customerID);
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
	public Address retrieveForCustomerID(Connection connection, Long customerID) throws SQLException, DAOException {
		if (customerID == null){
			throw new DAOException("Trying to retrieve an address from a null customer id");
		}
		final String retrieveQuery = "SELECT address1, address2, city, state, zipcode WHERE customerID = ?";
		PreparedStatement ps = null;
		try{
			ps=connection.prepareStatement(retrieveQuery);
			ps.setLong(1, customerID);
			ResultSet rs = ps.executeQuery();
			
			if(!rs.next()){
				return null;
			}
			
			Address ad = new Address();
			ad.setAddress1(rs.getString("address1"));
			ad.setAddress2(rs.getString("address2"));
			ad.setCity(rs.getString("city"));
			ad.setState(rs.getString("state"));
			ad.setZipcode(rs.getString("zipcode"));
			
			return ad;
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
		if(customerID == null){
			throw new DAOException("Trying to delete an address for a null customer Id");
		}
		final String deleteQuery = "DELETE FROM Address WHERE customerID = ?;";
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
