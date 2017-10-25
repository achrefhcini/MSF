package iservices;

import java.util.List;

import javax.ejb.Remote;

import persistance.SupportTicket;
import util.ResponseTmp;

@Remote
public interface ISupportTicketRemote {
	   public ResponseTmp addTicketSupport(SupportTicket st);
	   public List<SupportTicket> getSupportTickets();
	   public SupportTicket findSupportTicketById(int id);
	   public ResponseTmp updateSupportTicket(SupportTicket st);
	   public List<SupportTicket> getSupportTicketsFromTo(int fromId,int toId); 
	   public ResponseTmp changeState(SupportTicket st,int state);
	   public SupportTicket findSupportTicketByTicketNumber(String tNumber);
	   public List<SupportTicket> getSupportTickedOrderAD(String order,String orderBy);
}
