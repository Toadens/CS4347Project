package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cs4347.jdbcProject.ecomm.dao.PurchaseDAO;
import cs4347.jdbcProject.ecomm.entity.Product;
import cs4347.jdbcProject.ecomm.entity.Purchase;
import cs4347.jdbcProject.ecomm.services.PurchaseSummary;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class PurchaseDaoImpl implements PurchaseDAO
{
	private final static String insertSQL = 
			"INSERT INTO Purchase (productID, customerID, purchaseDate, purchaseAmount) VALUES (?, ?, ?, ?);";
	
	private final static String selectQuery = 
		    "SELECT id, productID, customerID, purchaseDate, purchaseAmount, FROM Purchase where id = ?;";
	
	private final static String selectByCustomerID= 
		    "SELECT id, productID, purchaseDate, purchaseAmount FROM Purchase WHERE customerID = ?;";
	
	private final static String selectByProductID= 
		    "SELECT id, customerID, purchaseDate, purchaseAmount FROM Purchase WHERE productID = ?;";
	
	private final static String updateSQL = 
			"UPDATE Purchase SET productID = ?, customerID = ?, purchaseDate = ?, purchaseAmount = ? WHERE id = ?;";
	
	private final static String deleteSQL = 
		    "DELETE FROM Purchase WHERE id = ?;";
	
	// Implemented
	@Override
	public Purchase create(Connection connection, Purchase purchase) throws SQLException, DAOException 
	{
		if (purchase.getId() != null) { throw new DAOException("Trying to insert Purchase with NON-NULL ID"); }
		
		PreparedStatement ps = null;
		ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
		
		try 
		{
			ps.setLong(1, purchase.getProductID());
			ps.setLong(2, purchase.getCustomerID());
			ps.setDate(3, purchase.getPurchaseDate());
			ps.setDouble(4, purchase.getPurchaseAmount());
		
			ps.executeUpdate();
			
			ResultSet keyRS = ps.getGeneratedKeys();
			keyRS.next();
			int lastKey = keyRS.getInt(1);
			purchase.setId((long) lastKey);
		
			return purchase;
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

	// Implemented
	@Override
	public Purchase retrieve(Connection connection, Long id) throws SQLException, DAOException
	{
		if (id == null) { throw new DAOException("Trying to retrieve Purchase with NULL ID"); }
		
		PreparedStatement ps = null;
		try 
		{
			ps = connection.prepareStatement(selectQuery);
			ps.setLong(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			if(!rs.next())  { return null; }
			
			Purchase purch = new Purchase();
			purch.setId(rs.getLong("id"));
			purch.setCustomerID(rs.getLong("customerID"));
			purch.setProductID(rs.getLong("productID"));
			purch.setPurchaseDate(rs.getDate("purchaseDate"));
			purch.setPurchaseAmount(rs.getDouble("purchaseAmount"));
			
			return purch;
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

	// Implemented
	@Override
	public int update(Connection connection, Purchase purchase) throws SQLException, DAOException
	{
		if (purchase.getId() == null) { throw new DAOException("Trying to update Purchase with NULL ID"); }
		
		PreparedStatement ps = null;
		try 
		{
			ps = connection.prepareStatement(updateSQL);
			ps.setLong(1, purchase.getProductID());
			ps.setLong(2, purchase.getCustomerID());
			ps.setDate(3, purchase.getPurchaseDate());
			ps.setDouble(4, purchase.getPurchaseAmount());
			ps.setLong(5, purchase.getId());
			
			int rows = ps.executeUpdate();
			return rows;
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

	// Implemented
	@Override
	public int delete(Connection connection, Long id) throws SQLException, DAOException
	{
		if (id == null) { throw new DAOException("Trying to delete Purchase with NULL ID"); }

		PreparedStatement ps = null;
		try 
		{
			ps = connection.prepareStatement(deleteSQL);
			ps.setLong(1, id);

			int rows = ps.executeUpdate();
			return rows;
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

	// Implemented
	@Override
	public List<Purchase> retrieveForCustomerID(Connection connection, Long customerID) throws SQLException, DAOException 
	{
		List<Purchase> result = new ArrayList<Purchase>();
		
		PreparedStatement ps = null;
		try 
		{
			ps = connection.prepareStatement(selectByCustomerID);
			ps.setLong(1, customerID);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) 
			{
				Purchase purch = new Purchase();
				
				purch.setId(rs.getLong("id"));
				purch.setProductID(rs.getLong("productID"));
				purch.setPurchaseDate(rs.getDate("purchaseDate"));
				purch.setPurchaseAmount(rs.getDouble("purchaseAmount"));
								
				result.add(purch);
			}
			return result;
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

	// Implemented
	@Override
	public List<Purchase> retrieveForProductID(Connection connection, Long productID) throws SQLException, DAOException 
	{
		List<Purchase> result = new ArrayList<Purchase>();
		
		PreparedStatement ps = null;
		try 
		{
			ps = connection.prepareStatement(selectByProductID);
			ps.setLong(1, productID);
			ResultSet rs = ps.executeQuery();
			
			if(!rs.next()) return null;
			
			while(rs.next()) 
			{
				Purchase purch = new Purchase();
				
				purch.setId(rs.getLong("id"));
				purch.setCustomerID(rs.getLong("customerID"));
				purch.setPurchaseDate(rs.getDate("purchaseDate"));
				purch.setPurchaseAmount(rs.getDouble("purchaseAmount"));
								
				result.add(purch);
			}
			return result;
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

	// Implemented
	@Override
	public PurchaseSummary retrievePurchaseSummary(Connection connection, Long customerID) throws SQLException, DAOException 
	{
		PurchaseSummary p1 = new PurchaseSummary();
		
		List<Purchase> allPurchases =  retrieveForCustomerID(connection, customerID);
		
		double min, max, avg = 0;
		
		if (allPurchases.isEmpty()) 
		{
			p1.avgPurchase = 0;
			p1.minPurchase = 0;
			p1.maxPurchase = 0;
			return p1;
		}
		
		min = allPurchases.get(0).getPurchaseAmount();
		max = allPurchases.get(0).getPurchaseAmount();
		avg += allPurchases.get(0).getPurchaseAmount();
		
		for (int i = 1; i < allPurchases.size(); i++)
		{
			double temp = allPurchases.get(i).getPurchaseAmount();
			avg += temp;
			if (temp > max) max = temp;
			if (temp < min) min = temp;
		}
		
		p1.avgPurchase = (float) (avg / allPurchases.size());
		p1.maxPurchase = (float) max;
		p1.minPurchase = (float) min;
		
		return p1;
	}
	
}
