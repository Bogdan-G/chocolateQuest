package com.chocolate.chocolateQuest.entity.mob.registry;

import com.chocolate.chocolateQuest.entity.mob.EntityHumanPirate;
import com.chocolate.chocolateQuest.entity.mob.EntityPirateBoss;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class MobPirate extends DungeonMonstersBase {

   public String getEntityName() {
      return "pirate";
   }

   public int getFlagId() {
      return 9;
   }

   public String getRegisteredEntityName() {
      return "chocolateQuest.pirate";
   }

   public Entity getBoss(World world, int x, int y, int z) {
      return new EntityPirateBoss(world);
   }

   public Entity getEntity(World world, int x, int y, int z) {
      return new EntityHumanPirate(world);
   }

   public String getTeamName() {
      return "mob_pirate";
   }

   public int getColor() {
      return 2236962;
   }

   public double getHealth() {
      return 30.0D;
   }

   public double getAttack() {
      return 1.0D;
   }

   public double getRange() {
      return 30.0D;
   }

   public int getDifficulty(World world, int posX, int posY, int posZ) {
      return 3;
   }
}
