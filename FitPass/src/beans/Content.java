package beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import beans.enums.ContentType;
import dao.ImageDAO;

public class Content {
	private String oldName;
	private String name;
	private ContentType type;
	private String image;
	private String description;
	private int duration;
	
	
	public Content() {
		super();
	}
	
	public Content(String oldName, String name, ContentType type, String image, String description, int duration) {
		super();
		this.oldName = oldName;
		this.name = name;
		this.type = type;
		this.image = image;
		this.description = description;
		this.duration = duration;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ContentType getType() {
		return type;
	}

	public void setType(ContentType type) {
		this.type = type;
	}

	public String getImage() {
		ImageDAO imageDAO = ImageDAO.getInstance();
		if(imageDAO.isBase64(image)) {
			return image;
		}
		else {
			return imageDAO.findImage(image);
		}
	}

	public void setImage(String image) {
		ImageDAO imageDAO = ImageDAO.getInstance();
		if(imageDAO.isBase64(image)) {
			this.image = imageDAO.saveImage(image);
		}
		else {
			this.image = image;
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}
	
	
}
