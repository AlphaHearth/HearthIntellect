package com.hearthintellect.mongo.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hearthintellect.model.Deck;

public class DeckRepository implements PagingAndSortingRepository<Deck, Integer> {

	public <S extends Deck> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public <S extends Deck> Iterable<S> save(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	public Deck findOne(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	public Iterable<Deck> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterable<Deck> findAll(Iterable<Integer> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	public void delete(Deck entity) {
		// TODO Auto-generated method stub
		
	}

	public void delete(Iterable<? extends Deck> entities) {
		// TODO Auto-generated method stub
		
	}

	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	public Iterable<Deck> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	public Page<Deck> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
