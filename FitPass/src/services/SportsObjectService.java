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
	
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response newSportsObject(SportsObject sportsObject, @Context HttpServletRequest request) {
		SportsObjectDAO sportsObjectDAO = (SportsObjectDAO) ctx.getAttribute("sportsObjectDAO");
		if(!sportsObjectDAO.newSportObject(sportsObject)) {
			//System.out.printlnsportsObjectDAO.findAll());
			System.out.println("dodje dovde");
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
	
	
	@GET
	@Path("/currentContent")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Content currentContent( @Context HttpServletRequest request) {
		SportsObjectDAO sportsObjectDAO = (SportsObjectDAO) ctx.getAttribute("sportsObjectDAO"); 
		return sportsObjectDAO.findContentByName(request.getParameter("name").replace("%20", " "));
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
