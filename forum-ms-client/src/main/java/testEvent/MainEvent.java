package testEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import iservices.IEventServiceRemote;
import persistance.Event;
import persistance.User;

public class MainEvent {
	public static void main(String[]args)
	{
		try {
		Context ctx=new InitialContext();
			
			
		IEventServiceRemote proxy= (IEventServiceRemote) ctx.lookup("forum-ms-ear/forum-ms-ejb/EventService!iservices.IEventServiceRemote");
		User u=new User();
		u.setId(1);
		Event event = new Event();
		event.setCreator(u);
		event.setEnable(true);
		event.setNbPlace(12);
		event.setTitle("esprit events");
		
		SimpleDateFormat sdfs = new SimpleDateFormat("dd-M-yyyy");
		String dateInStrings = "25-10-2017";
		Date EventStartDate = sdfs.parse(dateInStrings);
		
		SimpleDateFormat sdfe = new SimpleDateFormat("dd-M-yyyy");
		String dateInStringe = "30-10-2017";
		Date EventEndDate = sdfe.parse(dateInStringe);
		
		event.setStartDate(EventStartDate);
		event.setEndDate(EventEndDate);
		event.setPrice(12);
		System.err.println("Adding new event ...");
		
		if(proxy.AddEvent(event))
		System.err.println(event.getTitle()+ " added !");
		else
	    System.err.println(event.getTitle()+ " failed to add !");
		
		System.err.println("Display event by ID...");
		System.out.println(proxy.GetEventById(1));
		System.err.println("Display All Events...");
		System.out.println(proxy.GetAllEvents());
		System.err.println("Display All Events By Title...");
		System.out.println(proxy.GetEventsByTitle("balprojet"));
		System.err.println("Display All Events By User...");
		System.out.println(proxy.GetEventsByUser(1));
		System.err.println("Display Today Events...");
		System.out.println(proxy.GetTodayEvents());
		System.err.println("Display Events between two given dates...");
		System.out.println(proxy.GetEvensBetweenDates(EventStartDate,EventEndDate));
		System.err.println("Display Events of this week...");
		System.out.println(proxy.ListOfEventThisWeek());
		System.err.println("Display Event staut...");
		System.out.println(proxy.GetEtatEvent(1));
		System.err.println("Update Event...");
		event.setNbPlace(50);
		if(proxy.UpdateEvent(event))
			System.out.println("event updated");
		else 
			System.out.println("failed to update");
		
		System.err.println("Delete Event...");
		if(proxy.DelteEvent(event))
			System.out.println("event deleted");
		else 
			System.out.println("failed to delete");
		} catch (NamingException | ParseException e) {
			
			e.printStackTrace();
		}
	}
}
