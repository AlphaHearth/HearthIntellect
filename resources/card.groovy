import groovy.json.JsonSlurper
import groovy.transform.Canonical

@Canonical
class Card {
  int HHID
  String name
  String effect
  
  int cost
  int attack
  int health
  
  Quality quality
  Type type
  Set set
  Race race
  
  String image
  
  enum Quality { Free, Common, Rare, Epic, Legendary }
  enum Type {  Hero, Minion, Spell, Weapon, HeroPower }
  enum Set { 
    Basic, Classic, Reward, Missions, Promotion, Credits, Naxxramas,
        GoblinsVsGnomes, BlackrockMountain, TheGrandTournament, LeagueOfExplorers,
        WhisperOfOldGods
  }
  enum Race { None, Beast, Demon, Dragon, Mech, Murloc, Pirate, Totem }
  
  def static Card fromJson(json) {
    Card card = new Card()
    card.HHID = json.id
    card.name = json.name
    card.effect = json.description
    card.cost = json.cost
    card.attack = json.attack != null ? json.attack : 0 
    card.health = json.health != null ? json.health : 0
    card.image = json.image
    card.quality = Quality.values()[json.quality - 1]
    switch (json.type) {
      case 4: card.type = Type.Minion; break
      case 5: card.type = Type.Spell; break
      case 7: card.type = Type.Weapon
    }
    switch (json.set) {
      case 2: card.set = Set.Basic; break
      case 3: card.set = Set.Classic; break
      case 20: card.set = Set.LeagueOfExplorers; break
      case 21: card.set = Set.WhisperOfOldGods; break
      case 4: card.set = Set.Reward; break
      case 11: card.set = Set.Promotion; break
      case 12: card.set = Set.Naxxramas; break
      case 13: card.set = Set.GoblinsVsGnomes; break
      case 14: card.set = Set.BlackrockMountain; break
      case 15: card.set = Set.TheGrandTournament
    }
    switch (json.race) {
      case 17: card.race = Race.Mech; break
      case 20: card.race = Race.Beast; break
      case 21: card.race = Race.Totem; break
      case 23: card.race = Race.Pirate; break
      case 24: card.race = Race.Dragon; break
      case 14: card.race = Race.Murloc; break
      case 15: card.race = Race.Demon; break
      default: card.race = Race.None
    }
      
    card
  }
}

def slurper = new JsonSlurper()
def cards = slurper.parse(new File('cardsSource.js'))

for (card in cards)
  println Card.fromJson(card)