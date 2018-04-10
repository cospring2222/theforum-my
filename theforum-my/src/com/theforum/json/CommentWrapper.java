package com.theforum.json;

/**
 * @author Uliana and David
 */
// comment json model
public class CommentWrapper {
	private Long id;
	private Long discussionid;
	private String author;
	private String body;
	private Long answeredid;
	
	public CommentWrapper() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CommentWrapper(Long id, Long discussionid, String author, String body, Long answeredid) {
		super();
		this.id = id;
		this.discussionid = discussionid;
		this.author = author;
		this.body = body;
		this.answeredid = answeredid;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDiscussionid() {
		return discussionid;
	}
	public void setDiscussionid(Long discussionid) {
		this.discussionid = discussionid;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public Long getAnsweredid() {
		return answeredid;
	}
	public void setAnsweredid(Long answeredid) {
		this.answeredid = answeredid;
	}

	
	


	
	
//	private Long id;
//	private String username;
//	private String body;
//
//	public CommentWrapper() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//	
//	public CommentWrapper(Long id, String username, String body) {
//		super();
//		this.id = id;
//		this.username = username;
//		this.body = body;
//	}
//
//	
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public String getUsername() {
//		return username;
//	}
//
//	public void setUsername(String username) {
//		this.username = username;
//	}
//
//	public String getBody() {
//		return body;
//	}

}


