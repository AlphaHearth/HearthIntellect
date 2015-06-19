package com.hearthintellect.mongo.repo;

import static org.springframework.data.mongodb.core.query.Criteria.*;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.Repository;

import com.hearthintellect.model.Card;
import com.hearthintellect.mongo.repo.base.BaseRepository;

public class CardRepository extends BaseRepository implements Repository<Card, Integer> {

	public Card findOne(Integer cardId) {
		logger.info("Finding card with cardId=" + cardId);
		Card result = mongoOps.findOne(new Query(where("cardId").is(cardId)), Card.class);
		logger.debug("Fetched result: " + result.toString());
		return result;
	}

	public List<Card> findAll(Pageable pageable) {
		logger.info("Fecthing cards on page " + pageable.getPageNumber() + "...");
		List<Card> result = mongoOps.find(new Query().with(pageable), Card.class);
		logger.debug("Fetching result: ");
		for (Card card : result) {
			logger.debug(card.toString());
		}
		return result;
	}
	
	public List<Card> findAll(Pageable pageable, Sort sort) {
		logger.info("Fecthing cards on page " + pageable.getPageNumber() + " in designated order...");
		List<Card> result = mongoOps.find(new Query().with(sort).with(pageable), Card.class);
		logger.debug("Fetching result: ");
		for (Card card : result) {
			logger.debug(card.toString());
		}
		return result;
	}

	public List<Card> findAllByType(int typeId, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Card> findAllByType(int typeId, Pageable pageable, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Card> findAllByClass(int classId, Pageable pageable) {
		// TODO
		return null;
	}
	
	public List<Card> findAllCollectibleByClass(int classId, Sort sort) {
		logger.info("Fecthing cards of class " + classId + " in designated order...");
		List<Card> result = mongoOps.find(
				new Query(
						where("classs").is(classId == 0 ? null : classId)
							.and("collectible").is(1)
							.and("type").nin(3, 10)
					).with(sort),
				Card.class);
		logger.debug("Fetching result: ");
		for (Card card : result) {
			logger.debug(card.toString());
		}
		return result;
	}
	
	public List<Card> findAllByClass(int classId, Pageable pageable, Sort sort) {
		// TODO
		return null;
	}
	
	public List<Card> findAllByQuality(int qualityId, Pageable pageable) {
		// TODO
		return null;
	}
	
	public List<Card> findAllByQuality(int qualityId, Pageable pageable, Sort sort) {
		// TODO
		return null;
	}
	
	public List<Card> findAllByRace(int raceId, Pageable pageable) {
		// TODO
		return null;
	}
	
	public List<Card> findAllByRace(int raceId, Pageable pageable, Sort sort) {
		// TODO
		return null;
	}

}
