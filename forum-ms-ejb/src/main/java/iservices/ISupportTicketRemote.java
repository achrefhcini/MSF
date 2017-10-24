package iservices;

import java.util.List;

import javax.ejb.Remote;

import persistance.SupportTicket;

@Remote
public interface ISupportTicketRemote {
	   public boolean addTicketSupport(SupportTicket st);
	   public List<SupportTicket> getSupportTickets();
	   public SupportTicket findSupportTicketById(int id);
	   public boolean updateSupportTicket(SupportTicket st);
	   public List<SupportTicket> getSupportTicketsFromTo(int fromId,int toId); 
	   public boolean changeState(SupportTicket st,int state);
	   public SupportTicket findSupportTicketByTicketNumber(String tNumber);
	   public List<SupportTicket> getSupportTickedOrderAD(String order,String orderBy);
}
