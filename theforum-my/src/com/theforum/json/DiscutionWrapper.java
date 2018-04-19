package com.theforum.json;

/**
 * @author Uliana and David
 */
import java.util.Date;

//simple discussion json model for presentation on teamfulllist
public class DiscutionWrapper {
	private Long id;
	private String title;
	private String body;
	private String author;
	private String author_role;
	private String author_avator;
	private int watchers;
	private int comments;
	private Date created;

	public DiscutionWrapper() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DiscutionWrapper(Long id, String title, String body, String author, String author_role, int watchers,
			int comments, Date created) {
		super();
		this.id = id;
		this.title = title;
		this.body = body;
		this.author = author;
		this.author_role = author_role;
		this.watchers = watchers;
		this.comments = comments;
		this.created = created;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAuthor_role() {
		return author_role;
	}

	public void setAuthor_role(String author_role) {
		this.author_role = author_role;
	}

	public int getWatchers() {
		return watchers;
	}

	public void setWatchers(int watchers) {
		this.watchers = watchers;
	}

	public int getComments() {
		return comments;
	}

	public void setComments(int comments) {
		this.comments = comments;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getAuthor_avator() {
		return author_avator;
	}

	public void setAuthor_avator(String author_avator) {
		this.author_avator = author_avator;
	}
}
