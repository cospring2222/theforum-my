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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.json.JSONException;
import org.json.JSONObject;

import com.theforum.dao.ForumManager;
import com.theforum.dao.ForumManagerImpl;
import com.theforum.dao.TopicManager;
import com.theforum.dao.TopicManagerImpl;
import com.theforum.dao.UserManager;
import com.theforum.dao.UserManagerImpl;
import com.theforum.entities.Forums;
import com.theforum.entities.Topics;
import com.theforum.entities.Users;
import com.theforum.json.DiscutionWrapper;
import com.theforum.json.DiscutionWrapperGlobal;
import com.theforum.json.Theam;
import com.theforum.json.TheamWrapper;
import com.theforum.json.UserWrapper;
import com.theforum.util.DateUtils;
import com.theforum.util.Role;

@Path("/discusse")
public class DiscutionsRestApi {
	TopicManager topicManager = new TopicManagerImpl();
	ForumManager forumManager = new ForumManagerImpl();
	UserManager userManager = new UserManagerImpl();

	@Path("/getallbytheam")
	@GET
	@Produces("application/json")
	public Response getAllDiscussionsByTheam(@QueryParam("theamID") Long theamID) throws JSONException {

		JSONObject jsonObject = new JSONObject();

		Forums cur_forum = forumManager.findForumById(theamID);
		List<Topics> topics = cur_forum.getTopicses();
		// return only topics relevant date ws DiscutionWrapper array
		List<DiscutionWrapper> dw_list = new ArrayList<DiscutionWrapper>();
		for (Topics item : topics) {
			DiscutionWrapper dw = new DiscutionWrapper(item.getTopicId(), item.getTopicSubject(), ""); // last
																										// param
																										// will
																										// be
																										// body
			dw_list.add(dw);
		}
		return Response.status(200).entity(dw_list).build();
	}

	@Path("/getallbyuser")
	@GET
	@Produces("application/json")
	public Response getAllDiscussionsByUser(@QueryParam("userID") Long userID) throws JSONException {

		JSONObject jsonObject = new JSONObject();

		Users cur_u = userManager.findUserById(userID);
		List<Topics> topics = cur_u.getTopicses();
		// return only topics relevant date ws DiscutionWrapper array
		List<DiscutionWrapper> dw_list = new ArrayList<DiscutionWrapper>();
		for (Topics item : topics) {
			DiscutionWrapper dw = new DiscutionWrapper(item.getTopicId(), item.getTopicSubject(), ""); // last
																										// param
																										// will
																										// be
																										// body
			dw_list.add(dw);
		}
		return Response.status(200).entity(dw_list).build();
	}

	
	
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")

    public Response createDiscution(DiscutionWrapperGlobal dwg) throws JSONException  {

		JSONObject jsonObject = new JSONObject();
		Theam dw = dwg.getTheam(); // as disscution warper 
		
		if (dw.getTitle() == null || dw.getTitle() == "") {
			throw new WebApplicationException(
					Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity("Title  is mandatory").build());
		}

		Topics topic = new Topics();

		Forums forum = forumManager.findForumById(dwg.getTheamid());
		if (forum == null) {
			throw new WebApplicationException(
					Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity("Parent forum is not exist").build());

		}
		topic.setForums(forum);

		Long userID = (long) 1; /// !!! temp
		Users user = userManager.findUserById(userID);
		if (user == null) {
			throw new WebApplicationException(
					Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity("Parent user is not exist").build());

		}
		topic.setUsers(user);

		topic.setTopicSubject(dw.getTitle());
		topic.setTopicBody(dw.getBody());// !!! add in entity

		topicManager.saveOrUpdateTopic(topic);

		return Response.status(200).entity(jsonObject.toString()).build();

	}

	@DELETE
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response delete(@QueryParam("disscID") Long disscID) throws JSONException {

		JSONObject jsonObject = new JSONObject();

		// Long userID = 1;
		Topics cur_t = topicManager.findTopicById(disscID);
		if (cur_t != null) {
			topicManager.deleteTopic(cur_t);

		} else {
			jsonObject.put("status", "failed");
			jsonObject.put("message", "Disscution is not exists.");

		}

		return Response.status(200).entity(jsonObject.toString()).build();
	}

	@GET
	@Path("/edit")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response edit(@QueryParam("disscID") Long disscID) throws JSONException {

		JSONObject jsonObject = new JSONObject();

		Topics cur_t = topicManager.findTopicById(disscID);
		if (cur_t != null) {
			return Response.status(200).entity(cur_t).build();
		} else {
			jsonObject.put("status", "failed");
			jsonObject.put("message", "Disscution is not exists.");

		}

		return Response.status(200).entity(jsonObject.toString()).build();
	}

	@POST
	@Path("/edit")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response update(DiscutionWrapper dw) throws JSONException {

		JSONObject jsonObject = new JSONObject();

		if (dw.getId() == null) {
			throw new WebApplicationException(
					Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity("id is  mandatory").build());
		}

		if (dw.getTitle() == null) {
			throw new WebApplicationException(
					Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity("title is mandatory").build());
		}

		// body mandatory?
		// if (dw.getTitle() == null) {
		// throw new WebApplicationException(
		// Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity("title is
		// mandatory").build());
		// }

		Topics cur_dissc = topicManager.findTopicById(dw.getId());
		if (cur_dissc == null) {
			jsonObject.put("status", "failed");
			jsonObject.put("message", "Disscution is not exists.");
			return Response.status(200).entity(jsonObject.toString()).build();
		}

		cur_dissc.setTopicSubject(dw.getTitle());
		// cur_dissc.setForumDescription(dw.getText()); //body?

		topicManager.saveOrUpdateTopic(cur_dissc);

		return Response.status(200).entity(jsonObject.toString()).build();
	}

}