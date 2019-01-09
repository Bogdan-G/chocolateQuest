package com.chocolate.chocolateQuest.entity.mob;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanMob;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityHumanSpecter extends EntityHumanMob {

   public EntityHumanSpecter(World world) {
      super(world);
   }

   public DungeonMonstersBase getMonsterType() {
      return ChocolateQuest.specter;
   }

   public EnumCreatureAttribute getCreatureAttribute() {
      return EnumCreatureAttribute.UNDEAD;
   }

   protected String getLivingSound() {
      return "chocolatequest:specter_speak";
   }

   protected String getHurtSound() {
      return "chocolatequest:specter_hurt";
   }

   protected String getDeathSound() {
      return "chocolatequest:specter_death";
   }

   protected ItemStack getMainDrop() {
      return new ItemStack(Items.gunpowder);
   }
}
