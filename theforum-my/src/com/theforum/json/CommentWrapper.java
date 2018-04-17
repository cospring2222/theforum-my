package com.theforum.json;

/**
 * @author Uliana and David
 */
// comment json model
public class CommentWrapper {
	private Long id;
	private Long discussionid;
	private String body;
	private String author;
	private String author_role; 
	private String author_join;
	private String author_avator;
	private int author_posts_number;
	private String created;
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
	
	
	public String getAuthor_avator() {
		return author_avator;
	}
	public void setAuthor_avator(String author_avator) {
		this.author_avator = author_avator;
	}
	public String getAuthor_role() {
		return author_role;
	}
	public void setAuthor_role(String author_role) {
		this.author_role = author_role;
	}
	public String getAuthor_join() {
		return author_join;
	}
	public void setAuthor_join(String author_join) {
		this.author_join = author_join;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
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
	public int getAuthor_posts_number() {
		return author_posts_number;
	}
	public void setAuthor_posts_number(int author_posts_number) {
		this.author_posts_number = author_posts_number;
	}

	
	



}


