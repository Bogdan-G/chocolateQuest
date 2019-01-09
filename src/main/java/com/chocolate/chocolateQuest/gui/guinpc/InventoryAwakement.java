package com.chocolate.chocolateQuest.gui.guinpc;

import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.gui.InventoryCargo;
import net.minecraft.item.ItemStack;

public class InventoryAwakement extends InventoryCargo {

   EntityHumanNPC npc;


   public InventoryAwakement(EntityHumanNPC human) {
      super.cargoItems = new ItemStack[this.getSizeInventory()];
      this.npc = human;
   }

   public int getSizeInventory() {
      return 2;
   }

   public void updateInventory() {}
}
