package com.hearthintellect.action;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class IndexAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(IndexAction.class);
	
	private String name;
	
	public String execute() throws Exception {
		LOGGER.info("Incoming request from " + ServletActionContext.getRequest().getRemoteAddr()
				+ " at " + ServletActionContext.getRequest().getRequestURI());
		return SUCCESS;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
