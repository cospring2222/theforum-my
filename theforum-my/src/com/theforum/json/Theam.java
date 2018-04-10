package com.theforum.json;

import java.util.Date;

/**
 * @author Uliana and David
 */
//json Discussion(Topic)  model
public class Theam {
	
	private Long id;
	private String title;
	private String body;
	private String author;
	private String author_role;
	private int watchers;
	private int comments;
	private String created;
	
	
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
