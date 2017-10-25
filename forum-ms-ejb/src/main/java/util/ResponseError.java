package util;

public class ResponseError {
	public static final ResponseTmp ADDED=new ResponseTmp("you have failed added this Request ", "Error", 400);
	public static final ResponseTmp UPDATED=new ResponseTmp("you have failed update this Request ", "Error", 400);
	public static final ResponseTmp NO_DATA=new ResponseTmp("Your Request DATA is empty", "Error", 204 );
	public static final ResponseTmp NO_FIELDS=new ResponseTmp("Your have to insert required fields", "Error", 204 );
	public static final ResponseTmp NOT_TYPE=new ResponseTmp("Type of this request is not correct", "Error", 204 );
    public static final ResponseTmp SQL_EXCEPTION=new ResponseTmp("could not resolve property for this request","SqlException",275);
    public static final ResponseTmp NO_TARGET=new ResponseTmp("Cannot determine the target object for this request","OAuthException",275);

}
