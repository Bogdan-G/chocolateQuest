package com.chocolate.chocolateQuest.entity.ai;


public enum EnumAiCombat {

   OFFENSIVE("OFFENSIVE", 0, "ai.offensive.name"),
   DEFENSIVE("DEFENSIVE", 1, "ai.defensive.name"),
   EVASIVE("EVASIVE", 2, "ai.evasive.name"),
   FLEE("FLEE", 3, "ai.flee.name"),
   BACKSTAB("BACKSTAB", 4, "ai.backstab.name");
   public String ainame;
   // $FF: synthetic field
   private static final EnumAiCombat[] $VALUES = new EnumAiCombat[]{OFFENSIVE, DEFENSIVE, EVASIVE, FLEE, BACKSTAB};


   private EnumAiCombat(String var1, int var2, String name) {
      this.ainame = name;
   }

}
