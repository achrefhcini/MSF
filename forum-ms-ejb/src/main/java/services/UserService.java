package services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import iservices.IUserManagerLocal;
import persistance.User;

@Stateless
public class UserService implements IUserManagerLocal
{
	@PersistenceContext(unitName="forumMS")
	EntityManager entityManaer;
	public User GetUserById(int id)
	{
		return entityManaer.find(User.class, id);
	}
	public boolean AddUser(User user) {
		try
		{
			entityManaer.persist(user);
			entityManaer.flush();
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
		
	}
	
}
