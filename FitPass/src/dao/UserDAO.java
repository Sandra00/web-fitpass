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
import beans.enums.UserType;
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
	
	public List<User> findUsersByUsername(List<String> usernames){
		List<User> users = new ArrayList<User>();
		for(String username : usernames) {
			users.add(findUserByUsername(username));
		}
		return users;
	}
	
	public List<User> findAll(){
		return users;
	}
	
	
	public List<User> findUsersVisitedSportsObject(String sportsObjectName) {
		List<User> userList = new ArrayList<User>();
		for (User user : users) {
			for (String sportsObject : user.getVisitedSportsObjects()) {
				if(sportsObject.equals(sportsObjectName) && !userList.contains(user)) {
					userList.add(user);
				}
			}
		}
		return userList;
	}
	
	public String findManagersSportsObjectName(String managerUsername) {
		User manager = findUserByUsername(managerUsername);
		return manager != null ? manager.getSportsObject() : null;
	}
	
	public boolean newCustomer(User user) {
		if(!checkExisting(user)) {
			user.setOldUsername(user.getUsername());
			user.setUserType(UserType.CUSTOMER);
			users.add(user);
			saveUsers();
			return true;
		}
		saveUsers();
		return false;
	}
	
	public boolean newManager(User user) {
		if(!checkExisting(user)) {
			user.setOldUsername(user.getUsername());
			user.setUserType(UserType.MANAGER);
			users.add(user);
			saveUsers();
			return true;
		}
		return false;
	}
	
	public boolean newCoach(User user) {
		if(!checkExisting(user)) {
			user.setOldUsername(user.getUsername());
			user.setUserType(UserType.COACH);
			users.add(user);
			saveUsers();
			return true;
		}
		return false;
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
	
	
	public boolean checkExisting(User user) {
		for(User u : users) {
			if(u.getUsername().equals(user.getUsername())) return true;
		}
		return false;
	}
	
	public boolean editUser(User user) {
		if(checkExisting(user) && !user.getUsername().equals(user.getOldUsername())) {
			return  false;
		}
		User userForChange = findUserByUsername(user.getOldUsername());
		userForChange.setUsername(user.getUsername());
		userForChange.setOldUsername(user.getUsername());
		userForChange.setName(user.getName());
		userForChange.setSurname(user.getSurname());
		userForChange.setGender(user.getGender());
		userForChange.setDateOfBirth(user.getDateOfBirth());
		userForChange.setPassword(user.getPassword());
		saveUsers();
		return true;
	}
	
	public ArrayList<User> getFreeManagers(){
		ArrayList<User> managers = new ArrayList<User>();
		for(User user : users) {
			if(user.getUserType() == UserType.MANAGER && user.getSportsObject() == null) managers.add(user);
		}
		return managers;
	}
	
	public void addSportsObject(User user) {
		User manager = findUserByUsername(user.getUsername());
		manager.setSportsObject(user.getSportsObject());
		saveUsers();
	}
}
