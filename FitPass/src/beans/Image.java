package beans;

public class Image {
	private String id;
	private String image;
	private boolean deleted = false;
	
	
	public Image() {
		super();
	}

	
	public Image(String id, String image) {
		super();
		this.id = id;
		this.image = image;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public boolean isDeleted() {
		return deleted;
	}


	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	
	
}
