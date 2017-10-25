package resources;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.FilenameUtils;

import data.DataTest;
import iservices.ISupportResponse;
import iservices.ISupportTicketLocal;
import persistance.SupportResponse;
import persistance.SupportTicket;
import util.ResponseError;
import util.ResponseSuccess;
import util.ResponseTypeContent;
import util.SupportUtil;

@RequestScoped
@Path("/responses")
public class SuuportResponse {
	@EJB
	ISupportTicketLocal supportManager;
	
	@EJB 
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
     
        sr.setSupport_ticket(supportTicket);
        if(typeResponse==typeResponse.FILE){
         
        	String extension = FilenameUtils.getExtension(contentResponse);
        	String basename = FilenameUtils.getBaseName(contentResponse);
        	String nameFile=basename+SupportUtil.getResponseProcess()+"."+extension;
        	
            sr.setContentResponse("http://achref.com/forum/"+nameFile);
            try {
            	if((extension.toLowerCase()=="png")||(extension.toLowerCase()=="jpg")
            			||(extension.toLowerCase()=="zip")){
            		Process proc = Runtime.getRuntime().exec("java -jar C:\\Users\\Achref\\noname\\fileUpload\\target\\"
    						+ "fileupload.jar "+contentResponse+" "+nameFile);
            		sr.setTypeResponse(typeResponse);           	
            		
            	 return Response.ok(responseManager.addSupportResponse(sr)).entity(ResponseSuccess.ADDED).build();

            	}
            	else {
            		return Response.status(Status.FORBIDDEN).entity(ResponseError.EXTENSION).build();
	
            	}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return Response.status(Status.BAD_REQUEST).entity(ResponseError.ADDED).build();

			}

        }
        else if(typeResponse==typeResponse.CALL) {
        	String s=null;
    		try {
    			String number=DataTest.user1.getPhoneNumber();
				Process proc = Runtime.getRuntime().exec("java -jar C:\\Users\\Achref\\noname\\apiCall\\target\\"
						+ "call.jar"+" "+number);
				
			    	proc.waitFor();

		            InputStream is = proc.getInputStream();

		            byte b[] = new byte[is.available()];
		            is.read(b, 0, b.length); // probably try b.length-1 or -2 to remove "new-line(s)"

		            s = new String(b);
				sr.setTypeResponse(typeResponse); 
				sr.setContentResponse(s);
           	 return Response.ok(responseManager.addSupportResponse(sr)).entity(ResponseSuccess.ADDED).build();

				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return Response.status(Status.BAD_REQUEST).entity(ResponseError.ADDED).build();

			}
        }
    		else if(typeResponse==typeResponse.TXT) {
				sr.setTypeResponse(typeResponse); 
				   sr.setContentResponse(contentResponse);
		           	 return Response.ok(responseManager.addSupportResponse(sr)).entity(ResponseSuccess.ADDED).build();

    		}
        	
      
        return null;
        
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
