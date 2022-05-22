package beans;

import java.util.List;

public class Location {
	
	private List<Integer> name;
	private List<Integer> latitude;
	private Address address;
	
	public Location() {
		super();
	}

	public Location(List<Integer> name, List<Integer> latitude, Address address) {
		super();
		this.name = name;
		this.latitude = latitude;
		this.address = address;
	}

	public List<Integer> getName() {
		return name;
	}

	public void setName(List<Integer> name) {
		this.name = name;
	}

	public List<Integer> getLatitude() {
		return latitude;
	}

	public void setLatitude(List<Integer> latitude) {
		this.latitude = latitude;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	
	
}
