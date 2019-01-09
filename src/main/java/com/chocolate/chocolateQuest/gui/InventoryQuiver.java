package com.chocolate.chocolateQuest.gui;

import com.chocolate.chocolateQuest.gui.InventoryBag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class InventoryQuiver extends InventoryBag {

   public InventoryQuiver(ItemStack items, EntityPlayer player) {
      super(items, player);
   }

   public int getSizeInventory() {
      return 9;
   }

   public String getInventoryName() {
      return "Quiver";
   }

   public ItemStack getStackInSlotOnClosing(int i) {
      if(super.cargoItems[i] != null && super.cargoItems[i].getItem() != Items.arrow) {
         ItemStack temp = super.cargoItems[i];
         super.cargoItems[i] = null;
         return temp;
      } else {
         return null;
      }
   }
}
