package com.theforum.api;

/**
 * @author Uliana and David
 */
import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.util.ArrayList;
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
import com.theforum.json.TheamWrapper;
import com.theforum.util.AllowCrossResponse;

//Rest API for Theam(Forum) tasks
@Path("/theamslist")
public class TheamRestApi {
	// Tools for work with DB:
	PostManager postManager = new PostManagerImpl();
	ForumManager forumManager = new ForumManagerImpl();
	TopicManager topicManager = new TopicManagerImpl();
	UserManager userManager = new UserManagerImpl();
	
	// API return list of all theams (forums)
	@GET
	@Produces("application/json")
	public Response getAllTheams() throws JSONException {

		JSONObject jsonObject = new JSONObject();

		List<Forums> forums = forumManager.loadAllForums();
		List<TheamWrapper> tw_list = new ArrayList<TheamWrapper>();
		for (Forums item : forums) {
			tw_list.add(new TheamWrapper(item.getForumId(), item.getForumName(), item.getForumDescription(),
					item.getForumPic()));

		}

		return Response.status(200).entity(tw_list).build();
	}

	// API return list of all disscusions(topics) by theam (forum) ID
	@GET
	@Path("{id}")
	@Produces("application/json")
	public Response getAllDiscussionsByTheam(@PathParam("id") Long theamID) throws JSONException {
		JSONObject jsonObject = new JSONObject();

		Forums cur_forum = forumManager.findForumById(theamID);

		List<Topics> topics = cur_forum.getTopicses();
		// return only topics relevant date ws DiscutionWrapper array
		List<DiscutionWrapper> dw_list = new ArrayList<DiscutionWrapper>();
		for (Topics item : topics) {
			DiscutionWrapper dw = new DiscutionWrapper(item.getTopicId(), item.getTopicSubject(),  item.getTopicBody(),
					item.getUsers().getUsername(), item.getUsers().getUserRole().name(),item.getTopicWatcherNumber(),item.getTopicCommentNumber(), item.getTopicDate());
			dw.setBody(item.getTopicBody());
			dw_list.add(dw);
		}

		return Response.status(200).entity(dw_list).build();
	}

	// API creating new theam(forum) with received date
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response createTheam(TheamWrapper tw) throws JSONException {

		JSONObject jsonObject = new JSONObject();

		if (tw.getTitle() == null) {
			throw new WebApplicationException(
					Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity("Title  is mandatory").build());
		}

		Forums theam = new Forums();
		theam.setForumName(tw.getTitle());
		theam.setForumPic(tw.getImg());

		theam.setForumDescription(tw.getText());
		forumManager.saveOrUpdateForum(theam);

		List<Forums> forums = forumManager.loadAllForums();
		List<TheamWrapper> tw_list = new ArrayList<TheamWrapper>();
		for (Forums item : forums) {
			tw_list.add(new TheamWrapper(item.getForumId(), item.getForumName(), item.getForumDescription(),
					item.getForumPic()));
		}

		return Response.status(200).entity(tw_list).build();
	}

	// API delete theam(forum) by giving ID
	// @DELETE
	@GET
	@Path("/delete/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response deleteTheam(@PathParam("id") Long theamID) throws JSONException {

		JSONObject jsonObject = new JSONObject();

		//find forum by id
		Forums cur_f = forumManager.findForumById(theamID);
		if (cur_f == null) {
			jsonObject.put("status", "failed");
			jsonObject.put("message", "Forum is not exists.");
			return Response.status(400).entity(jsonObject).build();
		}
		//find all disscusions of the forum for tree dell
		List<Topics> forum_discussiond = cur_f.getTopicses();
		for (Topics cur_t : forum_discussiond) {
			//find all comments of the forum for tree dell
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
		}

		forumManager.deleteForum(cur_f);

		//load updated forums list
		List<Forums> forums = forumManager.loadAllForums();		
		List<TheamWrapper> tw_list = new ArrayList<TheamWrapper>();
		//convert list from entity object to warper , that used on client side:
		for (Forums item : forums) {
			tw_list.add(new TheamWrapper(item.getForumId(), item.getForumName(), item.getForumDescription(),
					item.getForumPic()));
		}

		return Response.status(200).entity(tw_list).build();

	}

	// API get edit method return theam(forum) by giving ID for editing
	@GET
	@Path("/edit")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response editTheam(@QueryParam("forumID") Long forumID) throws JSONException {

		JSONObject jsonObject = new JSONObject();

		Forums cur_f = forumManager.findForumById(forumID);
		if (cur_f != null) {
			return Response.status(200).entity(cur_f).build();
		} else {
			jsonObject.put("status", "failed");
			jsonObject.put("message", "Theam is not exists.");

		}

		return Response.status(200).entity(jsonObject.toString()).build();
	}

	// API post edit method to edit theam(forum) by giving date
	@POST
	@Path("/edit")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response updateTheam(TheamWrapper tw) throws JSONException {

		JSONObject jsonObject = new JSONObject();

		if (tw.getId() == null) {
			throw new WebApplicationException(
					Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity("id  is mandatory").build());
		}

		if (tw.getTitle() == null) {
			throw new WebApplicationException(
					Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity("title  is mandatory").build());
		}

		Forums cur_t = forumManager.findForumById(tw.getId());
		if (cur_t == null) {
			jsonObject.put("status", "failed");
			jsonObject.put("message", "Theam is not exists.");
			return Response.status(200).entity(jsonObject.toString()).build();
		}

		cur_t.setForumName(tw.getTitle());
		cur_t.setForumDescription(tw.getText());
		// cur_t.setForumPic(tw.getPic());
		forumManager.saveOrUpdateForum(cur_t);

		return Response.status(200).entity(jsonObject.toString()).build();
	}

}