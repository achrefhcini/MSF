package iservices;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import persistance.Event;
import persistance.User;

@Local
public interface IEventServiceLocal {

	boolean AddEvent(Event event);

	boolean UpdateEvent(Event event);

	boolean DelteEvent(Event event);

	Event GetEventById(int idevent);
	
	List<Event>GetAllEvents();

	List<Event>GetEventsByTitle(String title);
	
	List<Event>GetEventsByUser(int iduser);
	
	List<Event>GetTodayEvents();
	
	List<Event>GetEvensBetweenDates(Date date1,Date date2);
	
	List<User>GetAllUsersJoindEvent(int idevent);
	
	List<Event>GetEventJoinedByGivenUser(int iduser);
	
	List<Event>ListOfEventThisWeek();

	String GetEtatEvent(int idevent);
	
	List<Event>GetEventsForAjax(String title);
	
	boolean ValidateEventByAdmin(Event event);
	
	List<Event>GetActiveEventForAdmin();
	
	List<Event>GetInActiveEventForAdmin();

	boolean CountEventsBeforeDelete(Integer idevent);
	
}
