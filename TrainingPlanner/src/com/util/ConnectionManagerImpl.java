package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManagerImpl implements ConnectionManager {
	Connection con=null;

	@Override
	public Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("driver loaded");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/trainingdb", "root", "root");
			System.out.println("connection established");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return con;
	}

	@Override
	public void closeConnection() {
		// TODO Auto-generated method stub
		if(con!=null)
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
