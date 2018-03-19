package com.theforum.json;

public class MessagesWrapper {
	private Long id;
	private Long userId;
	private String title;
	private String text;

	
	
	public MessagesWrapper() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MessagesWrapper(Long id, String title, String text, Long userId) {
		super();
		this.id = id;
		this.userId = userId;
		this.title = title;
		this.text = text;
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
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
