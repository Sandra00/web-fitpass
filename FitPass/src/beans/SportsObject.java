package beans;

import java.time.LocalDateTime;
import java.util.List;

import beans.enums.*;

public class SportsObject {
	private String name;
	private LocationType locationType;
	private List<ContentType> contentTypes;
	private boolean status;
	public Location location;
	public String logo;
	public double averageGrade;
	private LocalDateTime startWorkingHour;
	private LocalDateTime endWorkingHour;
	
	public SportsObject() {
		super();
	}

	public SportsObject(String name, LocationType locationType, List<ContentType> contentTypes, boolean status,
			Location location, String logo, double averageGrade, LocalDateTime startWorkingHour,
			LocalDateTime endWorkingHour) {
		super();
		this.name = name;
		this.locationType = locationType;
		this.contentTypes = contentTypes;
		this.status = status;
		this.location = location;
		this.logo = logo;
		this.averageGrade = averageGrade;
		this.startWorkingHour = startWorkingHour;
		this.endWorkingHour = endWorkingHour;
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

	public List<ContentType> getContentTypes() {
		return contentTypes;
	}

	public void setContentTypes(List<ContentType> contentTypes) {
		this.contentTypes = contentTypes;
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

	public LocalDateTime getStartWorkingHour() {
		return startWorkingHour;
	}

	public void setStartWorkingHour(LocalDateTime startWorkingHour) {
		this.startWorkingHour = startWorkingHour;
	}

	public LocalDateTime getEndWorkingHour() {
		return endWorkingHour;
	}

	public void setEndWorkingHour(LocalDateTime endWorkingHour) {
		this.endWorkingHour = endWorkingHour;
	}
	
	
}
