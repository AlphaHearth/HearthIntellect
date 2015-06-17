package com.hearthintellect.action;

import com.opensymphony.xwork2.ActionSupport;

public class IndexAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
