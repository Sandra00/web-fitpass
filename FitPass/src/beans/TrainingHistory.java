package beans;

import java.time.LocalDateTime;

public class TrainingHistory {
	private LocalDateTime startDate;
	private int trainingId;
	private String buyerUsername;
	private String coachUsername;
	
	
	public TrainingHistory() {
		super();
	}


	public TrainingHistory(LocalDateTime startDate, int trainingId, String buyerUsername, String coachUsername) {
		super();
		this.startDate = startDate;
		this.trainingId = trainingId;
		this.buyerUsername = buyerUsername;
		this.coachUsername = coachUsername;
	}


	public LocalDateTime getStartDate() {
		return startDate;
	}


	public void setStartDate(LocalDateTime startDate) {
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
	
	
	
}
