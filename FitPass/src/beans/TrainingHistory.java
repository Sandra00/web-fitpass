package beans;

import java.time.LocalDateTime;

public class TrainingHistory {
	private int id;
	private String startDate;
	private int trainingId;
	private String buyerUsername;
	private String coachUsername;
	
	
	public TrainingHistory() {
		super();
	}


	public TrainingHistory(int id, String startDate, int trainingId, String buyerUsername, String coachUsername) {
		super();
		this.id = id;
		this.startDate = startDate;
		this.trainingId = trainingId;
		this.buyerUsername = buyerUsername;
		this.coachUsername = coachUsername;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public int getTrainingId() {
		return trainingId;
	}


	public void setTrainingId(int trainingId) {
		this.trainingId = trainingId;
	}


	public String getBuyerUsername() {
		return buyerUsername;
	}


	public void setBuyerUsername(String buyerUsername) {
		this.buyerUsername = buyerUsername;
	}


	public String getCoachUsername() {
		return coachUsername;
	}


	public void setCoachUsername(String coachUsername) {
		this.coachUsername = coachUsername;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
	
	
}
