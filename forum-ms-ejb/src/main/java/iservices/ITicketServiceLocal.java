package iservices;

import java.util.List;

import javax.ejb.Local;

import persistance.Ticket;

@Local
public interface ITicketServiceLocal {
	boolean AddTicket(Ticket event);

	boolean UpdateTicket(Ticket event);

	boolean DelteTicket(Ticket event);

	Ticket GetTicketById(int idTicket);
	
	List<Ticket>GetAllTicket();
}
