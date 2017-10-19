package services;


import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import iservices.IGroupServiceLocal;
import persistance.Group;
import persistance.User;


@Stateless
public class GroupService implements IGroupServiceLocal
{	
	@PersistenceContext(unitName="forumMS")
	EntityManager entityManager;

	UserService userService;

	@Override
	public JsonObject addGroup(Group group,int creator) {
		
		User user = userService.getUserById(creator);
		if(user!=null)
		{
			entityManager.persist(group);
			entityManager.flush();
			return null;
		}
		else
		{
			return null;
		}
		
		
	}


	
		
	}
	

