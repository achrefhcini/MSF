package iservices;

import java.util.List;
import java.util.Set;

import javax.ejb.Local;

import persistance.RateTopic;
import persistance.Topic;
import persistance.User;
@Local
public interface ITopicManagerLocal {
	public boolean AddTopic(Topic topic);
	public boolean deleteTopic(int id);
	public boolean updateTopic(String titre_topic,String description, int id);
	public boolean AddRateTopic(int id , int value ,int id_user);
	public Topic GetTopicById(int id);
	public RateTopic GetRateTopicByUserTopic(int idTopic,int idUser);
	public List<Topic> GetAllTopic();
	public Set<RateTopic> GetUserAllRateTopic(int id) ;
	public Set<RateTopic> GetTopicreactions(int id) ;
	public Set<Topic> GetUserAllTopic(int id) ;
	public void GenerateRate(Topic topic);
	public boolean UserRateExist(int iduser,int idtopic);
	public boolean UpdateRateTopic(RateTopic rate,int value);
}
