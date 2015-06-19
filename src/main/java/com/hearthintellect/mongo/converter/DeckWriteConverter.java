package com.hearthintellect.mongo.converter;

import org.springframework.core.convert.converter.Converter;

import com.hearthintellect.model.Deck;
import com.mongodb.DBObject;

public class DeckWriteConverter implements Converter<Deck, DBObject> {

	@Override
	public DBObject convert(Deck source) {
		// TODO Auto-generated method stub
		return null;
	}

}
