package com.theforum.api;

/**
 * @author Uliana and David
 */

import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;

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
import com.theforum.dao.PostManager;
import com.theforum.dao.PostManagerImpl;
import com.theforum.dao.TopicManager;
import com.theforum.dao.TopicManagerImpl;
import com.theforum.dao.UserManager;
import com.theforum.dao.UserManagerImpl;
import com.theforum.entities.Forums;
import com.theforum.entities.Posts;
import com.theforum.entities.Topics;
import com.theforum.entities.Users;
import com.theforum.json.DiscutionWrapper;
import com.theforum.json.DiscutionWrapperGlobal;
import com.theforum.json.Theam;
import com.theforum.json.TheamWrapper;
import com.theforum.json.UserWrapper;
import com.theforum.util.DateUtils;
import com.theforum.util.Role;

//Rest API for Discussions tasks
@Path("/discusse")
public class DiscutionsRestApi {

	// Tools for work with DB:
	PostManager postManager = new PostManagerImpl();
	TopicManager topicManager = new TopicManagerImpl();
	ForumManager forumManager = new ForumManagerImpl();
	UserManager userManager = new UserManagerImpl();

	// API return list of discussions(topics) by theam(Forum) ID
	@Path("/getallbytheam")
	@GET
	@Produces("application/json")
	public Response getAllDiscussionsByTheam(@QueryParam("theamID") Long theamID) throws JSONException {

		JSONObject jsonObject = new JSONObject();

		// find parent forum by id
		Forums cur_forum = forumManager.findForumById(theamID);

		if (cur_forum == null) { // return error if not exist
			throw new WebApplicationException(Response.status(400).entity("Parent forum is not exist").build());
		}

		// find discussions from forum parent
		List<Topics> topics = cur_forum.getTopicses();
		// convert to wrapper format list that maching client side
		List<DiscutionWrapper> dw_list = new ArrayList<DiscutionWrapper>();
		for (Topics item : topics) {

			DiscutionWrapper dw = new DiscutionWrapper(item.getTopicId(), item.getTopicSubject(), item.getTopicBody(),
					item.getUsers().getUsername(), item.getUsers().getUserRole().name(), item.getTopicWatcherNumber(),
					item.getTopicCommentNumber(), item.getTopicDate());
			dw.setBody(item.getTopicBody());

			dw_list.add(dw);
		}

		return Response.status(200).entity(dw_list).build();
	}

	// API return discussion(topic) by ID
	@Path("/getbyid/{id}")
	@GET
	@Produces("application/json")
	public Response getById(@PathParam("id") Long discID) throws JSONException {

		// find discussion by id
		Topics item = topicManager.findTopicById(discID);

		if (item == null) { // return error if not exist
			throw new WebApplicationException(Response.status(400).entity("Discussion is not exist").build());
		}

		// convert to wrapper format that maching client side
		DiscutionWrapper cur_topic = new DiscutionWrapper(item.getTopicId(), item.getTopicSubject(),
				item.getTopicBody(), item.getUsers().getUsername(), item.getUsers().getUserRole().name(),
				item.getTopicWatcherNumber(), item.getTopicCommentNumber(), item.getTopicDate());
		cur_topic.setAuthor_avator(item.getUsers().getAvator());
		cur_topic.setBody(item.getTopicBody());

		return Response.status(200).entity(cur_topic).build();
	}

	// API return list of discussions(topics) by user ID , not in use for now
	@Path("/getallbyuser")
	@GET
	@Produces("application/json")
	public Response getAllDiscussionsByUser(@QueryParam("userID") Long userID) throws JSONException {

		// find user by id
		Users cur_u = userManager.findUserById(userID);

		if (cur_u == null) { // return error if not exist
			throw new WebApplicationException(Response.status(400).entity("{Parent user  is not exist").build());
		}
		// find all topics for this user
		List<Topics> topics = cur_u.getTopicses();

		// convert to wrapper format that maching client side
		List<DiscutionWrapper> dw_list = new ArrayList<DiscutionWrapper>();

		for (Topics item : topics) {
			DiscutionWrapper dw = new DiscutionWrapper(item.getTopicId(), item.getTopicSubject(), item.getTopicBody(),
					item.getUsers().getUsername(), item.getUsers().getUserRole().name(), item.getTopicWatcherNumber(),
					item.getTopicCommentNumber(), item.getTopicDate());
		}

		return Response.status(200).entity(dw_list).build();
	}

	// Up discussion watchers counter by discussion id
	@GET
	@Path("/theamwatcher/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response updateTeamwatherCounterByTheamID(@PathParam("id") Long discID) throws JSONException {
		// find discussion
		Topics disc = topicManager.findTopicById(discID);// find discussion
		// chek if not null
		if (disc == null) {
			throw new WebApplicationException(Response.status(400).entity("Discussion is not exist").build());
		}

		// increase counter
		topicManager.increaseWatcherCounter(disc.getTopicId());

		return Response.status(200).entity("Discussion counter increased").build();
	}

	// API creating new discussions(topic) with received date
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")

