package com.chocolate.chocolateQuest.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryCargo implements IInventory {

   protected ItemStack[] cargoItems;


   public InventoryCargo() {}

   public InventoryCargo(ItemStack[] cargoItems) {
      this.cargoItems = cargoItems;
   }

   public int getSizeInventory() {
      return 6;
   }

   public void updateInventory() {}

   public ItemStack getStackInSlot(int i) {
      return this.cargoItems[i];
   }

   public ItemStack decrStackSize(int i, int j) {
      if(this.cargoItems[i] != null) {
         ItemStack itemstack1;
         if(this.cargoItems[i].stackSize <= j) {
            itemstack1 = this.cargoItems[i];
            this.cargoItems[i] = null;
            return itemstack1;
         } else {
            itemstack1 = this.cargoItems[i].splitStack(j);
            if(this.cargoItems[i].stackSize == 0) {
               this.cargoItems[i] = null;
            }

            this.markDirty();
            return itemstack1;
         }
      } else {
         return null;
      }
   }

   public ItemStack getStackInSlotOnClosing(int i) {
      if(this.cargoItems[i] != null) {
         ItemStack is = this.cargoItems[i];
         this.cargoItems[i] = null;
         return is;
      } else {
         return null;
      }
   }

   public void setInventorySlotContents(int i, ItemStack itemstack) {
      this.cargoItems[i] = itemstack;
   }

   public int getInventoryStackLimit() {
      return 64;
   }

   public boolean isUseableByPlayer(EntityPlayer entityplayer) {
      return true;
   }

   public boolean isItemValidForSlot(int i, ItemStack itemstack) {
      return true;
   }

   public void closeInventory() {
      this.updateInventory();
   }

   public String getInventoryName() {
      return "Custom inventory";
   }

   public boolean hasCustomInventoryName() {
      return false;
   }

   public void markDirty() {}

   public void openInventory() {}
}
