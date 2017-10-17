package resources;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import iservices.IEventServiceLocal;
import iservices.ITicketServiceLocal;
import persistance.Event;
import persistance.Ticket;
import persistance.User;

@Path("/Tikcet")
@RequestScoped
public class TikcetRessource {
	@Inject
	ITicketServiceLocal TicketManger;
	@Path("/addTicket")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes()
	public Response AddTicket(
							 @QueryParam("IDEvent") Integer IDEvent,
							 @QueryParam("IDUser") Integer IDUser,
							 @QueryParam("code") String code)
	{
		Event event=new Event();
		event.setIdEvent(IDEvent);
		User u=new User();
		u.setId(IDUser);
		Ticket tt=new Ticket();
		tt.setEvent(event);
		tt.setParticipant(u);
		tt.setCodeTicket(code);		
		return Response.ok(TicketManger.AddTicket(tt)).build();
	}
	
	   @Path("/GetAllTicket")
	   @GET
	   @Produces(MediaType.APPLICATION_JSON)	    
	   public Response GetAllTicket()
	   {
		   return Response.status(200).entity(TicketManger.GetAllTicket()).build();
	   }
	   
	    @Path("/getTicketById/{id}")
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.TEXT_PLAIN)
		public Response getEventById(@PathParam("id") int id)
		{
	    	
			Ticket ev=TicketManger.GetTicketById(id);
			//System.err.println(ev.toString());
			if(ev!=null)
			{
				
				return Response.ok(ev).build();
			}
				
			return Response.status(Status.NO_CONTENT).build();
		}
	    @Path("/DeleteTicket/{id}") 
	    @DELETE  
	    public Response DeleteTicketById(@PathParam(value="id")int id)
	    {
	    	Ticket tt=new Ticket();
	    	tt.setIdTicket(id);
	 	  if(TicketManger.DelteTicket(tt))
	 	   return Response.ok().build();
	 	  
	 	  return Response.status(404).build();
	    }
}
