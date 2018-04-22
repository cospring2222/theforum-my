package com.theforum.json;

/**
 * @author Uliana and David
 */
//mesages json model
public class MessagesWrapper {
	private Long id;
	private String fromUserName;
	private String toUserName;
	private String text;
	private String author_role;
	private String author_avator;
	private String created;
	
	public MessagesWrapper() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MessagesWrapper(Long id, String fromUserName, String toUserName, String text, String author_role,
			String author_avator, String created) {
		super();
		this.id = id;
		this.fromUserName = fromUserName;
		this.toUserName = toUserName;
		this.text = text;
		this.author_role = author_role;
		this.author_avator = author_avator;
		this.created = created;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getAuthor_role() {
		return author_role;
	}

	public void setAuthor_role(String author_role) {
		this.author_role = author_role;
	}

	public String getAuthor_avator() {
		return author_avator;
	}

	public void setAuthor_avator(String author_avator) {
		this.author_avator = author_avator;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}
	
	

	

	
}
