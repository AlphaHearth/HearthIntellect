package com.hearthintellect.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Sort;

import com.hearthintellect.model.Card;
import com.hearthintellect.mongo.repo.CardRepository;
import com.hearthintellect.mongo.repo.DeckRepository;
import com.opensymphony.xwork2.ActionSupport;

public class DeckSearchAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(DeckSearchAction.class);
	
	private int classs = 0;
	
	private List<Card> classCards;
	private List<Card> neutralCards;
	
	private CardRepository cardRepository;
	
	public String execute() throws Exception {
		LOGGER.info("Incoming request from " + ServletActionContext.getRequest().getRemoteAddr()
				+ " at " + ServletActionContext.getRequest().getRequestURI());
		if (classs == 0)
			return SUCCESS;
		
		classCards = cardRepository.findAllCollectibleByClass(classs, new Sort("cost"));
		neutralCards = cardRepository.findAllCollectibleByClass(0, new Sort("cost"));
		return SUCCESS;
	}
	
	public int getClasss() {
		return classs;
	}
	
	public void setClasss(int classs) {
		this.classs = classs;
	}
	
	public CardRepository getCardRepository() {
		return cardRepository;
	}
	
	public void setCardRepository(CardRepository cardRepository) {
		this.cardRepository = cardRepository;
	}
	
	public List<Card> getClassCards() {
		return classCards;
	}
	
	public List<Card> getNeutralCards() {
		return neutralCards;
	}

}
