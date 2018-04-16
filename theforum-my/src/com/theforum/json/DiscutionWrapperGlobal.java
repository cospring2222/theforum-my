package com.theforum.json;

/**
 * @author Uliana and David
 */
//global discussion json model for client side in new discussion creation
//DiscutionsRestApi.java -> Post @Path("/add") createDiscution

public class DiscutionWrapperGlobal {
	Theam theam;
	Long theamid;

	public DiscutionWrapperGlobal() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DiscutionWrapperGlobal(Theam theam, Long theamid) {
		super();
		this.theam = theam;
		this.theamid = theamid;
	}
	public Long getTheamid() {
		return theamid;
	}
	public void setTheamid(Long theamid) {
		this.theamid = theamid;
	}
	public Theam getTheam() {
		return theam;
	}
	public void setTheam(Theam theam) {
		this.theam = theam;
	}
	
}
