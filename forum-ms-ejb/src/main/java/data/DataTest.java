package data;

import persistance.SupportTicket;
import persistance.User;
import persistance.UserRole;

public final class DataTest {

	public static User user1=new User(7,"achref.hcini@esprit.tn","The_Saw","azerty",UserRole.MODERATOR,"+21698651804");
	public static User user2=new User(8,"contact@webshc.com","webshc","1234",UserRole.MASTER,"+21698651804");
	public static User user3=new User(9,"thesaw012@gmail.com","clubafricain","1920",UserRole.Section_ADMIN,"+21698651804");
	public static User user4=new User(10,"moez.mehri@esprit.tn","Moez94","google",UserRole.Section_ADMIN,"+21698651804");
	public static String categorie1="Informatique general";
	public static String categorie2="Paiement Service";
	public static String categorie3="Probleme service";
	public static String categorie4="Autre";
	
}
