package com.chocolate.chocolateQuest.entity.ai.npcai;

import net.minecraft.util.StatCollector;

public enum EnumNpcAI {

   GUARD("GUARD", 0, "ai.ward.name", "at"),
   STAY("STAY", 1, "ai.stay.name", "at"),
   SIT("SIT", 2, "ai.sit.name", "at"),
   WANDER("WANDER", 3, "ai.wander.name", "at"),
   SLEEP("SLEEP", 4, "ai.sleep.name", "at"),
   PATH("PATH", 5, "ai.path.name", "");
   public String ainame;
   public String join;
   // $FF: synthetic field
   private static final EnumNpcAI[] $VALUES = new EnumNpcAI[]{GUARD, STAY, SIT, WANDER, SLEEP, PATH};


   private EnumNpcAI(String var1, int var2, String name, String join) {
      this.ainame = name;
      this.join = join;
   }

   public static String[] getNames() {
      EnumNpcAI[] states = values();
      String[] names = new String[states.length];

      for(int i = 0; i < states.length; ++i) {
         names[i] = StatCollector.translateToLocal(states[i].ainame);
      }

      return names;
   }

}
