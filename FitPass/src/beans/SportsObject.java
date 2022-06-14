package beans;

import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import beans.enums.*;
import util.LocalTimeDeserializer;

public class SportsObject {
	private String name;
	private LocationType locationType;
	private List<ContentType> contentTypes;
	private boolean status;
	private Location location;
	private String logo;
	public double averageGrade;
	private LocalTime startWorkingHour;
	private LocalTime endWorkingHour;
	private int gradesCounter;
	
	public SportsObject() {
		super();
	}

	public SportsObject(String name, LocationType locationType, List<ContentType> contentTypes, boolean status,
			Location location, String logo, double averageGrade, LocalTime startWorkingHour,
			LocalTime endWorkingHour, int gradesCounter) {
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
		this.gradesCounter = gradesCounter;
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
	@JsonSerialize(using = LocalTimeSerializer.class)
	public LocalTime getStartWorkingHour() {
		return startWorkingHour;
	}

	@JsonDeserialize(using = LocalTimeDeserializer.class)
	public void setStartWorkingHour(LocalTime startWorkingHour) {
		this.startWorkingHour = startWorkingHour;
	}

	@JsonSerialize(using = LocalTimeSerializer.class)
	public LocalTime getEndWorkingHour() {
		return endWorkingHour;
	}
	
	

	public int getGradesCounter() {
		return gradesCounter;
	}

	public void setGradesCounter(int gradesCounter) {
		this.gradesCounter = gradesCounter;
	}

	@JsonDeserialize(using = LocalTimeDeserializer.class)
	public void setEndWorkingHour(LocalTime endWorkingHour) {
		this.endWorkingHour = endWorkingHour;
	}
	
	
}

