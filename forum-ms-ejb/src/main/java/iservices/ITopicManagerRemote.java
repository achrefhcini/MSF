package iservices;

import javax.ejb.Remote;

import persistance.Topic;

@Remote
public interface ITopicManagerRemote {
	public boolean AddTopic(Topic topic);
}
