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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import beans.Content;

import beans.SportsObject;
import beans.Training;
import beans.User;
import beans.enums.UserType;
import dao.SportsObjectDAO;
import dao.TrainingDAO;
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
	

	@GET
	@Path("/showObject")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SportsObject setObject(SportsObject so, @Context HttpServletRequest request) {
		SportsObjectDAO sportsObjectDAO = (SportsObjectDAO) ctx.getAttribute("sportsObjectDAO");
		System.out.println("udje ovde");
		System.out.println(so.getName());
		request.getSession().setAttribute("object", sportsObjectDAO.findByName(so.getName()));
		System.out.println(sportsObjectDAO.findByName(so.getName()).getName());
		return sportsObjectDAO.findByName(so.getName());
	}
	
	@GET
	@Path("/currentObject")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SportsObject currentObject( @Context HttpServletRequest request) {
		SportsObjectDAO sportsObjectDAO = (SportsObjectDAO) ctx.getAttribute("sportsObjectDAO"); 
		return sportsObjectDAO.findByName(request.getParameter("name"));
	}
	@PUT
	@Path("/add_content")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addContent(Content content,@Context HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		SportsObjectDAO sportsObjectDAO = (SportsObjectDAO) ctx.getAttribute("sportsObjectDAO");
		if(user != null && user.getUserType() == UserType.MANAGER) {
			if (sportsObjectDAO.addContents(user.getSportsObject(), content)){
				return Response.ok().build();
			}
			else {
				// maybe the error code is not appropriate but i could not find a better one
				return Response.status(400).build(); 
			}
		}
		return Response.status(401).build(); 
	}
	
	@PUT // changed from POST
	@Path("/add_training")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addTraining(Training training, @Context HttpServletRequest request) {
		TrainingDAO trainingDAO = (TrainingDAO) ctx.getAttribute("trainingDAO");
		User user = (User) request.getSession().getAttribute("user");
		if(user != null && user.getUserType() == UserType.MANAGER) {
			training.setSportsObject(user.getSportsObject());
			if(trainingDAO.addTraining(training)) {
				return Response.ok().build();	
			}
			else {
				// maybe the error code is not appropriate but i could not find a better one
				return Response.status(400).build(); 
			}
		}
		return Response.status(401).build(); 
	}
	
	@GET
	@Path("/currentContent")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Content currentContent( @Context HttpServletRequest request) {
		SportsObjectDAO sportsObjectDAO = (SportsObjectDAO) ctx.getAttribute("sportsObjectDAO"); 
		return sportsObjectDAO.findContentByName(request.getParameter("name"));
	}
	
	@GET
	@Path("/content/{name}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findContent(@PathParam("name") String sportsObjectName, @Context HttpServletRequest request) {
		SportsObjectDAO sportsObjectDAO = (SportsObjectDAO) ctx.getAttribute("sportsObjectDAO"); 
		return sportsObjectDAO.findContents(sportsObjectName) == null ? 
				Response.status(400).build() : 
					Response.ok(sportsObjectDAO.findContents(sportsObjectName), MediaType.APPLICATION_JSON).build();
	}
}
