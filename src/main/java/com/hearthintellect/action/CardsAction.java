package com.hearthintellect.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.PageRequest;

import com.hearthintellect.model.Card;
import com.hearthintellect.mongo.repo.CardRepository;
import com.opensymphony.xwork2.ActionSupport;

public class CardsAction extends ActionSupport {
	private static Logger LOGGER = Logger.getLogger(CardsAction.class);
	private static final long serialVersionUID = 1L;
	
	private int page = 1;
	
	private List<Card> cardsToShow;
	private CardRepository cardRepository;
	
	public String execute() throws Exception {
		LOGGER.info("Incoming request from " + ServletActionContext.getRequest().getRemoteAddr()
				+ " at " + ServletActionContext.getRequest().getRequestURI());
		cardsToShow = cardRepository.findAll(new PageRequest(page, 20));
		if (cardsToShow != null) {
			return SUCCESS;
		}
		return ERROR;
	}
	
	public void setPage(int page) {
		this.page = page;
	}
	
	public int getPage() {
		return page;
	}
	
	public List<Card> getCardsToShow() {
		return cardsToShow;
	}
	
	public CardRepository getCardRepository() {
		return cardRepository;
	}
	
	public void setCardRepository(CardRepository cardRepository) {
		this.cardRepository = cardRepository;
	}
	
}
