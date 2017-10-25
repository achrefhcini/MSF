package test;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import iservices.ISupportTicketRemote;
import persistance.SupportTicket;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Context ctx;
		try {
			ctx = new InitialContext();
			ISupportTicketRemote sv= (ISupportTicketRemote) ctx.lookup("/forum-ms-ear/forum-ms-ejb/SupportTicketService!iservices.ISupportTicketRemote");
				SupportTicket sp=new SupportTicket();
				sp.setCategory("dfdfdf");
				sv.addTicketSupport(sp);

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}

}
