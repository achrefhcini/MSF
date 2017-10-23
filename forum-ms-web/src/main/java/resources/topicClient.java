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
import util.TimerSessionBeanRemote;

@Path("/topic")
@RequestScoped
public class topicClient {
	@Inject
	ITopicManagerLocal topicManager ;
	@Inject
	IUserManagerLocal userManager ;
	@Inject
	TimerSessionBeanRemote timerManager ;

	
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
	
	@Path("/getreactionTopics/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@JsonIgnore
	public Response getreationtopics(@PathParam("id") int id)
	{
		Set<RateTopic> topiclist = topicManager.GetTopicreactions(id);
		return Response.ok(topiclist).build();
	}
	
	
	@Path("/getAllTopicAdmin")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@JsonIgnore
	public Response getallTopicadmin()
	{
		List<Topic> topiclist = topicManager.GetAllTopic_admin();
		return Response.ok(topiclist).build();
	}
	
	
	@Path("/getAllTopic")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@JsonIgnore
	public Response getallTopic()
	{
		List<Topic> topiclist = topicManager.GetAllTopic();
		return Response.ok(topiclist).build();
	}
	
	
	@Path("/getAllTopicSortAverage")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@JsonIgnore
	public Response getallTopicsortaverage()
	{
		List<Topic> topiclist = topicManager.GetAllTopicSortAverage();
		return Response.ok(topiclist).build();
	}
	
	
	@Path("/getAllTopicSortDate")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@JsonIgnore
	public Response getallTopicsortdate()
	{
		List<Topic> topiclist = topicManager.GetAllTopicSortDate();
		return Response.ok(topiclist).build();
	}
	
	
	@Path("/getUserReactions/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@JsonIgnore
	public Response getuserreactions(@PathParam("id") int id)
	{
		Set<RateTopic> topiclist = topicManager.GetUserAllRateTopic(id);
		return Response.ok(topiclist).build();
	}
	
	
	@Path("/getUserTopics/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@JsonIgnore
	public Response getusertopics(@PathParam("id") int id)
	{
		Set<Topic> topiclist = topicManager.GetUserAllTopic(id);
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
	@Consumes()
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
		if(topicManager.UserRateExist(idMember, idTopic)==false)
		{
		boolean valid = topicManager.AddRateTopic(idTopic,rateValue,idMember);
		Topic topic = topicManager.GetTopicById(idTopic);
		topicManager.GenerateRate(topic);
		return Response.ok(valid).build();
		}
		else
		{
			RateTopic rate = topicManager.GetRateTopicByUserTopic(idTopic,idMember);
			boolean valid = topicManager.UpdateRateTopic(rate,rateValue);
			Topic topic = topicManager.GetTopicById(idTopic);
			topicManager.GenerateRate(topic);
			return Response.ok(valid).build();
		}
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
	
	
	@Path("/blockTopic/{id}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response blocktopic(@PathParam("id") int idTopic,
			@QueryParam("periode") int periode
			)
	{
		   
		Topic topic = topicManager.GetTopicById(idTopic);
		boolean val = topicManager.BlockTopic(topic,periode);
		Topic topic2 = topicManager.GetTopicById(idTopic);
		timerManager.createTimer(periode,topic2);
		if( val == true)
		{
			User user = topic.getCreator();
			topicManager.sendmailblock(user, topic);
		}
		return Response.ok(val).build();
	}
	
	
	@Path("/deblockTopic/{id}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deblocktopic(@PathParam("id") int idTopic
			)
	{
		Topic topic = topicManager.GetTopicById(idTopic);
		boolean val = topicManager.DeBlockTopic(topic);
		timerManager.destroy();
		if( val == true)
		{
			User user = topic.getCreator();
			topicManager.sendmaildeblock(user, topic);
		}
		return Response.ok(val).build();
	}
}
