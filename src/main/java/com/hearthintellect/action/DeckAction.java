package com.hearthintellect.action;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.hearthintellect.model.Card;
import com.hearthintellect.model.Deck;
import com.hearthintellect.model.Deck.Entry;
import com.hearthintellect.mongo.repo.CardRepository;
import com.hearthintellect.mongo.repo.DeckRepository;
import com.opensymphony.xwork2.ActionSupport;

public class DeckAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(DeckSearchResultAction.class);
	
	private int id = 0;  // Deck ID
	
	private Deck deck;
	private List<CardInDeck> cards;
	
	private CardRepository cardRepository;
	private DeckRepository deckRepository;
	
	public String execute() {
		LOGGER.info("Incoming request from " + ServletActionContext.getRequest().getRemoteAddr()
				+ " at " + ServletActionContext.getRequest().getRequestURI());
		
		if (id == 0)
			return ERROR;
		deck = deckRepository.findOne(id);
		if (deck == null)
			return ERROR;
		List<Entry> cardsId = deck.getCards();
		
		cards = new LinkedList<CardInDeck>();
		for (Entry cardId : cardsId) {
			CardInDeck card = new CardInDeck(cardRepository.findOne(cardId.getCardId()));
			card.setCount(cardId.getCount());
			cards.add(card);
		}
		cards.sort(new Comparator<CardInDeck>() {
			@Override
			public int compare(CardInDeck a, CardInDeck b) {
				if (a.getCost() > b.getCost())
					return 1;
				else if (a.getCost() == b.getCost()) {
					return a.getName().compareTo(b.getName());
				}
				return -1;
			}
		});
		
		return SUCCESS;
	}
	
	public Deck getDeck() {
		return deck;
	}
	
	public List<CardInDeck> getCards() {
		return cards;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setCardRepository(CardRepository cardRepository) {
		this.cardRepository = cardRepository;
	}
	
	public void setDeckRepository(DeckRepository deckRepository) {
		this.deckRepository = deckRepository;
	}
	
	public class CardInDeck extends Card {
		private int count;
		
		public CardInDeck(Card card) {
			this.setCardId(card.getCardId());
			this.setName(card.getName());
			this.setCost(card.getCost());
		}
		
		public int getCount() {
			return count;
		}
		
		public void setCount(int count) {
			this.count = count;
		}
	}

}
