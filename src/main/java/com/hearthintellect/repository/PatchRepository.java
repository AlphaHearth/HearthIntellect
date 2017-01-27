package com.hearthintellect.repository;

import com.hearthintellect.model.Patch;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PatchRepository extends CrudRepository<Patch, Integer>, PagingAndSortingRepository<Patch, Integer> {}
