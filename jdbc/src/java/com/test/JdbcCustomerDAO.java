/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author leadscorp
 */
public class JdbcCustomerDAO implements CustomerDAO{
    private DataSource dataSource;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    
    @Override
    public void insert(Customer customer) {
        try {
            String sql = "INSERT INTO CUSTOMER " +
                    "(CUST_ID, NAME, AGE) VALUES (?, ?, ?)";
            Connection conn = null;
            conn=dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, customer.getCustId());
            ps.setString(2, customer.getName());
            ps.setInt(3, customer.getAge());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(JdbcCustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//insert data

    @Override
    public Customer findByCustomerId(int custId) {
            String sql = "SELECT * FROM CUSTOMER WHERE CUST_ID = ?";
            Connection conn = null;
            
        try {
            conn = dataSource.getConnection();
        
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, custId);
			Customer customer = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				customer = new Customer(
					rs.getInt("CUST_ID"),
					rs.getString("NAME"), 
					rs.getInt("Age")
				);
			}
			rs.close();
			ps.close();
			return customer;
	
            } catch (SQLException ex) {
            Logger.getLogger(JdbcCustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
}
