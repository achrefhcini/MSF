package resources;

import java.net.URI;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import iservices.ISupportResponse;
import iservices.ISupportTicketLocal;
import persistance.SupportResponse;
import persistance.SupportTicket;
import util.ResponseTypeContent;

@RequestScoped
@Path("/responses")
public class SuuportResponse {
	@Inject
	ISupportTicketLocal supportManager;
	
	@Inject 
	ISupportResponse responseManager;
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes()
	public Response addResponseTicket(	
			@QueryParam("ticketNumber") String tNumber,
			@QueryParam("typeResponse") ResponseTypeContent typeResponse,
			@QueryParam("contentResponse") String contentResponse
			
			){
		
		SupportTicket supportTicket=supportManager.findSupportTicketByTicketNumber(tNumber);
        SupportResponse sr=new SupportResponse();
        sr.setContentResponse(contentResponse);
        sr.setSupport_ticket(supportTicket);
        if(typeResponse==typeResponse.CALL){
         
           

        }
        
        
		return Response.ok(responseManager.addSupportResponse(sr)).build();
	}
	@Path("/img")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes()
	public Response uploadImage(){
		responseManager.uploadImage();
		return Response.ok().build();
	}
}
