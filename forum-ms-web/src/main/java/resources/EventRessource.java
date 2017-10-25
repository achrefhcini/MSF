package resources;

import java.util.Date;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
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
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import iservices.IEventServiceLocal;
import persistance.Event;
import persistance.User;

@Path("/Event")
@RequestScoped
public class EventRessource {
	
	@EJB
	IEventServiceLocal eventManger;
	
	
	 /*@Context 
	 ResourceContext resourceContext;
	 UploadFilesService b = resourceContext.getResource(UploadFilesService.class);*/
	//XXX********************ADD METHOD 1*********************XXX
	@Path("/addEvent")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes("multipart/form-data")
	public Response AddEvent(
							 @QueryParam("IDUser") Integer IDUser,
							 @QueryParam("Enable") Boolean Enable,
							 @QueryParam("NbPlace") Integer NbPlace,
							 @QueryParam("Title") String Title,
							 @QueryParam("StartDate") Date StartDate,
							 @QueryParam("EndDate") Date EndDate,
							 @QueryParam("price") Double price,
							 MultipartFormDataInput input)
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
		event.setImage(UploadFilesService.uploadFile(input));
		/*File f = new File("D:/event.jpg");//D:/event.jpg
		try {
		// Util.upload(f);//("http://localhost:80/restimage/", f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		event.setImage("http://localhost:80/restimage/"+f.getName());*/
		return Response.ok(eventManger.AddEvent(event)).build();
	}
	
	   //Get All Events
	  //XXX********************GET ALL EVENTS*********************XXX
	   @Path("/getAllEvents")
	   @GET
	   @Produces(MediaType.APPLICATION_JSON)
	   public Response GetAllEvents()
	   {
		   return Response.status(200).entity(eventManger.GetAllEvents()).build();
	   }
	   
	   //GetEventByID
	   //XXX********************GET Event By Id*********************XXX
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
	    
	    //XXX********************UPDATE EVENT*********************XXX
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
	    
	    //XXX********************DELETE EVENT*********************XXX
	    @Path(value="/DeleteEvent/{id}") 
	    @DELETE
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response DeleteEventById(@PathParam(value="id")int id)
	    {
	    	Event e=new Event();
	    	e.setIdEvent(id);
	 	  if(eventManger.DelteEvent(e))
	 		 return Response.ok(eventManger.DelteEvent(e)).build();
	 	  
	 	  //return Response.status(404).build();
	 	 return Response.ok(eventManger.DelteEvent(e)).build();
	    }
	  	    
	  //XXX********************Add Event Json*********************XXX
	    @POST
	    @Consumes(MediaType.APPLICATION_JSON)
	    @Path(value="AddEventJson")        
	    public Response AddEventJson(Event r)
	    {
	 	  if(eventManger.AddEvent(r))
	 	  return Response.status(Status.CREATED).build();//==Response.status(200)==Response.OK
	 	  return Response.status(404).build();
	    }
	    
	    //XXX********************GET EVENTS BY NAME*********************XXX
	       @Path("/GetEventsByName/{title}") 
		   @GET
		   @Produces(MediaType.APPLICATION_JSON)	    
		   public Response GetEventsByTitle(@PathParam(value="title")String title)
		   {
			   return Response.status(200).entity(eventManger.GetEventsByTitle(title)).build();
		   }
	       
	       //XXX********************Get Events By User*********************XXX
	       @Path("/GetEventsByUser/{iduser}") 
		   @GET
		   @Produces(MediaType.APPLICATION_JSON)	    
		   public Response GetEventsByUser(@PathParam(value="iduser")int iduser)
		   {
			   return Response.status(200).entity(eventManger.GetEventsByUser(iduser)).build();
		   }
	       
	     //XXX********************SEARCH TODAY EVENTS*********************XXX
	       @Path("/getTodayEvents")
		   @GET
		   @Produces(MediaType.APPLICATION_JSON)	    
		   public Response getTodayEvents()
		   {
			   return Response.status(200).entity(eventManger.GetTodayEvents()).build();
		   }
	      
	       //XXX********************SEARCH EVENTS BEETWEEN TWO GIVEN DATES*********************XXX
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
	       
	       //XXX********************Get All Users Joind Event*********************XXX
	       @Path("/GetAllUsersJoindEvent/{idevent}") 
		   @GET
		   @Produces(MediaType.APPLICATION_JSON)	    
		   public Response GetAllUsersJoindEvent(@PathParam(value="idevent")Integer idevent)
		   {
			   return Response.status(200).entity(eventManger.GetAllUsersJoindEvent(idevent)).build();
		   }
	       
	      
	      //XXX********************Get All Events joined by given user*********************XXX
	       @Path("/GetEventJoinedByGivenUser/{iduser}") 
		   @GET
		   @Produces(MediaType.APPLICATION_JSON)	    
		   public Response GetEventJoinedByGivenUser(@PathParam(value="iduser")Integer idUser)
		   {
			   return Response.status(200).entity(eventManger.GetEventJoinedByGivenUser(idUser)).build();
		   }
	       
	      
		  //XXX********************get all events of the current week*********************XXX
	       @Path("/ListOfEventThisWeek") 
		   @GET
		   @Produces(MediaType.APPLICATION_JSON)	    
		   public Response ListOfEventThisWeek()
		   {
			   return Response.status(200).entity(eventManger.ListOfEventThisWeek()).build();
		   }
	       
	       
		//XXX********************get event staut(finished/started/in progress)*********************XXX
	       @Path("/GetEtatEvent/{idevent}") 
		   @GET
		   @Produces(MediaType.TEXT_HTML)	    
		   public Response GetEtatEvent(@PathParam(value="idevent")Integer idevent)
	       {
	    	   return Response.status(200).entity(eventManger.GetEtatEvent(idevent)).build();
	       }
	       
	       
	       
	       @POST
	       @Path("/uploadimage")
	       @Consumes("*/*")
	       @Produces(MediaType.TEXT_PLAIN)
	       public Response uploadBinary(@MultipartForm MyEntity myEntity) {
	    
	           JsonObject jsonFile = Json.createObjectBuilder()
	                   .add("length", myEntity.getData().length)
	                   .add("file", myEntity.getData().toString())
	                   .build();
	    
	           return Response.ok(jsonFile).build();
	       }
	       
	      
			//XXX********************Get Events for ajax search*********************XXX
	       @Path("/GetEventsForAjax/{crit}") 
		   @GET
		   @Produces(MediaType.APPLICATION_JSON)	    
		   public Response GetEventsForAjax(@PathParam(value="crit")String crit)
		   {
			   return Response.status(200).entity(eventManger.GetEventsForAjax(crit)).build();
		   }
	       
	       
		//XXX********************UpdateEventAdmin*********************XXX
	       @Path(value="/UpdateEventAdmin/{id}") 
		   @PUT
		   @Consumes(MediaType.APPLICATION_JSON)
		    public Response UpdateEventByAdmin(@PathParam(value="id")int id,Event event)
		    {
		       event.setIdEvent(id);
		 	   Boolean res=eventManger.UpdateEvent(event);
		 	  if(res)
		 	  return Response.status(200).build();
		 	  
		 	  return Response.status(Status.NOT_FOUND).build();
		    }
	       
		//XXX********************GetActiveEventForAdmin*********************XXX
	       @Path("/GetActiveEventForAdmin") 
		   @GET
		   @Produces(MediaType.APPLICATION_JSON)	    
		   public Response GetActiveEventForAdmin()
		   {
			   return Response.status(200).entity(eventManger.GetActiveEventForAdmin()).build();
		   }
	       
			//XXX********************GetInActiveEventForAdmin*********************XXX
	       @Path("/GetInActiveEventForAdmin") 
		   @GET
		   @Produces(MediaType.APPLICATION_JSON)	    
		   public Response GetInActiveEventForAdmin()
		   {
			   return Response.status(200).entity(eventManger.GetInActiveEventForAdmin()).build();
		   }
	       
	       
}
