package services;

import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import iservices.ISupportResponse;
import iservices.ISupportTicketLocal;
import persistance.SupportResponse;
import util.StateWorkFollow;

@Stateless
public class SupportResponseService implements ISupportResponse {
	
	@PersistenceContext(unitName="forumMS")
	EntityManager entityManaer;
	@Override
	public Boolean addSupportResponse(SupportResponse sr) {
		// TODO Auto-generated method stub
		try
		{
		    sr.setDateResponse(new Date());
			entityManaer.persist(sr);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	@Override
	public Boolean uploadImage(){

		return true;
	}
	@Override
	public Boolean call(){
		
		return true;
	}

	
}
