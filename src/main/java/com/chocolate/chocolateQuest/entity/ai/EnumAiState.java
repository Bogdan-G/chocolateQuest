package com.chocolate.chocolateQuest.entity.ai;

import net.minecraft.util.StatCollector;

public enum EnumAiState {

   FOLLOW("FOLLOW", 0, "ai.follow.name"),
   FORMATION("FORMATION", 1, "ai.formation.name"),
   WARD("WARD", 2, "ai.ward.name"),
   PATH("PATH", 3, "ai.path.name"),
   SIT("SIT", 4, "ai.sit.name"),
   WANDER("WANDER", 5, "ai.wander.name");
   public String ainame;
   // $FF: synthetic field
   private static final EnumAiState[] $VALUES = new EnumAiState[]{FOLLOW, FORMATION, WARD, PATH, SIT, WANDER};


   private EnumAiState(String var1, int var2, String name) {
      this.ainame = name;
   }

   public static String[] getNames() {
      EnumAiState[] states = values();
      String[] names = new String[states.length];

      for(int i = 0; i < states.length; ++i) {
         names[i] = StatCollector.translateToLocal(states[i].ainame);
      }

      return names;
   }

}
