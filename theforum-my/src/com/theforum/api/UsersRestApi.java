package com.theforum.api;

import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;

/**
 * @author Uliana and David
 */

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import com.theforum.dao.UserManager;
import com.theforum.dao.UserManagerImpl;
import com.theforum.entities.Forums;
import com.theforum.entities.Topics;
import com.theforum.entities.Users;
import com.theforum.json.DiscutionWrapper;
import com.theforum.json.UserWrapper;
import com.theforum.util.Role;

@Path("/users")
public class UsersRestApi {
	UserManager userManager = new UserManagerImpl();
	


	@Path("/list/all")
	@GET
	@Produces("application/json")
	public Response getAllUsers() throws JSONException {


		JSONObject jsonObject = new JSONObject();

		List<Users> users = userManager.loadAllUsers();

	
		List<UserWrapper> uw_list = new ArrayList<UserWrapper>();
		for (Users item : users) {
			UserWrapper uw = new UserWrapper(item.getUserId(),item.getUsername(),item.getUserFirstName(),item.getUserSecondName(),item.getUserPassword());
			uw_list.add(uw);
		}
		return Response.status(200).entity(uw_list).build();
	
	}
	
	@Path("/list/page")
	@POST
	@Produces("application/json")
	public Response getAllUsers(Object obj) throws JSONException {


		JSONObject jsonObject = new JSONObject();

		List<Users> users = userManager.loadAllUsers();

	
		List<UserWrapper> uw_list = new ArrayList<UserWrapper>();
		for (Users item : users) {
			UserWrapper uw = new UserWrapper(item.getUserId(),item.getUsername(),item.getUserFirstName(),item.getUserSecondName(),item.getUserPassword());
			uw_list.add(uw);
		}
		return Response.status(200).entity(uw_list).build();
		
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	//public Response createUser(Object uw) throws JSONException {
	public Response createUser(UserWrapper uw) throws JSONException {
		JSONObject jsonObject = new JSONObject();

		if (uw.getUsername() == null) {
			throw new WebApplicationException(
					Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity("username  is mandatory").build());
		}
//		if (uw.getEmail() == null) {
//			throw new WebApplicationException(
//					Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity("email  is mandatory").build());
//		}
		if (uw.getPassword() == null) {
			throw new WebApplicationException(
					Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity("password  is mandatory").build());
		}

		String uw_un=uw.getUsername();
		Users old_user = userManager.findByUserName(uw_un);
		boolean userExsists = ( old_user != null);
		if (userExsists) {
			throw new WebApplicationException(
					Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity("username already in use").build());

		}

		Users u = new Users();
		u.setUserRole(Role.USER);
		u.setUserFirstName(uw.getFirstname());
		u.setUserSecondName(uw.getLastname());
		u.setUsername(uw.getUsername());
		u.setUserPassword(uw.getPassword());
		//u.setUserEmail(uw.getEmail());

		userManager.saveOrUpdateUser(u);

		return Response.status(200).entity(jsonObject.toString()).build();
	}

	@DELETE
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	// public Response delete(@QueryParam("userID") Long userID)
	public Response deleteUser(@QueryParam("userID") Long userID) throws JSONException {

		JSONObject jsonObject = new JSONObject();

		// Long userID = 1;
		Users cur_u = userManager.findUserById(userID);
		if (cur_u != null) {
			userManager.deleteUser(cur_u);

		} else {
			jsonObject.put("status", "failed");
			jsonObject.put("message", "Username is not exists.");

		}

		return Response.status(200).entity(jsonObject.toString()).build();
	}

	@GET
	@Path("/edit")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response editUser(@QueryParam("userID") Long userID) throws JSONException {

		JSONObject jsonObject = new JSONObject();

		Users cur_u = userManager.findUserById(userID);
		if (cur_u != null) {
			return Response.status(200).entity(cur_u).build();
		} else {
			jsonObject.put("status", "failed");
			jsonObject.put("message", "Username is not exists.");

		}

		return Response.status(200).entity(jsonObject.toString()).build();
	}

	@POST
	@Path("/edit")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response updateUser(UserWrapper uw, @QueryParam("prevUserName") String prevUserName) throws JSONException {

		JSONObject jsonObject = new JSONObject();

		if (prevUserName == null || prevUserName == "") {
			throw new WebApplicationException(
					Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity("id  is mandatory").build());
		}
		
		if (uw.getId() == null) {
			throw new WebApplicationException(
					Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity("id  is mandatory").build());
		}

		if (uw.getUsername() == null) {
			throw new WebApplicationException(
					Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity("username  is mandatory").build());
		}
//		if (uw.getEmail() == null) {
//			throw new WebApplicationException(
//					Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity("email  is mandatory").build());
//		}
		if (uw.getPassword() == null) {
			throw new WebApplicationException(
					Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity("password  is mandatory").build());
		}


		Users cur_u = userManager.findUserById(uw.getId());
		if (cur_u == null) {
			jsonObject.put("status", "failed");
			jsonObject.put("message", "Username is not exists.");
			return Response.status(200).entity(jsonObject.toString()).build();
		}
		//check if new username is alredy taked
		if (cur_u.getUsername() != prevUserName)
		{
			Users check_new_username_exist = userManager.findByUserName(uw.getUsername());
			if (check_new_username_exist != null)
			{
				jsonObject.put("status", "failed");
				jsonObject.put("message", "User name" + cur_u.getUsername()+"is alredy in use, select other one.");
				return Response.status(200).entity(jsonObject.toString()).build();
			}
			
		}
		cur_u.setUsername(uw.getUsername());
		// cur_u.setUserRole(Role.USER); !
		cur_u.setUserFirstName(uw.getFirstname());
		cur_u.setUserSecondName(uw.getLastname());
		cur_u.setUserPassword(uw.getPassword());
		//cur_u.setUserEmail(uw.getEmail());

		userManager.saveOrUpdateUser(cur_u);

		return Response.status(200).entity(jsonObject.toString()).build();
	}

}