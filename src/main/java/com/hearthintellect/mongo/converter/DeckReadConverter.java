package com.hearthintellect.mongo.converter;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;

import com.hearthintellect.model.Deck;
import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

public class DeckReadConverter implements Converter<DBObject, Deck> {

	@Override
	public Deck convert(DBObject source) {
		Deck result = new Deck();
		
		result.setId(((ObjectId) source.get("_id")).toHexString());
		result.setDeckId((int)((Double) source.get("deckId")).doubleValue());
		result.setName((String) source.get("name"));
		Object[] tempArr = ((BasicDBList)source.get("rating")).toArray();
		result.setGoodRating((int)((Double) tempArr[0]).doubleValue());
		result.setBadRating((int)((Double) tempArr[1]).doubleValue());
		
		BasicDBList cardListInDB = (BasicDBList) source.get("cards");
		List<Deck.DeckEntry> cardList = new LinkedList<Deck.DeckEntry>();
		Iterator<Object> i = cardListInDB.iterator();
		while (i.hasNext()) {
			DBObject currentObject = (DBObject) i.next();
			Deck.DeckEntry currentEntry = result.new DeckEntry();
			currentEntry.setCardId((int)((Double) currentObject.get("card")).doubleValue());
			currentEntry.setCount((int)((Double) currentObject.get("count")).doubleValue());
			cardList.add(currentEntry);
		}
		result.setCards(cardList);
		
		return result;
	}

}
