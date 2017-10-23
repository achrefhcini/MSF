package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import persistance.Topic;


@Stateless
public class TimerSessionBean implements TimerSessionBeanRemote{

   @Resource 
   private TimerService timerService;
   @PersistenceContext(unitName="forumMS")
	EntityManager entityManager;
   
   public void createTimer(int duration,Topic topic) {
	 
			try {
				  SimpleDateFormat formatter =  new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
					Calendar now = Calendar.getInstance();
					now.add(Calendar.MINUTE, duration);
					    String newTime = formatter.format(now.getTime());
						Date date;
						topic.setIsBlocked(0);
						topic.setBlockDate(null);
						topic.setPeriode(0);
						topic.setDeBlockDate(null);
				date = (Date) formatter.parse(newTime);
				Timer timer = timerService.createTimer(date, topic);
				System.out.print(timer.getTimeRemaining());
				
			} catch (ParseException e) {
				e.printStackTrace();
				
			}
	   }
   public void destroy() {
   	// TODO Add unimplemented method
   	for (Timer timer : timerService.getTimers()) {
   		timer.cancel();
   	}
   }
	   @Timeout
	   public void timeOutHandler(Timer timer) throws AddressException, MessagingException{
		   Topic top = (Topic) timer.getInfo();
		   Email.generateAndSendEmail(top.getCreator().getEmail(),"Topic Deblocked" ,"Your topic "+top.getTitre_topic()+" is deblocked now ");
		   entityManager.merge(top);
		   timer.cancel();    
	   }
	   
}