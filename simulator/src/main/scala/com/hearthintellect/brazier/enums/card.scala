package com.hearthintellect.brazier.enums

object Race extends Enumeration {
  val INVALID = Value(0)
  val BLOODELF = Value(1)
  val DRAENEI = Value(2)
  val DWARF = Value(3)
  val GNOME = Value(4)
  val GOBLIN = Value(5)
  val HUMAN = Value(6)
  val NIGHTELF = Value(7)
  val ORC = Value(8)
  val TAUREN = Value(9)
  val TROLL = Value(10)
  val UNDEAD = Value(11)
  val WORGEN = Value(12)
  val GOBLIN2 = Value(13)
  val MURLOC = Value(14)
  val DEMON = Value(15)
  val SCOURGE = Value(16)
  val MECHANICAL = Value(17)
  val ELEMENTAL = Value(18)
  val OGRE = Value(19)
  val BEAST = Value(20)
  val TOTEM = Value(21)
  val NERUBIAN = Value(22)
  val PIRATE = Value(23)
  val DRAGON = Value(24)

  // Alias
  val PET = BEAST
}

object Rarity extends Enumeration {
  val INVALID = Value(0)
  val COMMON = Value(1)
  val FREE = Value(2)
  val RARE = Value(3)
  val EPIC = Value(4)
  val LEGENDARY = Value(5)
}

object Faction extends Enumeration {
  val INVALID = Value(0)
  val HORDE = Value(1)
  val ALLIANCE = Value(2)
  val NEUTRAL = Value(3)
}

object CardType extends Enumeration {
  val INVALID = Value(0)
  val GAME = Value(1)
  val PLAYER = Value(2)
  val HERO = Value(3)
  val MINION = Value(4)
  val SPELL = Value(5)
  val ENCHANTMENT = Value(6)
  val WEAPON = Value(7)
  val ITEM = Value(8)
  val TOKEN = Value(9)
  val HERO_POWER = Value(10)

  // Renamed
  val ABILITY = SPELL
}

object CardSet extends Enumeration {
  val INVALID = Value(0)
  val TEST_TEMPORARY = Value(1)
  val CORE = Value(2)
  val EXPERT1 = Value(3)
  val REWARD = Value(4)
  val MISSIONS = Value(5)
  val DEMO = Value(6)
  val NONE = Value(7)
  val CHEAT = Value(8)
  val BLANK = Value(9)
  val DEBUG_SP = Value(10)
  val PROMO = Value(11)
  val NAXX = Value(12)
  val GVG = Value(13)
  val BRM = Value(14)
  val TGT = Value(15)
  val CREDITS = Value(16)
  val HERO_SKINS = Value(17)
  val TB = Value(18)
  val SLUSH = Value(19)
  val LOE = Value(20)

  // Aliased from the original enums
  val FP1 = Value(12)
  val PE1 = Value(13)

  // Renamed
  val FP2 = BRM
  val PE2 = TGT
}

object CardClass extends Enumeration {
  val INVALID = Value(0)
  val DEATHKNIGHT = Value(1)
  val DRUID = Value(2)
  val HUNTER = Value(3)
  val MAGE = Value(4)
  val PALADIN = Value(5)
  val PRIEST = Value(6)
  val ROGUE = Value(7)
  val SHAMAN = Value(8)
  val WARLOCK = Value(9)
  val WARRIOR = Value(10)
  val DREAM = Value(11)
  val COUNT = Value(12)

  // Alias
  val Neutral = INVALID
}
