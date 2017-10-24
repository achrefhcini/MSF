package iservices;

import java.util.List;
import java.util.Set;

import javax.ejb.*;

import persistance.SupportTicket;
import util.StateTicket;

@Local
public interface ISupportTicketLocal {
   public boolean addTicketSupport(SupportTicket st);
   public List<SupportTicket> getSupportTickets();
   public SupportTicket findSupportTicketById(int id);
   public boolean updateSupportTicket(SupportTicket st);
   public List<SupportTicket> getSupportTicketsFromTo(int fromId,int toId); 
   public boolean changeState(SupportTicket st,int state);
   public SupportTicket findSupportTicketByTicketNumber(String tNumber);
   public List<SupportTicket> getSupportTickedOrderAD(String order,String orderBy);
}
