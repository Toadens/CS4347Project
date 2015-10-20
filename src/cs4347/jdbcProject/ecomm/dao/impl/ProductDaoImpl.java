package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cs4347.jdbcProject.ecomm.dao.ProductDAO;
import cs4347.jdbcProject.ecomm.entity.Product;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class ProductDaoImpl implements ProductDAO
{
	private final static String insertSQL = 
			"INSERT INTO Product (prodName, prodDescription, prodCategory, prodUPC) VALUES (?, ?, ?, ?);";
	
	private final static String selectQuery = 
		    "SELECT id, prodName, prodDescription, prodCategory, prodUPC, FROM Product where id = ?;";
	
	private final static String selectByCategory= 
		    "SELECT id, prodName, prodDescription, prodUPC, FROM Product WHERE prodCategory = ?;";
	
	private final static String selectByUPC= 
		    "SELECT id, prodName, prodDescription, prodCategory, FROM Product WHERE prodUPC = ?;";
	
	private final static String updateSQL = 
			"UPDATE Product SET prodName = ?, prodDescription = ?, prodCategory = ?, prodUPC = ? WHERE id = ?;";
	
	private final static String deleteSQL = 
		    "DELETE FROM Product WHERE id = ?;";

	// Implemented
	@Override
	public Product create(Connection connection, Product product) throws SQLException, DAOException 
	{
		if (product.getId() != null) { throw new DAOException("Trying to insert Product with NON-NULL ID"); }
		
		PreparedStatement ps = null;
		ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
		
		try 
		{
			ps.setString(1, product.getProdName());
			ps.setString(2, product.getProdDescription());
			ps.setInt(3, product.getProdCategory());
			ps.setString(4, product.getProdUPC());
		
			ps.executeUpdate();
			
			ResultSet keyRS = ps.getGeneratedKeys();
			keyRS.next();
			int lastKey = keyRS.getInt(1);
			product.setId((long) lastKey);
		
			return product;
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
	public Product retrieve(Connection connection, Long id) throws SQLException, DAOException 
	{
		if (id == null) { throw new DAOException("Trying to retrieve Product with NULL ID"); }
		
		PreparedStatement ps = null;
		try 
		{
			ps = connection.prepareStatement(selectQuery);
			ps.setLong(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			if(!rs.next())  { return null; }
			
			Product prod = new Product();
			prod.setId(rs.getLong("id"));
			prod.setProdName(rs.getString("prodName"));
			prod.setProdDescription(rs.getString("prodDescription"));
			prod.setProdCategory(rs.getInt("prodCategory"));
			prod.setProdUPC(rs.getString("prodUPC"));
			
			return prod;
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
	public int update(Connection connection, Product product) throws SQLException, DAOException 
	{
		if (product.getId() == null) { throw new DAOException("Trying to update Product with NULL ID"); }
		
		PreparedStatement ps = null;
		try 
		{
			ps = connection.prepareStatement(updateSQL);
			ps.setString(1, product.getProdName());
			ps.setString(2, product.getProdDescription());
			ps.setInt(3, product.getProdCategory());
			ps.setString(4, product.getProdUPC());
			ps.setLong(5, product.getId());
			
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
		if (id == null) { throw new DAOException("Trying to delete Product with NULL ID"); }

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
	public List<Product> retrieveByCategory(Connection connection, int category) throws SQLException, DAOException 
	{
		List<Product> result = new ArrayList<Product>();
		
		PreparedStatement ps = null;
		try 
		{
			ps = connection.prepareStatement(selectByCategory);
			ps.setInt(1, category);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) 
			{
				Product prod = new Product();
				prod.setId(rs.getLong("id"));
				prod.setProdName(rs.getString("prodName"));
				prod.setProdDescription(rs.getString("prodDescription"));
				prod.setProdUPC(rs.getString("prodUPC"));
				
				result.add(prod);
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
	public Product retrieveByUPC(Connection connection, String upc) throws SQLException, DAOException 
	{
		PreparedStatement ps = null;
		try 
		{
			ps = connection.prepareStatement(selectByUPC);
			ps.setString(1, upc);
			ResultSet rs = ps.executeQuery();
			
			if(!rs.next())  { return null; }
			
			Product prod = new Product();
			prod.setId(rs.getLong("id"));
			prod.setProdName(rs.getString("prodName"));
			prod.setProdCategory(rs.getInt("prodCategory"));
			prod.setProdDescription(rs.getString("prodDescription"));
			
			return prod;
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
