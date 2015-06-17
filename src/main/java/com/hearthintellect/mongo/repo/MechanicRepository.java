package com.hearthintellect.mongo.repo;

import org.springframework.data.repository.CrudRepository;

import com.hearthintellect.model.Mechanic;

public class MechanicRepository implements CrudRepository<Mechanic, Integer> {

	public <S extends Mechanic> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public <S extends Mechanic> Iterable<S> save(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	public Mechanic findOne(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	public Iterable<Mechanic> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterable<Mechanic> findAll(Iterable<Integer> ids) {
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

	public void delete(Mechanic entity) {
		// TODO Auto-generated method stub
		
	}

	public void delete(Iterable<? extends Mechanic> entities) {
		// TODO Auto-generated method stub
		
	}

	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

}
