package com.hearthintellect.mongo.converter;

import org.springframework.core.convert.converter.Converter;

import com.hearthintellect.model.Card;
import com.mongodb.DBObject;

public class CardReadConverter implements Converter<DBObject, Card> {

	public Card convert(DBObject source) {
		Card card = new Card();
		
		card.setId((String) source.get("_id"));
		card.setCardId((Integer) source.get("id"));
		card.setName((String) source.get("name"));
		card.setDescription((String) source.get("description"));
		card.setIconUrl((String) source.get("icon"));
		card.setImageUrl((String) source.get("image"));
		card.setCollectible((Boolean) source.get("collectible"));
		card.setHealth((Integer) source.get("health"));
		card.setAttack((Integer) source.get("attack"));
		card.setCost((Integer) source.get("cost"));
		card.setSetId((Integer) source.get("set"));
		card.setTypeId((Integer) source.get("type"));
		card.setClassId((Integer) source.get("classs"));
		card.setFactionId((Integer) source.get("faction"));
		card.setQualityId((Integer) source.get("quality"));
		card.setRaceId((Integer) source.get("race"));
		card.setMechanics((int[]) source.get("mechanics"));
		
		return card;
	}

}
