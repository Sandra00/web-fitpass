package services;

import java.util.ArrayList;

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

import beans.User;
import dao.CommentDAO;
import dao.MembershipDAO;
import dao.UserDAO;

@Path("")
public class UserService {
	
	@Context
	ServletContext ctx;
	
	public UserService() {	}
	
	@PostConstruct
	private void init() {
		if (ctx.getAttribute("userDAO") == null) {
			ctx.setAttribute("userDAO", new UserDAO());
		}
		if (ctx.getAttribute("membershipDAO") == null) {
			ctx.setAttribute("membershipDAO", new MembershipDAO());
		}
		if (ctx.getAttribute("commentDAO") == null) {
			ctx.setAttribute("commentDAO", new CommentDAO());
		}
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(User user, @Context HttpServletRequest request) {
		UserDAO userDao = (UserDAO) ctx.getAttribute("userDAO");
		User loggedUser = userDao.findUserByUsername(user.getUsername());
		if (loggedUser == null || (loggedUser != null && !loggedUser.getPassword().equals(user.getPassword()))) {
			return Response.status(400).entity("Invalid username and/or password").build();
		}
		request.getSession().setAttribute("user", loggedUser);
		return Response.status(200).build();
	}
	
	@POST
	@Path("/checkExisting")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkExisting(User user) {
		UserDAO userDao = (UserDAO) ctx.getAttribute("userDAO");
		if(userDao.checkExisting(user)) {
			return Response.status(200).build();
		}else {
			return Response.status(400).entity("Invalid username and/or password").build();
		}
	}
	
	@GET
	@Path("/currentUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User login(@Context HttpServletRequest request) {
		return (User) request.getSession().getAttribute("user");
	}
	
	@POST
	@Path("/logout")
	public Response logout(@Context HttpServletRequest request) {
		request.getSession().invalidate();
		return Response.ok().build();
	}
	
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response newCustomer(User user, @Context HttpServletRequest request) {
		UserDAO userDao = (UserDAO) ctx.getAttribute("userDAO");
		if(!userDao.newCustomer(user)) {
			return Response.status(400).entity("Postoji korisnik sa unetim korisničkim imenom").build();
		}
		return Response.status(200).build();
	}
	
	@POST
	@Path("/editUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editUser(User user, @Context HttpServletRequest request) {
		UserDAO userDao = (UserDAO) ctx.getAttribute("userDAO");
		if(userDao.editUser(user)) {
			return Response.status(200).build();
		}
		return Response.status(400).entity("nesto bas i ne radi").build();
	}
	
	@GET
	@Path("/freeManagers")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<User> getFreeManagers(){
		UserDAO userDao = (UserDAO) ctx.getAttribute("userDAO");
		return userDao.getFreeManagers();
	}
	
	@GET
	@Path("/all-coaches")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCoaches(){
		UserDAO userDao = (UserDAO) ctx.getAttribute("userDAO");
		return Response.ok(userDao.findAllCoaches(), MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path("/memberships")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllMemberships(){
		MembershipDAO membershipDAO = (MembershipDAO) ctx.getAttribute("membershipDAO");
		return Response.ok(membershipDAO.findAll(), MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path("/membership/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMembershipById(@PathParam("id") String membershipId){
		MembershipDAO membershipDAO = (MembershipDAO) ctx.getAttribute("membershipDAO");
		return Response.ok(membershipDAO.findById(membershipId), MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/addSportsObject")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void addSportsObject(User user) {
		UserDAO userDao = (UserDAO) ctx.getAttribute("userDAO");
		userDao.addSportsObject(user);
	}
		
}
