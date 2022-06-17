package beans;

import beans.enums.*;

public class Training {
	private int trainingId;
	private String name;
	private TrainingType trainingType;
	private String sportsObject;
	private int length;
	private String coach;
	private String description;
	private String image;
	
	public Training() {
		super();
	}

	public Training(int trainingId, String name, TrainingType trainingType, String sportsObject, int length, String coach,
			String description, String image) {
		super();
		this.trainingId = trainingId;
		this.name = name;
		this.trainingType = trainingType;
		this.sportsObject = sportsObject;
		this.length = length;
		this.coach = coach;
		this.description = description;
		this.image = image;
	}

	public int getTrainingId() {
		return trainingId;
	}

	public void setTrainingId(int trainingId) {
		this.trainingId = trainingId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TrainingType getTrainingType() {
		return trainingType;
	}

	public void setTrainingType(TrainingType trainingType) {
		this.trainingType = trainingType;
	}

	public String getSportsObject() {
		return sportsObject;
	}

	public void setSportsObject(String sportsObject) {
		this.sportsObject = sportsObject;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getCoach() {
		return coach;
	}

	public void setCoach(String coach) {
		this.coach = coach;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	
}
