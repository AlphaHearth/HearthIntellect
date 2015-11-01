package com.hearthintellect.dao;

import com.hearthintellect.model.*;
import com.hearthintellect.model.Class;

import java.util.Iterator;

public interface CardRepository {

    Card findById(long cardId);

    Iterator<Card> findAllByName(String name);

    Iterator<Card> findAllByClass(Class _class);

    Iterator<Card> findAllByRace(Race race);

    Iterator<Card> findAllBySet(CardSet set);

    Iterator<Card> findAllByQuality(CardQuality quality);

    Iterator<Card> findAllByMechanic(Mechanic mechanic);

    void save(Card card);
    void update(Card card);
    void remove(Card card);

    // TODO order by class/race/set/quality/craft/disenchant

}
