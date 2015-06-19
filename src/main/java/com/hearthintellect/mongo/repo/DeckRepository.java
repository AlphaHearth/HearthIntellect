package com.hearthintellect.mongo.repo;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;
import org.springframework.data.mongodb.core.query.Query;

import com.hearthintellect.model.Deck;
import com.hearthintellect.mongo.repo.base.BaseRepository;

public class DeckRepository extends BaseRepository implements Repository<Deck, Integer> {

	public <S extends Deck> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public Deck findOne(Integer deckId) {
		logger.info("Finding deck with deckId=" + deckId);
		Deck result = mongoOps.findOne(query(where("deckId").is(deckId)), Deck.class);
		logger.debug("Fetched result: " + result);
		return result;
	}

	public void delete(Double id) {
		// TODO Auto-generated method stub
		
	}

	public List<Deck> findAll(Pageable pageable) {
		logger.info("Fecthing decks on page " + pageable.getPageNumber() + "...");
		List<Deck> result = mongoOps.find(new Query().with(pageable), Deck.class);
		logger.debug("Fetching result: ");
		for (Deck deck : result) {
			logger.debug(deck.toString());
		}
		return result;
	}

}
