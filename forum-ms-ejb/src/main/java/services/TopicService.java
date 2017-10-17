package services;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import iservices.ITopicManagerLocal;
import persistance.RateTopic;
import persistance.Topic;
import persistance.User;


@Stateless
public class TopicService implements ITopicManagerLocal {
	@PersistenceContext(unitName="forumMS")
	EntityManager entityManager;
	@Override
	public Topic GetTopicById(int id) {
		return entityManager.find(Topic.class, id);
	}

	@Override
	public boolean AddTopic(Topic topic) {
		try
		{
			entityManager.persist(topic);
			entityManager.flush();
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	@Override
	public boolean updateTopic(String titre_topic,String description,int id) {
		   try{
		    Topic topic =entityManager.find(Topic.class, id);
            topic.setTitre_topic(titre_topic);
            topic.setDescription(description);
            entityManager.merge(topic); 
            entityManager.flush();
            return true;
		   }
		   catch(Exception e){
            return false;
		   }
        }
	@Override
        public boolean deleteTopic(int id){
		try{
			 Topic topic =entityManager.find(Topic.class, id);
	            entityManager.remove(topic); 
	            entityManager.flush();
	            return true;
		}
           catch(Exception e){
        	   return false;
           }
        }

	@Override
	public List<Topic> GetAllTopic() {
		return (List<Topic>)entityManager.createQuery("SELECT t FROM Topic t",Topic.class).getResultList();
	}
	@Override
	public Set<RateTopic> GetAllRateTopic(int id) {
		List<RateTopic> rateliste = entityManager.createQuery("SELECT t.rateValue FROM RateTopic t ",RateTopic.class).getResultList();
         Set<RateTopic> ratelist = new HashSet<RateTopic>(rateliste);
		return  ratelist;
	}

	@Override
	public boolean AddRateTopic(int id , int value ,int id_user) {
		try{
		    Topic topic =entityManager.find(Topic.class, id);
		    RateTopic ratetopic = new RateTopic();
		    User user = entityManager.find(User.class, id_user);
		    ratetopic.setRateValue(value);
		    ratetopic.setReactedUser(user);
		    ratetopic.setTopic(topic);
            entityManager.persist(ratetopic);
            entityManager.flush();
            return true;
		   }
		   catch(Exception e){
            return false;
		   }
	}

}
