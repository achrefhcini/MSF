package iservices;

import java.util.List;
import java.util.Set;

import javax.ejb.Local;

import persistance.RateTopic;
import persistance.Topic;
import persistance.User;
//import persistance.User;
@Local
public interface ITopicManagerLocal {
	public boolean AddTopic(Topic topic);
	public boolean deleteTopic(int id);
	public boolean updateTopic(String titre_topic,String description, int id);
	public boolean AddRateTopic(int id , int value ,int id_user);
	public boolean UserRateExist(int iduser,int idtopic);
	public boolean UpdateRateTopic(RateTopic rate,int value);
	public void GenerateRate(Topic topic);
	public Topic GetTopicById(int id);
	public RateTopic GetRateTopicByUserTopic(int idTopic,int idUser);
	public Set<RateTopic> GetUserAllRateTopic(int id) ;
	public Set<RateTopic> GetTopicreactions(int id) ;
	public Set<Topic> GetUserAllTopic(int id) ;
	public List<Topic> GetAllTopicSortAverage();
	public List<Topic> GetAllTopicSortDate();
	public List<Topic> GetAllTopic();
	public boolean verifTopicCreator(User user,Topic topic);
	////////////////////////////////////////////////////////Partie Admin
	public List<Topic> GetAllTopic_admin();
	public boolean BlockTopic(Topic topic,int periode);
	public boolean DeBlockTopic(Topic topic);
	public boolean VerifBlock(Topic topic);
	public boolean sendmailblock(User user,Topic topic);
	public boolean sendmaildeblock(User user,Topic topic);
}
