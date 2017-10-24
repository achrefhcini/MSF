package test;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import iservices.ITopicManagerRemote;
import persistance.Topic;
import persistance.User;

public class MainTopic {
	public static void main(String[]args)
	{
		try {
			Context ctx=new InitialContext();
		ITopicManagerRemote proxy= (ITopicManagerRemote) ctx.lookup("forum-ms-ear/forum-ms-ejb/TopicService!iservices.ITopicManagerRemote");
		User user = new User(1);
		Topic topic=new Topic("fff","ddd",user);
		proxy.AddTopic(topic);
		//u.setId(1);
		//proxy.FindUser(u);
		//proxy.DeleteUser(u);
		//proxy.FindByName("wassim"); //pb
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}