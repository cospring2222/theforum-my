package com.theforum.json;

public class TheamWrapper {
	
	private Long id;
	private String title;
	private String text;
	private String img;
	
	
	public TheamWrapper() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TheamWrapper(Long id, String title, String text, String img) {
		super();
		this.id = id;
		this.title = title;
		this.text = text;
		this.img = img;
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
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
}
