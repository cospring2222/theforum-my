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

	// Tools for work with DB:

	TopicManager topicManager = new TopicManagerImpl();
	UserManager userManager = new UserManagerImpl();
	PostManager postManager = new PostManagerImpl();

	// API return list of comments(posts) by discussion(topic) ID
	@GET
	@Path("{id}")
	@Produces("application/json")
	public Response getAllCommentsByDisscution(@PathParam("id") Long disscID) throws JSONException {
		// find parent discussion
		Topics cur_topic = topicManager.findTopicById(disscID);

		if (cur_topic == null) {// if not exist return error
			throw new WebApplicationException(Response.status(400).entity("Disscution is not exists.").build());
		}

		// get all comments from parent discussion
		List<Posts> posts = cur_topic.getPostses();

		// convert to wrapper format that matching client side
		List<CommentWrapper> cw_list = new ArrayList<CommentWrapper>();

		for (Posts item : posts) {
			Users post_user = item.getUsers();
			if (post_user == null) {// if not exist return error
				throw new WebApplicationException(Response.status(400).entity("Some post have no user.").build());
			}

			CommentWrapper cw = new CommentWrapper(item.getPostId(), disscID, post_user.getUsername(),
					item.getPostText(), new Long(0));
			cw.setAuthor_join(DateUtils.dateToMonthYearOnlyString(post_user.getUserRegdate()));
			cw.setAuthor_avator(post_user.getAvator());
			cw.setCreated(item.getPostDate().toString());
			cw.setAuthor_posts_number(post_user.getUserCommentNumber());
			cw_list.add(cw);
		}

		return Response.status(200).entity(cw_list).build();

	}

	// API return list of comments(posts) by User ID , not in use pof now
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
			Topics cur_topic = item.getTopics();
			CommentWrapper cw = new CommentWrapper(item.getPostId(), cur_topic.getTopicId(), cur_u.getUsername(),
					item.getPostText(), new Long(0));
			cw.setAuthor_join(DateUtils.dateToMonthYearOnlyString(cur_u.getUserRegdate()));
			cw.setAuthor_avator(cur_u.getAvator());
			cw.setCreated(item.getPostDate().toString());
			cw.setAuthor_posts_number(cur_u.getUserCommentNumber());
			cw_list.add(cw);
		}

		return Response.status(200).entity(cw_list).build();
	}

	// API creating new comment(post) with received date
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response createPost(CommentWrapper cw) throws JSONException {

		// check if model data correct
		if (cw.getBody() == null || cw.getBody() == "") {
			throw new WebApplicationException(Response.status(400).entity("Body  is mandatory").build());
		}

		// create new comments
		Posts post = new Posts();

		// find parent discussion by id
		Topics topic = topicManager.findTopicById(cw.getDiscussionid());

		if (topic == null) {// if not exist return error
			throw new WebApplicationException(Response.status(400).entity("Parent topic is not exist").build());

		}
		// set parent discussion for comment
		post.setTopics(topic);

		// find parent user
		Users user = userManager.findByUserName(cw.getAuthor());

		if (user == null) {// if not exist return error
			throw new WebApplicationException(Response.status(400).entity("Parent user is not exist").build());

		}

		// set parent user
		post.setUsers(user);

		// set data and save
		post.setPostText(cw.getBody());

		postManager.saveOrUpdatePost(post);

		// user comments counter update
		userManager.increaseCommentCounter(user.getUserId());

		// topic comments counter update
		topicManager.increaseCommentCounter(topic.getTopicId());

		return Response.status(200).entity(cw).build();

	}

	// API delete comment(post) by giving ID
	@GET
	@Path("/delete/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response delete(@PathParam("id") Long commentID) throws JSONException {

		// find comment by id
		Posts cur_post = postManager.findPostById(commentID);
		if (cur_post == null) {// if not exist return error
			throw new WebApplicationException(Response.status(400).entity("Comment is not exist").build());
		}
		// find parent discussion and user
		Users parent_user = cur_post.getUsers();
		Topics parent_topic = cur_post.getTopics();

		postManager.deletePost(cur_post);

		// comments counter update if user exist
		if (parent_user != null) {
			userManager.decreaseCommentCounter(parent_user.getUserId());
		}

		// comments counter update if discussion exist
		if (parent_topic != null) {
			topicManager.decreaseCommentCounter(parent_topic.getTopicId());
		}

		return Response.status(200).entity("Comment deleted Successful").build();
	}

	// API get edit method return comment(post) by giving ID for editing not in
	// use for now
	// @GET
	// @Path("/edit")
	// @Consumes(MediaType.APPLICATION_JSON)
	// @Produces("application/json")
	// public Response edit(@QueryParam("commentID") Long commentID) throws
	// JSONException {
	//
	// JSONObject jsonObject = new JSONObject();
	//
	// Posts cur_post = postManager.findPostById(commentID);
	//
	// if (cur_post != null) {
	// Long ID = cur_post.getPostId();
	// Users u = cur_post.getUsers();
	// if (u == null) {
	// jsonObject.put("status", "failed");
	// jsonObject.put("message", "Comment parent user is not exists.");
	// }
	// String un = u.getUsername();
	// String body = cur_post.getPostText();
	// // CommentWrapper cw = new CommentWrapper(ID,un,body);
	// // CommentWrapper cw = new
	// // CommentWrapper(item.getTopics().getTopicId(),
	// // item.getUsers().getUsername(),
	// // item.getPostText(),item.getPostId());
	// CommentWrapper cw = new CommentWrapper();
	// return Response.status(200).entity(cw).build();
	// } else {
	// jsonObject.put("status", "failed");
	// jsonObject.put("message", "Comment is not exists.");
	//
	// }
	//
	// return Response.status(200).entity(jsonObject.toString()).build();
	// }

	// API post edit method to edit comment(post) by giving date
	@POST
	@Path("/edit")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response update(CommentWrapper cw) throws JSONException {

		if (cw.getId() == null) {
			throw new WebApplicationException(
					Response.status(400).entity("id is mandatory").build());
		}

		if (cw.getBody() == null) {
			throw new WebApplicationException(
					Response.status(400).entity("body is mandatory").build());
		}

		Posts cur_comment = postManager.findPostById(cw.getId());
		
		if (cur_comment == null) {
			throw new WebApplicationException(
					Response.status(400).entity("Comment is not exists.").build());
		}

		//update text and save
		cur_comment.setPostText(cw.getBody());

		postManager.saveOrUpdatePost(cur_comment);
		
		
		return Response.status(200).entity(cw).build();
	}

}