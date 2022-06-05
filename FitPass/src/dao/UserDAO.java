package dao;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.User;

public class UserDAO {
	private List<User> users;
	private String contextPath;
	
	public UserDAO(String path) {
		users = new ArrayList<User>();
		this.contextPath = path;
		loadUsers(path);
	}
	
	
	public User findUserByUsername(String username) {
		for(User user : users) {
			if(user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}
	public List<User> findAll(){
		return users;
	}
	
	public User newCustomer(User user) {
		users.add(user);
		saveUsers(this.contextPath);
		return user;
	}
	
	private void saveUsers(String path) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(Paths.get(path + "users.txt").toFile(), users);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void loadUsers(String path) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			System.out.println(path + "users.txt");
			users = new ArrayList<>(Arrays.asList(mapper.readValue(Paths.get(path + "users.txt").toFile(), User[].class)));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
