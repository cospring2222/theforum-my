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
	
	//Tools for work with DB:
	PostManager postManager = new PostManagerImpl();
	TopicManager topicManager = new TopicManagerImpl();
	ForumManager forumManager = new ForumManagerImpl();
	UserManager userManager = new UserManagerImpl();

	//API return list of discussions(topics) by theam(Forum) ID
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
			
			DiscutionWrapper dw = new DiscutionWrapper(item.getTopicId(), item.getTopicSubject(),  item.getTopicBody(),
					item.getUsers().getUsername(),item.getUsers().getUserRole().name(),0,0 ,item.getTopicDate());
			dw.setBody(item.getTopicBody());
			dw_list.add(dw);
		}
		return Response.status(200).entity(dw_list).build();
	}

	//API return list of discussions(topics) by user ID
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
			DiscutionWrapper dw = new DiscutionWrapper(item.getTopicId(), item.getTopicSubject(),  item.getTopicBody(),
					item.getUsers().getUsername(),item.getUsers().getUserRole().name(),0,0 ,item.getTopicDate());
		}
		return Response.status(200).entity(dw_list).build();
	}

	
	//API creating new discussions(topic) with received date
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

		Users user = userManager.findByUserName(dwg.getTheam().getAuthor());
		if (user == null) {
			throw new WebApplicationException(
					Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity("Parent user is not exist").build());

		}
		topic.setUsers(user);

		topic.setTopicSubject(dw.getTitle());
		topic.setTopicBody(dw.getBody());

		topicManager.saveOrUpdateTopic(topic);

		forum = forumManager.findForumById(dwg.getTheamid());
		List<Topics> topics = forum.getTopicses();
		// return only topics relevant date ws DiscutionWrapper array
		List<DiscutionWrapper> dw_list = new ArrayList<DiscutionWrapper>();
		for (Topics item : topics) {
			DiscutionWrapper new_dw = new DiscutionWrapper(item.getTopicId(), item.getTopicSubject(), item.getTopicBody(),
					item.getUsers().getUsername(),item.getUsers().getUserRole().name(),0,0 ,item.getTopicDate());
			dw_list.add(new_dw);
		}
		return Response.status(200).entity(dw_list).build();		

	}

	
	//API delete discussions(topic) by giving  ID
//	@DELETE
	@GET
	@Path("/delete/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response delete(@PathParam("id") Long disscID) throws JSONException {

		JSONObject jsonObject = new JSONObject();

		// Long userID = 1;
		
		Topics cur_t = topicManager.findTopicById(disscID);
		if (cur_t == null) {
			jsonObject.put("status", "failed");
			jsonObject.put("message", "Disscution is not exists.");
			
			return Response.status(400).entity(jsonObject.toString()).build();
		} 
		
		Long cur_forum_id = cur_t.getForums().getForumId(); //parent forum id
		
		 //all comments for current discussion delete too
    	List<Posts> tp= cur_t.getPostses();
    	
    	for (Posts post : tp) {  		
    		postManager.deletePost(post);
		}
    	//now delete discussion
		topicManager.deleteTopic(cur_t);
		
		//find updated list of discussions by parent forum id
		Forums cur_forum = forumManager.findForumById(cur_forum_id); //parent forum
		List<Topics> topics = cur_forum.getTopicses();
		// return only topics relevant date ws DiscutionWrapper array
		List<DiscutionWrapper> dw_list = new ArrayList<DiscutionWrapper>();
		for (Topics item : topics) {
			
			DiscutionWrapper dw = new DiscutionWrapper(item.getTopicId(), item.getTopicSubject(),  item.getTopicBody(),
					item.getUsers().getUsername(),item.getUsers().getUserRole().name(),0,0 ,item.getTopicDate());
			dw_list.add(dw);
		}
		return Response.status(200).entity(dw_list).build();	

	}
	//API get edit method return discussions(topic) by giving  ID for editing
	@GET
	@Path("/edit")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response edit(@QueryParam("disscID") Long disscID) throws JSONException {

		JSONObject jsonObject = new JSONObject();

		Topics cur_t = topicManager.findTopicById(disscID);
		
		if (cur_t == null){
			jsonObject.put("status", "failed");
			jsonObject.put("message", "Disscution is not exists.");
			return Response.status(400).entity(jsonObject.toString()).build();
		}
	
		return Response.status(200).entity(cur_t).build();
	}

	//API post edit method to edit discussions(topic) by giving  date
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
		Forums cur_forum = cur_dissc.getForums();
		List<Topics> topics = cur_forum.getTopicses();
		// return only topics relevant date ws DiscutionWrapper array
		List<DiscutionWrapper> dw_list = new ArrayList<DiscutionWrapper>();
		for (Topics item : topics) {
			DiscutionWrapper new_dw = new DiscutionWrapper(item.getTopicId(), item.getTopicSubject(),  item.getTopicBody(),
					item.getUsers().getUsername(),item.getUsers().getUserRole().name(),0,0 ,item.getTopicDate());
			dw_list.add(new_dw);
		}
		return Response.status(200).entity(dw_list).build();	
	}

}