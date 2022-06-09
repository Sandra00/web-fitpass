package dao;

import java.io.File;
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
		System.out.println(username);
		for(User user : users) {
			System.out.println(user.getUsername());
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
			mapper.writeValue(Paths.get("users.txt").toFile(), users);
			System.out.println(users.size());
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
			File file  = Paths.get("users.txt").toFile();
			users = new ArrayList<>(Arrays.asList(mapper.readValue(Paths.get("users.txt").toFile(), User[].class)));
			System.out.println(users.size());
			//System.out.println(Paths.get("users.txt"));
			System.out.println(file.getPath());
		} catch (JsonParseException e) {
			e.printStackTrace();
			System.out.println("greska1");
		} catch (JsonMappingException e) {
			e.printStackTrace();
			System.out.println("greska2");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("greska3");
		}
	}
	
}
