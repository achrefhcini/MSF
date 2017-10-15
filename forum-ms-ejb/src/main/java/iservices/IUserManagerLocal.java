package iservices;

import javax.ejb.Local;
import javax.json.JsonObject;

import persistance.User;

@Local
public interface IUserManagerLocal {
	public User getUserById(int id);
	public JsonObject quickSignup(User user);
	public JsonObject login(String usernameOrEmail,String password);
	public JsonObject enableUser(String username,String token);
}
