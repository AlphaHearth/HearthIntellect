package com.hearthintellect.dao.morphia;

import com.hearthintellect.dao.DeckRepository;
import com.hearthintellect.model.Deck;
import com.hearthintellect.model.HeroClass;

import java.util.Iterator;

public class DeckRepositoryImpl extends MorphiaRepository<Deck> implements DeckRepository {
    @Override
    public Iterator<Deck> findAllByClass(HeroClass heroClass) {
        return createQuery().field("classs").equal(heroClass).iterator();
    }

    @Override
    public void like() {

    }

    @Override
    public void dislike() {

    }

    @Override
    protected Class<Deck> getEntityClass() {
        return Deck.class;
    }
}
