package com.hearthintellect.model;

/**
 * Entity for Card
 * @author Robert Peng
 */
public class Card extends BaseCollection {
	
	private int cardId;
	private String name;
	private String description;
	
	private boolean collectible;
	
	private String iconUrl;
	private String imageUrl;
	
	private int setId;
	private int typeId;
	private int factionId;
	private int classId;
	private int qualityId;
	private int raceId;
	
	private int health;
	private int attack;
	private int cost;
	
	private int[] mechanics;
	
	// only exists for cards with type = 3 (hero cards)
	private int heroPower;

	public int getCardId() {
		return cardId;
	}

	public void setCardId(int cardId) {
		this.cardId = cardId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean getCollectible() {
		return collectible;
	}

	public void setCollectible(boolean collectible) {
		this.collectible = collectible;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getSetId() {
		return setId;
	}

	public void setSetId(int setId) {
		this.setId = setId;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public int getFactionId() {
		return factionId;
	}

	public void setFactionId(int factionId) {
		this.factionId = factionId;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public int getQualityId() {
		return qualityId;
	}

	public void setQualityId(int qualityId) {
		this.qualityId = qualityId;
	}

	public int getRaceId() {
		return raceId;
	}

	public void setRaceId(int raceId) {
		this.raceId = raceId;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int[] getMechanics() {
		return mechanics;
	}

	public void setMechanics(int[] mechanics) {
		this.mechanics = mechanics;
	}

	public int getHeroPower() {
		return heroPower;
	}

	public void setHeroPower(int heroPower) {
		this.heroPower = heroPower;
	}
	
}
