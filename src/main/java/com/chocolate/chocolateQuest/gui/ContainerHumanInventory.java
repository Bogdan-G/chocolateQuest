package com.chocolate.chocolateQuest.gui;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.gui.ContainerBDChest;
import com.chocolate.chocolateQuest.gui.InventoryHuman;
import com.chocolate.chocolateQuest.gui.slot.SlotArmor;
import com.chocolate.chocolateQuest.gui.slot.SlotHandLeft;
import com.chocolate.chocolateQuest.gui.slot.SlotHandRight;
import com.chocolate.chocolateQuest.gui.slot.SlotLocked;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerHumanInventory extends ContainerBDChest {

   public ContainerHumanInventory(IInventory playerInventory, IInventory chestInventory) {
      super(playerInventory, chestInventory);
   }

   public void layoutInventory(IInventory chestInventory) {
      EntityHumanBase human = ((InventoryHuman)chestInventory).human;
      byte yPos = 14;
      byte x = 0;
      byte SLOT_DIST = 18;
      if(!human.inventoryLocked) {
         SlotHandRight potions = new SlotHandRight(chestInventory, 0, x, yPos + 28);
         this.addSlotToContainer(potions);

         for(int leftHandSlot = 0; leftHandSlot < 4; ++leftHandSlot) {
            this.addSlotToContainer(new SlotArmor(chestInventory, 4 - leftHandSlot, x + SLOT_DIST, yPos + 10 + leftHandSlot * 18, leftHandSlot));
         }

         SlotHandLeft var10 = new SlotHandLeft(chestInventory, 5, x + SLOT_DIST * 2, yPos + 28, potions);
         this.addSlotToContainer(var10);
         potions.setOpossedHandSlot(var10);
      } else {
         this.addSlotToContainer(new SlotLocked(chestInventory, 0, x, yPos + 28));

         for(int var8 = 0; var8 < 4; ++var8) {
            this.addSlotToContainer(new SlotLocked(chestInventory, 4 - var8, x + SLOT_DIST, yPos + 10 + var8 * 18));
         }

         this.addSlotToContainer(new SlotLocked(chestInventory, 5, x + SLOT_DIST * 2, yPos + 28));
      }

      Slot var9 = new Slot(chestInventory, 6, x + SLOT_DIST * 2, yPos + 28 + 16);
      this.addSlotToContainer(var9);
   }

   public int getPlayerInventoryY() {
      return 172 + (this.getRowCount() - 4) * 18;
   }
}
