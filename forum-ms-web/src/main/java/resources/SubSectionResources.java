package resources;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import iservices.ISubSectionLocal;
import persistance.SubSection;

@Path("/subsection")
@RequestScoped
public class SubSectionResources {
	@Inject
	ISubSectionLocal subsectionBusiness;
	
	@GET
	@Path("/{sid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSubsectionsOfSection(@PathParam(value = "sid") int sectionID, @QueryParam(value="subsid") Integer subSID) {
		if (subSID != null)
			return Response.status(Status.OK).entity(subsectionBusiness.getSubSectionByID(subSID)).build();
		
		return Response.status(Status.OK).entity(subsectionBusiness.getAllSubSectionsOfSection(sectionID)).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addSubSection(SubSection subSection) {
		if (subsectionBusiness.addSubSecion(subSection))
			return Response.status(Status.CREATED).build();
		else
			return Response.status(Status.EXPECTATION_FAILED).build();
	}
	
	@POST
	@Path("{subsectionid}")
	public Response setSubSectionVisibility(@PathParam(value = "subsectionid") int subSID) {
		if (subsectionBusiness.setSubSectionVisibility(subSID))
			return Response.status(Status.OK).build();
		else
			return Response.status(Status.EXPECTATION_FAILED).build();
	}
	
	@DELETE
	@Path("{subsid}")
	public Response deleteSubSection(@PathParam(value = "subsid")int subSID) {
		if (subsectionBusiness.deleteSubSection(subSID))
			return Response.status(Status.OK).build();
		else
			return Response.status(Status.EXPECTATION_FAILED).build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editSubSection(SubSection subSection) {
		if (subsectionBusiness.editSubSection(subSection))
			return Response.status(Status.OK).build();
		else
			return Response.status(Status.EXPECTATION_FAILED).build();
	}
}
