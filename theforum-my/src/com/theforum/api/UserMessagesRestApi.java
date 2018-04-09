package com.theforum.api;

import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;

/**
 * @author Uliana and David
 */

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

@Path("/usermessages")
public class UserMessagesRestApi {
	UserMessageManager userMessageManager = new UserMessageManagerImpl();
	UserManager userManager = new UserManagerImpl();

	@Path("/getall")
	@GET
	@Produces("application/json")
	public Response getAllUserMessages() throws JSONException {

		JSONObject jsonObject = new JSONObject();

		List<UserMessages> um_list = userMessageManager.loadAllUserMessages();
		List<MessagesWrapper> mw_list = new ArrayList<MessagesWrapper>();
		for (UserMessages item : um_list) {
					mw_list.add(new MessagesWrapper(item.getId(), item.getUsersByUsermsgsFromUserid().getUsername(),
					item.getUsersByUsermsgsToUserid().getUsername(), item.getUsermsgsSubject(),
					item.getUsermsgsText()));

		}

		return Response.status(200).entity(um_list).build();
	}

	@Path("/getallbyuser/{id}")
	@GET
	@Produces("application/json")
	public Response getAllUserMessagesByUser(@PathParam("id") Long userID) throws JSONException {

		JSONObject jsonObject = new JSONObject();

		Users cur_u = userManager.findUserById(userID);
		List<UserMessages> um_list = cur_u.getUserMessgesesForUsermsgsToUserid();

		List<MessagesWrapper> mw_list = new ArrayList<MessagesWrapper>();
		for (UserMessages item : um_list) {

			mw_list.add(new MessagesWrapper(item.getId(), item.getUsersByUsermsgsFromUserid().getUsername(),
					item.getUsersByUsermsgsToUserid().getUsername(), item.getUsermsgsSubject(),
					item.getUsermsgsText()));
		}
		return Response.status(200).entity(mw_list).build();
	}

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response createUserMessage(MessagesWrapper mw) throws JSONException {

		JSONObject jsonObject = new JSONObject();

		if (mw.getTitle() == null) {
			throw new WebApplicationException(
					Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity("Title  is mandatory").build());
		}

		UserMessages um = new UserMessages();
		um.setUsermsgsSubject(mw.getTitle());
		um.setUsermsgsText(mw.getText());

		userMessageManager.saveOrUpdateUserMessage(um);

		return Response.status(200).entity(jsonObject.toString()).build();

	}

	@DELETE
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response deleteTheam(@QueryParam("umID") Long umID) throws JSONException {

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

		if (mw.getTitle() == null) {
			throw new WebApplicationException(
					Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity("title  is mandatory").build());
		}

		UserMessages cur_um = userMessageManager.findUserMessageById(mw.getId());
		if (cur_um == null) {
			jsonObject.put("status", "failed");
			jsonObject.put("message", "UserMessage is not exists.");
			return Response.status(200).entity(jsonObject.toString()).build();
		}

		cur_um.setUsermsgsSubject(mw.getTitle());
		cur_um.setUsermsgsText(mw.getText());

		userMessageManager.saveOrUpdateUserMessage(cur_um);

		return Response.status(200).entity(jsonObject.toString()).build();
	}

}