package com.squadro.tandir.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.net.URI;
import java.net.URISyntaxException;

public class Database {
	
	private Database() {
		// nothing
	}
	
	public static boolean checkPassword(String username, String password) {
		boolean result = false;
		try {
			String query = "SELECT * FROM ACCOUNT WHERE USER_ID = ?";

			Connection conn = connect();
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, username);
			ResultSet resultSet = stmt.executeQuery();
			if(resultSet.next()) {
				String pass = resultSet.getString("USER_PASSWORD");
				if(password.equals(pass)) {
					result = true;
				}
				else {
					result = false;
				}
			}
			else {
				result = false;
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		
		return result;
	}
	
	public static boolean addUser(String username, String password) {
		boolean result = false;
		try {
			Connection conn = connect();
			
			// check if user exists
			String query = "SELECT * FROM ACCOUNT WHERE USER_ID = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, username);
			ResultSet resultSet = stmt.executeQuery();
			if(resultSet.next()) {
				result = false;
			}
			else {
				query = "INSERT INTO ACCOUNT VALUES (?, ?)";
				stmt = conn.prepareStatement(query);
				stmt.setString(1, username);
				stmt.setString(2, password);
				if(stmt.executeUpdate()==0)
					result = false;
				else
					result = true;
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		
		return result;
	}
	
	private static Connection connect() throws URISyntaxException, SQLException {
		String dbUrl = System.getenv("DBASE_URL");
		return DriverManager.getConnection(dbUrl);
	}
}