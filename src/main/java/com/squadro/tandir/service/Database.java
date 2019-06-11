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

import com.squadro.tandir.message.Recipe;

public class Database {
	
	private Database() {
		// nothing
	}
		
	private static Connection connect() throws URISyntaxException, SQLException {
		String dbUrl = System.getenv("DBASE_URL");
		return DriverManager.getConnection(dbUrl);
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
	
	public static String getUserIdWithCookie(String cookie) {
		String result = null;
		try {
			Connection conn = connect();
			
			// check if user exists
			String query = "SELECT * FROM ACCOUNT WHERE USER_COOKIE = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, cookie);
			ResultSet resultSet = stmt.executeQuery();
			if(resultSet.next()) {
				result = resultSet.getString("USER_ID");
			}
			else {
				result = null;
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		
		return result;
	}
	
	public static boolean setCookie(String username, String cookie){
		boolean result = false;
		try {
			Connection conn = connect();
			
			// check if user exists
			String query = "SELECT * FROM ACCOUNT WHERE USER_ID = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, username);
			ResultSet resultSet = stmt.executeQuery();
			if(resultSet.next()) {
				query = "UPDATE ACCOUNT SET USER_COOKIE = ? WHERE USER_ID = ?";
				stmt = conn.prepareStatement(query);
				stmt.setString(1, cookie);
				stmt.setString(2, username);
				if(stmt.executeUpdate()==0)
					result = false;
				else
					result = true;
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
	
	public static boolean addRecipe(String recipeid, String name, String desc, String userid){
		try {
			Connection conn = connect();
			
			// check if user exists
			String query = "SELECT * FROM ACCOUNT WHERE USER_ID = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, userid);
			ResultSet resultSet = stmt.executeQuery();
			if(!resultSet.next()){
				conn.close();
				return false;
			}
			// check if recipe exists
			query = "SELECT * FROM RECIPE WHERE RECIPE_ID = ?";
			stmt = conn.prepareStatement(query);
			stmt.setString(1, recipeid);
			resultSet = stmt.executeQuery();
			if(resultSet.next()){
				conn.close();
				return false;
			}
			query = "INSERT INTO RECIPE VALUES(?, ?, ?)";
			stmt = conn.prepareStatement(query);
			stmt.setString(1, recipeid);
			stmt.setString(2, name);
			stmt.setString(3, desc);
			if(stmt.executeUpdate()==0){
				conn.close();
				return false;
			}
			query = "INSERT INTO ACCOUNT_RECIPE VALUES(?, ?)";
			stmt = conn.prepareStatement(query);
			stmt.setString(1, userid);
			stmt.setString(2, recipeid);
			if(stmt.executeUpdate()==0) {
				conn.close();
				return false;
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static Recipe getRecipe(String id){
		
		try {
			Connection conn = connect();
			
			// check if recipe exists
			String query = "SELECT * FROM RECIPE WHERE RECIPE_ID = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, id);
			ResultSet resultSet = stmt.executeQuery();
			if(resultSet.next()) {
				String recipe_id = resultSet.getString("RECIPE_ID");
				String recipe_name = resultSet.getString("RECIPE_NAME");
				String recipe_desc = resultSet.getString("RECIPE_DESC");
				
				//get userid
				query = "SELECT * FROM ACCOUNT_RECIPE WHERE RECIPE_ID = ?";
				stmt = conn.prepareStatement(query);
				stmt.setString(1, id);
				resultSet = stmt.executeQuery();
				if(resultSet.next()){
					String user_id = resultSet.getString("USER_ID");
					Recipe recipe = new Recipe(recipe_id, recipe_name, recipe_desc, user_id);
					conn.close();
					return recipe;
				}
			}
			
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}