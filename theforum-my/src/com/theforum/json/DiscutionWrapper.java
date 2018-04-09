package com.theforum.json;

import java.util.Date;
//simple discussion json model
public class DiscutionWrapper {
	private Long id;
	private String title;
	private String body;
	private String author;
	private String author_role;
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

	private int watchers;
	private int comments;
	private Date created;

	public DiscutionWrapper(Long id, String title, String body) {
		super();
		this.id = id;
		this.title = title;
		this.body = body;
	}

	public DiscutionWrapper() {
		super();
		// TODO Auto-generated constructor stub
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
}
