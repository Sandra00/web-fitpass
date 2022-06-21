package services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Training;
import beans.TrainingHistory;
import beans.User;
import dao.SportsObjectDAO;
import dao.TrainingDAO;
import dao.TrainingHistoryDAO;
import dao.UserDAO;

@Path("/training-history")
public class TrainingHistoryService {
	@Context
	ServletContext ctx;
	
	public TrainingHistoryService() { 
		
	}
	
	@PostConstruct
	private void init() {
		if (ctx.getAttribute("sportsObjectDAO") == null) {
			ctx.setAttribute("sportsObjectDAO", new SportsObjectDAO());
		}
		if (ctx.getAttribute("trainingDAO") == null) {
			ctx.setAttribute("trainingDAO", new TrainingDAO());
		}
		if (ctx.getAttribute("userDAO") == null) {
			ctx.setAttribute("userDAO", new UserDAO());
		}
		if (ctx.getAttribute("trainingHistoryDAO") == null) {
			ctx.setAttribute("trainingHistoryDAO", new TrainingHistoryDAO());
		}
	}
	
	@GET
	@Path("/find-customer-training-history")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Training> findTrainingsForCustomer(@Context HttpServletRequest request){
		TrainingHistoryDAO trainingHistoryDAO = (TrainingHistoryDAO) ctx.getAttribute("trainingHistoryDAO");
		TrainingDAO trainingDAO = (TrainingDAO) ctx.getAttribute("trainingDAO");
		List<TrainingHistory> trainingsHistory = trainingHistoryDAO.findTrainingHistoryForCustomer(request.getParameter("username"));
		//System.out.println(trainingsHistory.size());
		List<Training> trainings = new ArrayList<Training>();
		for(TrainingHistory trainingInHistory : trainingsHistory) {
			//System.out.println(trainingInHistory.getTrainingId());
			//System.out.println(trainingDAO.findById(trainingInHistory.getTrainingId()).getName());
			if(!trainings.contains(trainingDAO.findById(trainingInHistory.getTrainingId()))) {
				trainings.add(trainingDAO.findById(trainingInHistory.getTrainingId()));
				//System.out.println("udje ovde");
			}
		}
		//System.out.println(trainings.size());
		return trainings;
	}
	
	@GET
	@Path("find-history-for-training")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> findHistoryForTraining(int id){
		TrainingHistoryDAO trainingHistoryDAO = (TrainingHistoryDAO) ctx.getAttribute("trainingHistoryDAO");
		return trainingHistoryDAO.findHistoryForTraining(id);
	}
}
