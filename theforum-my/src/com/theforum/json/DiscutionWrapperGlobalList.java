package com.theforum.json;

import java.util.List;

/**
 * @author Uliana and David
 */
//global discussion list json model
public class DiscutionWrapperGlobalList {
	List<Theam> theam;
	Long theamid;
	public DiscutionWrapperGlobalList() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DiscutionWrapperGlobalList(List<Theam> theams, Long theamid) {
		super();
		this.theam = theam;
		this.theamid = theamid;
	}
	public List<Theam> getTheams() {
		return theam;
	}
	public void setTheams(List<Theam> theams) {
		this.theam = theam;
	}
	public Long getTheamid() {
		return theamid;
	}
	public void setTheamid(Long theamid) {
		this.theamid = theamid;
	}


}
