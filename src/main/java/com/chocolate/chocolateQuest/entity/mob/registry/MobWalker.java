package com.chocolate.chocolateQuest.entity.mob.registry;

import com.chocolate.chocolateQuest.entity.mob.EntityHumanWalker;
import com.chocolate.chocolateQuest.entity.mob.EntityWalkerBoss;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class MobWalker extends DungeonMonstersBase {

   public String getEntityName() {
      return "walker";
   }

   public int getFlagId() {
      return 10;
   }

   public String getRegisteredEntityName() {
      return "chocolateQuest.abyssWalker";
   }

   public Entity getBoss(World world, int x, int y, int z) {
      return new EntityWalkerBoss(world);
   }

   public Entity getEntity(World world, int x, int y, int z) {
      return new EntityHumanWalker(world);
   }

   public String getTeamName() {
      return "mob_walker";
   }

   public double getHealth() {
      return 35.0D;
   }

   public double getAttack() {
      return 2.0D;
   }

   public double getRange() {
      return 30.0D;
   }

   public int getColor() {
      return 8339378;
   }

   public int getDifficulty(World world, int posX, int posY, int posZ) {
      return 4;
   }
}
