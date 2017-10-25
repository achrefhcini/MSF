package services;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import data.DataTest;
import iservices.ISupportWorkFollow;
import persistance.SupportTicket;
import persistance.SupportWorkFollow;
import persistance.User;
import util.ResponseError;
import util.ResponseSuccess;
import util.ResponseTmp;
import util.StateWorkFollow;

@Stateless
public class SupportWorkFollowService implements ISupportWorkFollow  {
	@PersistenceContext(unitName="forumMS")
	EntityManager entitymanager;
	public ResponseTmp addSupportWF(SupportWorkFollow sWF) {
		try
		{
			sWF.setDateReply(new Date());;
			sWF.setStateWF(StateWorkFollow.PROCESSING);
			entitymanager.persist(sWF);
			return ResponseSuccess.ADDED;
		}
		catch(Exception e)
		{
			return ResponseError.ADDED;
		}
	}
	@Override
	public List<SupportWorkFollow> getSupportWorkFollow() {

		return entitymanager.createQuery("SELECT sw FROM SupportWorkFollow sw",
				SupportWorkFollow.class).getResultList();
	}

	@Override
	public List<SupportWorkFollow> findSupportWorkFollowByUser(User user) {

		List<SupportWorkFollow> list=null;
		 try {
		Query query=entitymanager.createQuery("SELECT sw FROM SupportWorkFollow sw where"
					+ " sw.userReply.idMember = :id ",SupportWorkFollow.class);
			
		query.setParameter("id", user.getIdMember());
		System.out.println("--------"+query.getFirstResult());
		return query.getResultList();
			} catch (Exception e) {
				list = null;
			}
			 return list;
	}
	@Override
	public List<SupportWorkFollow> findSupportWorkFollowByTicket(SupportTicket st) {
		// TODO Auto-generated method stub
		List<SupportWorkFollow> list;
			 try {
					Query query=entitymanager.createQuery("SELECT sw FROM SupportWorkFollow sw "
					     	+ "join sw.supportTickets sp where"
							+ " sp.ticketNumber = :number ",SupportWorkFollow.class);
					
				query.setParameter("number", st.getTicketNumber());
				list= query.getResultList();
			} catch (Exception e) {
				list = null;
			}
			 return list;
			
	}
	@Override
	public ResponseTmp changeStateWF(SupportWorkFollow wf) {
		try {
			if(wf.getStateWF()==StateWorkFollow.IGNORED){
				User user2=DataTest.user2;
				SupportWorkFollow wf1=new SupportWorkFollow();
				wf1.setCauseWF(wf.getCauseWF());
				wf1.setNoticeWF(wf.getNoticeWF());
				wf1.setSupportTickets(wf.getSupportTickets());
				wf1.setUserReply(user2);
				wf.setDateEndReply(new Date());
				wf.setResponseWF(false);
				this.addSupportWF(wf1);
		
			} 
	
             else if(wf.getStateWF()==StateWorkFollow.RESOLVED){
 				wf.setDateEndReply(new Date());
 				wf.setResponseWF(true);
 				
 				
 			}
			entitymanager.merge(wf);
			return ResponseSuccess.UPDATED;
			
			
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return ResponseError.UPDATED;
		}
	
	}
	@Override
	public ResponseTmp affectTOWF(SupportWorkFollow wf,SupportWorkFollow wf1) {
		// TODO Auto-generated method stub
		try {
		wf.setDateEndReply(new Date());
		if(wf1.getCauseWF()==null){
			wf1.setCauseWF(wf.getCauseWF());
		}
		if(wf1.getNoticeWF()==null){
			wf1.setNoticeWF(wf.getNoticeWF());
		}
		wf1.setUserReply(DataTest.user2);
		wf1.setSupportTickets(wf.getSupportTickets());
		this.addSupportWF(wf1);
		entitymanager.merge(wf);
		return ResponseSuccess.ADDED;
		} catch  (Exception e) {
			return ResponseError.ADDED;
		}
		
		
	
	}
	@Override
	public ResponseTmp updateSupportWF(SupportWorkFollow sWF) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public SupportWorkFollow findSupportWorkFollowByID(int id) {
		
		return entitymanager.find(SupportWorkFollow.class, id);
	}
}
