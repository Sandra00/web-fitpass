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

import beans.Membership;
import beans.PromoCode;
import beans.User;
import beans.enums.MembershipType;
import beans.enums.UserType;
import dao.MembershipDAO;
import dao.PromoCodeDAO;
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
		if (ctx.getAttribute("promoCodeDAO") == null) {
			ctx.setAttribute("promoCodeDAO", new PromoCodeDAO());
		}
		if (ctx.getAttribute("userDAO") == null) {
			ctx.setAttribute("userDAO", new UserDAO());
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
						calculateDiscountPrice(membership, promoCode.getDiscountPercentage());
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
	
	@PUT
	@Path("/check-in/{name}") // add parameter for training, add training to training history here
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response sportsObjectCheckIn(	@QueryParam("sportsObjectName") String sportsObjectName, 
											@QueryParam("trainingId") String trainingId, 
											@Context HttpServletRequest request ) {
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
