package com.chocolate.chocolateQuest.entity.mob.registry;

import com.chocolate.chocolateQuest.entity.mob.EntityHumanSkeleton;
import com.chocolate.chocolateQuest.entity.mob.EntityNecromancer;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class MobSkeleton extends DungeonMonstersBase {

   public String getEntityName() {
      return "skeleton";
   }

   public int getFlagId() {
      return 8;
   }

   public String getRegisteredEntityName() {
      return "chocolateQuest.armoredSkeleton";
   }

   public Entity getBoss(World world, int x, int y, int z) {
      return new EntityNecromancer(world);
   }

   public Entity getEntity(World world, int x, int y, int z) {
      return new EntityHumanSkeleton(world);
   }

   public String getTeamName() {
      return "mob_undead";
   }

   public int getColor() {
      return 3355443;
   }

   public int getDifficulty(World world, int posX, int posY, int posZ) {
      return 1;
   }
}
