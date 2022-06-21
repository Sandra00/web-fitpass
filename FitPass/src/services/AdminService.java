package services;

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
import beans.enums.UserType;
import dao.SportsObjectDAO;
import dao.UserDAO;

@Path("/admin")
public class AdminService {

	@Context
	ServletContext ctx;
	
	public AdminService() {
		
	}
	
	@PostConstruct
	private void init() {
		if (ctx.getAttribute("sportsObjectDAO") == null) {
			ctx.setAttribute("sportsObjectDAO", new SportsObjectDAO());
		}
	}
	
	private boolean isAuthorized(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if(user != null && user.getUserType() == UserType.ADMIN) {
			return true;
		}
		return false;
	}
	
	@POST
	@Path("/register-sports-object")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response newSportsObject(SportsObject sportsObject, @Context HttpServletRequest request) {
		if(isAuthorized(request)) {
			SportsObjectDAO sportsObjectDAO = (SportsObjectDAO) ctx.getAttribute("sportsObjectDAO");
			if(!sportsObjectDAO.newSportObject(sportsObject)) {
				return Response.status(400).entity("Postoji sportski objekat sa unetim nazivom").build();
			}
			return Response.status(200).build();
		}
		return Response.status(401).build(); 		// 401 - NOT AUTHORIZED
	}
	
	@POST
	@Path("/new-coach")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response newCoach(User user, @Context HttpServletRequest request) {
		if(isAuthorized(request)) {
			UserDAO userDao = (UserDAO) ctx.getAttribute("userDAO");
			if (userDao.newCoach(user)) {
				return Response.ok().build();
			}
			return Response.status(409).build(); 	// 409 - CONFLICT
		}
		return Response.status(401).build();  		// 401 - NOT AUTHORIZED
	}
	
	@POST
	@Path("/new-manager")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response newManager(User user, @Context HttpServletRequest request) {
		if(isAuthorized(request)) {
			UserDAO userDao = (UserDAO) ctx.getAttribute("userDAO");
			if(userDao.newManager(user)) {
				return Response.ok().build();
			}
			return Response.status(409).build(); 	// 409 - CONFLICT
		}
		return Response.status(401).build(); 
	}
	
	@GET
	@Path("/all")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response all(@Context HttpServletRequest request) {
		if(isAuthorized(request)) {
			UserDAO userDao = (UserDAO) ctx.getAttribute("userDAO");
			return Response.ok(userDao.findAll(), MediaType.APPLICATION_JSON).build();
		}
		return Response.status(401).build(); 		// 401 - NOT AUTHORIZED
	}
}
