package com.chocolate.chocolateQuest.entity.mob.registry;

import com.chocolate.chocolateQuest.entity.mob.EntityHumanSpecter;
import com.chocolate.chocolateQuest.entity.mob.EntitySpecterBoss;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class MobSpecter extends DungeonMonstersBase {

   public String getEntityName() {
      return "specter";
   }

   public int getFlagId() {
      return 12;
   }

   public String getRegisteredEntityName() {
      return "chocolateQuest.specter";
   }

   public Entity getBoss(World world, int x, int y, int z) {
      return new EntitySpecterBoss(world);
   }

   public Entity getEntity(World world, int x, int y, int z) {
      return new EntityHumanSpecter(world);
   }

   public String getTeamName() {
      return "mob_undead";
   }

   public double getHealth() {
      return 35.0D;
   }

   public double getRange() {
      return 25.0D;
   }

   public int getColor() {
      return 1301760;
   }

   public int getDifficulty(World world, int posX, int posY, int posZ) {
      return 3;
   }
}
