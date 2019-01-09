package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.items.ItemArmorBase;
import com.chocolate.chocolateQuest.items.swords.ItemCQBlade;
import com.chocolate.chocolateQuest.magic.Awakements;
import com.chocolate.chocolateQuest.misc.EnumEnchantType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class AwakementAutoRepair extends Awakements {

   public AwakementAutoRepair(String name, int icon) {
      super(name, icon);
   }

   public boolean canBeUsedOnItem(ItemStack is) {
      return is.getItem() instanceof ItemArmorBase?((ItemArmorBase)is.getItem()).isEpic():is.getItem() instanceof ItemCQBlade;
   }

   public int getMaxLevel() {
      return 4;
   }

   public void onUpdate(Entity entity, ItemStack itemStack) {
      if(entity instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)entity;
         int repairLevel = getEnchantLevel(itemStack, this);
         if(itemStack.getItemDamage() >= repairLevel && player.experienceLevel > 0) {
            player.addExperience(-1);
            player.experienceTotal = 0;
            if(player.experience < 0.0F) {
               if(player.experienceLevel > 0) {
                  player.addExperienceLevel(-1);
                  player.experience = 1.0F;
               } else {
                  player.experience = 0.0F;
               }
            }

            itemStack.setItemDamage(itemStack.getItemDamage() - repairLevel);
         }
      }

   }

   public boolean canBeAddedByNPC(int type) {
      return type == EnumEnchantType.BLACKSMITH.ordinal();
   }

   public int getLevelCost() {
      return 3;
   }
}
