package dao;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.*;
import util.PersonalConfig;

public class TrainingDAO {
	private List<Training> trainings;
	private String pathToFile = PersonalConfig.PROJECT_FOLDER_PATH + "\\WebContent\\trainings.json";
	
	public TrainingDAO() {
		trainings = new ArrayList<Training>();
		load();
	}
	
	public List<Training> findAll(){
		return trainings;
	}
	
	private int getNewId() {
		int max = 0;
		for (Training training : trainings) {
			if (training.getTrainingId() > max) {
				max = training.getTrainingId();
			}
		}
		return max + 1;
	}
	
	private boolean exists(int trainingId) {
		for (Training training : trainings) {
			if (training.getTrainingId() == trainingId) {
				return true;
			} 
		}
		return false;
	}
	
	public boolean addTraining(Training training) {
		if (exists(training.getTrainingId())) {
			return false;
		}
		training.setTrainingId(getNewId());
		boolean isAdded = trainings.add(training);
		save();
		return isAdded;
	}
	
	public List<String> findTrainersBySportsObjects(String sportsObjectName){
		List<String> trainers = new ArrayList<String>();
		for (Training training : trainings){
			if (training.getSportsObject().equals(sportsObjectName) && !trainers.contains(training.getTrainer())) {
				trainers.add(training.getTrainer());
			}
		}
		return trainers;
	}
	
	private void load() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			trainings = new ArrayList<>(Arrays.asList(mapper.readValue(Paths.get(pathToFile).toFile(), Training[].class)));
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
			mapper.writeValue(Paths.get(pathToFile).toFile(), trainings);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
