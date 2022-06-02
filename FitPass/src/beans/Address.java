package beans;

public class Address {
	private String street;
	private int number;
	private String town;
	private int zipcode;
	
	public Address() {
		super();
	}

	public Address(String street, int number, String town, int zipcode) {
		super();
		this.street = street;
		this.number = number;
		this.town = town;
		this.zipcode = zipcode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public int getZipcode() {
		return zipcode;
	}

	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}
	
	
}
