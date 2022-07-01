package dao;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Training;
import beans.TrainingHistory;
import beans.User;
import util.PersonalConfig;

public class TrainingHistoryDAO {
	private List<TrainingHistory> trainingsHistory;
	private String pathToFile = PersonalConfig.PROJECT_FOLDER_PATH + "\\WebContent\\training-history.json";

	public TrainingHistoryDAO() {
		trainingsHistory = new ArrayList<TrainingHistory>();
		load();
	}
	
	public List<TrainingHistory> findTrainingHistoryForCustomer(String username){
		List<TrainingHistory> returnList = new ArrayList<TrainingHistory>();
		for(TrainingHistory trainigHistory : trainingsHistory) {
			if(trainigHistory.getBuyerUsername().equals(username)) {
				returnList.add(trainigHistory);
			}
		}
		return returnList;
	}
	
	public void addTrainingHistory(TrainingHistory trainingHistory) {
		trainingHistory.setId(findMax() + 1);
		trainingsHistory.add(trainingHistory);
		save();
	}
	
	private int findMax() {
		int max = 0;
		for(TrainingHistory trainigHistory : trainingsHistory) {
			if(trainigHistory.getId() > max) {
				max = trainigHistory.getId();
			}
		}
		return max;
	}
	
	public List<TrainingHistory> findCoachsTrainigs(String username){
		List<TrainingHistory> trainingsCoach = new ArrayList<TrainingHistory>();
		System.out.println(username);
		for(TrainingHistory training : trainingsHistory) {
			//System.out.println(training.getCoachUsername());
			if(training.getCoachUsername().equals(username)) {
				trainingsCoach.add(training);
			}
		}
		return trainingsCoach;
	}
	
	public List<TrainingHistory> findCustomersTrainigs(String username){
		List<TrainingHistory> trainingsCustomer = new ArrayList<TrainingHistory>();
		System.out.println(username);
		for(TrainingHistory training : trainingsHistory) {
			//System.out.println(training.getCoachUsername());
			if(training.getBuyerUsername().equals(username)) {
				trainingsCustomer.add(training);
			}
		}
		return trainingsCustomer;
	}
	
	public List<LocalDateTime> findHistoryForTraining(int id){
		List<LocalDateTime> startDates = new ArrayList<LocalDateTime>();
		for(TrainingHistory trainingHistory : trainingsHistory) {
			if(trainingHistory.getTrainingId() == id) {
				startDates.add(trainingHistory.getStartDate());
			}
		}
		//System.out.println(startDates.size());
		return startDates;
	}
	
	public TrainingHistory findById(int id) {
		for(TrainingHistory tr : trainingsHistory) {
			if(tr.getId() == id) {
				return tr;
			}
		}
		return null;
	}
	
	public List<TrainingHistory> findAll(){
		return trainingsHistory;
	}
	public void removeTraining(int id) {
		TrainingHistory training = findById(id);
		trainingsHistory.remove(training);
		save();
	}
	
	private void load() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			trainingsHistory = new ArrayList<>(Arrays.asList(mapper.readValue(Paths.get(pathToFile).toFile(), TrainingHistory[].class)));
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
			mapper.writeValue(Paths.get(pathToFile).toFile(), trainingsHistory);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
