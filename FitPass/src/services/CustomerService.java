package services;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import beans.User;
import beans.enums.UserType;

@Path("/customer")
public class CustomerService {
	@Context
	ServletContext ctx;
	
	public CustomerService() {
		
	}
	
	@PostConstruct
	private void init() {
	}
	
	private boolean isAuthorized(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if(user != null && user.getUserType() == UserType.CUSTOMER) {
			return true;
		}
		return false;
	}
}
