package services;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import iservices.ISupportTicketLocal;
import iservices.ISupportTicketRemote;
import persistance.SupportTicket;
import util.ResponseError;
import util.ResponseSuccess;
import util.ResponseTmp;
import util.StateTicket;
import util.SupportUtil;

@Stateless
public class SupportTicketService implements ISupportTicketLocal,ISupportTicketRemote {
	
	@PersistenceContext(unitName="forumMS")
	EntityManager entityManaer;
	@Override
	public ResponseTmp addTicketSupport(SupportTicket sp) {
		try
		{
			sp.setCreationDate(new Date());
			entityManaer.persist(sp);
			return ResponseSuccess.ADDED;
		}
		catch(Exception e)
		{
			return ResponseError.UPDATED;
		}
	}
	
	
	@Override
	public List<SupportTicket> getSupportTickets() {
		// TODO Auto-generated method stub
		return entityManaer.createQuery("SELECT s FROM SupportTicket s",SupportTicket.class).getResultList();
	}
	
	
	@Override
	public ResponseTmp updateSupportTicket(SupportTicket st) {
		// TODO Auto-generated method stub
		try {
			st.setUpdateDate(new Date());
			entityManaer.merge(st);
			return ResponseSuccess.UPDATED;
		} catch (Exception e) {
			return ResponseError.UPDATED;
		}
		
		
	}
	@Override
	public SupportTicket findSupportTicketById(int id) {
		// TODO Auto-generated method stub
		try {
			return entityManaer.find(SupportTicket.class, id);

		} catch (Exception e) {
            return null; 		
  
		}
	}
	@Override
	public SupportTicket findSupportTicketByTicketNumber(String tNumber) {
		SupportTicket sp=null;
		try {
		 Query query= entityManaer.createQuery("SELECT s FROM SupportTicket s WHERE "
			 		+ "s.ticketNumber =:tNumber ",SupportTicket.class);
			 query.setParameter("tNumber", tNumber);
		
			 sp= (SupportTicket) query.getSingleResult();
		} catch (Exception e) {
			sp=null;		
  
		}
		return sp;
	}
	@Override
	public List<SupportTicket> getSupportTicketsFromTo(int fromId, int toId) {
		// TODO Auto-generated method stub
		 Query query= entityManaer.createQuery("SELECT s FROM SupportTicket s WHERE "
		 		+ "s.IdSupTicket >=:fromid and s.IdSupTicket <=:toid",SupportTicket.class);
		 query.setParameter("fromid", fromId);
		 query.setParameter("toid", toId);
		 return query.getResultList();
		 

	}
	@Override
	public ResponseTmp changeState(SupportTicket st, int state) {
		// TODO Auto-generated method stub
		try {
			st.setUpdateDate(new Date());
			st.setState(SupportUtil.checkStatTicket(st, state));
			entityManaer.merge(st);
			return ResponseSuccess.UPDATED;
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseError.UPDATED;
		}
		
		
	}
	@Override
	public List<SupportTicket> getSupportTickedOrderAD(String order,String orderBy) {
		// TODO Auto-generated method stub
		Query query= entityManaer.createQuery("SELECT s FROM SupportTicket s ORDER BY  s."+orderBy+" "+order+"",SupportTicket.class);
		
		 return query.getResultList();

	}

	
	

}
