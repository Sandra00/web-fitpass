package beans;

import java.util.ArrayList;
import java.util.List;

import beans.enums.LocationType;

public class SportsObject {
	private String name;
	private LocationType locationType;
	private List<Content> content;
	private boolean status;
	private Location location;
	private String logo;
	public double averageGrade;
	private String startWorkingHour;
	private String endWorkingHour;
	private int gradesCounter;
	private boolean deleted;
	
	public SportsObject() {
		super();
	}


	public SportsObject(String name, LocationType locationType, List<Content> content, boolean status,
			Location location, String logo, double averageGrade, String startWorkingHour,
			String endWorkingHour, int gradesCounter, boolean deleted) {

		super();
		this.name = name;
		this.locationType = locationType;
		this.content = content;
		this.status = status;
		this.location = location;
		this.logo = logo;
		this.averageGrade = averageGrade;
		this.startWorkingHour = startWorkingHour;
		this.endWorkingHour = endWorkingHour;
		this.gradesCounter = gradesCounter;
		this.deleted = deleted;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocationType getLocationType() {
		return locationType;
	}

	public void setLocationType(LocationType locationType) {
		this.locationType = locationType;
	}

	public List<Content> getContent() {
		return content != null ? content : new ArrayList<Content>();
	}

	public void setContent(List<Content> content) {
		this.content = content;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}


	public double getAverageGrade() {
		return averageGrade;
	}

	public void setAverageGrade(double averageGrade) {
		this.averageGrade = averageGrade;
	}
	
	public String getStartWorkingHour() {
		return startWorkingHour;
	}

	public void setStartWorkingHour(String startWorkingHour) {
		this.startWorkingHour = startWorkingHour;
	}

	public String getEndWorkingHour() {
		return endWorkingHour;
	}
	
	public int getGradesCounter() {
		return gradesCounter;
	}

	public void setGradesCounter(int gradesCounter) {
		this.gradesCounter = gradesCounter;
	}

	public void setEndWorkingHour(String endWorkingHour) {
		this.endWorkingHour = endWorkingHour;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
}

