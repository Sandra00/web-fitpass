package services;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.PromoCode;
import beans.SportsObject;
import beans.User;
import beans.enums.UserType;
import dao.CommentDAO;
import dao.MembershipDAO;
import dao.PromoCodeDAO;
import dao.SportsObjectDAO;
import dao.TrainingDAO;
import dao.TrainingHistoryDAO;
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
		if (ctx.getAttribute("promoCodeDAO") == null) {
			ctx.setAttribute("promoCodeDAO", new PromoCodeDAO());
		}
		if (ctx.getAttribute("commentDAO") == null) {
			ctx.setAttribute("commentDAO", new CommentDAO());
		}
		if (ctx.getAttribute("membershipDAO") == null) {
			ctx.setAttribute("membershipDAO", new MembershipDAO());
		}
		if (ctx.getAttribute("trainingHistoryDAO") == null) {
			ctx.setAttribute("trainingHistoryDAO", new TrainingHistoryDAO());
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
	
	private boolean isAuthorized(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if(user != null && user.getUserType() == UserType.ADMIN) {
			return true;
		}
		return false;
	}
	
	@GET
	@Path("/all-users")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response all(@Context HttpServletRequest request) {
		if(isAuthorized(request)) {
			UserDAO userDao = (UserDAO) ctx.getAttribute("userDAO");
			return Response.ok(userDao.findAll(), MediaType.APPLICATION_JSON).build();
		}
		return Response.status(401).build(); 		// 401 - NOT AUTHORIZED
	}
	
	@GET
	@Path("/all-promo-codes")
	@Produces(MediaType.APPLICATION_JSON)
	public Response allPromoCodes(@Context HttpServletRequest request) {
		if(isAuthorized(request)) {
			PromoCodeDAO promoCodeDAO = (PromoCodeDAO) ctx.getAttribute("promoCodeDAO");
			return Response.ok(promoCodeDAO.findAll(), MediaType.APPLICATION_JSON).build();
		}
		return Response.status(401).build(); 		// 401 - NOT AUTHORIZED
	}
	
	@GET
	@Path("/all-memberships")
	@Produces(MediaType.APPLICATION_JSON)
	public Response allMemberships(@Context HttpServletRequest request) {
		if(isAuthorized(request)) {
			MembershipDAO membershipDAO = (MembershipDAO) ctx.getAttribute("membershipDAO");
			return Response.ok(membershipDAO.findAll(), MediaType.APPLICATION_JSON).build();
		}
		return Response.status(401).build(); 		// 401 - NOT AUTHORIZED
	}
	
	@GET
	@Path("/all-trainings")
	@Produces(MediaType.APPLICATION_JSON)
	public Response allTrainings(@Context HttpServletRequest request) {
		if(isAuthorized(request)) {
			TrainingDAO trainingDAO = (TrainingDAO) ctx.getAttribute("trainingDAO");
			return Response.ok(trainingDAO.findAll(), MediaType.APPLICATION_JSON).build();
		}
		return Response.status(401).build(); 		// 401 - NOT AUTHORIZED
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
		return Response.status(401).build(); 		// 401 - NOT AUTHORIZED
	}
	
	@POST
	@Path("/new-promo-code")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response newPromoCode(PromoCode promoCode, @Context HttpServletRequest request) {
		if(isAuthorized(request)) {
			PromoCodeDAO promoCodeDAO = (PromoCodeDAO) ctx.getAttribute("promoCodeDAO");
			if(promoCodeDAO.newPromoCode(promoCode)) {
				return Response.ok().build();
			}
			return Response.status(409).build(); 	// 409 - CONFLICT
		}
		return Response.status(401).build(); 		// 401 - NOT AUTHORIZED
	}
	
	@DELETE
	@Path("/delete/user/{id}")
	public Response deleteUser(@PathParam("id") String id, @Context HttpServletRequest request) {
		if(isAuthorized(request)) {
			UserDAO userDAO = (UserDAO) ctx.getAttribute("userDAO");
			if(userDAO.delete(id)) {
				return Response.ok().build();
			}
			return Response.status(400).build();
		}
		return Response.status(401).build(); 		// 401 - NOT AUTHORIZED
	}

	@DELETE
	@Path("/delete/sports-object/{id}")
	public Response deleteSportsObject(@PathParam("id") String id, @Context HttpServletRequest request) {
		if(isAuthorized(request)) {
			SportsObjectDAO sportsObjectDAO = (SportsObjectDAO) ctx.getAttribute("sportsObjectDAO");
			if(sportsObjectDAO.delete(id)) {
				return Response.ok().build();
			}
			return Response.status(400).build();
		}
		return Response.status(401).build(); 		// 401 - NOT AUTHORIZED
	}
	
	@DELETE
	@Path("/delete/membership/{id}")
	public Response deleteMembership(@PathParam("id") String id, @Context HttpServletRequest request) {
		if(isAuthorized(request)) {
			MembershipDAO membershipDAO = (MembershipDAO) ctx.getAttribute("membershipDAO");
			if(membershipDAO.delete(id)) {
				return Response.ok().build();
			}
			return Response.status(400).build();
		}
		return Response.status(401).build(); 		// 401 - NOT AUTHORIZED
	}
	
	@DELETE
	@Path("/delete/promo-code/{id}")
	public Response deletePromoCode(@PathParam("id") String id, @Context HttpServletRequest request) {
		if(isAuthorized(request)) {
			PromoCodeDAO promoCodeDAO = (PromoCodeDAO) ctx.getAttribute("promoCodeDAO");
			if(promoCodeDAO.delete(id)) {
				return Response.ok().build();
			}
			return Response.status(400).build();
		}
		return Response.status(401).build(); 		// 401 - NOT AUTHORIZED
	}
	
	@DELETE
	@Path("/delete/training/{id}")
	public Response deleteTraining(@PathParam("id") int id, @Context HttpServletRequest request) {
		if(isAuthorized(request)) {
			TrainingDAO trainingDAO = (TrainingDAO) ctx.getAttribute("trainingDAO");
			TrainingHistoryDAO trainingHistoryDAO = (TrainingHistoryDAO) ctx.getAttribute("trainingHistoryDAO");
			if(trainingDAO.delete(id) && trainingHistoryDAO.deleteByTrainingId(id)) {
				return Response.ok().build();
			}
			return Response.status(400).build();
		}
		return Response.status(401).build(); 		// 401 - NOT AUTHORIZED
	}
}
