package beans;

public class Address {
	private String street;
	private int number;
	private String Town;
	private int zipcode;
	
	public Address() {
		super();
	}

	public Address(String street, int number, String town, int zipcode) {
		super();
		this.street = street;
		this.number = number;
		Town = town;
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
		return Town;
	}

	public void setTown(String town) {
		Town = town;
	}

	public int getZipcode() {
		return zipcode;
	}

	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}
	
	
}
