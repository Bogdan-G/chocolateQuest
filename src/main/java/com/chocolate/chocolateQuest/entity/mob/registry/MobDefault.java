package com.chocolate.chocolateQuest.entity.mob.registry;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class MobDefault extends DungeonMonstersBase {

   public String getEntityName() {
      return "default";
   }

   public int getFlagId() {
      return 8;
   }

   public String getRegisteredEntityName() {
      return "chocolateQuest.armoredSkeleton";
   }

   public Entity getBoss(World world, int x, int y, int z) {
      return this.getDungeonMonster(world, x, y, z).getBoss(world, x, y, z);
   }

   public Entity getEntity(World world, int x, int y, int z) {
      return this.getDungeonMonster(world, x, y, z).getEntity(world, x, y, z);
   }

   public String getTeamName() {
      return "mob_undead";
   }

   public DungeonMonstersBase getDungeonMonster(World world, int x, int y, int z) {
      double dist = Math.sqrt((double)((world.getSpawnPoint().posX - x) * (world.getSpawnPoint().posX - x) + (world.getSpawnPoint().posZ - z) * (world.getSpawnPoint().posZ - z)));
      return dist > 1000.0D?ChocolateQuest.zombie:(dist > 2000.0D?ChocolateQuest.specter:(dist > 3000.0D?ChocolateQuest.pigZombie:(dist > 4000.0D?ChocolateQuest.minotaur:ChocolateQuest.skeleton)));
   }

   public int getDifficulty(World world, int posX, int posY, int posZ) {
      return this.getDungeonMonster(world, posX, posY, posZ).getDifficulty(world, posX, posY, posZ);
   }
}
