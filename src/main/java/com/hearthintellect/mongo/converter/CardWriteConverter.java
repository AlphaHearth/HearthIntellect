package com.hearthintellect.mongo.converter;

import org.springframework.core.convert.converter.Converter;

import com.hearthintellect.model.Card;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class CardWriteConverter implements Converter<Card, DBObject> {

	public DBObject convert(Card source) {
		DBObject dbo = new BasicDBObject();
		
		dbo.put("_id", source.getId());
		dbo.put("id", source.getCardId());
		dbo.put("name", source.getName());
		dbo.put("description", source.getDescription());
		dbo.put("icon", source.getIconUrl());
		dbo.put("image", source.getImageUrl());
		dbo.put("collectible", source.getCollectible());
		dbo.put("health", source.getHealth());
		dbo.put("attack", source.getAttack());
		dbo.put("cost", source.getCost());
		dbo.put("set", source.getSetId());
		dbo.put("type", source.getTypeId());
		dbo.put("classs", source.getClassId());
		dbo.put("faction", source.getFactionId());
		dbo.put("quality", source.getQualityId());
		dbo.put("race", source.getRaceId());
		dbo.put("mechanics", source.getMechanics());
		
		return dbo;
	}

}
