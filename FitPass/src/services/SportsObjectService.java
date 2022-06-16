package services;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import beans.SportsObject;
import beans.User;
import dao.SportsObjectDAO;
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
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("sportsObjectDAO", new SportsObjectDAO());
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
		System.out.println("preuzeto " + request.getParameter("name"));
		SportsObjectDAO sportsObjectDAO = (SportsObjectDAO) ctx.getAttribute("sportsObjectDAO");
		System.out.println("tu smo");
		System.out.println(request.getParameter("name"));
		return sportsObjectDAO.findByName(request.getParameter("name"));
	}
}
