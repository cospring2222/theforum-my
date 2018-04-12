package com.theforum.api;
/**
 * @author Uliana and David
 */


import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

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
import com.theforum.entities.Forums;
import com.theforum.entities.Posts;
import com.theforum.entities.Topics;
import com.theforum.json.CommentWrapper;
import com.theforum.json.DiscutionWrapper;
import com.theforum.json.TheamWrapper;
import com.theforum.util.AllowCrossResponse;

//not in use - can be used , if task for old posts will called outside
@Path("/global")
public class GlobalRestApi {
	PostManager postManager = new PostManagerImpl();
	//test only
	@GET
	@Produces("application/json")
	public Response getAllTheams() throws JSONException {

		JSONObject jsonObject = new JSONObject();

		return Response.status(200).entity(jsonObject).build();
	}

	@POST
	@Path("/deloldcomments")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response createTheam(@QueryParam("NumDaysAfter") int numDaysAfter) throws JSONException {
//	@GET 
//	@Path("/deloldcomments")
//	@Produces("application/json")	
//	public Response createTheam() throws JSONException {
//		int numDaysAfter=5;
		JSONObject jsonObject = new JSONObject();

		// check if send by system only.

		// get all comments:
		List<Posts> posts = postManager.loadAllPosts();
		
		Calendar c=new GregorianCalendar();
		c.add(Calendar.DATE, -numDaysAfter);
		Date d=c.getTime();

		for (Posts item : posts) {
			if ( item != null && item.getPostDate().before(d)  ) {
				postManager.deletePost(item);
			}
		}
		
		return Response.status(200).entity(jsonObject).build();
	}

}