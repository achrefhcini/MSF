package iservices;

import java.util.List;
import java.util.Set;

import javax.ejb.Local;

import persistance.RateTopic;
import persistance.Topic;
@Local
public interface ITopicManagerLocal {
	public Topic GetTopicById(int id);
	public boolean AddTopic(Topic topic);
	public boolean deleteTopic(int id);
	public boolean updateTopic(String titre_topic,String description, int id);
	public List<Topic> GetAllTopic();
	public boolean AddRateTopic(int id , int value ,int id_user);
	public Set<RateTopic> GetAllRateTopic(int id) ;
}
