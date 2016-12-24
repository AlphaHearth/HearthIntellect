package com.hearthintellect.dao.morphia;

import com.hearthintellect.dao.CardRepository;
import com.hearthintellect.model.Card;
import com.hearthintellect.model.HeroClass;
import com.hearthintellect.model.Mechanic;
import com.hearthintellect.utils.Page;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

public class CardRepositoryImpl extends MorphiaRepository<String, Card> implements CardRepository {
    private static final Logger LOG = LoggerFactory.getLogger(CardRepositoryImpl.class);

    @Override
    protected Class<Card> getEntityClass() {
        return Card.class;
    }

    @Override
    public Iterator<Card> findAll(String order, Page page) {
        Query<Card> query = createQuery();
        processOrderAndPage(query, order, page);

        return query.iterator();
    }

    @Override
    public Iterator<Card> findAllByName(String name, String order, Page page) {
        Query<Card> query = createQuery().field("name").equal(name);
        processOrderAndPage(query, order, page);

        return query.iterator();
    }

    @Override
    public Iterator<Card> findAllByClass(HeroClass heroClass, String order, Page page) {
        Query<Card> query = createQuery().field("class").equal(heroClass);
        processOrderAndPage(query, order, page);

        return query.iterator();
    }

    @Override
    public Iterator<Card> findAllByRace(Card.Race race, String order, Page page) {
        Query<Card> query = createQuery().field("race").equal(race);
        processOrderAndPage(query, order, page);

        return query.iterator();
    }

    @Override
    public Iterator<Card> findAllBySet(Card.Set set, String order, Page page) {
        Query<Card> query = createQuery().field("set").equal(set);
        processOrderAndPage(query, order, page);

        return query.iterator();
    }

    @Override
    public Iterator<Card> findAllByQuality(Card.Quality quality, String order, Page page) {
        Query<Card> query = createQuery().field("quality").equal(quality);
        processOrderAndPage(query, order, page);

        return query.iterator();
    }

    @Override
    public Iterator<Card> findAllByMechanic(Mechanic mechanic, String order, Page page) {
        Query<Card> query = createQuery().field("mechanics").hasThisElement(mechanic);
        processOrderAndPage(query, order, page);

        return query.iterator();
    }

    @Override
    public Iterator<Card> findAllByMechanicId(int mechanicId, String order, Page page) {
        Query<Card> query = createQuery().field("mechanics").hasThisElement(mechanicId);
        processOrderAndPage(query, order, page);

        return query.iterator();
    }
}
