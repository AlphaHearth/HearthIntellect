package com.hearthintellect.dao;

import com.hearthintellect.model.HeroClass;
import com.hearthintellect.model.Deck;

import java.util.Iterator;

public interface DeckRepository extends Repository<Deck> {

    Iterator<Deck> findAllByClass(HeroClass heroClass);

    void like();
    void dislike();

    // TODO Order by rating/patch
}
