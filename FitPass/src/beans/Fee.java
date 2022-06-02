package beans;

import java.time.LocalDateTime;

import beans.enums.*;

public class Fee {
	private String id;
	private FeeType feeType;
	private LocalDateTime transactionDate;
	private LocalDateTime dueDate;
	private double price;
	private User buyer;
	private FeeStatus feeStatus;
	private int numberOfTrainings;
	// promocode
	
	public Fee() {
		super();
	}
	public Fee(String id, FeeType feeType, LocalDateTime transactionDate, LocalDateTime dueDate, double price,
			User buyer, FeeStatus feeStatus, int numberOfTrainings) {
		super();
		this.id = id;
		this.feeType = feeType;
		this.transactionDate = transactionDate;
		this.dueDate = dueDate;
		this.price = price;
		this.buyer = buyer;
		this.feeStatus = feeStatus;
		this.numberOfTrainings = numberOfTrainings;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public FeeType getFeeType() {
		return feeType;
	}
	public void setFeeType(FeeType feeType) {
		this.feeType = feeType;
	}
	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}
	public LocalDateTime getDueDate() {
		return dueDate;
	}
	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public User getBuyer() {
		return buyer;
	}
	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}
	public FeeStatus getFeeStatus() {
		return feeStatus;
	}
	public void setFeeStatus(FeeStatus feeStatus) {
		this.feeStatus = feeStatus;
	}
	public int getNumberOfTrainings() {
		return numberOfTrainings;
	}
	public void setNumberOfTrainings(int numberOfTrainings) {
		this.numberOfTrainings = numberOfTrainings;
	}
	
	
}
