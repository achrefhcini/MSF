package resources;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import iservices.ISectionLocal;
import persistance.Section;

@Path("/section")
@RequestScoped
public class SectionResources {
	
	@Inject
	ISectionLocal sectionBusiness;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSections(@QueryParam(value = "sid") Integer sectionID) {
		if (sectionID != null)
			return Response.status(Status.OK).entity(sectionBusiness.getSectionByID(sectionID)).build();
		else
			return Response.status(Status.OK).entity(sectionBusiness.getAllSections()).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addSection(Section section) {
		if (sectionBusiness.addSection(section))
			return Response.status(Status.CREATED).build();
		
		return Response.status(Status.EXPECTATION_FAILED).build();
	}
	
	@POST
	@Path("{sid}")
	public Response setLock(@PathParam(value = "sid") int sectionID) {
		if (sectionBusiness.setSectionLock(sectionID))
			return Response.status(Status.OK).build();
		else
			return Response.status(Status.EXPECTATION_FAILED).build();
	}
	
	@DELETE
	@Path("/{sid}")
	public Response deleteSection(@PathParam(value = "sid") int sectionID) {
		if (sectionBusiness.deleteSection(sectionID))
			return Response.status(Status.OK).build();
		else
			return Response.status(Status.EXPECTATION_FAILED).build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editSection(Section section) {
		if (sectionBusiness.editSection(section))
			return Response.status(Status.OK).build();
		else
			return Response.status(Status.EXPECTATION_FAILED).build();
	}
	
	// Stats below
	
	@GET
	@Path("{sid}/{startYearRange}/{endYearRange}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPerYearTopicPosts(@PathParam(value = "sid") int sectionID,
										@PathParam(value = "startYearRange") int startYearRange,
										@PathParam(value = "endYearRange") int endYearRange) {
		return Response.status(Status.OK).entity(sectionBusiness.getPerYearPostStats(startYearRange, endYearRange, sectionID)).build();
	}
	
	@GET
	@Path("{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSectionsSortedByPopularity(@PathParam(value = "year") int targetYear) {
		return Response.status(Status.OK).entity(sectionBusiness.getSectionsSortedByPopularity(targetYear)).build();
	}
	
	@GET
	@Path("{sid}/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPerMonthSectionActivity(@PathParam(value = "year") int targetYear,
											@PathParam(value = "sid") int sectionID) {
		return Response.status(Status.OK).entity(sectionBusiness.getPerMonthSectionActivity(sectionID, targetYear)).build();
	}
}
