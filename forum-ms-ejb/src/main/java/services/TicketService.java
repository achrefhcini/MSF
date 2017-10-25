package services;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
			System.err.println(GetNBplacesReserved(tt.getEvent().getIdEvent()));
			System.err.println(GetNBplaces(tt.getEvent().getIdEvent()));
			if(GetNBplacesReserved(tt.getEvent().getIdEvent())>=GetNBplaces(tt.getEvent().getIdEvent()))
			return false;
			if(!VerifTicket(tt.getEvent().getIdEvent(), tt.getParticipant().getIdMember()))
		    return false;
			
			entityManger.persist(tt);
			
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
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
	public Ticket GetTicketById(Integer idTicket) {
		
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

	@Override
	public Long GetNBplacesReserved(Integer idevent) {
	
		String sql = "SELECT COUNT(d.idTicket) FROM Ticket d WHERE d.event.idEvent = :idevent";
		Query q = entityManger.createQuery(sql).setParameter("idevent", idevent);
		Long count = (Long)q.getSingleResult();
		return count;
	}

	@Override
	public Integer GetNBplaces(Integer idevent) {
		String sql = "SELECT d.NbPlace FROM Event d WHERE d.idEvent = :idevent";
		Query q = entityManger.createQuery(sql).setParameter("idevent", idevent);
		Integer count = (Integer)q.getSingleResult();
		return count;
	}

	@Override
	public boolean VerifTicket(Integer idevent, Integer iduser) {
		String sql = "SELECT COUNT(d.idTicket) FROM Ticket d WHERE d.event.idEvent = :idevent and d.participant.idMember=:iduser";
		Query q = entityManger.createQuery(sql).setParameter("idevent", idevent).setParameter("iduser",iduser );
		Long count = (Long)q.getSingleResult();
		if(count==1)
		return false;
		else 
		return true;
		
		
	}

	

}
