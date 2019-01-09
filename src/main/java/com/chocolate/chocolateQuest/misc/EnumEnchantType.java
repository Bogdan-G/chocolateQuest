package com.chocolate.chocolateQuest.misc;


public enum EnumEnchantType {

   ENCHANT("ENCHANT", 0, "Enchantment"),
   BLACKSMITH("BLACKSMITH", 1, "Blacksmith"),
   GUNSMITH("GUNSMITH", 2, "Gunsmith"),
   STAVES("STAVES", 3, "Staff enchantment"),
   TAILOR("TAILOR", 4, "Armor enchantment");
   public String name;
   // $FF: synthetic field
   private static final EnumEnchantType[] $VALUES = new EnumEnchantType[]{ENCHANT, BLACKSMITH, GUNSMITH, STAVES, TAILOR};


   private EnumEnchantType(String var1, int var2, String name) {
      this.name = name;
   }

   public static String[] getNames() {
      EnumEnchantType[] states = values();
      String[] names = new String[states.length];

      for(int i = 0; i < states.length; ++i) {
         names[i] = states[i].name;
      }

      return names;
   }

}
