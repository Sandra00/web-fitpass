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
	private String pathToFile = PersonalConfig.PROJECT_FOLDER_PATH + "\\data\\trainings.json";
	
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
	
	public Training findById(int id) {
		for(Training training : trainings) {
			if(training.getTrainingId() == id && !training.isDeleted()) {
				return training;
			}
		}
		return null;
	}
	
	private boolean exists(int trainingId) {
		for (Training training : trainings) {
			if (training.getTrainingId() == trainingId && !training.isDeleted()) {
				return true;
			} 
		}
		return false;
	}
	
	public boolean addTraining(Training training) {
		// modify this if statement if the same name for training in one sports object is not allowed
		if (exists(training.getTrainingId())) { 
			return false;
		}
		training.setTrainingId(getNewId());
		training.setDeleted(false);
		boolean isAdded = trainings.add(training);
		save();
		return isAdded;
	}

	public List<Training> findTrainingsBySportsObject(String sportsObjectName){
		List<Training> trainingList = new ArrayList<Training>();
		for (Training training : trainings){
			if (training.getSportsObject().equals(sportsObjectName) && !training.isDeleted()) {
				trainingList.add(training);
			}
		}
		return trainingList;
	}
	
	public List<String> findCoachesBySportsObjects(String sportsObjectName){
		List<String> coaches = new ArrayList<String>();
		for (Training training : trainings){
			if (training.getSportsObject().equals(sportsObjectName) && !training.isDeleted() && !coaches.contains(training.getCoach())) {
				coaches.add(training.getCoach());
			}
		}
		return coaches;
	}
	
	public List<Training> findCoachsTrainigs(String username){
		List<Training> trainingsCoach = new ArrayList<Training>();
		for(Training training : trainings) {
			if(training.getCoach().equals(username) && !training.isDeleted()) {
				trainingsCoach.add(training);
			}
		}
		return trainingsCoach;
	}
	
	public boolean delete(int id) {
		if(findById(id) != null) {
			findById(id).setDeleted(true);
			save();
			return true;
		}
		return false;
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
