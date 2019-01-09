package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.magic.SpellProjectile;
import net.minecraft.item.ItemStack;

public class SpellProjectileArrow extends SpellProjectile {

   public int getType() {
      return 109;
   }

   public int getCoolDown() {
      return 50;
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

   public float getDamageMultiplier() {
      return 0.75F;
   }
}
