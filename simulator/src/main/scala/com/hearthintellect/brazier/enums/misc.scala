package com.hearthintellect.brazier.enums

object Booster extends Enumeration {
  val INVALID = Value(0)
  val CLASSIC = Value(1)
  val GOBLINS_VS_GNOMES = Value(9)
  val THE_GRAND_TOURNAMENT = Value(10)
}

object Type extends Enumeration {
  val UNKNOWN = Value(0)
  val BOOL = Value(1)
  val NUMBER = Value(2)
  val COUNTER = Value(3)
  val ENTITY = Value(4)
  val PLAYER = Value(5)
  val TEAM = Value(6)
  val ENTITY_DEFINITION = Value(7)
  val STRING = Value(8)

  // Not present at the time
  val LOCSTRING = Value(-2)
}

object Locale extends Enumeration {
  val UNKNOWN = Value(-1)
  val enUS = Value(0)
  val enGB = Value(1)
  val frFR = Value(2)
  val deDE = Value(3)
  val koKR = Value(4)
  val esES = Value(5)
  val esMX = Value(6)
  val ruRU = Value(7)
  val zhTW = Value(8)
  val zhCN = Value(9)
  val itIT = Value(10)
  val ptBR = Value(11)
  val plPL = Value(12)
  val ptPT = Value(13)
  val jaJP = Value(14)
}

