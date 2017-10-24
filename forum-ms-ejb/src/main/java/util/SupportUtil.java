package util;

import java.util.Random;

import persistance.SupportTicket;
import persistance.SupportWorkFollow;

public class SupportUtil {

	
	public final static String getTicketNumberRandom(){
		 Random random = new Random(System.nanoTime() % 100000);

		  int randomInt = random.nextInt(1000000000);
		   System.out.println("------------------------------"+randomInt);
		  return String.valueOf(randomInt);
	}
	public final static String getResponseProcess(){
		 Random random = new Random(System.nanoTime() % 100000);

		  int randomInt = random.nextInt(10000000);
		  return String.valueOf(randomInt);
	}
	public final static int checkStatTicket(SupportTicket st,int state){
		 if(state==StateTicket.CLOSED){
			 return (StateTicket.CLOSED);
		 }
		 else if(state==StateTicket.OPEN){
			 return (StateTicket.OPEN);
		 }
		 else if(state==StateTicket.TRANSFERED){
			 return (StateTicket.TRANSFERED);
		 }
		 else if(state==StateTicket.WAITING){
			 return (StateTicket.WAITING);
		 }
		 else {
			 return st.getState();
		 } 
		
	}
	public final static int checkStateWF(SupportWorkFollow sw,int state){
		if(state==StateWorkFollow.IGNORED){
			return StateWorkFollow.IGNORED;
		}
		else if (state==StateWorkFollow.PASSED){
			return StateWorkFollow.PASSED;
		}
		else if (state==StateWorkFollow.PROCESSING){
			return StateWorkFollow.PROCESSING;
		}
		else if (state==StateWorkFollow.RESOLVED){
			return StateWorkFollow.RESOLVED;
		}
		else return sw.getStateWF();
	}
}
