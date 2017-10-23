package iservices;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.json.JsonObject;
import persistance.Device;
import persistance.User;
import persistance.UserGender;

@Local
public interface IUserManagerLocal 
{
	public User getUserById(int id);
	public User getUserByUsername(String username);
	public User getUserByEmail(String username);
	public JsonObject quickSignup(User user, Device device);
	public JsonObject login(String usernameOrEmail,String password,Device device);//ne9sa
	public JsonObject logout(int iduser,int idDevice);
	public JsonObject isConnected(int id);//to test
	public JsonObject isConnected(String usernameOrEmail);//to test
	public JsonObject enableUser(String username,String token);
	public JsonObject disableUser(int id);//to test
	public JsonObject chnageProfilPicture(int id,String path);
	public JsonObject changePassword(int id,String currentPsd,String NewcurrentPsd);
	//public JsonObject resetPassword(int idUser ,String NewPsd);//to do
	public List<User> getAllUser();//to test
	public List<User> getEnabledUsers();//to test
	public List<User> getDisableUsers();//to test
	public JsonObject updateFirstname(int idUser ,String firstname);//to test
	public JsonObject updateLastname(int idUser ,String lastname);//to test
	public JsonObject updateBirthDate(int idUser ,Date BirthDate);//to test
	public JsonObject updatePhoneNumber(int idUser ,String phoneNumber);//to test
	public JsonObject updateGender(int idUser ,UserGender gender);//to test
	public List<User> getUsersAbleModerator();//to test
	public List<User> getUsersAbleSectionAdministrator();//to test
	public List<User> getUsersAbleSubSectionAdministrator();//to test
	//public List<User> doYouKnow(int idUser);//to test
	

}
