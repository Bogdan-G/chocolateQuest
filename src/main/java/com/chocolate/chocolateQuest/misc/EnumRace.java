package com.chocolate.chocolateQuest.misc;


public enum EnumRace {

   HUMAN("HUMAN", 0, 0, 1.0F, "Human"),
   DWARF("DWARF", 1, 1, 0.5F, "Dwarf"),
   ORC("ORC", 2, 2, 1.0F, "Orc"),
   TRITON("TRITON", 3, 3, 1.0F, "Triton"),
   MINOTAUR("MINOTAUR", 4, 4, 1.0F, "Minotaur"),
   SKELETON("SKELETON", 5, 5, 1.0F, "Skeleton"),
   SPECTER("SPECTER", 6, 6, 1.0F, "Specter"),
   MONKEY("MONKEY", 7, 7, 1.0F, "Monkey"),
   GOLEM("GOLEM", 8, 8, 1.0F, "Golem");
   int model;
   float size;
   String name;
   // $FF: synthetic field
   private static final EnumRace[] $VALUES = new EnumRace[]{HUMAN, DWARF, ORC, TRITON, MINOTAUR, SKELETON, SPECTER, MONKEY, GOLEM};


   private EnumRace(String var1, int var2, int model, float size, String name) {
      this.model = model;
      this.size = size;
      this.name = name;
   }

   public static String[] getNames() {
      EnumRace[] states = values();
      String[] names = new String[states.length];

      for(int i = 0; i < states.length; ++i) {
         names[i] = states[i].name;
      }

      return names;
   }

}
