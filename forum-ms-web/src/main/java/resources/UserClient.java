package resources;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import iservices.IUserManagerLocal;
import persistance.Device;
import persistance.User;

@Path("/user")
@RequestScoped
public class UserClient {
	@Inject
	IUserManagerLocal userManager ;
	private final String UPLOADED_FILE_PATH = "E:\\test\\";
	
	@Path("/getUserById/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	public Response getUserById(@PathParam("id") int id)
	{
		User user=userManager.getUserById(id);
		if(user!=null)
		{
			System.err.println(user.getCreationDate());
			return Response.ok(user).build();
		}
			
		return Response.status(Status.NO_CONTENT).build();
	}
	@Path("/enable/{username}/{token}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	public Response enable(@PathParam("username") String username,@PathParam("token") String token)
	{
		return Response.ok(userManager.enableUser(username, token)).build();
	}
	@Path("/addUser")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes()
	public Response addUser(
			@Context HttpServletRequest req,
			@QueryParam("email") String email,
			@QueryParam("username") String username,
			@QueryParam("password") String password
			)
	{
		
		User user = new User(email,username,password);
		String tab[]=getOsBrowserUser(req.getHeader("User-Agent"));
		String remoteHost = req.getRemoteHost();
		Device device = new Device(user,tab[0],tab[1],remoteHost,Boolean.FALSE);
		return Response.ok(userManager.quickSignup(user,device)).build();
	}
	@Path("/changePassword")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes()
	public Response changePassword(
			@QueryParam("id") int id,
			@QueryParam("currentPwd") String currentPwd,
			@QueryParam("newPwd") String newPwd
			)
	{
		
		return Response.ok(userManager.changePassword(id, currentPwd, newPwd)).build();
	}
	@Path("/login")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes()
	public Response login(
			@Context HttpServletRequest req,
			@QueryParam("usernameOrEmail") String username,
			@QueryParam("password") String password
			)
	{
		GenerateToken generateToken = new GenerateToken();
		String tab[]=getOsBrowserUser(req.getHeader("User-Agent"));
		String remoteHost = req.getRemoteHost();
		Device device = new Device();
		device.setOs(tab[0]);
		device.setBrowser(tab[1]);
		device.setConnected(Boolean.FALSE);
		device.setIp(remoteHost);
		JsonObject jsonObject =userManager.login(username, password,device);
		
		if(jsonObject.containsKey("error"))
		{
			return Response.ok(jsonObject).build();
		}
		else
		{
			jsonObject=jsonObjectToBuilder(jsonObject).add("token", generateToken.issueToken(username)).build();
			return Response.ok(jsonObject).build();
		}
		
	}
	@Path("/ip")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response getIp(@Context HttpServletRequest req) {
	    String tab[]=getOsBrowserUser(req.getHeader("User-Agent"));
	    return Response.ok("OS: "+tab[0]+" Bowser: "+tab[1]).build();
	}

	@Path("/logout/{idUser}/{idDevice}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout(
			@PathParam("idUser") int idUser,
			@PathParam("idDevice") int idDevice
			) {
	    return Response.ok(userManager.logout(idUser,idDevice)).build();
	}
	
	@POST
	@Path("/changePicture")
	@Consumes("multipart/form-data")
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadFile(
			MultipartFormDataInput input
			) {

		String fileName = "";
		int id=0;
		try {
			id = input.getFormDataPart("id", Integer.class, null);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		List<InputPart> inputParts = uploadForm.get("uploadedFile");
		
		for (InputPart inputPart : inputParts) {

		 try {

			MultivaluedMap<String, String> header = inputPart.getHeaders();
			fileName = getFileName(header);

			//convert the uploaded file to inputstream
			InputStream inputStream = inputPart.getBody(InputStream.class,null);

			byte [] bytes = IOUtils.toByteArray(inputStream);

			//constructs upload file path
			fileName = UPLOADED_FILE_PATH + fileName;

			writeFile(bytes,fileName);

		  } catch (IOException e) {
			e.printStackTrace();
		  }

		}
		System.out.println(id);
		return Response.status(200)
		    .entity(userManager.chnageProfilPicture(id, fileName)).build();

	}

	/**
	 * header sample
	 * {
	 * 	Content-Type=[image/png],
	 * 	Content-Disposition=[form-data; name="file"; filename="filename.extension"]
	 * }
	 **/
	//get uploaded filename, is there a easy way in RESTEasy?
	private String getFileName(MultivaluedMap<String, String> header) {

		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

		for (String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {

				String[] name = filename.split("=");

				String finalFileName = name[1].trim().replaceAll("\"", "");
				return finalFileName;
			}
		}
		return "unknown";
	}

	//save to somewhere
	private void writeFile(byte[] content, String filename) throws IOException {

		File file = new File(filename);

		if (!file.exists()) {
			file.createNewFile();
		}

		FileOutputStream fop = new FileOutputStream(file);

		fop.write(content);
		fop.flush();
		fop.close();

	}
	private String[] getOsBrowserUser(String browserDetails)
	{
	        String  userAgent       =   browserDetails;
	        String  user            =   userAgent.toLowerCase();

	        String os = "";
	        String browser = "";

	        //=================OS=======================
	         if (userAgent.toLowerCase().indexOf("windows") >= 0 )
	         {
	             os = "Windows";
	         } else if(userAgent.toLowerCase().indexOf("mac") >= 0)
	         {
	             os = "Mac";
	         } else if(userAgent.toLowerCase().indexOf("x11") >= 0)
	         {
	             os = "Unix";
	         } else if(userAgent.toLowerCase().indexOf("android") >= 0)
	         {
	             os = "Android";
	         } else if(userAgent.toLowerCase().indexOf("iphone") >= 0)
	         {
	             os = "IPhone";
	         }else{
	             os = "UnKnown, More-Info: "+userAgent;
	         }
	         //===============Browser===========================
	        if (user.contains("msie"))
	        {
	            String substring=userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
	            browser=substring.split(" ")[0].replace("MSIE", "IE")+"-"+substring.split(" ")[1];
	        } else if (user.contains("safari") && user.contains("version"))
	        {
	            browser=(userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0]+"-"+(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
	        } else if ( user.contains("opr") || user.contains("opera"))
	        {
	            if(user.contains("opera"))
	                browser=(userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0]+"-"+(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
	            else if(user.contains("opr"))
	                browser=((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-")).replace("OPR", "Opera");
	        } else if (user.contains("chrome"))
	        {
	            browser=(userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
	        } else if ((user.indexOf("mozilla/7.0") > -1) || (user.indexOf("netscape6") != -1)  || (user.indexOf("mozilla/4.7") != -1) || (user.indexOf("mozilla/4.78") != -1) || (user.indexOf("mozilla/4.08") != -1) || (user.indexOf("mozilla/3") != -1) )
	        {
	            //browser=(userAgent.substring(userAgent.indexOf("MSIE")).split(" ")[0]).replace("/", "-");
	            browser = "Netscape-?";

	        } else if (user.contains("firefox"))
	        {
	            browser=(userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
	        } else if(user.contains("rv"))
	        {
	            browser="IE-" + user.substring(user.indexOf("rv") + 3, user.indexOf(")"));
	        } else
	        {
	            browser = "UnKnown, More-Info: "+userAgent;
	        }
	        String[] tabs = {os,browser};
	        return tabs;
	       	
	}
	private JsonObjectBuilder jsonObjectToBuilder(JsonObject jo) {
	    JsonObjectBuilder job = Json.createObjectBuilder();

	    for (Entry<String, JsonValue> entry : jo.entrySet()) {
	    	if(entry!=null&&entry.getKey()!=null&&entry.getValue()!=null)
	    	{
	    		job.add(entry.getKey(), entry.getValue());
	    	}
	    }

	    return job;
	}
}
