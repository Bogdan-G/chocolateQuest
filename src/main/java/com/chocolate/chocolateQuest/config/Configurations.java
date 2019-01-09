package com.chocolate.chocolateQuest.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class Configurations {

   public int dungeonSeparation;
   public boolean dungeonsInFlat;
   public int distToDespawn;
   public boolean updateData;
   public int potionMinePreventionID = 31;
   public boolean useInstantDungeonBuilder = true;
   public int builderSpeed = 1000;


   public void load(Configuration config) {
      config.load();
      Property prop = config.get("general", "dungeonSeparation", 10);
      prop.comment = "Distance in chunks(16 blocks) from dungeon to dungeon";
      this.dungeonSeparation = prop.getInt(10);
      prop = config.get("general", "dungeonsInFlat", false);
      prop.comment = "Generate dungeons in flat maps";
      this.dungeonsInFlat = prop.getBoolean(false);
      prop = config.get("general", "distanceToDespawn", 64);
      prop.comment = "Distance in blocks to the closest player to despawn mobs";
      this.distToDespawn = prop.getInt(64);
      prop = config.get("general", "extractData", true);
      prop.comment = "If true the mod will update the Chocolate folder on startup";
      this.updateData = prop.getBoolean();
      prop = config.get("general", "potionMinePreventionID", this.potionMinePreventionID);
      prop.comment = "Mining Prevention Potion ID";
      this.potionMinePreventionID = prop.getInt(this.potionMinePreventionID);
      prop = config.get("general", "useInstantDungeonBuilder", this.useInstantDungeonBuilder);
      prop.comment = "If false dungeons will be build when the player get nearbu intead of with world generation(WIP)";
      this.useInstantDungeonBuilder = prop.getBoolean();
      prop = config.get("general", "dungeonBuilderSpeed", this.builderSpeed);
      prop.comment = "If the instant dungeon builder is disabled, the dungeons will be buit at a rate of \"dungeonBuilderSpeed\" blocks/tick";
      this.builderSpeed = prop.getInt();
      config.save();
   }
}
