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
	
	public UserDAO(String path) {
		users = new ArrayList<User>();
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
	
	private void loadUsers(String path) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			users = new ArrayList<>(Arrays.asList(mapper.readValue(Paths.get(path + "users.txt").toFile(), User[].class)));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	
	public User newCustomer(User user) {
		users.add(user);
		return user;
	}
	
	public List<User> findAll(){
		return users;
	}
}
