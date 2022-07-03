package beans;

public class Comment {
	private String customerUsername;
	private String sportsObjectName;
	private String text;
	private int grade;
	private boolean approved;
	
	public Comment() {
		super();
	}
	public Comment(String customerUsername, String sportsObjectName, String text, int grade, boolean approved) {
		super();
		this.customerUsername = customerUsername;
		this.sportsObjectName = sportsObjectName;
		this.text = text;
		this.grade = grade;
		this.approved = approved;
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
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	
	
}
