package beans;

import java.util.ArrayList;
import java.util.List;

import beans.enums.*;


public class User {
	private String oldUsername;
	private String username;
	private String password;
	private String name;
	private String surname;
	private String gender;
	private String dateOfBirth;
	private UserType userType;
	private List<Integer> trainings;
	private List<Fee> fee;
	private String sportsObject;
<<<<<<< HEAD
	private List<String> visitedSportsObjects;
=======
	private List<SportsObject> visitedSportsObjects;
>>>>>>> fe8c60e7fdf9f2b0e9d0a7a5a9e4e0062eb2ed08
	private int points;
	private CustomerType customerType;
	private boolean isDeleted = false;
	
	public User() {
		super();
	}

	public User(String oldUsername, String username, String password, String name, String surname, String gender, String dateOfBirth,
<<<<<<< HEAD
			UserType userType, List<Integer> trainings, List<Fee> fee, String sportsObject,
			List<String> visitedSportsObjects, int points, CustomerType customerType) {
=======
			UserType userType, List<Training> trainings, List<Fee> fee, String sportsObject,
			List<SportsObject> visitedSportsObjects, int points, CustomerType customerType) {
>>>>>>> fe8c60e7fdf9f2b0e9d0a7a5a9e4e0062eb2ed08
		super();
		this.oldUsername = oldUsername;
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.userType = userType;
		this.trainings = trainings;
		this.fee = fee;
		this.sportsObject = sportsObject;
		this.visitedSportsObjects = visitedSportsObjects;
		this.points = points;
		this.customerType = customerType;
	}
	
	

	public User(String oldUsername, String username, String name, String surname, String gender,
			String dateOfBirth, String password) {
		super();
		this.oldUsername = oldUsername;
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public List<Integer> getTrainings() {
		return trainings;
	}

	public void setTrainings(List<Integer> trainings) {
		this.trainings = trainings;
	}

	public List<Fee> getFee() {
		return fee;
	}

	public void setFee(List<Fee> fee) {
		this.fee = fee;
	}

	public String getSportsObject() {
		return sportsObject;
	}

	public void setSportsObject(String sportsObject) {
		this.sportsObject = sportsObject;
	}

	public List<String> getVisitedSportsObjects() {
		return visitedSportsObjects != null ? visitedSportsObjects : new ArrayList<String>();
	}

	public void setVisitedSportsObjects(List<String> visitedSportsObjects) {
		this.visitedSportsObjects = visitedSportsObjects;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getOldUsername() {
		return oldUsername;
	}

	public void setOldUsername(String oldUsername) {
		this.oldUsername = oldUsername;
	}
	
	
	
}
