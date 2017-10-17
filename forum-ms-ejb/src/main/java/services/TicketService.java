package services;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import iservices.ITicketServiceLocal;
import persistance.Ticket;

@Stateless
@LocalBean
public class TicketService implements ITicketServiceLocal{
	@PersistenceContext(unitName="forumMS")
	EntityManager entityManger;

	@Override
	public boolean AddTicket(Ticket tt) {
		try
		{
			entityManger.persist(tt);
			
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}

	@Override
	public boolean UpdateTicket(Ticket tt) {
		try
		{
		entityManger.merge(tt);
		return true;
		}catch(Exception e)
		{
		return false;
		}
	}

	@Override
	public boolean DelteTicket(Ticket event) {
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
	public Ticket GetTicketById(int idTicket) {
		
		Ticket eve=new Ticket();
		eve= entityManger.find(Ticket.class,idTicket);
		return eve;
	}

	@Override
	public List<Ticket> GetAllTicket() {
		List<Ticket> depts = entityManger.createQuery("Select a From Ticket a",
		Ticket.class).getResultList();
	    return depts;
	}
}
