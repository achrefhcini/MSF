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
	//public JsonObject resetPassword(int idUser ,String NewPsd);//to do
	//public List<User> getAllUser();//to do
	//public JsonObject updateFirstname(int idUser ,String firstname);//to do
	//public JsonObject updateLastname(int idUser ,String lastname);//to do
	//public JsonObject updateBirthDate(int idUser ,Date BirthDate);//to do
	//public JsonObject updatePhoneNumber(int idUser ,String phoneNumber);//to do
	//public JsonObject updateGender(int idUser ,UserGender gender);//to do
	//public List<User> getUsersAbleModerator();//to do
	//public List<User> getUsersAbleSectionAdministrator();//to do
	//public List<User> getUsersAbleSubSectionAdministrator();//to do
	//public List<User> doYouKnow(int idUser);//to do
	

}
