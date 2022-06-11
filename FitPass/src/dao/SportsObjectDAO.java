package dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.SportsObject;
import beans.enums.ContentType;

public class SportsObjectDAO {
	private List<SportsObject> sportsObjects;
	
	public SportsObjectDAO(String path) {
		sportsObjects = new ArrayList<SportsObject>();
		loadSportsObjects(path);
		System.out.println("udje ovde");
	}
	
	public List<SportsObject> findAll(){
		return sportsObjects; 
	}
	
	private void loadSportsObjects(String path) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			File file  = Paths.get("sportsObjects.txt").toFile();
			sportsObjects = new ArrayList<>(Arrays.asList(mapper.readValue(Paths.get("sportsObjects.txt").toFile(), SportsObject[].class)));
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
			System.out.println("greska2");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("greska3");
		}
	}
	
	private void saveSportsObjects(String path) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(Paths.get("sportsObjects.txt").toFile(), sportsObjects);
			System.out.println(sportsObjects.size());
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
