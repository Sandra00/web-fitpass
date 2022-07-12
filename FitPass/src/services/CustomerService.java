package services;

import java.time.LocalDateTime;

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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Comment;
import beans.Membership;
import beans.PromoCode;
import beans.Training;
import beans.TrainingHistory;
import beans.User;
import beans.enums.CommentStatus;
import beans.enums.UserType;
import dao.CommentDAO;
import dao.MembershipDAO;
import dao.PromoCodeDAO;
import dao.SportsObjectDAO;
import dao.TrainingDAO;
import dao.TrainingHistoryDAO;
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
		if (ctx.getAttribute("promoCodeDAO") == null) {
			ctx.setAttribute("promoCodeDAO", new PromoCodeDAO());
		}
		if (ctx.getAttribute("userDAO") == null) {
			ctx.setAttribute("userDAO", new UserDAO());
		}
		if (ctx.getAttribute("trainingHistoryDAO") == null) {
			ctx.setAttribute("trainingHistoryDAO", new TrainingHistoryDAO());
		}
		if (ctx.getAttribute("trainingDAO") == null) {
			ctx.setAttribute("trainingDAO", new TrainingDAO());
		}
		if (ctx.getAttribute("sportsObjectDAO") == null) {
			ctx.setAttribute("sportsObjectDAO", new SportsObjectDAO());
		}
		if (ctx.getAttribute("commentDAO") == null) {
			ctx.setAttribute("commentDAO", new CommentDAO());
		}
	}
	
	private boolean isAuthorized(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if(user != null && user.getUserType() == UserType.CUSTOMER) {
			return true;
		}
		return false;
	}
	
	@GET
	@Path("/promo-code/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkPromoCode(@PathParam("id") String promoCode, @Context HttpServletRequest request) {
		if(isAuthorized(request)) {
			PromoCodeDAO promoCodeDAO = (PromoCodeDAO) ctx.getAttribute("promoCodeDAO");
			if(promoCodeDAO.findByCode(promoCode) != null) {
				return Response.ok(promoCodeDAO.findByCode(promoCode)).build();
			}
			return Response.status(405).build();
		}
		return Response.status(401).build();
	}
	
	@PUT
	@Path("/new-membership")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response setMembership(	@QueryParam("membershipId") String membershipId, 
									@QueryParam("promoId") String promoId, 
									@Context HttpServletRequest request ) {
		
		if(isAuthorized(request)) {
			User user = (User) request.getSession().getAttribute("user");
			MembershipDAO membershipDAO = (MembershipDAO) ctx.getAttribute("membershipDAO");
			PromoCodeDAO promoCodeDAO = (PromoCodeDAO) ctx.getAttribute("promoCodeDAO");
			UserDAO userDAO = (UserDAO) ctx.getAttribute("userDAO");
			if(membershipDAO.findById(membershipId) != null) {
				Membership membership = new Membership(membershipDAO.findById(membershipId));
				PromoCode promoCode = promoCodeDAO.findByCode(promoId);
				if(promoCode != null) {

					if(promoCode.getExpirationDate().isAfter(LocalDateTime.now()) && promoCode.getUsesLeft() >= 1)
					{
						promoCodeDAO.decrementUsesLeft(promoCode);
						int memberDiscount = 0;
						if(user.getCustomerType() != null) {
							switch(user.getCustomerType()) {
							case BRONZE:
								memberDiscount = 1;
								break;
							case GOLD:
								memberDiscount = 5;
								break;
							case SILVER:
								memberDiscount = 10;
								break;
							}
						}
						calculateDiscountPrice(membership, promoCode.getDiscountPercentage() + memberDiscount);
						setMembershipDates(membership);
						userDAO.setMembership(user.getUsername(), membership);
						return Response.ok().build();
					}
					return Response.status(405).build();
				}
				userDAO.setMembership(user.getUsername(), membership);
				return Response.ok().build();
			}
			return Response.status(405).build();
		}
		return Response.status(401).build();
	}
	
	
	/*
	 * check if authorized -- works
	 * check if user has membership -- works
	 * check if sports object exists -- works
	 * check if training exists -- works
	 * check if membership has sufficient number of trainings -- works
	 * check if membership is not expired -- works
	 * decrement number of trainings in users membership -- works
	 * add sports object to visited sports object set in user -- works
	 * add training to training history -- works
	 */
	@PUT
	@Path("/check-in")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response sportsObjectCheckIn(	@QueryParam("sportsObjectName") String sportsObjectName, 
											@QueryParam("trainingId") String trainingId, 
											@Context HttpServletRequest request ) {
		if(isAuthorized(request)) {
			User user = (User) request.getSession().getAttribute("user");
			TrainingHistoryDAO trainingHistoryDAO = (TrainingHistoryDAO) ctx.getAttribute("trainingHistoryDAO");
			SportsObjectDAO sportsObjectDAO = (SportsObjectDAO) ctx.getAttribute("sportsObjectDAO");
			TrainingDAO trainingDAO = (TrainingDAO) ctx.getAttribute("trainingDAO");
			UserDAO userDAO = (UserDAO) ctx.getAttribute("userDAO");
			User databaseUser = userDAO.findUserByUsername(user.getUsername());
			int trainingIdNum;
			
			try {
				trainingIdNum = Integer.parseInt(trainingId);
			}
			catch(Exception ex) {
				return Response.status(405).build();
			}
			
			Training training = trainingDAO.findById(trainingIdNum);
			if(databaseUser.getMembership() != null && 
					databaseUser.getMembership().getDueDate().isAfter(LocalDateTime.now()) && 
						training != null &&
							sportsObjectDAO.exists(sportsObjectName) &&
								databaseUser.getMembership().getTrainingsUsed() < databaseUser.getMembership().getNumberOfTrainings()) {
				
				int numberOfTrainings = databaseUser.getMembership().getTrainingsUsed();
				numberOfTrainings++;
				databaseUser.getMembership().setTrainingsUsed(numberOfTrainings);
				databaseUser.getVisitedSportsObjects().add(sportsObjectName);
				TrainingHistory trainingHistory = new TrainingHistory(0, LocalDateTime.now(), trainingIdNum, databaseUser.getUsername(), training.getCoach(), false);
				trainingHistoryDAO.addTrainingHistory(trainingHistory);
				userDAO.save();
				return Response.ok().build();
			}
			return Response.status(405).build();
		}
		return Response.status(401).build();
	}
	
	@GET
	@Path("/has-visited/{name}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response hasVisited(@PathParam("name") String sportsObjectName, @Context HttpServletRequest request) {
		if(isAuthorized(request)) {
			User user = (User) request.getSession().getAttribute("user");
			UserDAO userDAO = (UserDAO) ctx.getAttribute("userDAO");
			User databaseUser = userDAO.findUserByUsername(user.getUsername());
			if(databaseUser.getVisitedSportsObjects().contains(sportsObjectName)) {
				return Response.ok().build();
			}
			return Response.status(400).build();
		}
		return Response.status(401).build();
	}
	
	@GET
	@Path("/comments/{name}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response sportsObjectComments(@PathParam("name") String sportsObjectName, @Context HttpServletRequest request) {
		if(isAuthorized(request)) {
			CommentDAO commentDAO = (CommentDAO) ctx.getAttribute("commentDAO");
			return Response.ok(commentDAO.findApprovedCommentsBySportsObject(sportsObjectName), MediaType.APPLICATION_JSON).build();
		}
		return Response.status(401).build();
	}
	
	@POST
	@Path("/leave-comment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response leaveComment(Comment comment, @Context HttpServletRequest request) {
		if(isAuthorized(request)) {
			User user = (User) request.getSession().getAttribute("user");
			UserDAO userDAO = (UserDAO) ctx.getAttribute("userDAO");
			User databaseUser = userDAO.findUserByUsername(user.getUsername());
			if(databaseUser.getVisitedSportsObjects().contains(comment.getSportsObjectName())) {
				CommentDAO commentDAO = (CommentDAO) ctx.getAttribute("commentDAO");
				comment.setStatus(CommentStatus.WAITING);
				comment.setCustomerUsername(databaseUser.getUsername());
				if(comment.getGrade() >= 1 && comment.getGrade() <= 5) {
					commentDAO.addComment(comment);
					return Response.ok().build();
				}
				return Response.status(400).build();
			}
			return Response.status(400).build();
		}
		return Response.status(401).build();
	}
	
	private void calculateDiscountPrice(Membership membership, double discountPercentage) {
		double currentPrice = membership.getPrice();
		double discountedPrice = currentPrice * ((100 - discountPercentage) / 100);
		membership.setPrice(discountedPrice);
	}
	
	
	private void setMembershipDates(Membership membership) {
		membership.setTransactionDate(LocalDateTime.now());
		switch(membership.getMembershipType()) {
		case ANNUALLY:
			membership.setDueDate(LocalDateTime.now().plusYears(1));
			break;
		case MONTHLY:
			membership.setDueDate(LocalDateTime.now().plusMonths(1));
			break;
		case WEEKLY:
			membership.setDueDate(LocalDateTime.now().plusWeeks(1));
			break;
			
		}
	}
}
