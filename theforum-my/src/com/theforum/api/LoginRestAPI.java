package com.theforum.api;
/**
 * @author Uliana and David
 */
import java.io.*;
import java.net.*;



import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import com.theforum.dao.UserManager;
import com.theforum.dao.UserManagerImpl;
import com.theforum.entities.Users;
import com.theforum.json.AuthenticationDetails;
import com.theforum.json.UserWrapper;

//Rest API for Login form tasks
@Path("/login")
public class LoginRestAPI {
	// for tests only json clinte
	@Path("/authenticate")
	@GET
	@Produces("application/json")
	public void test() {
		String string = "";
		try {

			// Step1: Let's 1st read file from fileSystem
			// Change JSON.txt path here
			// InputStream crunchifyInputStream = new
			// FileInputStream("F:/Limudim/!evn_proj_java/TheForumApi/MyDocuments/auth.txt");
			InputStream crunchifyInputStream = new FileInputStream(
					"F:/Limudim/!evn_proj_java/TheForumApi/MyDocuments/uw.txt");
			// InputStream crunchifyInputStream = new FileInputStream(
			// "F:/Limudim/!evn_proj_java/TheForumApi/MyDocuments/tw.txt");
			// InputStream crunchifyInputStream = new FileInputStream(
			// "F:/Limudim/!evn_proj_java/TheForumApi/MyDocuments/cw.txt");
			InputStreamReader crunchifyReader = new InputStreamReader(crunchifyInputStream);
			BufferedReader br = new BufferedReader(crunchifyReader);
			String line;
			while ((line = br.readLine()) != null) {
				string += line + "\n";
			}

			JSONObject jsonObject = new JSONObject(string);
			System.out.println(jsonObject);

			// Step2: Now pass JSON File Data to REST Service
			try {
				// URL url = new
				// URL("http://localhost:8080/TheForum/rest/api/login/authenticate");
				URL url = new URL("http://localhost:8080/TheForum/rest/api/users/regisration");
				// URL url = new
				// URL("http://localhost:8080/TheForum/rest/api/theamslist/add");
				// URL url = new
				// URL("http://localhost:8080/TheForum/rest/api/comment/edit");
				URLConnection connection = url.openConnection();
				connection.setDoOutput(true);
				connection.setRequestProperty("Content-Type", "application/json");
				connection.setConnectTimeout(5000);
				connection.setReadTimeout(5000);
				OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
				out.write(jsonObject.toString());
				out.close();

				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

				while (in.readLine() != null) {
				}
				System.out.println("\nCrunchify REST Service Invoked Successfully..");
				in.close();
			} catch (Exception e) {
				System.out.println("\nError while calling Crunchify REST Service");
				System.out.println(e);
			}

			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//login checking user and pass
	@Path("/authenticate")
	@POST
	@Produces("application/json")
	public Response authenticate(AuthenticationDetails ad) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		UserManager userManager = new UserManagerImpl();
		Users cur_u = userManager.findByUserName(ad.getUsername());
		UserWrapper uw = new UserWrapper();

		//check that  user exist
		if (cur_u != null) {
			//check that  pass is correct
			
			String cur_uspass = cur_u.getUserPassword();
			String ad_uspass = ad.getPassword();
			
			if (cur_uspass.equals(ad_uspass)) {
				uw.setUsername(cur_u.getUsername());
				uw.setId(cur_u.getUserId());
				uw.setFirstname(cur_u.getUserFirstName());
				uw.setLastname(cur_u.getUserSecondName());
				uw.setToken("fake-jwt-token");
			}
		} else {
			jsonObject.put("status", "failed");
			jsonObject.put("message", "Username or password is incorrect.");
			return Response.status(400).entity(jsonObject.toString()).build();

		}

		return Response.status(200).entity(uw).build();
	}
}
