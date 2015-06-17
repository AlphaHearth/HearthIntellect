package com.hearthintellect.mongo.repo.base;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.MongoOperations;

/**
 * Parent class for all repositories, containing those shared methods
 * @author Robert Peng
 */
public abstract class BaseRepository {

	protected Logger logger = Logger.getLogger(getClass());
	protected MongoOperations mongoOps;
	
	public MongoOperations getMongoOps() {
		return mongoOps;
	}
	
	public void setMongoOps(MongoOperations mongoOps) {
		this.mongoOps = mongoOps;
	}
	
}
