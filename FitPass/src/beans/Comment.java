package beans;

public class Comment {
	private String buyerUsername;
	private String sportsObjectName;
	private String text;
	private int grade;
	public Comment() {
		super();
	}
	public Comment(String buyerUsername, String sportsObjectName, String text, int grade) {
		super();
		this.buyerUsername = buyerUsername;
		this.sportsObjectName = sportsObjectName;
		this.text = text;
		this.grade = grade;
	}
	public String getBuyerUsername() {
		return buyerUsername;
	}
	public void setBuyerUsername(String buyerUsername) {
		this.buyerUsername = buyerUsername;
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
	
	
}
