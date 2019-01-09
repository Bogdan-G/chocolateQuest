package com.chocolate.chocolateQuest.misc;


public enum EnumVoice {

   DEFAULT("DEFAULT", 0, "none", "game.neutral.hurt", "game.neutral.die"),
   VILLAGER("VILLAGER", 1, "mob.villager.idle", "mob.villager.hit", "mob.villager.death"),
   BAT("BAT", 2, "mob.bat.idle", "mob.bat.hurt", "mob.bat.death"),
   CHICKEN("CHICKEN", 3, "mob.chicken.say", "mob.chicken.hurt", "mob.chicken.death"),
   COW("COW", 4, "mob.cow.say", "mob.cow.hurt", "mob.cow.hurt"),
   PIG("PIG", 5, "mob.pig.say", "mob.pig.say", "mob.pig.death"),
   SHEEP("SHEEP", 6, "mob.sheep.say", "mob.sheep.say", "mob.sheep.say"),
   WOLF("WOLF", 7, "mob.wolf.bark", "mob.wolf.hurt", "mob.wolf.death"),
   BLAZE("BLAZE", 8, "mob.blaze.breathe", "mob.blaze.hit", "mob.blaze.death"),
   ENDERMEN("ENDERMEN", 9, "mob.endermen.idle", "mob.endermen.hit", "mob.endermen.death"),
   SILVERFISH("SILVERFISH", 10, "mob.silverfish.say", "mob.silverfish.hit", "mob.silverfish.kill"),
   SKELETON("SKELETON", 11, "mob.skeleton.say", "mob.skeleton.hurt", "mob.skeleton.death"),
   SPIDER("SPIDER", 12, "mob.spider.say", "mob.spider.say", "mob.spider.death"),
   ZOMBIE("ZOMBIE", 13, "mob.zombie.say", "mob.zombie.hurt", "mob.zombie.death"),
   ZOMBIEPIG("ZOMBIEPIG", 14, "mob.zombiepig.zpig", "mob.zombiepig.zpighurt", "mob.zombiepig.zpigdeath"),
   PIRATE("PIRATE", 15, "chocolatequest:pirate_speak", "chocolatequest:pirate_hurt", "chocolatequest:pirate_death"),
   MONKEY("MONKEY", 16, "chocolatequest:monking_speak", "chocolatequest:monking_hurt", "chocolatequest:monking_death"),
   GOBLIN("GOBLIN", 17, "chocolatequest:goblin_speak", "chocolatequest:goblin_hurt", "chocolatequest:goblin_death");
   public String say;
   public String hurt;
   public String death;
   // $FF: synthetic field
   private static final EnumVoice[] $VALUES = new EnumVoice[]{DEFAULT, VILLAGER, BAT, CHICKEN, COW, PIG, SHEEP, WOLF, BLAZE, ENDERMEN, SILVERFISH, SKELETON, SPIDER, ZOMBIE, ZOMBIEPIG, PIRATE, MONKEY, GOBLIN};


   private EnumVoice(String var1, int var2, String say, String hurt, String death) {
      this.say = say;
      this.hurt = hurt;
      this.death = death;
   }

   public static String[] getNames() {
      EnumVoice[] states = values();
      String[] names = new String[states.length];

      for(int i = 0; i < states.length; ++i) {
         names[i] = states[i].toString();
      }

      return names;
   }

   public static EnumVoice getVoice(int selected) {
      EnumVoice[] values = values();
      if(selected >= values.length) {
         selected = 0;
      }

      return values[selected];
   }

}
