package util;

import javax.ejb.Local;

import persistance.Topic;



@Local
public interface TimerSessionBeanRemote {
   public void createTimer(int minutes , Topic topic);
   public void destroy(Topic topic);
}