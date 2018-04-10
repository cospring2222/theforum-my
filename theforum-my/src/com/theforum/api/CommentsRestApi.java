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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import org.json.JSONException;
import org.json.JSONObject;

import com.theforum.dao.PostManager;
import com.theforum.dao.PostManagerImpl;
import com.theforum.dao.TopicManager;
import com.theforum.dao.TopicManagerImpl;
import com.theforum.dao.UserManager;
import com.theforum.dao.UserManagerImpl;
import com.theforum.entities.Posts;
import com.theforum.entities.Topics;
import com.theforum.entities.Users;
import com.theforum.json.CommentWrapper;
import com.theforum.util.DateUtils;
import com.theforum.util.Role;

//Rest API for comments tasks
@Path("/commentslist")
public class CommentsRestApi {
	
	//Tools for work with DB:
	
	TopicManager topicManager = new TopicManagerImpl();
	UserManager userManager = new UserManagerImpl();
	PostManager postManager = new PostManagerImpl();

	//API return list of comments(posts) by discussion(topic) ID
	@GET
	@Path("{id}")
	@Produces("application/json")
	public Response getAllCommentsByDisscution(@PathParam("id") Long disscID) throws JSONException {

		JSONObject jsonObject = new JSONObject();

		Topics cur_topic = topicManager.findTopicById(disscID);
		List<Posts> posts = cur_topic.getPostses();
		// return only topics relevant date ws DiscutionWrapper array
		List<CommentWrapper> cw_list = new ArrayList<CommentWrapper>();
		for (Posts item : posts) {
			CommentWrapper cw = new CommentWrapper(item.getPostId(),disscID, item.getUsers().getUsername(), item.getPostText(),new Long(0));
			cw_list.add(cw);
		}
		return Response.status(200).entity(cw_list).build();
	}

	//API return list of comments(posts) by User ID
	@Path("/getallbyuser")
	@GET
	@Produces("application/json")
	public Response getAllPostsByUser(@QueryParam("userID") Long userID) throws JSONException {

		JSONObject jsonObject = new JSONObject();

		Users cur_u = userManager.findUserById(userID);
		List<Posts> posts = cur_u.getPostses();		
		// return only topics relevant date ws DiscutionWrapper array
		List<CommentWrapper> cw_list = new ArrayList<CommentWrapper>();
		for (Posts item : posts) {
			CommentWrapper cw = new CommentWrapper(item.getPostId(),item.getTopics().getTopicId(), item.getUsers().getUsername(), item.getPostText(),new Long(0));
			cw_list.add(cw);
		}
		return Response.status(200).entity(cw_list).build();
	}

	//API creating new comment(post) with received date
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response createPost(CommentWrapper cw)throws JSONException {
	//public Response createPost(Object cw)throws JSONException {
		JSONObject jsonObject = new JSONObject();


		if (cw.getBody() == null || cw.getBody() == "") {
			throw new WebApplicationException(
					Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity("Body  is mandatory").build());
		}

		Posts post = new Posts();

		Topics topic = topicManager.findTopicById(cw.getDiscussionid());
		if (topic == null) {
			throw new WebApplicationException(
					Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity("Parent topic is not exist").build());

		}
		post.setTopics(topic);
		// set forum parent too?

//		Users user = userManager.findByUserName(cw.getAuthor());
		
		
		Users user = userManager.findUserById(new Long(1));
		if (user == null) {
			throw new WebApplicationException(
					Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity("Parent user is not exist").build());

		}
		post.setUsers(user);

		post.setPostText(cw.getBody());

		postManager.saveOrUpdatePost(post);

		return Response.status(200).entity("Comment added").build();

	}

	//API delete comment(post) by giving  ID
	@DELETE
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response delete(@QueryParam("commentID") Long commentID) throws JSONException {

		JSONObject jsonObject = new JSONObject();

		// Long userID = 1;
		Posts cur_post = postManager.findPostById(commentID);
		if (cur_post != null) {
			postManager.deletePost(cur_post);

		} else {
			jsonObject.put("status", "failed");
			jsonObject.put("message", "Post is not exists.");

		}

		return Response.status(200).entity(jsonObject.toString()).build();
	}

	//API get edit method return comment(post) by giving  ID for editing
	@GET
	@Path("/edit")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response edit(@QueryParam("commentID") Long commentID) throws JSONException {

		JSONObject jsonObject = new JSONObject();

		Posts cur_post = postManager.findPostById(commentID);

		if (cur_post != null) {
			Long ID=cur_post.getPostId();
			Users u= cur_post.getUsers();
			if (u == null)  {
				jsonObject.put("status", "failed");
				jsonObject.put("message", "Comment parent user is not exists.");
			}
			String un=u.getUsername();
			String body =cur_post.getPostText();
			//CommentWrapper cw = new CommentWrapper(ID,un,body);
			//CommentWrapper cw = new CommentWrapper(item.getTopics().getTopicId(), item.getUsers().getUsername(), item.getPostText(),item.getPostId());
			CommentWrapper cw = new CommentWrapper();
			return Response.status(200).entity(cw).build();
		} else {
			jsonObject.put("status", "failed");
			jsonObject.put("message", "Comment is not exists.");

		}

		return Response.status(200).entity(jsonObject.toString()).build();
	}
	
	//API post edit method to edit comment(post) by giving  date
	@POST
	@Path("/edit")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response update(CommentWrapper cw) throws JSONException {

		JSONObject jsonObject = new JSONObject();

//		if (cw.getId() == null) {
//			throw new WebApplicationException(
//					Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity("id is  mandatory").build());
//		}
//
//		if (cw.getBody() == null) {
//			throw new WebApplicationException(
//					Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity("body is mandatory").build());
//		}
//
//		Posts cur_comment = postManager.findPostById(cw.getId());
//		if (cur_comment == null) {
//			jsonObject.put("status", "failed");
//			jsonObject.put("message", "Comment is not exists.");
//			return Response.status(200).entity(jsonObject.toString()).build();
//		}
//
//		cur_comment.setPostText(cw.getBody());
//		// cur_dissc.setForumDescription(dw.getText()); //body?
//
//		postManager.saveOrUpdatePost(cur_comment);
		return Response.status(200).entity(jsonObject.toString()).build();
	}

}