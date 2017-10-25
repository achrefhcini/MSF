package iservices;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import persistance.Event;
import persistance.User;

@Remote
public interface IEventServiceRemote {
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
	
}
