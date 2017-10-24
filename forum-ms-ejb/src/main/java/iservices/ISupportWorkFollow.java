package iservices;

import java.util.List;

import javax.ejb.Local;

import persistance.SupportTicket;
import persistance.SupportWorkFollow;
import persistance.User;
import util.ResponseTmp;

@Local
public interface ISupportWorkFollow {
	public Boolean addSupportWF(SupportWorkFollow sWF);
	public List<SupportWorkFollow> getSupportWorkFollow();
	public List<SupportWorkFollow> findSupportWorkFollowByUser(User user);
	public List<SupportWorkFollow> findSupportWorkFollowByTicket(SupportTicket st);
	public SupportWorkFollow findSupportWorkFollowByID(int id);
	public ResponseTmp changeStateWF(SupportWorkFollow wf);
	public ResponseTmp affectTOWF(SupportWorkFollow wf,SupportWorkFollow wf1);
	public ResponseTmp updateSupportWF(SupportWorkFollow sWF);
	
}
