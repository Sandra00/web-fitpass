package services;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Membership;
import beans.User;
import beans.enums.UserType;
import dao.MembershipDAO;
import dao.SportsObjectDAO;
import dao.UserDAO;

@Path("/customer")
public class CustomerService {
	@Context
	ServletContext ctx;
	
	public CustomerService() {
		
	}
	
	@PostConstruct
	private void init() {
		if (ctx.getAttribute("membershipDAO") == null) {
			ctx.setAttribute("membershipDAO", new MembershipDAO());
		}
	}
	
	private boolean isAuthorized(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if(user != null && user.getUserType() == UserType.CUSTOMER) {
			return true;
		}
		return false;
	}
	
	@PUT
	@Path("/new-membership/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response setMembership(@PathParam("id") String membershipId, @Context HttpServletRequest request) {
		if(isAuthorized(request)) {
			MembershipDAO membershipDAO = (MembershipDAO) ctx.getAttribute("membershipDAO");
			if(membershipDAO.findById(membershipId) != null) {
				Membership membership = new Membership(membershipDAO.findById(membershipId));
				User user = (User) request.getSession().getAttribute("user");
				UserDAO userDAO = (UserDAO) ctx.getAttribute("userDAO");
				userDAO.setMembership(user.getUsername(), membership);
				return Response.ok().build();
			}
			return Response.status(405).build();
		}
		return Response.status(401).build();
	}
	
	@PUT
	@Path("/check-in/{name}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response sportsObjectCheckIn(@PathParam("name") String sportsObjectName, @Context HttpServletRequest request) {
		if(isAuthorized(request)) {
			User user = (User) request.getSession().getAttribute("user");
			if(user.getMembership() != null) {
				int numberOfTrainings = user.getMembership().getNumberOfTrainings();
				if(numberOfTrainings >= 1) {
					numberOfTrainings--;
					user.getMembership().setNumberOfTrainings(numberOfTrainings);
					user.getVisitedSportsObjects().add(sportsObjectName);
					return Response.ok().build();
				}
			}
			return Response.status(405).build();
		}
		return Response.status(401).build();
	}
}
