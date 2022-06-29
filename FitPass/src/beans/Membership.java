package beans;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import beans.enums.*;

public class Membership {
	private String id;
	private MembershipType membershipType;
	private LocalDateTime transactionDate;
	private LocalDateTime dueDate;
	private double price;
	private MembershipStatus membershipStatus;
	private int numberOfTrainings;
	
	public Membership() {
		super();
	}
	public Membership(String id, MembershipType membershipType, LocalDateTime transactionDate, LocalDateTime dueDate, double price,
			MembershipStatus membershipStatus, int numberOfTrainings) {
		super();
		this.id = id;
		this.membershipType = membershipType;
		this.transactionDate = transactionDate;
		this.dueDate = dueDate;
		this.price = price;
		this.membershipStatus = membershipStatus;
		this.numberOfTrainings = numberOfTrainings;
	}
	public Membership(Membership membership) {
		this.id = membership.id;
		this.membershipType = membership.membershipType;
		this.transactionDate = membership.transactionDate;
		this.dueDate = membership.dueDate;
		this.price = membership.price;
		this.membershipStatus = membership.membershipStatus;
		this.numberOfTrainings = membership.numberOfTrainings;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public MembershipType getMembershipType() {
		return membershipType;
	}
	public void setMembershipType(MembershipType membershipStatus) {
		this.membershipType = membershipStatus;
	}
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	public LocalDateTime getDueDate() {
		return dueDate;
	}
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public MembershipStatus getMembershipStatus() {
		return membershipStatus;
	}
	public void setMembershipStatus(MembershipStatus membershipStatus) {
		this.membershipStatus = membershipStatus;
	}
	public int getNumberOfTrainings() {
		return numberOfTrainings;
	}
	public void setNumberOfTrainings(int numberOfTrainings) {
		this.numberOfTrainings = numberOfTrainings;
	}
	
	
}
