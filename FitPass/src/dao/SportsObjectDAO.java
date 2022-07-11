package dao;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Content;
import beans.SportsObject;
import util.PersonalConfig;

public class SportsObjectDAO {
	private List<SportsObject> sportsObjects;
	private String pathToFile = PersonalConfig.PROJECT_FOLDER_PATH + "\\data\\sports_objects.json";
	
	public SportsObjectDAO() {
		sportsObjects = new ArrayList<SportsObject>();
		load();
	}
	
	public List<SportsObject> findAll(){
		List<SportsObject> existingSportsObjects = new ArrayList<SportsObject>();
		for (SportsObject sportsObject : sportsObjects) {
			if(!sportsObject.isDeleted()) {
				existingSportsObjects.add(sportsObject);
			}
		}
		return existingSportsObjects; 
	}
	
	public SportsObject findByName(String sportsObjectName) {

		for (SportsObject sportsObject : sportsObjects) {
			if(sportsObject.getName().equals(sportsObjectName) && !sportsObject.isDeleted()) {
				return sportsObject;
			}
		}
		return null;
	}
	
	public void addGradeToSportsObject(String sportsObjectName, int grade) {
		SportsObject sportsObject = findByName(sportsObjectName);
		int numberOfGrades = sportsObject.getGradesCounter();
		double averageGrade = ((sportsObject.getAverageGrade() * numberOfGrades) + grade) / ++numberOfGrades;
		sportsObject.setAverageGrade(averageGrade);
		sportsObject.setGradesCounter(numberOfGrades);
		save();
	}
	
	public boolean exists(SportsObject sportsObject) {
		for(SportsObject so : sportsObjects) {
			if(so.getName().equals(sportsObject.getName()) && !so.isDeleted()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean exists(String sportsObjectName) {
		for(SportsObject so : sportsObjects) {
			if(so.getName().equals(sportsObjectName) && !so.isDeleted()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean newSportObject(SportsObject sportsObject) {
		if(exists(sportsObject)) return false;
		sportsObject.setDeleted(false);
		sportsObjects.add(sportsObject);
		save();
		return true;
	}
	
	public List<Content> findContents(String sportsObjectName){
		if(!exists(sportsObjectName)) return null;
		return findByName(sportsObjectName).getContent();
	}
	
	public Content findContentByName(String name) {
		for(SportsObject sportsObject : sportsObjects) {
			for(Content content : sportsObject.getContent()) {
				if(content.getName().equals(name)) {
					return content;
				}
			}
		}
		return null;
	}
	
	public boolean addContents(String sportsObjectName, Content content) {
		SportsObject sportsObject = findByName(sportsObjectName);
		for (Content objectContent : sportsObject.getContent()) {
			if(objectContent.getName().equals(content.getName())) {
				return false;
			}
		}
		List<Content> contents = sportsObject.getContent();
		content.setOldName(content.getName()); //set old name same as name
		contents.add(content);
		sportsObject.setContent(contents);
		save();
		return true;
	}
	
	public boolean checkExistingContentName(String name) {
		for(SportsObject sportsObject : sportsObjects) {
			for(Content content : sportsObject.getContent()) {
				if(content.getName().equals(name)) return true;
			}
		}
		return false;
	}
	public boolean editContent(Content content) {
		if(checkExistingContentName(content.getName()) && !content.getName().equals(content.getOldName())) {
			return false;
		}
		Content contentForChange = findContentByName(content.getOldName());
		contentForChange.setName(content.getName());
		contentForChange.setOldName(content.getName());
		contentForChange.setType(content.getType());
		contentForChange.setImage(content.getImage());
		contentForChange.setDescription(content.getDescription());
		contentForChange.setDuration(content.getDuration());
		save();
		return true;
	}
	
	public boolean delete(String sportsObjectName) {
		if(findByName(sportsObjectName) != null) {
			findByName(sportsObjectName).setDeleted(true);
			save();
			return true;
		}
		return false;
	}
	
	private void load() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			sportsObjects = new ArrayList<>(Arrays.asList(mapper.readValue(Paths.get(pathToFile).toFile(), SportsObject[].class)));
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
			mapper.writeValue(Paths.get(pathToFile).toFile(), sportsObjects);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
