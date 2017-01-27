package com.hearthintellect.repository;

import com.hearthintellect.model.Card;
import com.hearthintellect.model.HeroClass;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CardRepository extends PagingAndSortingRepository<Card, String> {

    default List<Card> findByClass(HeroClass heroClass) {
        return findByClass(heroClass, null);
    }

    List<Card> findByClass(HeroClass heroClass, Pageable page);

    default List<Card> findByRace(Card.Race race) {
        return findByRace(race, null);
    }

    List<Card> findByRace(Card.Race race, Pageable page);

    default List<Card> findBySet(Card.Set set) {
        return findBySet(set, null);
    }

    List<Card> findBySet(Card.Set set, Pageable page);

    default List<Card> findByQuality(Card.Quality quality) {
        return findByQuality(quality, null);
    }

    List<Card> findByQuality(Card.Quality quality, Pageable page);

    default List<Card> findByMechanic(String mechanicId) {
        return findByMechanics_MechanicIdContains(mechanicId);
    }

    List<Card> findByMechanics_MechanicIdContains(String mechanicId);

}
