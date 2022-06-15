package dao;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.SportsObject;
import beans.User;
import util.PersonalConfig;

public class SportsObjectDAO {
	private List<SportsObject> sportsObjects;
	private String pathToFile = PersonalConfig.PROJECT_FOLDER_PATH + "\\WebContent\\sports_objects.json";
	
	public SportsObjectDAO() {
		sportsObjects = new ArrayList<SportsObject>();
		loadSportsObjects();
	}
	
	public List<SportsObject> findAll(){
		return sportsObjects; 
	}
	
	private void loadSportsObjects() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			sportsObjects = new ArrayList<>(Arrays.asList(mapper.readValue(Paths.get(pathToFile).toFile(), SportsObject[].class)));
			//System.out.println(sportsObjects.size());
			//System.out.println(file.getAbsolutePath());
			/*SportsObject gymA = new SportsObject();
			gymA.setName("Gym A");
			List<ContentType> gymAContents = new ArrayList<>();
			gymAContents.add(ContentType.PERSONALTRAINING);
			gymAContents.add(ContentType.SAUNA);
			gymA.setContentTypes(gymAContents);
			sportsObjects.add(gymA);*/
		} catch (JsonParseException e) {
			e.printStackTrace();
			System.out.println("greska1");
		} catch (JsonMappingException e) {
			e.printStackTrace();
			System.out.println("greska2 s");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("greska3");
		}
	}
	
	private void saveSportsObjects() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(Paths.get(pathToFile).toFile(), sportsObjects);
		} catch (JsonParseException e) {
			e.printStackTrace();
			System.out.println("greska 1 upis");
		} catch (JsonMappingException e) {
			e.printStackTrace();
			System.out.println("greska 2 upis");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("greska 3 upis");
		}
	}
	
	public boolean exists(SportsObject sportsObject) {
		for(SportsObject so : sportsObjects) {
			if(so.getName().equals(sportsObject.getName())) return true;
		}
		return false;
	}
	public boolean newSportObject(SportsObject sportsObject) {
		System.out.println(exists(sportsObject));
		if(exists(sportsObject)) return false;
		sportsObjects.add(sportsObject);
		saveSportsObjects();
		return true;
	}
}
