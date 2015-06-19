package com.hearthintellect.action;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.hearthintellect.model.Deck;
import com.opensymphony.xwork2.ActionSupport;

public class DeckSearchResultAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(DeckSearchResultAction.class);
	
	private String cards;
	private List<Deck> searchResult;
	
	public String execute() {
		LOGGER.info("Incoming request from " + ServletActionContext.getRequest().getRemoteAddr()
				+ " at " + ServletActionContext.getRequest().getRequestURI());
		
		// TODO Process cards and generate search result here
		
		return SUCCESS;
	}
	
	public void setCards(String cards) {
		this.cards = cards;
	}
	
	public List<Deck> getSearchResult() {
		return searchResult;
	}

}
