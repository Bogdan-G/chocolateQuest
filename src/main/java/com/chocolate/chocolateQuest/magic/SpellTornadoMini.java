package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.magic.SpellProjectile;
import net.minecraft.item.ItemStack;

public class SpellTornadoMini extends SpellProjectile {

   public int getType() {
      return 10;
   }

   public int getCoolDown() {
      return 400;
   }

   public int getRange(ItemStack itemstack) {
      return 10;
   }

   public int getCastingTime() {
      return 30;
   }

   public float getCost(ItemStack itemstack) {
      return 30.0F;
   }
}
