package services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Training;
import beans.TrainingHistory;
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
		List<Training> trainings = new ArrayList<Training>();
		for(TrainingHistory trainingInHistory : trainingsHistory) {
			if(!trainings.contains(trainingDAO.findById(trainingInHistory.getTrainingId()))) {
				trainings.add(trainingDAO.findById(trainingInHistory.getTrainingId()));
			}
		}
		return trainings;
	}
	
	@GET
	@Path("find-history-for-training")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<LocalDateTime> findHistoryForTraining(@Context HttpServletRequest request){
		TrainingHistoryDAO trainingHistoryDAO = (TrainingHistoryDAO) ctx.getAttribute("trainingHistoryDAO");
		return trainingHistoryDAO.findHistoryForTraining(Integer.parseInt(request.getParameter("trainingId")));
	}
	
	@GET
	@Path("/trainings/coach")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<TrainingHistory> findCoachsTrainigs(@Context HttpServletRequest request){
		TrainingHistoryDAO trainingHistoryDAO = (TrainingHistoryDAO) ctx.getAttribute("trainingHistoryDAO");
		return trainingHistoryDAO.findCoachsTrainigs(request.getParameter("username"));
	}
	
	@GET
	@Path("/trainings/customer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<TrainingHistory> findCustomersTrainigs(@Context HttpServletRequest request){
		TrainingHistoryDAO trainingHistoryDAO = (TrainingHistoryDAO) ctx.getAttribute("trainingHistoryDAO");
		return trainingHistoryDAO.findCustomersTrainigs(request.getParameter("username"));
	}
	
	@DELETE
	@Path("/trainings/coach-remove-training")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void removeTraining(@Context HttpServletRequest request) {
		TrainingHistoryDAO trainingHistoryDAO = (TrainingHistoryDAO) ctx.getAttribute("trainingHistoryDAO");
		System.out.println("pronadjen trening " +  request.getParameter("trainingHistoryId"));
		trainingHistoryDAO.delete(Integer.parseInt(request.getParameter("trainingHistoryId")));
	}
}
