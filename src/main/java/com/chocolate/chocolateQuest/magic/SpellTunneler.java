package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.magic.SpellMiner;
import net.minecraft.item.ItemStack;

public class SpellTunneler extends SpellMiner {

   public int getCoolDown() {
      return 20;
   }

   public float getCost(ItemStack itemstack) {
      return 0.5F;
   }

   public int getRange(ItemStack itemstack) {
      return 3;
   }

   public boolean isAreaMiner() {
      return true;
   }
}
