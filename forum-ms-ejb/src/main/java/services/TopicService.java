package services;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
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
import util.Email;
import util.TimerSessionBeanRemote;


@Stateless
public class TopicService implements ITopicManagerLocal {
	@PersistenceContext(unitName="forumMS")
	EntityManager entityManager;
	
	TimerSessionBeanRemote timerManager ;
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
		    if(topic == null)
		    {
		    	return false;
		    }else
		    {
            topic.setTitre_topic(titre_topic);
            topic.setDescription(description);
            entityManager.merge(topic); 
            entityManager.flush();
            return true;
		    }
		   }
		   catch(Exception e){
            return false;
		   }
        }
	@Override
        public boolean deleteTopic(int id){
		try{
			 Topic topic =entityManager.find(Topic.class, id);
			 if(topic == null)
			 {
				 return false;
			 }else
			 {
	            entityManager.remove(topic); 
	            entityManager.flush();
	            return true;
			 }
		}
           catch(Exception e){
        	   return false;
           }
        }

	@Override
	public List<Topic> GetAllTopic_admin() {
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

	@Override
	public List<Topic> GetAllTopicSortAverage() {
		List<Topic> topicliste = entityManager.createQuery("SELECT t FROM Topic t ORDER BY t.Moyenne DESC",Topic.class).getResultList();
		return topicliste;
	}

	@Override
	public List<Topic> GetAllTopicSortDate() {
		List<Topic> topicliste = entityManager.createQuery("SELECT t FROM Topic t ORDER BY t.creationDate DESC",Topic.class).getResultList();
		return topicliste;
	}

	@Override
	public List<Topic> GetAllTopic() {
		return (List<Topic>)entityManager.createQuery("SELECT t FROM Topic t WHERE t.isBlocked = 0",Topic.class).getResultList();
	}

	@Override
	public boolean BlockTopic(Topic topic, int periode) {
	try{
		if(topic.getIsBlocked() == 0)
		{
			topic.setIsBlocked(1);
			Date t = new Date();
			Date t2 =new Date();
			topic.setBlockDate(t);
			topic.setPeriode(periode);
			Calendar cal = Calendar.getInstance();
			cal.setTime(t2);
			cal.add(Calendar.DAY_OF_WEEK, periode);
			t2.setTime(cal.getTime().getTime()); // or
			t2 = new Timestamp(cal.getTime().getTime());
			topic.setDeBlockDate(t2);
			entityManager.merge(topic);
			entityManager.flush();
			return true;
		}
		else
		{
			return false;
		}
		
	} catch (Exception e) {
		return false;
	}
	}

	@Override
	public boolean DeBlockTopic(Topic topic) {
		try{
			if(VerifBlock(topic) == false)
			{
			topic.setIsBlocked(0);
			topic.setBlockDate(null);
			topic.setPeriode(0);
			topic.setDeBlockDate(null);
			entityManager.merge(topic);
			entityManager.flush();
			return true;
			}
			else{
				return false;
				}
			}
		catch (Exception e) {
			return false;
		}
		} 
	

	@Override
	public boolean VerifBlock(Topic topic) {
		int block = topic.getIsBlocked();
		if(block == 1)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	@Override
	public boolean sendmailblock(User user,Topic topic) {
		try{
		String mail = user.getEmail();
		String body = "Your topic "+topic.getTitre_topic()+" is blocked for "+topic.getPeriode()+" days";
		String Subject = "Topic Blocked";
		Email.generateAndSendEmail(mail, Subject, body);
		return true;
		}
		catch(Exception e)
		{
			return false;
		}
		
	}

	@Override
	public boolean sendmaildeblock(User user,Topic topic) {
		try{
		String mail = user.getEmail();
		String body = "Your topic "+topic.getTitre_topic()+" is deblocked now ";
		String Subject = "Topic Deblocked";
		Email.generateAndSendEmail(mail, Subject, body);
		return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}

	@Override
	public boolean verifTopicCreator(User user,Topic topic) {
		if(user.getIdMember() == topic.getCreator().getIdMember())
		{
			return false;
		}
		else
		{
			return true;
		}
	}
}
