package com.hearthintellect.mongo.repo;

import org.springframework.data.repository.CrudRepository;

import com.hearthintellect.model.Adventure;

public class AdventureRepository implements CrudRepository<Adventure, Integer> {

	public <S extends Adventure> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public <S extends Adventure> Iterable<S> save(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	public Adventure findOne(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	public Iterable<Adventure> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterable<Adventure> findAll(Iterable<Integer> ids) {
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

	public void delete(Adventure entity) {
		// TODO Auto-generated method stub
		
	}

	public void delete(Iterable<? extends Adventure> entities) {
		// TODO Auto-generated method stub
		
	}

	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

}
