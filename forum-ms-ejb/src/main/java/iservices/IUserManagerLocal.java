package iservices;

import javax.ejb.Local;
import javax.json.JsonObject;
import persistance.Device;
import persistance.User;

@Local
public interface IUserManagerLocal 
{
	public User getUserById(int id);
	public User getUserByUsername(String username);//to test
	public User getUserByEmail(String username);//to test
	public JsonObject quickSignup(User user, Device device);
	public JsonObject logout(int iduser,int idDevice);
	public JsonObject isConnected(int id);//to test
	public JsonObject isConnected(String usernameOrEmail);//to test
	public JsonObject login(String usernameOrEmail,String password,Device device);
	public JsonObject enableUser(String username,String token);
	public JsonObject disableUser(int id);//to test
	public JsonObject chnageProfilPicture(int id,String path);
	public JsonObject changePassword(int id,String currentPsd,String NewcurrentPsd);
	//public JsonObject resetPassword(int id ,String NewPsd);//to do
	

}
