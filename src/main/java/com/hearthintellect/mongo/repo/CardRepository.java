package com.hearthintellect.mongo.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.Repository;

import com.hearthintellect.model.Card;

public class CardRepository implements Repository<Card, Integer> {

	public <S extends Card> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public Card findOne(Integer cardId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterable<Card> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Iterable<Card> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	public Page<Card> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Page<Card> findAll(Sort sort, Pageable pageable) {
		// TODO
		return null;
	}

	public Iterable<Card> findAllByType(int typeId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Iterable<Card> findAllByClass(int classId) {
		// TODO
		return null;
	}
	
	public Iterable<Card> findAllByQuality(int qualityId) {
		// TODO
		return null;
	}
	
	public Iterable<Card> findAllByRace(int raceId) {
		// TODO
		return null;
	}

	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

}
