package com.chocolate.chocolateQuest.gui;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.gui.ContainerHumanInventory;
import com.chocolate.chocolateQuest.gui.slot.SlotLockedToClass;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerGolemInventory extends ContainerHumanInventory {

   public ContainerGolemInventory(IInventory playerInventory, IInventory chestInventory) {
      super(playerInventory, chestInventory);
   }

   public void layoutInventory(IInventory chestInventory) {
      byte yPos = 14;
      byte x = 0;
      byte SLOT_DIST = 18;
      this.addSlotToContainer(new Slot(chestInventory, 0, x, yPos + 28));

      for(int i = 0; i < 4; ++i) {
         this.addSlotToContainer(new SlotLockedToClass(chestInventory, 4 - i, x + SLOT_DIST, yPos + 10 + i * 18, ChocolateQuest.golemUpgrade));
      }

      this.addSlotToContainer(new Slot(chestInventory, 5, x + SLOT_DIST * 2, yPos + 28));
   }
}
