package services;

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

import beans.Image;
import dao.ImageDAO;


@Path("/image")
public class ImageService {

	@Context
	ServletContext ctx;
	
	private static ImageDAO imageDAO;
	
	public ImageService() {	}
	
	@PostConstruct
	private void init() {
		imageDAO = ImageDAO.getInstance();
	}
	
	@GET
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findImage(@PathParam("id") String id, @Context HttpServletRequest request) {
		return Response.ok(imageDAO.findImage(id)).build();
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveImage(Image image, @Context HttpServletRequest request) {
		String imageId = imageDAO.saveImage(image.getImage());
		if(imageId == null) {
			return Response.status(400).build();
		}
		return Response.ok(imageId, MediaType.APPLICATION_JSON).build();
	}
}
