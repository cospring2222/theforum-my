package com.theforum.api;
/**
 * @author Uliana and David
 */

import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;


import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import com.theforum.dao.UserManager;
import com.theforum.dao.UserManagerImpl;
import com.theforum.dao.UserMessageManager;
import com.theforum.dao.UserMessageManagerImpl;
import com.theforum.entities.Topics;
import com.theforum.entities.UserMessages;
import com.theforum.entities.Users;
import com.theforum.json.DiscutionWrapper;
import com.theforum.json.MessagesWrapper;
import com.theforum.json.TheamWrapper;

//Rest API for UserMessages tasks
@Path("/usermessages")
public class UserMessagesRestApi {
	//Tools for work with DB:
	UserMessageManager userMessageManager = new UserMessageManagerImpl();
	UserManager userManager = new UserManagerImpl();

	//API return list of all UserMessages
	@Path("/getall")
	@GET
	@Produces("application/json")
	public Response getAllUserMessages() throws JSONException {

		JSONObject jsonObject = new JSONObject();

		List<UserMessages> um_list = userMessageManager.loadAllUserMessages();
		List<MessagesWrapper> mw_list = new ArrayList<MessagesWrapper>();
		for (UserMessages item : um_list) {
			Users fromUser=item.getUsersByUsermsgsFromUserid();
			MessagesWrapper mw = new MessagesWrapper(item.getId(), fromUser.getUsername(),
					item.getUsersByUsermsgsToUserid().getUsername(), item.getUsermsgsText(),fromUser.getUserRole().name(),fromUser.getAvator(),item.getUsermsgsDate().toString());
			mw_list.add(mw);
		}

		return Response.status(200).entity(um_list).build();
	}

	//API return list of all UserMessages by user ID
	@Path("/getallbyuser/{id}")
	@GET
	@Produces("application/json")
	public Response getAllUserMessagesByUser(@PathParam("id") Long userID) throws JSONException {

		//add list to one warper objects list
		List<MessagesWrapper> mw_list = getAllMessagesByUser(userID);

		return Response.status(200).entity(mw_list).build();
	}
	
	
	private List<MessagesWrapper>  getAllMessagesByUser(Long userID) throws JSONException {
		Users cur_u = userManager.findUserById(userID);
		//find list of all mesages to current user
		List<UserMessages> um_list = cur_u.getUserMessgesesForUsermsgsToUserid();
		
		//find list of all mesages from current user
		List<UserMessages> um_list2 = cur_u.getUserMessgesesForUsermsgsFromUserid();
		
		um_list.addAll(um_list2);
		

		//order list by created date:		
		um_list.sort(Comparator.comparing(UserMessages::getUsermsgsDate));	

		//add list to one warper objects list
		List<MessagesWrapper> mw_list = new ArrayList<MessagesWrapper>();
		for (UserMessages item : um_list) {
			Users fromUser=item.getUsersByUsermsgsFromUserid();
			MessagesWrapper mw = new MessagesWrapper(item.getId(), fromUser.getUsername(),	item.getUsersByUsermsgsToUserid().getUsername(), item.getUsermsgsText(),fromUser.getUserRole().name(),fromUser.getAvator(),item.getUsermsgsDate().toString());
			mw_list.add(mw);
		}
		return mw_list;
		
	}
	

	//API creating new UserMessage with received date
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response createUserMessage(MessagesWrapper mw) throws JSONException {

		JSONObject jsonObject = new JSONObject();

		if (mw.getText() == null) {
			throw new WebApplicationException(
					Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity("Text  is mandatory").build());
		}

		Users fromUser= userManager.findByUserName(mw.getFromUserName());
		Users toUser= userManager.findByUserName(mw.getToUserName());
		if (fromUser == null || toUser ==null) {
			throw new WebApplicationException(
					Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity("UserFrom or userTo is empty").build());
		}
		
		UserMessages um = new UserMessages();


		um.setUsersByUsermsgsFromUserid(fromUser);
		um.setUsersByUsermsgsToUserid(toUser);
		
		um.setUsermsgsText(mw.getText());

		userMessageManager.saveOrUpdateUserMessage(um);
		
		
		//add list to one warper objects list
		List<MessagesWrapper> mw_list = getAllMessagesByUser(fromUser.getUserId());
		

		return Response.status(200).entity(mw_list).build();

	}

	//API delete UserMessage by giving  ID	
//	@DELETE
	@GET
	@Path("/delete/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response deleteTheam(@PathParam("id") Long umID) throws JSONException {

		JSONObject jsonObject = new JSONObject();

		UserMessages cur_um = userMessageManager.findUserMessageById(umID);
		if (cur_um != null) {
			userMessageManager.deleteUserMessage(cur_um);

		} else {
			jsonObject.put("status", "failed");
			jsonObject.put("message", "UserMessage is not exists.");

		}

		return Response.status(200).entity(jsonObject.toString()).build();
	}

	//API get edit method return UserMessage by giving  ID for editing
	@GET
	@Path("/edit")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response editUserMessage(@QueryParam("umID") Long umID) throws JSONException {

		JSONObject jsonObject = new JSONObject();

		UserMessages cur_um = userMessageManager.findUserMessageById(umID);
		if (cur_um != null) {
			return Response.status(200).entity(cur_um).build();
		} else {
			jsonObject.put("status", "failed");
			jsonObject.put("message", "UserMessage is not exists.");

		}

		return Response.status(200).entity(jsonObject.toString()).build();
	}

	//API post edit method to edit UserMessage by giving  date
	@POST
	@Path("/edit")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response updateUserMessage(MessagesWrapper mw) throws JSONException {

		JSONObject jsonObject = new JSONObject();

		if (mw.getId() == null) {
			throw new WebApplicationException(
					Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity("id  is mandatory").build());
		}

		if (mw.getText() == null) {
			throw new WebApplicationException(
					Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity("Text  is mandatory").build());
		}

		UserMessages cur_um = userMessageManager.findUserMessageById(mw.getId());
		if (cur_um == null) {
			jsonObject.put("status", "failed");
			jsonObject.put("message", "UserMessage is not exists.");
			return Response.status(200).entity(jsonObject.toString()).build();
		}

		cur_um.setUsermsgsText(mw.getText());

		userMessageManager.saveOrUpdateUserMessage(cur_um);

		return Response.status(200).entity(jsonObject.toString()).build();
	}

}