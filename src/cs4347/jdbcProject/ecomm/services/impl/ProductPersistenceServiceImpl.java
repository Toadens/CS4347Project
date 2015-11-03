package cs4347.jdbcProject.ecomm.services.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import cs4347.jdbcProject.ecomm.dao.ProductDAO;
import cs4347.jdbcProject.ecomm.dao.impl.ProductDaoImpl;
import cs4347.jdbcProject.ecomm.entity.Product;
import cs4347.jdbcProject.ecomm.services.ProductPersistenceService;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class ProductPersistenceServiceImpl implements ProductPersistenceService
{
	private DataSource dataSource;

	public ProductPersistenceServiceImpl(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}

	@Override
	public Product create(Product product) throws SQLException, DAOException {
		ProductDAO productDAO = new ProductDaoImpl();
		
		Connection connection = dataSource.getConnection();
		try{
			connection.setAutoCommit(false);
			
			Product prod = productDAO.create(connection, product);
			
			if(prod.getId() != null){
				throw new DAOException("Trying to return a product with a non-null ID");
			}
			
			connection.commit();
			return prod;
		}
		catch (Exception ex) {
			connection.rollback();
			throw ex;
		}
		finally {
			if (connection != null) {
				connection.setAutoCommit(true);
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
	}

	@Override
	public Product retrieve(Long id) throws SQLException, DAOException {
		ProductDAO productDAO = new ProductDaoImpl();
		Connection connection = dataSource.getConnection();
		
		try{
			if(id == null) throw new DAOException("Trying to get a product with a null ID");
			else {
				Product prod = new Product();
				prod = productDAO.retrieve(connection, id);
				return prod;
			}
		}
		catch (Exception ex){
			throw ex;
		}
		finally {
			if (connection != null) {
				connection.setAutoCommit(true);
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
	}

	@Override
	public int update(Product product) throws SQLException, DAOException {
		ProductDAO productDAO = new ProductDaoImpl();
		Connection connection = dataSource.getConnection();
		
		try{
			if(product.getId() == null) throw new DAOException("Trying to update a product with a null ID");
			else {
				Product prod = new Product();
				productDAO.update(connection, product);
			}
		}
		catch (Exception ex){
			throw ex;
		}
		finally {
			if (connection != null) {
				connection.setAutoCommit(true);
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
		return 0;
	}

	@Override
	public int delete(Long id) throws SQLException, DAOException {
		ProductDAO productDAO = new ProductDaoImpl();
		Connection connection = dataSource.getConnection();
		
		try{
			if(id == null) throw new DAOException("Trying to delete a product with a null ID");
			else {
				productDAO.delete(connection, id);
			}
		}
		catch (Exception ex){
			throw ex;
		}
		finally {
			if (connection != null) {
				connection.setAutoCommit(true);
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
		return 0;
	}

	@Override
	public Product retrieveByUPC(String upc) throws SQLException, DAOException {
		ProductDAO productDAO = new ProductDaoImpl();
		Connection connection = dataSource.getConnection();
		
		try{
			if(upc == null) throw new DAOException("Trying to retrieve a product with a null UPC");
			else {
				productDAO.retrieveByUPC(connection, upc);
			}
		}
		catch (Exception ex){
			throw ex;
		}
		finally {
			if (connection != null) {
				connection.setAutoCommit(true);
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
		return null;
	}

	@Override
	public List<Product> retrieveByCategory(int category) throws SQLException, DAOException {
		ProductDAO productDAO = new ProductDaoImpl();
		Connection connection = dataSource.getConnection();
		
		try{
				productDAO.retrieveByCategory(connection, category);
		}
		catch (Exception ex){
			throw ex;
		}
		finally {
			if (connection != null) {
				connection.setAutoCommit(true);
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
		return null;
	}

}
