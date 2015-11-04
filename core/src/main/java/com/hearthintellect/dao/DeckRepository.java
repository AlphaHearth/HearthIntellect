package com.hearthintellect.dao;

import com.hearthintellect.model.HeroClass;
import com.hearthintellect.model.Deck;

public interface DeckRepository {

    Deck findById(long deckId);

    Iterable<Deck> findAllByClass(HeroClass heroClass);

    void like();
    void dislike();

    // TODO Order by rating/patch
}
