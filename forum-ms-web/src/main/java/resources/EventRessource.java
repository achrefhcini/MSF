package resources;
import java.io.File;
import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.annotation.JsonIgnore;

import iservices.IEventServiceLocal;
import persistance.Event;
import persistance.User;

@Path("/Event")
@RequestScoped
public class EventRessource {
	
	@Inject
	IEventServiceLocal eventManger;
	
	
	@Path("/addEvent")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes()
	public Response AddEvent(
							 @QueryParam("IDUser") Integer IDUser,
							 @QueryParam("Enable") Boolean Enable,
							 @QueryParam("NbPlace") Integer NbPlace,
							 @QueryParam("Title") String Title,
							 @QueryParam("StartDate") Date StartDate,
							 @QueryParam("EndDate") Date EndDate,
							 @QueryParam("price") Double price,
							 @QueryParam("image") String image)
	{
		User u=new User();
		u.setId(IDUser);
		Event event = new Event();
		event.setCreator(u);
		event.setEnable(Enable);
		event.setNbPlace(NbPlace);
		event.setTitle(Title);
		event.setStartDate(StartDate);
		event.setEndDate(EndDate);
		event.setPrice(price);
		File f = new File("D:/event.jpg");//D:/event.jpg
		try {
		// Util.upload(f);//("http://localhost:80/restimage/", f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		event.setImage("http://localhost:80/restimage/"+f.getName());
		return Response.ok(eventManger.AddEvent(event)).build();
	}
	
	   @Path("/getAllEvents")
	   @GET
	   @Produces(MediaType.APPLICATION_JSON)
	   public Response GetAllEvents()
	   {
		   return Response.status(200).entity(eventManger.GetAllEvents()).build();
	   }
	   
	    @Path("/getEventById/{id}")
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.TEXT_PLAIN)
		public Response getEventById(@PathParam("id") int id)
		{
	    	
			Event ev=eventManger.GetEventById(id);
			//System.err.println(ev.toString());
			if(ev!=null)
			{
				
				return Response.ok(ev).build();
			}
				
			return Response.status(Status.NO_CONTENT).build();
		}
	    
	    @Path(value="/UpdateEvent/{id}") 
	    @PUT
	    @Consumes(MediaType.APPLICATION_JSON)
	    public Response UpdateRdv(@PathParam(value="id")int id,Event event)
	    {
	       event.setIdEvent(id);
	 	   Boolean res=eventManger.UpdateEvent(event);
	 	  if(res)
	 	  return Response.status(200).build();
	 	  
	 	  return Response.status(Status.NOT_FOUND).build();
	    }
	    
	    
	    @Path("/DeleteEvent/{id}") 
	    @DELETE  
	    public Response DeleteEventById(@PathParam(value="id")int id)
	    {
	    	Event e=new Event();
	    	e.setIdEvent(id);
	 	  if(eventManger.DelteEvent(e))
	 	   return Response.ok().build();
	 	  
	 	  return Response.status(404).build();
	    }
	    @POST
	    @Consumes(MediaType.APPLICATION_JSON)
	    @Path(value="AddEventJson")        
	    public Response AddEventJson(Event r)
	    {
	 	  if(eventManger.AddEvent(r))
	 	  return Response.status(Status.CREATED).build();//==Response.status(200)==Response.OK
	 	  return Response.status(404).build();
	    }
	    
	    
	       @Path("/GetEventsByName/{title}") 
		   @GET
		   @Produces(MediaType.APPLICATION_JSON)	    
		   public Response GetEventsByTitle(@PathParam(value="title")String title)
		   {
			   return Response.status(200).entity(eventManger.GetEventsByTitle(title)).build();
		   }
	       
	       @Path("/GetEventsByUser/{iduser}") 
		   @GET
		   @Produces(MediaType.APPLICATION_JSON)	    
		   public Response GetEventsByUser(@PathParam(value="iduser")int iduser)
		   {
			   return Response.status(200).entity(eventManger.GetEventsByUser(iduser)).build();
		   }
	       @Path("/getTodayEvents")
		   @GET
		   @Produces(MediaType.APPLICATION_JSON)	    
		   public Response getTodayEvents()
		   {
			   return Response.status(200).entity(eventManger.GetTodayEvents()).build();
		   }
	       @Path("/GetEventsBetweenDates")
		   @POST
		   @Produces(MediaType.APPLICATION_JSON)
		   @Consumes()
	   	  public Response GetEventsBetweenDates(
	   							@QueryParam("Date1") Date date1,
	   							@QueryParam("Date2") Date date2)
	       {
	    	   return Response.status(200).entity(eventManger.GetEvensBetweenDates(date1, date2)).build();
	       }
	       @Path("/GetAllUsersJoindEvent/{idevent}") 
		   @GET
		   @Produces(MediaType.APPLICATION_JSON)	    
		   public Response GetAllUsersJoindEvent(@PathParam(value="idevent")Integer idevent)
		   {
			   return Response.status(200).entity(eventManger.GetAllUsersJoindEvent(idevent)).build();
		   }
	       @Path("/GetEventJoinedByGivenUser/{iduser}") 
		   @GET
		   @Produces(MediaType.APPLICATION_JSON)	    
		   public Response GetEventJoinedByGivenUser(@PathParam(value="iduser")Integer idUser)
		   {
			   return Response.status(200).entity(eventManger.GetEventJoinedByGivenUser(idUser)).build();
		   }
	       
	       @Path("/ListOfEventThisWeek") 
		   @GET
		   @Produces(MediaType.APPLICATION_JSON)	    
		   public Response ListOfEventThisWeek()
		   {
			   return Response.status(200).entity(eventManger.ListOfEventThisWeek()).build();
		   }
	       @Path("/GetEtatEvent/{idevent}") 
		   @GET
		   @Produces(MediaType.TEXT_HTML)	    
		   public Response GetEtatEvent(@PathParam(value="idevent")Integer idevent)
	       {
	    	   return Response.status(200).entity(eventManger.GetEtatEvent(idevent)).build();
	       }
}
