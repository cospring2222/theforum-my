package com.theforum.json;

/**
 * @author Uliana and David
 */
//mesages json model
public class MessagesWrapper {
	private Long id;
	private String fromUserName;
	private String toUserName;
	private String title;
	private String text;
	public MessagesWrapper() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MessagesWrapper(Long id, String fromUserName, String toUserName, String title, String text) {
		super();
		this.id = id;
		this.fromUserName = fromUserName;
		this.toUserName = toUserName;
		this.title = title;
		this.text = text;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	
	

	
}
