package com.chocolate.chocolateQuest.gui;

import com.chocolate.chocolateQuest.gui.ContainerBDChest;
import com.chocolate.chocolateQuest.gui.slot.SlotArmor;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerArmorStand extends ContainerBDChest {

   public ContainerArmorStand(IInventory playerInventory, IInventory chestInventory) {
      super(playerInventory, chestInventory);
   }

   public void layoutInventory(IInventory chestInventory) {
      byte yPos = 14;
      byte x = 0;
      byte SLOT_DIST = 18;

      for(int i = 0; i < 4; ++i) {
         this.addSlotToContainer(new SlotArmor(chestInventory, 3 - i, x + SLOT_DIST, yPos + 10 + i * 18, i));
      }

      this.addSlotToContainer(new Slot(chestInventory, 4, x, yPos + 28));
      this.addSlotToContainer(new Slot(chestInventory, 5, x + SLOT_DIST * 2, yPos + 28));
   }

   public int getPlayerInventoryY() {
      return 190 + (this.getRowCount() - 4) * 18;
   }
}