	public Response createDiscution(DiscutionWrapperGlobal dwg) throws JSONException {

		// get discussion data from global discussion warper
		Theam dw = dwg.getTheam(); // as disscution warper

		// check model poperties
		if (dw.getTitle() == null || dw.getTitle() == "") {
			throw new WebApplicationException(Response.status(400).entity("Title  is mandatory").build());
		}

		// create new discussion
		Topics topic = new Topics();

		// find parent forum
		Forums forum = forumManager.findForumById(dwg.getTheamid());
		if (forum == null) { // if not exist return error
			throw new WebApplicationException(Response.status(400).entity("Parent forum is not exist").build());

		}
		// set parent forum for discussion
		topic.setForums(forum);

		// find parent user
		Users user = userManager.findByUserName(dwg.getTheam().getAuthor());
		if (user == null) {// if not exist return error
			throw new WebApplicationException(Response.status(400).entity("Parent user is not exist").build());

		}
		// set parent user for discussion
		topic.setUsers(user);

		// set data for discussion and create
		topic.setTopicSubject(dw.getTitle());
		topic.setTopicBody(dw.getBody());

		topicManager.saveOrUpdateTopic(topic);

		// get again forum parent from DB with updated list of discussions
		forum = forumManager.findForumById(dwg.getTheamid());
		List<Topics> topics = forum.getTopicses();

		// convert to wrapper format that matching client side
		List<DiscutionWrapper> dw_list = new ArrayList<DiscutionWrapper>();
		for (Topics item : topics) {
			DiscutionWrapper new_dw = new DiscutionWrapper(item.getTopicId(), item.getTopicSubject(),
					item.getTopicBody(), item.getUsers().getUsername(), item.getUsers().getUserRole().name(),
					item.getTopicWatcherNumber(), item.getTopicCommentNumber(), item.getTopicDate());
			dw_list.add(new_dw);
		}

		return Response.status(200).entity(dw_list).build();

	}

	// API delete discussions(topic) by giving ID
	@GET
	@Path("/delete/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response delete(@PathParam("id") Long disscID) throws JSONException {

		//find discussion by id
		Topics cur_t = topicManager.findTopicById(disscID);
		if (cur_t == null) {//if not exist return error
			throw new WebApplicationException(
					Response.status(400).entity("Disscution is not exists.").build());
		}

		//find parent forum id
		Long cur_forum_id = cur_t.getForums().getForumId();

		// all comments for current discussion delete too
		List<Posts> tp = cur_t.getPostses();

		for (Posts post : tp) {
			
			postManager.deletePost(post);
			Users user = post.getUsers();
			// update user posts counter 
			if (user != null) {
				userManager.decreaseCommentCounter(user.getUserId());
			}			
		}
		// now delete discussion
		topicManager.deleteTopic(cur_t);

		// find updated list of discussions by parent forum id
		Forums cur_forum = forumManager.findForumById(cur_forum_id); // parent
																		// forum
		List<Topics> topics = cur_forum.getTopicses();
		// convert to wrapper format that matching client side
		List<DiscutionWrapper> dw_list = new ArrayList<DiscutionWrapper>();
		for (Topics item : topics) {

			DiscutionWrapper dw = new DiscutionWrapper(item.getTopicId(), item.getTopicSubject(), item.getTopicBody(),
					item.getUsers().getUsername(), item.getUsers().getUserRole().name(), item.getTopicWatcherNumber(),
					item.getTopicCommentNumber(), item.getTopicDate());
			dw_list.add(dw);
		}
		return Response.status(200).entity(dw_list).build();

	}

	// API post edit method to edit discussions(topic) by giving date
	@POST
	@Path("/edit")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response update(DiscutionWrapper dw) throws JSONException {
		
		//check model data
		if (dw.getId() == null) {
			throw new WebApplicationException(
					Response.status(400).entity("id is  mandatory").build());
		}

		if (dw.getTitle() == null) {
			throw new WebApplicationException(
					Response.status(400).entity("title is mandatory").build());
		}

		//find discussion by id
		Topics cur_dissc = topicManager.findTopicById(dw.getId());
		
		if (cur_dissc == null) {//if not exist return error
			throw new WebApplicationException(
					Response.status(400).entity("Disscution is not exists.").build());
		}
		
		//update date
		cur_dissc.setTopicSubject(dw.getTitle());

		topicManager.saveOrUpdateTopic(cur_dissc);

		//get updated list of discussions
		Forums cur_forum = cur_dissc.getForums();
		List<Topics> topics = cur_forum.getTopicses();
		
		// convert to wrapper format that matching client side
		List<DiscutionWrapper> dw_list = new ArrayList<DiscutionWrapper>();
		for (Topics item : topics) {
			DiscutionWrapper new_dw = new DiscutionWrapper(item.getTopicId(), item.getTopicSubject(),
					item.getTopicBody(), item.getUsers().getUsername(), item.getUsers().getUserRole().name(),
					item.getTopicWatcherNumber(), item.getTopicCommentNumber(), item.getTopicDate());
			dw_list.add(new_dw);
		}
		
		return Response.status(200).entity(dw_list).build();
	}

}