package services;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.SportsObject;
import beans.User;
<<<<<<< HEAD
import beans.enums.UserType;
import dao.SportsObjectDAO;
import dao.TrainingDAO;
=======
import dao.SportsObjectDAO;
>>>>>>> fe8c60e7fdf9f2b0e9d0a7a5a9e4e0062eb2ed08
import dao.UserDAO;

@Path("/objects")
public class SportsObjectService {
	
	@Context
	ServletContext ctx;
	
	public SportsObjectService() { 
		
	}
	
	@PostConstruct
	private void init() {
		if (ctx.getAttribute("sportsObjectDAO") == null) {
<<<<<<< HEAD
			ctx.setAttribute("sportsObjectDAO", new SportsObjectDAO());
		}
		if (ctx.getAttribute("trainingDAO") == null) {
			ctx.setAttribute("trainingDAO", new TrainingDAO());
		}
		if (ctx.getAttribute("userDAO") == null) {
			ctx.setAttribute("userDAO", new UserDAO());
=======
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("sportsObjectDAO", new SportsObjectDAO());
>>>>>>> fe8c60e7fdf9f2b0e9d0a7a5a9e4e0062eb2ed08
		}
	}
	
	
	@GET
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<SportsObject> findAllSportsObjects(@Context HttpServletRequest request) {
		SportsObjectDAO sportsObjectDAO = (SportsObjectDAO) ctx.getAttribute("sportsObjectDAO");
		//System.out.println(sportsObjectDAO.findAll().size());
		return sportsObjectDAO.findAll();
	}
	
<<<<<<< HEAD
	@GET
	@Path("/managed")
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findManagersSportsObject(@Context HttpServletRequest request) {
		SportsObjectDAO sportsObjectDAO = (SportsObjectDAO) ctx.getAttribute("sportsObjectDAO");
		User user = (User) request.getSession().getAttribute("user");
		if(user != null && user.getUserType() == UserType.MANAGER) {
			String sportsObjectName = user.getSportsObject();
			return Response.ok(sportsObjectDAO.findByName(sportsObjectName), MediaType.APPLICATION_JSON).build();
		}
		return Response.status(401).build(); 
	}
	
	@GET
	@Path("/trainers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findSportsObjectTrainers(@Context HttpServletRequest request) {
		TrainingDAO trainingDAO = (TrainingDAO) ctx.getAttribute("trainingDAO");
		User user = (User) request.getSession().getAttribute("user");
		if(user != null && user.getUserType() == UserType.MANAGER) {
			String name = user.getSportsObject();
			List<String> trainerUsernames = trainingDAO.findTrainersBySportsObjects(name);
			UserDAO userDAO = (UserDAO) ctx.getAttribute("userDAO");
			return Response.ok(userDAO.findUsersByUsername(trainerUsernames), MediaType.APPLICATION_JSON).build();
		}
		return Response.status(401).build(); 
	}
	
	@GET
	@Path("/visited")
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
	
=======
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response newSportsObject(SportsObject sportsObject, @Context HttpServletRequest request) {
		SportsObjectDAO sportsObjectDAO = (SportsObjectDAO) ctx.getAttribute("sportsObjectDAO");
		if(!sportsObjectDAO.newSportObject(sportsObject)) {
			//System.out.printlnsportsObjectDAO.findAll());
			return Response.status(400).entity("Postoji sportski objekat sa unetim nazivom").build();
		}
		//System.out.println(userDao.findAll());
		//userDao.newCustomer(user);
		return Response.status(200).build();
	}
>>>>>>> fe8c60e7fdf9f2b0e9d0a7a5a9e4e0062eb2ed08
}
