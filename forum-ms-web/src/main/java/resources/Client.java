package resources;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import iservices.IUserManagerLocal;
import persistance.User;

@Path("/user")
@RequestScoped
public class Client {
	@Inject
	IUserManagerLocal userManager ;
	
	@Path("/getUserById/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	public Response getUserById(@PathParam("id") int id)
	{
		User user=userManager.GetUserById(id);
		if(user!=null)
		{
			System.err.println(user.getCreationDate());
			return Response.ok(user).build();
		}
			
		return Response.status(Status.NO_CONTENT).build();
	}
	
	@Path("/addUser")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes()
	public Response addUser(
			@QueryParam("email") String email,
			@QueryParam("username") String username,
			@QueryParam("password") String password
			)
	{
		User user = new User(email,username,password);
		return Response.ok(userManager.AddUser(user)).build();
	}
	
	
}
