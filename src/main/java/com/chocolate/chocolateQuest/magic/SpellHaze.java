package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.magic.SpellProjectile;
import net.minecraft.item.ItemStack;

public class SpellHaze extends SpellProjectile {

   public int getType() {
      return 110;
   }

   public int getCoolDown() {
      return 250;
   }

   public int getCastingTime() {
      return 40;
   }

   public int getRange(ItemStack itemstack) {
      return 15;
   }

   public float getCost(ItemStack itemstack) {
      return 35.0F;
   }
}
