package com.hearthintellect.action;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.PageRequest;

import com.hearthintellect.model.Deck;
import com.hearthintellect.mongo.repo.DeckRepository;
import com.opensymphony.xwork2.ActionSupport;

public class DecksAction extends ActionSupport {
	private static Logger LOGGER = Logger.getLogger(DecksAction.class);
	private static final long serialVersionUID = 1L;
	
	private int page = 1;
	
	private List<Deck> decksToShow;
	private DeckRepository deckRepository;
	
	public String execute() {
		LOGGER.info("Incoming request from " + ServletActionContext.getRequest().getRemoteAddr()
				+ " at " + ServletActionContext.getRequest().getRequestURI());
		
		decksToShow = deckRepository.findAll(new PageRequest(page, 20));
		if (decksToShow != null)
			return SUCCESS;
		
		return ERROR;
	}
	
	public List<Deck> getDecksToShow() {
		return decksToShow;
	}
	
	public void setPage(int page) {
		this.page = page;
	}
	
	public void setDeckRepository(DeckRepository deckRepository) {
		this.deckRepository = deckRepository;
	}
	
}
