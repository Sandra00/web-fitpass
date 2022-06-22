package beans;

import java.time.LocalDateTime;

import beans.enums.*;

public class Membership {
	private String id;
	private MembershipType feeType;
	private LocalDateTime transactionDate;
	private LocalDateTime dueDate;
	private double price;
	private User buyer;
	private MembershipStatus feeStatus;
	private int numberOfTrainings;
	// promocode
	
	public Membership() {
		super();
	}
	public Membership(String id, MembershipType feeType, LocalDateTime transactionDate, LocalDateTime dueDate, double price,
			User buyer, MembershipStatus feeStatus, int numberOfTrainings) {
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
	public MembershipType getFeeType() {
		return feeType;
	}
	public void setFeeType(MembershipType feeType) {
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
	public MembershipStatus getFeeStatus() {
		return feeStatus;
	}
	public void setFeeStatus(MembershipStatus feeStatus) {
		this.feeStatus = feeStatus;
	}
	public int getNumberOfTrainings() {
		return numberOfTrainings;
	}
	public void setNumberOfTrainings(int numberOfTrainings) {
		this.numberOfTrainings = numberOfTrainings;
	}
	
	
}
