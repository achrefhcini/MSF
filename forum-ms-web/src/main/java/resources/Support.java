package resources;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.JsonValue;
import javax.validation.constraints.Null;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;
import data.DataTest;
import iservices.ISupportTicketLocal;
import persistance.SupportTicket;
import persistance.User;
import util.ResponseTmp;
import util.SupportUtil;
import util.TypeTicket;


@Path("/me")
@RequestScoped
public class Support {
	@EJB
	ISupportTicketLocal supportManager;
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getInfo(@Context HttpHeaders headers) {

		String userAgent = headers.getRequestHeader("user-agent").get(0);
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		json.put("userConnected", DataTest.user1.getUsername());
		json.put("userAgent",userAgent);
		array.put(json);
		

		return Response.ok(array.toString())
			.build();

	}  

	@Path("/tickets")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes()
	public Response addTicket(
			@QueryParam("subject") String subject,
			@QueryParam("category") String category,
			@QueryParam("universe") String universe,
			@QueryParam("typeTicket") TypeTicket typeTicket,
			@QueryParam("fields") String fields
			)
	{
	
			
		
		SupportTicket supportTicket = new SupportTicket();
		supportTicket.setSubject(subject);
		supportTicket.setCategory(category);;
		supportTicket.setUniverse(universe);
		supportTicket.setTypeTicket(typeTicket);
		supportTicket.setTicketNumber(SupportUtil.getTicketNumberRandom()+"");;
		supportTicket.setConcernedUser(DataTest.user1);	

		
		return Response.ok(supportManager.addTicketSupport(supportTicket)).build();
		
	}
	
	@Path("/tickets")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response ListSupportTicket(){
	
		System.out.println(supportManager.getSupportTickets());
		
		return Response.ok(supportManager.getSupportTickets()).build();
	
		
	}
	
	@Path("/tickets/{idTicket}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTicketsById(@PathParam("idTicket") String idTicket){
	
	
		
		return Response.ok(supportManager.findSupportTicketByTicketNumber(idTicket)).build();
	
		
	}
	
	@Path("/tickets")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateSupportId(@QueryParam("idTicket") int idTicket,@QueryParam("numberT") String numberT
			){
		    
		
		SupportTicket st=supportManager.findSupportTicketById(idTicket);
		st.setTicketNumber(numberT);
		return Response.ok(supportManager.updateSupportTicket(st)).build(); 
		
	}
	

	@Path("/tickets/{orderBy}/{target}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response ListSupportTicketOrder(@PathParam("target") String order,
			@PathParam("orderBy") String orderBy
			){
		
	if((order.toString().toUpperCase().equals("ASC"))||
			(order.toString().toUpperCase().equals("DESC"))){
		System.out.println("------"+order);
		try {
			List<SupportTicket> ls=supportManager.getSupportTickedOrderAD(order,orderBy);
			return Response.ok(ls).build();
		} catch (Exception e) {
			ResponseTmp rp=new ResponseTmp("could not resolve property for this request","SqlException",275);
		    return Response.status(416).entity(rp).build();
		}
	
		
	}
	else {
		ResponseTmp rp=new ResponseTmp("Cannot determine the target object for this request","OAuthException",275);
	    return Response.status(416).entity(rp).build();
	}
		
	}
	
	@Path("/tickets/t/{idfrom}/{idto}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response ListSupportTicketFromTo(@PathParam("idfrom") int fromId,
			@PathParam("idto") int toId){
		if(fromId<=toId){
			try {
				Runtime.getRuntime().exec("javac -cp App");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return Response.ok(supportManager.getSupportTicketsFromTo(fromId, toId)).build();
  
		}
		else {
		
			ResponseTmp errorMessage=new ResponseTmp("Error","",401);
			return Response.status(Status.NOT_ACCEPTABLE)
    				.entity(errorMessage)
    				.type(MediaType.APPLICATION_JSON).
    				build();
		}
		
	}
	
	@Path("/tickets")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response ChangeStateTicketSupport(@QueryParam("idTicket") int idTicket,
			@QueryParam("state") int state){
	
		SupportTicket st=supportManager.findSupportTicketById(idTicket);
		System.out.println(st.toString());
		return Response.ok(supportManager.changeState(st, state)).build();
		
	}
	
	
	
}
	