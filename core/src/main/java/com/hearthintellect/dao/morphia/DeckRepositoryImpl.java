package com.hearthintellect.dao.morphia;

import com.hearthintellect.dao.DeckRepository;
import com.hearthintellect.model.Deck;
import com.hearthintellect.model.HeroClass;
import com.hearthintellect.model.User;
import com.hearthintellect.util.Page;
import org.mongodb.morphia.query.Query;

import java.time.LocalDateTime;
import java.util.Iterator;

public class DeckRepositoryImpl extends MorphiaRepository<Deck> implements DeckRepository {
    @Override
    public Iterator<Deck> findAllByClass(HeroClass heroClass, String order, Page page) {
        Query<Deck> query = createQuery().field("classs").equal(heroClass);
        processOrderAndPage(query, order, page);

        return query.iterator();
    }

    @Override
    public Iterator<Deck> findAllByUser(User user, String order, Page page) {
        Query<Deck> query = createQuery().field("author").equal(user.getId());
        processOrderAndPage(query, order, page);

        return query.iterator();
    }

    @Override
    public Iterator<Deck> findAll(String order, Page page) {
        Query<Deck> query = createQuery();
        processOrderAndPage(query, order, page);

        return query.iterator();
    }

    @Override
    public void updateWithModifiedDate(Deck deck) {
        deck.setLastModified(LocalDateTime.now());

        update(deck);
    }

    @Override
    protected Class<Deck> getEntityClass() {
        return Deck.class;
    }
}
