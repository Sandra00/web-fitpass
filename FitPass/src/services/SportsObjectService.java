package services;

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
import javax.ws.rs.core.Response;

import beans.SportsObject;
import beans.User;
import beans.enums.UserType;
import dao.SportsObjectDAO;
import dao.TrainingDAO;
import dao.UserDAO;

@Path("/objects")
public class SportsObjectService {
	
	@Context
	ServletContext ctx;
	
	public SportsObjectService() { }
	
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
	
	@GET
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<SportsObject> findAllSportsObjects(@Context HttpServletRequest request) {
		SportsObjectDAO sportsObjectDAO = (SportsObjectDAO) ctx.getAttribute("sportsObjectDAO");
		return sportsObjectDAO.findAll();
	}
	
	@GET
	@Path("/managed")
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findManagersSportsObject(@Context HttpServletRequest request) {
		SportsObjectDAO sportsObjectDAO = (SportsObjectDAO) ctx.getAttribute("sportsObjectDAO");
		User user = (User) request.getSession().getAttribute("user");
		if(user != null && user.getUserType() == UserType.MANAGER) {
			String name = user.getSportsObject();
			return Response.ok(sportsObjectDAO.findByName(name), MediaType.APPLICATION_JSON).build();
		}
		return Response.status(401).build(); 
	}
	
	@GET
	@Path("/trainers")
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findSportsObjectTrainers(@Context HttpServletRequest request) {
		TrainingDAO trainingDAO = (TrainingDAO) ctx.getAttribute("trainingDAO");
		User user = (User) request.getSession().getAttribute("user");
		if(user != null && user.getUserType() == UserType.MANAGER) {
			String name = user.getSportsObject();
			return Response.ok(trainingDAO.findTrainersBySportsObjects(name), MediaType.APPLICATION_JSON).build();
		}
		return Response.status(401).build(); 
	}
	
	@GET
	@Path("/visited")
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findUsersVisited(@Context HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		UserDAO userDAO = (UserDAO) ctx.getAttribute("userDAO");
		if(user != null && user.getUserType() == UserType.MANAGER) {
			String name = user.getSportsObject();
			return Response.ok(userDAO.findUsersVisitedSportsObject(name), MediaType.APPLICATION_JSON).build();
		}
		return Response.status(401).build(); 
	}
	
}
