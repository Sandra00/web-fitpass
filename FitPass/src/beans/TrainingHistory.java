package beans;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

public class TrainingHistory {
	private int id;
	private LocalDateTime startDate;
	private int trainingId;
	private String buyerUsername;
	private String coachUsername;
	
	
	public TrainingHistory() {
		super();
	}


	public TrainingHistory(int id, LocalDateTime startDate, int trainingId, String buyerUsername, String coachUsername) {
		super();
		this.id = id;
		this.startDate = startDate;
		this.trainingId = trainingId;
		this.buyerUsername = buyerUsername;
		this.coachUsername = coachUsername;
	}

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	public LocalDateTime getStartDate() {
		return startDate;
	}

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
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


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
	
	
}
