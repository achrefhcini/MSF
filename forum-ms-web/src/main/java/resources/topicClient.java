package resources;

import java.util.List;
import java.util.Set;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

import iservices.ITopicManagerLocal;
import iservices.IUserManagerLocal;
import persistance.RateTopic;
import persistance.Topic;
import persistance.User;

@Path("/topic")
@RequestScoped
public class topicClient {
	@Inject
	ITopicManagerLocal topicManager ;
	@Inject
	IUserManagerLocal userManager ;
	
	@Path("/getTopicById/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	public Response getTopicById(@PathParam("id") int id)
	{
		Topic topic=topicManager.GetTopicById(id);
		if(topic!=null)
		{
			return Response.ok(topic).build();
		}
			
		return Response.status(Status.NO_CONTENT).build();
	}
	@Path("/getAllTopic")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@JsonIgnore
	public Response getallTopic()
	{
		List<Topic> topiclist = topicManager.GetAllTopic();
		/*for (Topic topic : topiclist) {
			//Set<RateTopic> ratelist = topicManager.GetAllRateTopic(topic.getIdTopic());
			Set<RateTopic> ratelist=topic.getReactions();
			for (RateTopic rateTopic : ratelist) {
				rateTopic.setTopic(null);
			}
		}*/
		
		return Response.ok(topiclist).build();
	}
	
	@Path("/deleteTopic/{id}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes()
	public Response deletetopic(@PathParam("id") int id)
	{
		return Response.ok(topicManager.deleteTopic(id)).build();
	}
	
	
	@Path("/addTopic")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addtopic(
			@QueryParam("titre_topic") String titre_topic,
			@QueryParam("description") String description,
			@QueryParam("creator") int creator
			)
	{
		
		User user = userManager.GetUserById(creator);
		User user2 = new User();
		user2.setIdMember(user.getIdMember());
		user2.setEmail(user.getEmail());
		user2.setUsername(user.getUsername());
		Topic topic = new Topic(titre_topic,description,user2);
		return Response.ok(topicManager.AddTopic(topic)).build();
	}
	@Path("/addRateTopic")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addratetopic(
			@QueryParam("idTopic") int idTopic,
			@QueryParam("rateValue") int rateValue,
			@QueryParam("idMember") int idMember
			)
	{
		return Response.ok(topicManager.AddRateTopic(idTopic,rateValue,idMember)).build();
	}
	
	
	@Path("/updateTopic")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updatetopic(
			@QueryParam("titre_topic") String titre_topic,
			@QueryParam("description") String description,
			@QueryParam("idTopic") int id
			)
	{
		return Response.ok(topicManager.updateTopic(titre_topic, description,id)).build();
	}
	
}
