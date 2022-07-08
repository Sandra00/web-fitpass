package dao;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Membership;
import beans.User;
import beans.enums.UserType;
import util.PersonalConfig;

public class UserDAO {
	private List<User> users;
	private String pathToFile = PersonalConfig.PROJECT_FOLDER_PATH + "\\WebContent\\users.json";
	
	public UserDAO() {
		users = new ArrayList<User>();
		load();
	}
	
	public User findUserByUsername(String username) {
		for(User user : users) {
			if(user.getUsername().equals(username) && !user.isDeleted()) {
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
		List<User> allUsers = new ArrayList<User>();
		for(User user : users)
		{
			if(!user.isDeleted()) {
				allUsers.add(user);
			}
		}
		return allUsers;
	}
	
	
	public List<User> findUsersVisitedSportsObject(String sportsObjectName) {
		List<User> userList = new ArrayList<User>();
		for (User user : users) {
			for (String sportsObject : user.getVisitedSportsObjects()) {
				if(sportsObject.equals(sportsObjectName) && !userList.contains(user) && !user.isDeleted()) {
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
	
	public List<User> findAllCoaches(){
		List<User> coaches = new ArrayList<User>();
		for(User user : users) {
			if(user.getUserType() == UserType.COACH && !user.isDeleted()) {
				coaches.add(user);
			}
		}
		return coaches;
	}
	
	public boolean newCustomer(User user) {
		if(!checkExisting(user)) {
			user.setOldUsername(user.getUsername());
			user.setUserType(UserType.CUSTOMER);
			users.add(user);
			save();
			return true;
		}
		save();
		return false;
	}
	
	public boolean newManager(User user) {
		if(!checkExisting(user)) {
			user.setOldUsername(user.getUsername());
			user.setUserType(UserType.MANAGER);
			user.setDeleted(false);
			users.add(user);
			save();
			return true;
		}
		return false;
	}
	
	public boolean newCoach(User user) {
		if(!checkExisting(user)) {
			user.setOldUsername(user.getUsername());
			user.setUserType(UserType.COACH);
			user.setDeleted(false);
			users.add(user);
			save();
			return true;
		}
		return false;
	}
	
	public boolean setMembership(String username, Membership membership) {
		if(!exists(username)) {
			return false;
		}
		findUserByUsername(username).setMembership(membership);
		save();
		return true;
	}
	
	public boolean delete(String username) {
		if(findUserByUsername(username) != null) {
			findUserByUsername(username).setDeleted(true);
			return true;
		}
		return false;
	}
	
	private boolean exists(String username) {
		for(User user : users) {
			if(user.getUsername().equals(username) && !user.isDeleted()) {
				return true;
			}
		}
		return false;
	}
	
	public void save() {
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
	
	private void load() {
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
			if(u.getUsername().equals(user.getUsername()) && !u.isDeleted()) {
				return true;
			}
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
		save();
		return true;
	}
	
	public ArrayList<User> getFreeManagers(){
		ArrayList<User> managers = new ArrayList<User>();
		for(User user : users) {
			if(user.getUserType() == UserType.MANAGER && !user.isDeleted() && user.getSportsObject() == null) managers.add(user);
		}
		return managers;
	}
	
	public void addSportsObject(User user) {
		User manager = findUserByUsername(user.getUsername());
		manager.setSportsObject(user.getSportsObject());
		save();
	}
}
