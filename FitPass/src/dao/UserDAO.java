package dao;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.User;
import util.PersonalConfig;

public class UserDAO {
	private List<User> users;
	private String pathToFile = PersonalConfig.PROJECT_FOLDER_PATH + "\\WebContent\\users.json";
	
	public UserDAO() {
		users = new ArrayList<User>();
		loadUsers();
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
		saveUsers();
		return user;
	}
	
	private void saveUsers() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(Paths.get(pathToFile).toFile(), users);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void loadUsers() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			users = new ArrayList<>(Arrays.asList(mapper.readValue(Paths.get(pathToFile).toFile(), User[].class)));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
