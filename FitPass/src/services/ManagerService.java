package services;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Content;
import beans.Training;
import beans.User;
import beans.enums.UserType;
import dao.SportsObjectDAO;
import dao.TrainingDAO;
import dao.UserDAO;

@Path("/manager")
public class ManagerService {

	@Context
	ServletContext ctx;
	
	public ManagerService() { 
		
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
	}
	
	private boolean isAuthorized(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if(user != null && user.getUserType() == UserType.MANAGER) {
			return true;
		}
		return false;
	}
	
	@GET
	@Path("/managed")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findManagersSportsObject(@Context HttpServletRequest request) {
		if(isAuthorized(request)) {
			SportsObjectDAO sportsObjectDAO = (SportsObjectDAO) ctx.getAttribute("sportsObjectDAO");
			User user = (User) request.getSession().getAttribute("user");
			String sportsObjectName = user.getSportsObject();
			return Response.ok(sportsObjectDAO.findByName(sportsObjectName), MediaType.APPLICATION_JSON).build();
		}
		return Response.status(401).build(); 
	}
	
	@GET
	@Path("/visited")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findUsersVisited(@Context HttpServletRequest request) {
		if(isAuthorized(request)) {
			User user = (User) request.getSession().getAttribute("user");
			UserDAO userDAO = (UserDAO) ctx.getAttribute("userDAO");
			String name = user.getSportsObject();
			return Response.ok(userDAO.findUsersVisitedSportsObject(name), MediaType.APPLICATION_JSON).build();
		}
		return Response.status(401).build(); 
	}
	
	@GET
	@Path("/coaches")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findSportsObjectCoaches(@Context HttpServletRequest request) {
		if(isAuthorized(request)) {
			TrainingDAO trainingDAO = (TrainingDAO) ctx.getAttribute("trainingDAO");
			User user = (User) request.getSession().getAttribute("user");
			String name = user.getSportsObject();
			List<String> trainerUsernames = trainingDAO.findCoachesBySportsObjects(name);
			UserDAO userDAO = (UserDAO) ctx.getAttribute("userDAO");
			return Response.ok(userDAO.findUsersByUsername(trainerUsernames), MediaType.APPLICATION_JSON).build();
		}
		return Response.status(401).build(); 
	}
	
	@PUT
	@Path("/add-training")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addTraining(Training training, @Context HttpServletRequest request) {
		if(isAuthorized(request)) {
			TrainingDAO trainingDAO = (TrainingDAO) ctx.getAttribute("trainingDAO");
			User user = (User) request.getSession().getAttribute("user");
			training.setSportsObject(user.getSportsObject());
			if(trainingDAO.addTraining(training)) {
				return Response.ok().build();	
			}
			else {
				return Response.status(400).build(); 
			}
		}
		return Response.status(401).build(); 
	}
	
	@PUT
	@Path("/add-content")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addContent(Content content,@Context HttpServletRequest request) {
		if(isAuthorized(request)) {
			User user = (User) request.getSession().getAttribute("user");
			SportsObjectDAO sportsObjectDAO = (SportsObjectDAO) ctx.getAttribute("sportsObjectDAO");
			if (sportsObjectDAO.addContents(user.getSportsObject(), content)){
				return Response.ok().build();
			}
			else {
				return Response.status(400).build(); 
			}
		}
		return Response.status(401).build(); 
	}
	
	@POST
	@Path("/edit-content")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editContent(Content content, @Context HttpServletRequest request) {
		if(isAuthorized(request)) {
			SportsObjectDAO sportsObjectDAO = (SportsObjectDAO) ctx.getAttribute("sportsObjectDAO");
			if(sportsObjectDAO.editContent(content)) {
				return Response.status(200).build();
			}
			return Response.status(400).build();
		}
		return Response.status(401).build(); 
	}
	
}
