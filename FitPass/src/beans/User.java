package beans;

import java.time.LocalDateTime;
import java.util.List;

import enums.UserType;


public class User {
	private String username;
	private String password;
	private String name;
	private String surname;
	private String gender;
	private LocalDateTime dateOfBirth;
	private UserType userType;
	private List<Training> trainings;
	private int due; //clanarina
	private SportsObject sportsObject;
	private List<SportsObject> visitedSportsObjects;
	private int points;
	private CustomerType customerType;
	
	public User() {
		super();
	}

	public User(String username, String password, String name, String surname, String gender, LocalDateTime dateOfBirth,
			UserType userType, List<Training> trainings, int due, SportsObject sportsObject,
			List<SportsObject> visitedSportsObjects, int points, CustomerType customerType) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.userType = userType;
		this.trainings = trainings;
		this.due = due;
		this.sportsObject = sportsObject;
		this.visitedSportsObjects = visitedSportsObjects;
		this.points = points;
		this.customerType = customerType;
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

	public LocalDateTime getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDateTime dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public List<Training> getTrainings() {
		return trainings;
	}

	public void setTrainings(List<Training> trainings) {
		this.trainings = trainings;
	}

	public int getDue() {
		return due;
	}

	public void setDue(int due) {
		this.due = due;
	}

	public SportsObject getSportsObject() {
		return sportsObject;
	}

	public void setSportsObject(SportsObject sportsObject) {
		this.sportsObject = sportsObject;
	}

	public List<SportsObject> getVisitedSportsObjects() {
		return visitedSportsObjects;
	}

	public void setVisitedSportsObjects(List<SportsObject> visitedSportsObjects) {
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
	
	
}
