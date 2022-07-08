package dao;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Image;
import util.PersonalConfig;

public class ImageDAO {
	private List<Image> images;
	private String pathToFile = PersonalConfig.PROJECT_FOLDER_PATH + "\\data\\images.json";
	private static ImageDAO instance = null;
	
	private ImageDAO() {
		load();
	}
	
	public static ImageDAO getInstance() {
		if(instance == null) {
			return new ImageDAO();
		}
		return instance;
	}
	
	
	public String saveImage(String content) {
		if (isBase64(content)) {
			Image image = new Image(getNewId(), content);
			images.add(image);
			save();
			return image.getId();
		}
		return null;
	}
	
	public String findImage(String id) {
		for (Image image : images) {
			if(image.getId().equals(id)) {
				return image.getImage();
			}
		}
		return null;
	}
	
	public boolean isBase64(String content) {
		/*String base64pattern = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$";
		Pattern pattern = Pattern.compile(base64pattern);
		Matcher matcher = pattern.matcher(content);
		System.out.print("Is base 64: " + matcher.matches());*/
		// the logic behind this is too primitive, consider upgrading the logic
		if(content == null)
			return false;
		return content.length() > 20;
	}
	
	private int findMax() {
		int max = 0;
		for (Image image : images) {
			int parsedId = Integer.parseInt(image.getId());
			if (parsedId > max) {
				max = parsedId;
			}
		}
		return max;
	}
	
	private String getNewId() {
		return String.valueOf(findMax() + 1);
	}
	
	private void load() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			images = new ArrayList<>(Arrays.asList(mapper.readValue(Paths.get(pathToFile).toFile(), Image[].class)));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void save() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(Paths.get(pathToFile).toFile(), images);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}