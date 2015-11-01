package com.hearthintellect.dao.mongo;

import com.hearthintellect.dao.CardRepository;
import com.hearthintellect.model.*;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

public class CardRepositoryImpl implements CardRepository {
    private static final Logger LOG = LoggerFactory.getLogger(CardRepositoryImpl.class);

    private Datastore datastore;

    public CardRepositoryImpl(Datastore datastore) {
        this.datastore = datastore;
    }

    private Query<Card> createQuery() {
        return datastore.createQuery(Card.class);
    }

    @Override
    public Card findById(long cardId) {
        return createQuery().field("_id").equal(cardId).get();
    }

    @Override
    public Iterator<Card> findAllByName(String name) {
        return createQuery().field("name").contains(name).iterator();
    }

    @Override
    public Iterator<Card> findAllByClass(com.hearthintellect.model.Class _class) {
        return createQuery().field("class").equal(_class).iterator();
    }

    @Override
    public Iterator<Card> findAllByRace(Race race) {
        return createQuery().field("race").equal(race).iterator();
    }

    @Override
    public Iterator<Card> findAllBySet(CardSet set) {
        return createQuery().field("set").equal(set).iterator();
    }

    @Override
    public Iterator<Card> findAllByQuality(CardQuality quality) {
        return createQuery().field("quality").equal(quality).iterator();
    }

    @Override
    public Iterator<Card> findAllByMechanic(Mechanic mechanic) {
        return createQuery().field("mechanics").hasThisOne(mechanic).iterator();
    }

    @Override
    public void save(Card card) {
        datastore.save(card);
    }

    @Override
    public void update(Card card) {
        datastore.updateFirst(
            createQuery().field("_id").equal(card.getCardId()),
            card,
            false
        );
    }

    @Override
    public void remove(Card card) {
        datastore.delete(card);
    }
}
