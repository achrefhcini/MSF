package services;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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
	public Set<RateTopic> GetUserAllRateTopic(int id) {
		List<RateTopic> rateliste = entityManager.createQuery("SELECT t FROM RateTopic t where t.reactedUser.idMember = :id",RateTopic.class).setParameter("id",id).getResultList();
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

	@Override
	public Set<Topic> GetUserAllTopic(int id) {
		List<Topic> topicliste = entityManager.createQuery("SELECT t FROM Topic t where t.creator.idMember = :id",Topic.class).setParameter("id",id).getResultList();
        Set<Topic> Topiclist = new HashSet<Topic>(topicliste);
		return  Topiclist;
	}

	@Override
	public Set<RateTopic> GetTopicreactions(int id) {
		List<RateTopic> topicliste = entityManager.createQuery("SELECT t FROM RateTopic t where t.topic.idTopic = :id",RateTopic.class).setParameter("id",id).getResultList();
        Set<RateTopic> Topiclist = new HashSet<RateTopic>(topicliste);
		return  Topiclist;
	}

	@Override
	public void GenerateRate(Topic topic) {
		int id = topic.getIdTopic();
		int i = 0;
		int somme = 0;
		List<RateTopic> topicliste = entityManager.createQuery("SELECT t FROM RateTopic t where t.topic.idTopic = :id",RateTopic.class).setParameter("id",id).getResultList();
        Set<RateTopic> Topiclist = new HashSet<RateTopic>(topicliste);
        for (RateTopic rateTopic : Topiclist) {
        	
			somme = somme + rateTopic.getRateValue();
			i++;
		}
        float moyenne =(float) somme/i;
        topic.setMoyenne(moyenne);
        entityManager.merge(topic);
        entityManager.flush();
	}

	@Override
	public boolean UserRateExist(int iduser,int idtopic) {
		Set<RateTopic> topicreactions =  GetTopicreactions(idtopic);
		for (RateTopic rateTopic : topicreactions) {
			User user = rateTopic.getReactedUser();
			if(user.getIdMember() == iduser)
			{
				return true;
			}		
		}
		return false;
	}

	@Override
	public RateTopic GetRateTopicByUserTopic(int id,int id2) {
		TypedQuery<RateTopic> rate = entityManager.createQuery("SELECT t FROM RateTopic t WHERE t.topic.idTopic = :id AND t.reactedUser.idMember = :id2",RateTopic.class);
		return rate.setParameter("id",id).setParameter("id2",id2).getSingleResult();
	}

	@Override
	public boolean UpdateRateTopic(RateTopic rate,int value) {
		try {
			//Topic topic =entityManager.find(Topic.class, id);
		    //User user = entityManager.find(User.class, id_user);
			//RateTopic rate = GetRateTopicByUserTopic(id,id_user);
		    rate.setRateValue(value);
			entityManager.merge(rate);
			entityManager.flush();
			return true;
			
		} catch (Exception e) {
			return false;
		}
	}

}
