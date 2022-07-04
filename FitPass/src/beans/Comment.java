package beans;

import beans.enums.CommentStatus;

public class Comment {
	private int id;
	private String customerUsername;
	private String sportsObjectName;
	private String text;
	private int grade;
	private CommentStatus status;
	
	public Comment() {
		super();
	}
	public Comment(int id, String customerUsername, String sportsObjectName, String text, int grade, CommentStatus status) {
		super();
		this.id = id;
		this.customerUsername = customerUsername;
		this.sportsObjectName = sportsObjectName;
		this.text = text;
		this.grade = grade;
		this.status = status;
	}
	public String getCustomerUsername() {
		return customerUsername;
	}
	public void setCustomerUsername(String customerUsername) {
		this.customerUsername = customerUsername;
	}
	public String getSportsObjectName() {
		return sportsObjectName;
	}
	public void setSportsObjectName(String sportsObjectName) {
		this.sportsObjectName = sportsObjectName;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		if(grade >= 1 && grade <= 5) {
			this.grade = grade;
		}
	}
	public CommentStatus getStatus() {
		return status;
	}
	public void setStatus(CommentStatus status) {
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
