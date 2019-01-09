package com.chocolate.chocolateQuest.entity.mob.registry;

import com.chocolate.chocolateQuest.entity.mob.EntityHumanPirate;
import com.chocolate.chocolateQuest.entity.mob.EntitySpaceWarrior;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MobSpaceWarrior extends DungeonMonstersBase {

   public String getEntityName() {
      return "space_invader";
   }

   public int getFlagId() {
      return 9;
   }

   public String getRegisteredEntityName() {
      return "chocolateQuest.spaceWarrior";
   }

   public Entity getBoss(World world, int x, int y, int z) {
      EntitySpaceWarrior m = new EntitySpaceWarrior(world);
      m.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(800.0D);
      m.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0D);
      m.setCurrentItemOrArmor(0, (ItemStack)null);
      return m;
   }

   public Entity getEntity(World world, int x, int y, int z) {
      return new EntityHumanPirate(world);
   }

   public String getTeamName() {
      return "mob_invader";
   }

   public int getColor() {
      return 1644912;
   }
}
