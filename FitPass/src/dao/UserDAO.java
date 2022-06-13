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
import beans.enums.UserType;

public class UserDAO {
	private List<User> users;
	private String contextPath;
	
	public UserDAO(String path) {
		users = new ArrayList<User>();
		this.contextPath = path;
		loadUsers(path);
	}
	
	
	public User findUserByUsername(String username) {
		//System.out.println(username);
		for(User user : users) {
			//System.out.println(user.getUsername());
			if(user.getUsername().equals(username)) {
				//System.out.println("nasao");
				return user;
			}
		}
		return null;
	}
	public List<User> findAll(){
		return users;
	}
	
	
	public boolean newCustomer(User user) {
		if(!checkExisting(user)) {
			user.setOldUsername(user.getUsername());
			user.setUserType(UserType.CUSTOMER);
			users.add(user);
			return true;
		}
		saveUsers(this.contextPath);
		return false;
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
			//System.out.println(file.getPath());
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
	
	
	public boolean checkExisting(User user) {
		for(User u : users) {
			if(u.getUsername().equals(user.getUsername())) return true;
		}
		return false;
	}
	
	public boolean editUser(User user) {
		if(checkExisting(user)) {
			return  false;
		}
		User userForChange = findUserByUsername(user.getOldUsername());
		userForChange.setUsername(user.getUsername());
		userForChange.setOldUsername(user.getUsername());
		userForChange.setName(user.getName());
		userForChange.setSurname(user.getSurname());
		userForChange.setDateOfBirth(user.getGender());
		userForChange.setPassword(user.getPassword());
		saveUsers(this.contextPath);
		return true;
	}
}
