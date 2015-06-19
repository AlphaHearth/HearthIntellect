package com.hearthintellect.mongo.converter;

import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;

import com.hearthintellect.model.Card;
import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

public class CardReadConverter implements Converter<DBObject, Card> {

	public Card convert(DBObject source) {
		Card card = new Card();
		
		Object tempResult = null;
		card.setId(((ObjectId) source.get("_id")).toHexString());
		card.setCardId((int)((Double) source.get("cardId")).doubleValue());
		card.setName((String) source.get("name"));
		card.setDescription((String) source.get("description"));
		card.setIconUrl((String) source.get("icon"));
		card.setImageUrl((String) source.get("image"));
		tempResult = source.get("collectible");
		if (tempResult == null) {		// Non-collectible
			card.setCollectible(0);
		} else {
			card.setCollectible((int)((Double) tempResult).doubleValue());
		}
		tempResult = source.get("health"); // Spell does not have health
		if (tempResult == null) {
			card.setHealth(-1);
		} else {
			card.setHealth((int)((Double) source.get("health")).doubleValue());
		}
		tempResult = source.get("attack"); // Hero does not have attack
		if (tempResult == null) {
			card.setAttack(-1);
		} else {
			card.setAttack((int)((Double) tempResult).doubleValue());
		}
		card.setCost((int)((Double) source.get("cost")).doubleValue());
		card.setSetId((int)((Double) source.get("set")).doubleValue());
		card.setTypeId((int)((Double) source.get("type")).doubleValue());
		tempResult = source.get("classs"); // Neutral cards have no class
		if (tempResult == null) {
			card.setClassId(0);
		} else {
			card.setClassId((int)((Double) tempResult).doubleValue());
		}
		tempResult = source.get("faction");	// Some cards have no faction, but I still don't know what faction means
		if (tempResult == null) {
			card.setFactionId(0);
		} else {
			card.setFactionId((int)((Double) tempResult).doubleValue());
		}
		card.setQualityId((int)((Double) source.get("quality")).doubleValue());
		tempResult = source.get("race"); // Not all minions have race
		if (tempResult == null) {
			card.setRaceId(0);
		} else {
			card.setRaceId((int)((Double) tempResult).doubleValue());
		}
		tempResult = source.get("mechanics");
		if (tempResult == null) {
			card.setMechanics(null);
		} else {
			Object[] fromArr = ((BasicDBList) tempResult).toArray();
			int[] resultArr= new int[fromArr.length];
			for (int i = 0; i < fromArr.length; i++)
				resultArr[i] = (int)((Double) fromArr[i]).doubleValue();
			card.setMechanics(resultArr);
		}
		
		return card;
	}

}
