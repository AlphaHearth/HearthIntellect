package com.hearthintellect.dao;

import com.hearthintellect.model.HeroClass;
import com.hearthintellect.model.Deck;
import com.hearthintellect.model.DeckType;

public interface DeckRepository {

    Deck findById(long deckId);

    Iterable<Deck> findAllByType(DeckType type);

    Iterable<Deck> findAllByClass(HeroClass _Hero_class);

    void like();
    void dislike();

    // TODO Order by rating/patch
}
