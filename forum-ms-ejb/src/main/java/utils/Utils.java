package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class Utils {
	public static boolean emailValidator(String email) {
		boolean isValid = false;
		try {
			InternetAddress internetAddress = new InternetAddress(email);
			internetAddress.validate();
			isValid = true;
			}
		catch (AddressException e) 
		{
			 isValid = false;
		}
		return isValid;
	}
	public static String tokenGenerator () 
	{
		SecureRandom random = new SecureRandom();
		long longToken = Math.abs( random.nextLong() );
        String result = Long.toString( longToken, 16 );
        return result;
	}
	
	public static String toMD5(String passwordToHash) throws NoSuchAlgorithmException {
		String generatedPassword = null;
     
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(passwordToHash.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();

		return generatedPassword;
	}
	
	
}
