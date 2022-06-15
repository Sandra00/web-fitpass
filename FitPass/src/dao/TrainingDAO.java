package dao;

import java.util.ArrayList;
import java.util.List;

import beans.*;
import beans.enums.TrainingType;
import util.PersonalConfig;

public class TrainingDAO {
	private List<Training> trainings;
	private String pathToFile = PersonalConfig.PROJECT_FOLDER_PATH + "\\WebContent\\trainings.json";
	
	public TrainingDAO() {
		trainings = new ArrayList<Training>();
		Training tr1 = new Training(1, "Trening A", TrainingType.PERSONAL, "Gym 1", 60, "janko", "", "");
		trainings.add(tr1);
	}
	
	public List<Training> findAll(){
		return trainings;
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
}
