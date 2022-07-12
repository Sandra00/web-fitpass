package beans;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	private Membership membership;
	private String sportsObject;
	private Set<String> visitedSportsObjects;
	private int points;
	private CustomerType customerType;
	private boolean isDeleted;
	
	public User() {
		super();
	}

	public User(String oldUsername, String username, String password, String name, String surname, String gender, String dateOfBirth,
			UserType userType, List<Integer> trainings, Membership membership, String sportsObject,
			Set<String> visitedSportsObjects, int points, CustomerType customerType) {
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
		this.membership = membership;
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

	public Membership getMembership() {
		return membership;
	}

	public void setMembership(Membership membership) {
		this.membership = membership;
	}

	public String getSportsObject() {
		return sportsObject;
	}

	public void setSportsObject(String sportsObject) {
		this.sportsObject = sportsObject;
	}

	public Set<String> getVisitedSportsObjects() {
		if(visitedSportsObjects == null) {
			visitedSportsObjects = new HashSet<String>();
		}
		return visitedSportsObjects;
	}

	public void setVisitedSportsObjects(Set<String> visitedSportsObjects) {
		this.visitedSportsObjects = visitedSportsObjects;
	}

	public int getPoints() {
		if(this.membership != null && this.membership.getMembershipStatus() == MembershipStatus.INACTIVE && !this.membership.isCalculatedPoints()) {
			double usedTrainingsPercentage = (double) this.membership.getTrainingsUsed() / (double) this.membership.getNumberOfTrainings();
			if(usedTrainingsPercentage < 0.3) {
				double lostPoints = this.membership.getPrice() / 1000 * 133 * 4;
				this.points -= (int) lostPoints;
				this.membership.setCalculatedPoints(true);
				return this.points;
			}
			else {
				double wonPoints = this.membership.getPrice() / 1000 * this.membership.getTrainingsUsed();
				this.points += (int) wonPoints;
				this.membership.setCalculatedPoints(true);
				return this.points;
			}
		}
		else {
			return this.points;
		}
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public CustomerType getCustomerType() {
		if(userType != UserType.CUSTOMER) {
			return null;
		}
		if(this.points >= 1000 && this.points <= 2000) {
			this.customerType = CustomerType.BRONZE;
		} else if(this.points > 2000 && this.points <= 3000) {
			this.customerType = CustomerType.SILVER;
		} else if(this.points > 3000) {
			this.customerType = CustomerType.GOLD;
		}
		else {
			this.customerType = null;
		}
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
