package resources;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import data.DataTest;
import iservices.ISupportTicketLocal;
import iservices.ISupportWorkFollow;
import persistance.SupportTicket;
import persistance.SupportWorkFollow;
import util.ResponseError;
import util.ResponseTmp;
import util.StateWorkFollow;
import util.SupportUtil;
import util.TypeTicket;

@Path("/me/workfollows")
@RequestScoped
public class SupportWF {
	@EJB
	ISupportTicketLocal supportManager;
	
	@EJB
	ISupportWorkFollow workFollowManager;
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes()
	public Response addWorkFollow(
			@QueryParam("ticketNumber") String ticketNumber,
			@QueryParam("causeWF") String causeWF,
			@QueryParam("noticeWF") String noticeWF
			) {
		
		SupportTicket supportTicket=supportManager.findSupportTicketByTicketNumber(ticketNumber);
		SupportWorkFollow sWF=new SupportWorkFollow();
		Set<SupportTicket> supportTickets=new HashSet();
		supportTickets.add(supportTicket);
		sWF.setSupportTickets(supportTickets);
		sWF.setUserReply(DataTest.user1);
		sWF.setCauseWF(causeWF);
		sWF.setNoticeWF(noticeWF);
		return Response.ok(workFollowManager.addSupportWF(sWF)).build();
			
           }

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllSupportWorkFollow(){
		if(!workFollowManager.getSupportWorkFollow().isEmpty()){
			return Response.ok(workFollowManager.getSupportWorkFollow()).build();
		}
		else {
			ResponseTmp rp=new ResponseTmp("could not find Data for this request","DataException",290);
		    return Response.status(416).entity(rp).build();
		}
		
	}
	
	@Path("/t/{idTicket}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getWorkFollowByIdTicket(@PathParam("idTicket") String idTicket){
		List<SupportWorkFollow> list=null;
		try {
		SupportTicket supportTicket=supportManager.findSupportTicketByTicketNumber(idTicket);
            
		list=
        		workFollowManager.findSupportWorkFollowByTicket(supportTicket);
	      
		if(list==null){
		    return Response.status(416).entity(ResponseError.NO_DATA).build();
		}
			return Response.ok(list).build();
		}
		catch (Exception e){
			
		    return Response.status(416).entity(ResponseError.NO_DATA).build();
		}
	    
	   
		
		
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateStateWF(@QueryParam("idWF") int idWF,@QueryParam("state") int state){
		SupportWorkFollow wf=workFollowManager.findSupportWorkFollowByID(idWF);
		wf.setStateWF(SupportUtil.checkStateWF(wf, state));
		
		
		return Response.ok(workFollowManager.changeStateWF(wf)).build();
		
	}
	@Path("/to")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response affecTStateWF(@QueryParam("idWF") int idWF,@QueryParam("state") int state,@QueryParam("causeWF")
	String causeWF,@QueryParam("noticeWF") String noticeWF){
		if(state==StateWorkFollow.PASSED){
			SupportWorkFollow wf=workFollowManager.findSupportWorkFollowByID(idWF);
			SupportWorkFollow wf1=new SupportWorkFollow();
			wf1.setCauseWF(causeWF);
			wf1.setNoticeWF(noticeWF);
			wf.setStateWF(StateWorkFollow.PASSED);
			
			
			return Response.ok(workFollowManager.affectTOWF(wf,wf1)).build();
		}
		else 
			return Response.status(416).entity(ResponseError.ADDED).build();
		
		
	}

	
	@Path("/u/{idUser}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getWorkFollowByIdUser(@PathParam("idUser") String idUser){
		
		List<SupportWorkFollow> list=null;
		try {
			list=workFollowManager.findSupportWorkFollowByUser(DataTest.user1);
	      
		if(list==null){
		    return Response.status(416).entity(ResponseError.NO_DATA).build();
		}
			return Response.ok(list).build();
		}
		catch (Exception e){
			
		    return Response.status(416).entity(ResponseError.NO_DATA).build();
		}
	    


		
	}
	
	
	
 
	
}
