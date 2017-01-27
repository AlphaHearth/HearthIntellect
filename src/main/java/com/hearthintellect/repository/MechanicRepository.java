package com.hearthintellect.repository;

import com.hearthintellect.model.Mechanic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MechanicRepository extends CrudRepository<Mechanic, String>,
        PagingAndSortingRepository<Mechanic, String> {}
