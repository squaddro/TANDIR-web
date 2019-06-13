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
import java.util.ArrayList;

import com.squadro.tandir.message.Recipe;
import com.squadro.tandir.message.User;

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
	
	public static Recipe[] getAllRecipes(){	
		ArrayList<Recipe> recipes = new ArrayList<Recipe>();
		try {
			Connection conn = connect();
			
			
			String query = "SELECT * FROM RECIPE";
			PreparedStatement stmt = conn.prepareStatement(query);
			ResultSet resultSet = stmt.executeQuery();
			while(resultSet.next()) {
				String recipe_id = resultSet.getString("RECIPE_ID");
				String recipe_name = resultSet.getString("RECIPE_NAME");
				String recipe_desc = resultSet.getString("RECIPE_DESC");
				
				//get userid
				query = "SELECT * FROM ACCOUNT_RECIPE WHERE RECIPE_ID = ?";
				stmt = conn.prepareStatement(query);
				stmt.setString(1, recipe_id);
				ResultSet resultSet2 = stmt.executeQuery();
				Recipe recipe = null;
				if(resultSet2.next()){
					String user_id = resultSet2.getString("USER_ID");
					recipe = new Recipe(recipe_id, recipe_name, recipe_desc, user_id);
				}
				else {
					conn.close();
					return null;
				}
				recipes.add(recipe);
			}
			
			conn.close();
			return recipes.toArray(new Recipe[recipes.size()]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static User[] getAllUsers(){	
		ArrayList<User> users = new ArrayList<User>();
		try {
			Connection conn = connect();
			
			String query = "SELECT * FROM ACCOUNT";
			PreparedStatement stmt = conn.prepareStatement(query);
			ResultSet resultSet = stmt.executeQuery();
			while(resultSet.next()) {
				String user_id = resultSet.getString("USER_ID");
				
				//get recipe ids
				query = "SELECT * FROM ACCOUNT_RECIPE WHERE USER_ID = ?";
				stmt = conn.prepareStatement(query);
				stmt.setString(1, user_id);
				ResultSet resultSet2 = stmt.executeQuery();
				
				ArrayList<Recipe> recipes = new ArrayList<Recipe>();
				while(resultSet2.next()){
					recipes.add(Database.getRecipe(resultSet2.getString("RECIPE_ID")));
				}
				
				User user = new User(user_id, recipes.toArray(new Recipe[recipes.size()]));
				users.add(user);
			}
			
			conn.close();
			return users.toArray(new User[users.size()]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static User getUser(String id){	
		
		try {
			Connection conn = connect();
			
			String query = "SELECT * FROM ACCOUNT WHERE USER_ID = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, id);
			ResultSet resultSet = stmt.executeQuery();
			
			if(resultSet.next()) {
				String user_id = resultSet.getString("USER_ID");
				
				//get recipe ids
				query = "SELECT * FROM ACCOUNT_RECIPE WHERE USER_ID = ?";
				stmt = conn.prepareStatement(query);
				stmt.setString(1, user_id);
				resultSet = stmt.executeQuery();
				
				ArrayList<Recipe> recipes = new ArrayList<Recipe>();
				while(resultSet.next()){
					recipes.add(Database.getRecipe(resultSet.getString("RECIPE_ID")));
				}
				
				User user = new User(user_id, recipes.toArray(new Recipe[recipes.size()]));
				conn.close();
				return user;
			}
			
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static boolean deleteRecipe(String recipeid, String userid){
		try {
			Connection conn = connect();
			
			// check if user has recipe
			String query = "SELECT * FROM ACCOUNT_RECIPE WHERE USER_ID = ? AND RECIPE_ID = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, userid);
			stmt.setString(2, recipeid);
			ResultSet resultSet = stmt.executeQuery();
			if(!resultSet.next()){
				conn.close();
				return false;
			}
			
			query = "DELETE FROM ACCOUNT_RECIPE WHERE RECIPE_ID = ?";
			stmt = conn.prepareStatement(query);
			stmt.setString(1, recipeid);
			if(stmt.executeUpdate()==0){
				conn.close();
				return false;
			}
			
			query = "DELETE FROM RECIPE WHERE RECIPE_ID = ?";
			stmt = conn.prepareStatement(query);
			stmt.setString(1, recipeid);
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
	
	public static boolean updateRecipeName(String recipeid, String recipeName, String userid){
		try {
			Connection conn = connect();
			
			// check if user has recipe
			String query = "SELECT * FROM ACCOUNT_RECIPE WHERE USER_ID = ? AND RECIPE_ID = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, userid);
			stmt.setString(2, recipeid);
			ResultSet resultSet = stmt.executeQuery();
			if(!resultSet.next()){
				conn.close();
				return false;
			}
			
			// update recipe name
			query = "UPDATE RECIPE SET RECIPE_NAME = ? WHERE RECIPE_ID = ?";
			stmt = conn.prepareStatement(query);
			stmt.setString(1, recipeName);
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
	
	public static boolean updateRecipeDesc(String recipeid, String recipeDesc, String userid){
		try {
			Connection conn = connect();
			
			// check if user has recipe
			String query = "SELECT * FROM ACCOUNT_RECIPE WHERE USER_ID = ? AND RECIPE_ID = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, userid);
			stmt.setString(2, recipeid);
			ResultSet resultSet = stmt.executeQuery();
			if(!resultSet.next()){
				conn.close();
				return false;
			}
			
			// update recipe desc
			query = "UPDATE RECIPE SET RECIPE_DESC = ? WHERE RECIPE_ID = ?";
			stmt = conn.prepareStatement(query);
			stmt.setString(1, recipeDesc);
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
}