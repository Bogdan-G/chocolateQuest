package com.chocolate.chocolateQuest.entity.mob.registry;

import com.chocolate.chocolateQuest.entity.mob.EntityHumanZombie;
import com.chocolate.chocolateQuest.entity.mob.EntityLich;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class MobZombie extends DungeonMonstersBase {

   public String getEntityName() {
      return "zombie";
   }

   public int getFlagId() {
      return 7;
   }

   public String getRegisteredEntityName() {
      return "chocolateQuest.armoredZombie";
   }

   public Entity getBoss(World world, int x, int y, int z) {
      return new EntityLich(world);
   }

   public Entity getEntity(World world, int x, int y, int z) {
      return new EntityHumanZombie(world);
   }

   public String getTeamName() {
      return "mob_undead";
   }

   public double getHealth() {
      return 25.0D;
   }

   public double getAttack() {
      return 1.0D;
   }

   public int getColor() {
      return 4417792;
   }
}
