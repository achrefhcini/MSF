package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import iservices.IEventServiceLocal;
import persistance.Event;
import persistance.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

@Stateless
@LocalBean
public class EventService implements IEventServiceLocal {
	
	
	@PersistenceContext(unitName="forumMS")
	EntityManager entityManger;
	
	
	@Override
	public boolean AddEvent(Event event) {
	try
		{
			entityManger.persist(event);
			entityManger.flush();
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
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
	@Override
	public boolean DelteEvent(Event event) {
		try
		{
		entityManger.remove(entityManger.merge(event));
		return true;
		}catch(Exception e)
		{
		return false;
		}
	}
		@Override
		public Event GetEventById(int idevent)
		{
			
			System.err.println("waaaa"+ idevent);
			Event eve=new Event();
			eve= entityManger.find(Event.class,idevent);
			System.err.println("ahowaa  "+eve);
			return eve;
		}
	
	
	@Override
	public List<Event> GetAllEvents() {
		List<Event> depts = entityManger.createQuery("Select a From Event a",
        Event.class).getResultList();
		return depts;
		
	}
	@Override
	public List<Event> GetEventsByTitle(String title) {
		List<Event> depts = entityManger.createQuery("Select a From Event a  WHERE a.Title = :Title",
		Event.class).setParameter("Title",title).getResultList();
	    return depts;
	}
	@Override
	public List<Event> GetEventsByUser(int iduser) 
	{
		List<Event> depts = entityManger.createQuery("Select a From Event a  WHERE a.creator.idMember = :creator",
	    Event.class).setParameter("creator",iduser).getResultList();
	    return depts;
	}
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
	@Override
	public List<Event> GetEvensBetweenDates(Date date1, Date date2) {//(StartDate BETWEEN '2017/10/14 00:00:00' and '2017/10/21 00:00:00') and (EndDate BETWEEN '2017/10/14 00:00:00' and '2017/10/21 00:00:00')
		List<Event> depts = entityManger.createQuery("Select a From Event a  WHERE (a.StartDate BETWEEN  :date1 and :date2) and (a.EndDate BETWEEN  :date1 and :date2) ",
	    Event.class).setParameter("date1",date1).setParameter("date2",date2).getResultList();
	    return depts;
		
	}
	@Override
	public List<User> GetAllUsersJoindEvent(int idevent) {
		Query q = entityManger.createNativeQuery("select u.email,u.firstName,u.username from fms_member u join fms_ticket t on u.idMember=t.participant join fms_event e on e.idEvent=t.event WHERE e.idEvent=?;");
		q.setParameter(1, idevent);
		List<User> users = q.getResultList();
		 
		/*for (Object[] a : users) {
		    System.out.println("User "
		            + a[0]
		            + " "
		            + a[1]);}*/
		return users;
	}
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
		Query q = entityManger.createNativeQuery("select e.Title,e.price,e.StartDate,e.EndDate from fms_member u join fms_ticket t on u.idMember=t.participant join fms_event e on e.idEvent=t.event WHERE u.idMember=? and e.StartDate>?");
		q.setParameter(1, iduser);
		q.setParameter(2, today);
		List<Event> events = q.getResultList();
		return events;
	}
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
	}
	

