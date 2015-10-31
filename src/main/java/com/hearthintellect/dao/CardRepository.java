package com.hearthintellect.dao;

import com.hearthintellect.model.*;
import com.hearthintellect.model.Class;

public interface CardRepository {

    Card findById(long cardId);

    Iterable<Card> findAllByName(String name);

    Iterable<Card> findAllByClass(Class _class);

    Iterable<Card> findAllByRace(Race race);

    Iterable<Card> findAllBySet(CardSet set);

    Iterable<Card> findAllByQuality(CardQuality quality);

    Iterable<Card> findAllByMechanic(Mechanic mechanic);

    void save(Card card);
    void update(Card card);
    void remove(Card card);

    // TODO order by class/race/set/quality/craft/disenchant

}
