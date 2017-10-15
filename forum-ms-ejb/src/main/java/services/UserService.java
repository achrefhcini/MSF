package services;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import iservices.IUserManagerLocal;
import persistance.User;
import utils.Mail;
import utils.Utils;

@Stateless
public class UserService implements IUserManagerLocal
{
	@PersistenceContext(unitName="forumMS")
	EntityManager entityManager;
	@EJB
	Mail mail;
	public User getUserById(int id)
	{
		return entityManager.find(User.class, id);
	}
	public JsonObject enableUser(String username,String token)
	{
		User user =this.verifToken(username, token);
		if(user!=null)
		{
			user.setToken(null);
			user.setIsEnabled(Boolean.TRUE);
			entityManager.persist(user);
			entityManager.flush();
			return Json.createObjectBuilder().add("succes", "your account has been activated successfully").build();
		}
		else
		{
			return Json.createObjectBuilder().add("error", "your username or your token is not correct").build();
		}
		
	}
	public JsonObject quickSignup(User user) 
		{
		JsonObjectBuilder errorBuilder=Json.createObjectBuilder();
		if(Utils.emailValidator(user.getEmail()))
		{
			if(this.isExistEmail(user.getEmail()))
			{
				errorBuilder.add("error", "the email address is allready exist");
				return errorBuilder.build();
			}
			else if(this.isExistUsername(user.getUsername()))
			{
				errorBuilder.add("error", "the username is allready exist");
				return errorBuilder.build();
			}
			else if(!this.isValidPassword(user.getPassword()))
			{
				errorBuilder.add("error", "the password is too weak");
				return errorBuilder.build();
			}
		}
		else
		{
			errorBuilder.add("error", "your email address is not valid");
			return errorBuilder.build();
		}
			
			try {
				user.setPassword(Utils.toMD5(user.getPassword()));
			} catch (NoSuchAlgorithmException e) {
				errorBuilder.add("error", "the password is too weak");
				return errorBuilder.build();
				
			}
			user.setToken(Utils.tokenGenerator());
			entityManager.persist(user);
			entityManager.flush();
			JsonObjectBuilder succesBuilder=Json.createObjectBuilder();
			succesBuilder.add("succes", "quick signup successfully completed");
			succesBuilder.add("user_id",user.getId());
			mail.send(user.getEmail(), "Quick singup", "your account has been successfully created please activate it now <br> " 
			+"http://localhost:18080/forum-ms-web/v0/user/enable/"+user.getUsername()+"/"+user.getToken());
			return succesBuilder.build();
			
		}
	
	public JsonObject login(String usernameOrEmail, String password)
	{
		try {
			password=Utils.toMD5(password);
		} catch (NoSuchAlgorithmException e) {
			return Json.createObjectBuilder().add("error", "your password is not correct").build();
		}
		if(isExistEmail(usernameOrEmail))
		{
			User user =loginEmail(usernameOrEmail,password);
			if(user!=null)
			{
				if(user.getIsEnabled())
				{
					user.setConnected(Boolean.TRUE);
					user.setLastConnect(new Date());
					entityManager.persist(user);
					entityManager.flush();
					return Json.createObjectBuilder()
							.add("succes", "you have been logged in successfully")
							.add("user_id",user.getId())
							.build();
				}else
				{
					return Json.createObjectBuilder()
							.add("error", "your account is disabled")
							.build();
				}
				

			}else
			{
				return Json.createObjectBuilder().add("error", "your email or your password is not correct").build();
			}
		}
		else
		{
			User user =loginUsername(usernameOrEmail,password);
			if(user!=null)
			{
				if(user.getIsEnabled())
				{
					user.setConnected(Boolean.TRUE);
					user.setLastConnect(new Date());
					entityManager.persist(user);
					entityManager.flush();
					return Json.createObjectBuilder()
							.add("succes", "you have been logged in successfully")
							.add("user_id",user.getId())
							.build();
					
				}else
				{
					return Json.createObjectBuilder()
							.add("error", "your account is disabled")
							.build();
				}
			
			}
			else
			{
				return Json.createObjectBuilder().add("error", "your username or your password is not correct").build();
			}
			
		}
	}
	
	
	
	
	
	
	
	
	
	private User loginEmail(String email, String password)
	{
		try
		{
			User result=(User) entityManager.createQuery(
					  "SELECT u from User u WHERE u.email = :email and u.password = :password")
						.setParameter("email", email)
						.setParameter("password", password)
						.getSingleResult();
				return result;	
		}
		catch(NoResultException e)
		{
			return null;
		}
	}
	private User loginUsername(String username, String password)
	{
		try
		{
			User result=(User) entityManager.createQuery(
					  "SELECT u from User u WHERE u.username = :username and u.password = :password")
						.setParameter("username", username)
						.setParameter("password", password)
						.getSingleResult();
				return result;	
		}
		catch(NoResultException e)
		{
			return null;
		}
	}
	
	
	private boolean isExistEmail(String email)
	{
		long result=(long) entityManager.createQuery(
				  "SELECT count(u) from User u WHERE u.email = :email").
				  setParameter("email", email).getSingleResult();
		
		if(result==0)
			return false;			
		else
			return true;
	}
	private boolean isExistUsername(String username)
	{
		long result=(long) entityManager.createQuery(
				  "SELECT count(u) from User u WHERE u.username = :username").
				  setParameter("username", username).getSingleResult();
		if(result==0)
			return false;			
		else
			return true;
	}
	private boolean isValidPassword(String password)
	{
		if(password.length()<5)
		return false;
		else
		return true;
	}	
	private User verifToken(String username,String token)
	{
		try
		{
			User result=(User) entityManager.createQuery(
					  "SELECT u from User u WHERE u.username = :username and u.token = :token")
						.setParameter("username", username)
						.setParameter("token", token)
						.getSingleResult();
				return result;	
		}
		catch(NoResultException e)
		{
			return null;
		}
				


	}
	
		
	}
	

