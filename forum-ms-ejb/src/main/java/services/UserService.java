package services;




import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import iservices.IUserManagerLocal;
import iservices.IUserMangerRemote;

import persistance.User;

@Stateless
public class UserService implements IUserManagerLocal,IUserMangerRemote
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
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
		
	}
	
	
}
