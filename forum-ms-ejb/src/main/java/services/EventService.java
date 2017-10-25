package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import iservices.IEventServiceLocal;
import iservices.IEventServiceRemote;
import persistance.Event;
import persistance.User;
import util.Mail;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

@Stateless
@LocalBean
public class EventService implements IEventServiceLocal,IEventServiceRemote {
	
	
	@PersistenceContext(unitName="forumMS")
	EntityManager entityManger;
	
	
	//XXX********************ADD EVENT*****************************XXX
	@Override
	public boolean AddEvent(Event event) {
	try
		{	
			entityManger.persist(event);
			entityManger.flush();
			List<User>users=entityManger.createQuery("Select u From User u",User.class).getResultList();
			//System.err.println(users.toString());
			for(User i :users){Mail.generateAndSendEmail(i.getEmail());}
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	//XXX********************UPDATE EVENT*****************************XXX
	@Override
	public boolean UpdateEvent(Event event) {
		try
		{
		entityManger.merge(event);
		return true;
		}catch(Exception e)
		{
		return false;
		}
	}
	//XXX********************DELETE EVENT*****************************XXX
	@Override
	public boolean DelteEvent(Event event) {
		try
		{
		if(CountEventsBeforeDelete(event.getIdEvent()))
		return false;
		
		entityManger.remove(entityManger.merge(event));
		return true;
		
		}catch(Exception e)
		{
		return false;
		}
	}
	    //XXX********************GetEventById*****************************XXX
		@Override
		public Event GetEventById(int idevent)
		{
			
			//System.err.println("waaaa"+ idevent);
			Event eve=new Event();
			eve= entityManger.find(Event.class,idevent);
			//System.err.println("ahowaa  "+eve);
			return eve;
		}
	
	//XXX********************GetAllEvents*****************************XXX
	@Override
	public List<Event> GetAllEvents() {
		List<Event> depts = entityManger.createQuery("Select a From Event a",
        Event.class).getResultList();
		return depts;
		
	}
	//XXX********************GetEventsByTitle*****************************XXX
	@Override
	public List<Event> GetEventsByTitle(String title) {
		List<Event> depts = entityManger.createQuery("Select a From Event a  WHERE a.Title = :Title",
		Event.class).setParameter("Title",title).getResultList();
	    return depts;
	}
	//XXX********************GetEventsByUser*****************************XXX
	@Override
	public List<Event> GetEventsByUser(int iduser) 
	{
		List<Event> depts = entityManger.createQuery("Select a From Event a  WHERE a.creator.idMember = :creator",
	    Event.class).setParameter("creator",iduser).getResultList();
	    return depts;
	}
	//XXX********************GetTodayEvents*****************************XXX
	@Override
	public List<Event> GetTodayEvents() {
		Date today = null;
		Calendar calendar = Calendar.getInstance();     
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String sDate = dateFormat.format(calendar.getTime());
		 try {
	   today = dateFormat.parse(sDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 List<Event> depts = entityManger.createQuery("Select a From Event a  WHERE a.StartDate = :today",
		 Event.class).setParameter("today",today,TemporalType.DATE).getResultList();
		 return depts;
		
	}
	//XXX********************GetEvensBetweenDates*****************************XXX
	@Override
	public List<Event> GetEvensBetweenDates(Date date1, Date date2) {//(StartDate BETWEEN '2017/10/14 00:00:00' and '2017/10/21 00:00:00') and (EndDate BETWEEN '2017/10/14 00:00:00' and '2017/10/21 00:00:00')
		List<Event> depts = entityManger.createQuery("Select a From Event a  WHERE (a.StartDate BETWEEN  :date1 and :date2) and (a.EndDate BETWEEN  :date1 and :date2) ",
	    Event.class).setParameter("date1",date1).setParameter("date2",date2).getResultList();
	    return depts;
		
	}
	//XXX********************GetAllUsersJoindEvent*****************************XXX
	@Override
	public List<User> GetAllUsersJoindEvent(int idevent) {
		Query q = entityManger.createNativeQuery("select u.email,u.firstName,u.username from fms_member u join fms_ticket t on u.idMember=t.participant join fms_event e on e.idEvent=t.event WHERE e.idEvent=?;");
		q.setParameter(1, idevent);
		List<User> users = q.getResultList();
		return users;
	}
	//XXX********************GetEventJoinedByGivenUser*****************************XXX
	@Override
	public List<Event> GetEventJoinedByGivenUser(int iduser) 
	{
		Date today = null;
		Calendar calendar = Calendar.getInstance();     
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String sDate = dateFormat.format(calendar.getTime());
		 try {
	   today = dateFormat.parse(sDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Query q = entityManger.createNativeQuery("select e.Title,e.price,e.StartDate,e.EndDate from fms_member u join fms_ticket t on u.idMember=t.participant join fms_event e on e.idEvent=t.event WHERE u.idMember=? and e.StartDate>=?");
		q.setParameter(1, iduser);
		q.setParameter(2, today);
		List<Event> events = q.getResultList();
		return events;
	}
	//XXX********************ListOfEventThisWeek*****************************XXX
	@Override
	public List<Event> ListOfEventThisWeek() {
		Date today = null;
		Date Usedtoday = null;
		Date today2 = null;
		Calendar calendar = Calendar.getInstance();     
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String sDate = dateFormat.format(calendar.getTime());
		 try {
	   today = dateFormat.parse(sDate);
	   Usedtoday=today;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 calendar.setTime(today);
		 calendar.add(Calendar.DATE, 7);
		 today2=calendar.getTime();
		List<Event> depts = entityManger.createQuery("Select a From Event a  WHERE (a.StartDate BETWEEN  :today1 and :today2) and (a.EndDate BETWEEN  :today1 and :today2) ",
	    Event.class).setParameter("today1",Usedtoday).setParameter("today2",today2).getResultList();
	    return depts;
	}
	//XXX********************GetEtatEvent*****************************XXX
	@Override
	public String GetEtatEvent(int idevent) {
		Date today = null;
		Calendar calendar = Calendar.getInstance();     
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String sDate = dateFormat.format(calendar.getTime());
		 try {
	   today = dateFormat.parse(sDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 List<Event> depts = entityManger.createQuery("Select a From Event a  WHERE a.idEvent = :idEvent",Event.class).setParameter("idEvent", idevent).getResultList();
		for(Event ev :depts)
		{
			if(ev.getStartDate().after(today))return ev.getTitle()+" Not_Started_Yet";
			else if(ev.getEndDate().before(today))return ev.getTitle()+" finished";
			else return ev.getTitle()+" in_progress";
		}
		return "no event found";
	}
	//XXX********************GetEventsForAjax*****************************XXX
	@Override
	public List<Event> GetEventsForAjax(String crit) {
		/*Query q = entityManger.createNativeQuery("SELECT * FROM `fms_event` WHERE `Title` like ?");
		q.setParameter(1, crit+"%");
		List<Event> events = q.getResultList();
		return events;*/
		List<Event> depts = entityManger.createQuery("Select a From Event a  WHERE a.Title like :crit",
	    Event.class).setParameter("crit",crit+"%").getResultList();
	    return depts;
	}
	//XXX********************ValidateEventByAdmin*****************************XXX
	@Override
	public boolean ValidateEventByAdmin(Event event) {
		try
		{
		entityManger.merge(event);
		return true;
		}catch(Exception e)
		{
		return false;
		}
	}
	//XXX********************GetActiveEventForAdmin*****************************XXX
	@Override
	public List<Event> GetActiveEventForAdmin() {
		List<Event> depts = entityManger.createQuery("Select a From Event a  WHERE a.Enable = :Enable",
		Event.class).setParameter("Enable",true).getResultList();
	    return depts;
	}
	//XXX********************GetInActiveEventForAdmin*****************************XXX
	@Override
	public List<Event> GetInActiveEventForAdmin() {
		List<Event> depts = entityManger.createQuery("Select a From Event a  WHERE a.Enable = :Enable",
		Event.class).setParameter("Enable",false).getResultList();
		return depts;
	}
	//XXX********************CountEventsBeforeDelete*****************************XXX
	@Override
	public boolean CountEventsBeforeDelete(Integer idevent) {
		String sql = "SELECT COUNT(d.idTicket) FROM Ticket d WHERE d.event.idEvent = :idevent";
		Query q = entityManger.createQuery(sql).setParameter("idevent", idevent);;
		Long count = (Long)q.getSingleResult();
		if(count==1)
		return true;
		else 
		return false;
	
	}
	
	}
	

