package iservices;

import java.util.List;

import javax.ejb.Local;

import persistance.User;

@Local
public interface IUserManagerLocal {
	public User GetUserById(int id);
	public boolean AddUser(User user);
	
}
