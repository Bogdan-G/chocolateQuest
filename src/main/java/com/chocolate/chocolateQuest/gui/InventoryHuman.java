package com.chocolate.chocolateQuest.gui;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.gui.InventoryCargo;
import net.minecraft.item.ItemStack;

public class InventoryHuman extends InventoryCargo {

   public EntityHumanBase human;
   int tempid;
   public static final int POTION_SLOT = 6;


   public InventoryHuman(EntityHumanBase human) {
      super.cargoItems = new ItemStack[this.getSizeInventory()];

      for(int i = 0; i < 5; ++i) {
         super.cargoItems[i] = human.getEquipmentInSlot(i);
      }

      super.cargoItems[5] = human.leftHandItem;
      this.human = human;
      if(human.potionCount > 0) {
         super.cargoItems[6] = new ItemStack(ChocolateQuest.potion, human.potionCount, 0);
      }

   }

   public int getSizeInventory() {
      return 7;
   }

   public void setInventorySlotContents(int i, ItemStack itemstack) {
      super.setInventorySlotContents(i, itemstack);
   }

   public void updateInventory() {
      for(int i = 0; i < 5; ++i) {
         this.human.setCurrentItemOrArmor(i, super.cargoItems[i]);
      }

      this.human.leftHandItem = super.cargoItems[5];
      if(super.cargoItems[6] != null) {
         this.human.potionCount = super.cargoItems[6].stackSize;
      } else {
         this.human.potionCount = 0;
      }

   }

   public boolean isItemValidForSlot(int i, ItemStack itemstack) {
      return super.isItemValidForSlot(i, itemstack);
   }
}
