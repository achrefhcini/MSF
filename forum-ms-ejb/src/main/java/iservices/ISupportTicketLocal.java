package iservices;

import java.util.List;
import java.util.Set;

import javax.ejb.*;

import persistance.SupportTicket;
import util.ResponseTmp;
import util.StateTicket;

@Local
public interface ISupportTicketLocal {
   public ResponseTmp addTicketSupport(SupportTicket st);
   public List<SupportTicket> getSupportTickets();
   public SupportTicket findSupportTicketById(int id);
   public ResponseTmp updateSupportTicket(SupportTicket st);
   public List<SupportTicket> getSupportTicketsFromTo(int fromId,int toId); 
   public ResponseTmp changeState(SupportTicket st,int state);
   public SupportTicket findSupportTicketByTicketNumber(String tNumber);
   public List<SupportTicket> getSupportTickedOrderAD(String order,String orderBy);
}
