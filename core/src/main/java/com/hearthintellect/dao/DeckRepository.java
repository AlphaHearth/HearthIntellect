package com.hearthintellect.dao;

import com.hearthintellect.model.HeroClass;
import com.hearthintellect.model.Deck;
import com.hearthintellect.model.User;
import com.hearthintellect.util.Page;

import java.util.Iterator;

public interface DeckRepository extends Repository<Deck> {

    default Iterator<Deck> findAllByClass(HeroClass heroClass) {
        return findAllByClass(heroClass, null, null);
    }
    Iterator<Deck> findAllByClass(HeroClass heroClass, String order, Page page);

    default Iterator<Deck> findAllByUser(User user) {
        return findAllByUser(user, null, null);
    }
    Iterator<Deck> findAllByUser(User user, String order, Page page);

    default Iterator<Deck> findAll() {
        return findAll(null, null);
    }
    Iterator<Deck> findAll(String order, Page page);

    void updateWithModifiedDate(Deck deck);
}
