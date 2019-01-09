package com.chocolate.chocolateQuest.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBDChest extends Container {

   static final int COLUMS = 9;
   static final int ICON_DESP = 17;
   static final int MARGIN = 10;
   protected EntityPlayer player;
   protected IInventory chest;


   public ContainerBDChest(IInventory playerInventory, IInventory chestInventory) {
      this.chest = chestInventory;
      this.player = ((InventoryPlayer)playerInventory).player;
      chestInventory.openInventory();
      this.layoutContainer(playerInventory, chestInventory);
   }

   public boolean canInteractWith(EntityPlayer player) {
      return this.chest.isUseableByPlayer(player);
   }

   public void onContainerClosed(EntityPlayer entityplayer) {
      this.chest.closeInventory();
      super.onContainerClosed(entityplayer);
   }

   protected void layoutContainer(IInventory playerInventory, IInventory chestInventory) {
      this.layoutPlayerInventory(playerInventory);
      this.layoutInventory(chestInventory);
   }

   public void layoutPlayerInventory(IInventory playerInventory) {
      byte leftCol = 8;
      int yPos = this.getPlayerInventoryY();

      int hotbarSlot;
      for(hotbarSlot = 0; hotbarSlot < 3; ++hotbarSlot) {
         for(int col = 0; col < 9; ++col) {
            this.addSlotToContainer(new Slot(playerInventory, col + hotbarSlot * 9 + 9, leftCol + col * 18, 0 + hotbarSlot * 18 + yPos));
         }
      }

      for(hotbarSlot = 0; hotbarSlot < 9; ++hotbarSlot) {
         this.addSlotToContainer(new Slot(playerInventory, hotbarSlot, leftCol + hotbarSlot * 18, 58 + yPos));
      }

   }

   public int getPlayerInventoryY() {
      return 118;
   }

   public void layoutInventory(IInventory chestInventory) {
      for(int chestRow = 0; chestRow < this.getRowCount(); ++chestRow) {
         for(int chestCol = 0; chestCol < this.getRowLength(); ++chestCol) {
            int index = chestCol + chestRow * this.getRowLength();
            if(chestRow * this.getRowLength() + chestCol < chestInventory.getSizeInventory()) {
               this.addSlotToContainer(new Slot(chestInventory, index, 10 + chestCol * 17, 17 + chestRow * 17));
            }
         }
      }

   }

   public int getRowLength() {
      return 9;
   }

   public int getRowCount() {
      return 1 + this.chest.getSizeInventory() / this.getRowLength();
   }

   public ItemStack transferStackInSlot(EntityPlayer player, int index) {
      ItemStack slotItemStackCopy = null;
      Slot slot = (Slot)super.inventorySlots.get(index);
      if(slot != null && slot.getHasStack()) {
         ItemStack slotItemStack = slot.getStack();
         int playerInventorySize = super.inventorySlots.size() - this.chest.getSizeInventory();
         if(index < playerInventorySize) {
            for(int i = 0; i < super.inventorySlots.size() - playerInventorySize; ++i) {
               Slot chestSlot = (Slot)super.inventorySlots.get(playerInventorySize + i);
               if(chestSlot.isItemValid(slotItemStack)) {
                  int chestEmptyStacks;
                  if(chestSlot.getStack() == null) {
                     chestEmptyStacks = slotItemStack.stackSize - this.chest.getInventoryStackLimit();
                     if(chestEmptyStacks > 0) {
                        slotItemStackCopy = slotItemStack.splitStack(chestEmptyStacks);
                     } else {
                        slotItemStackCopy = chestSlot.getStack();
                     }

                     chestSlot.putStack(slotItemStack);
                     slot.putStack(slotItemStackCopy);
                     slot.onSlotChanged();
                     chestSlot.onSlotChanged();
                     return null;
                  }

                  if(chestSlot.getStack().isStackable() && chestSlot.getStack().isItemEqual(slotItemStack) && chestSlot.getStack().stackSize < this.chest.getInventoryStackLimit()) {
                     chestEmptyStacks = this.chest.getInventoryStackLimit() - chestSlot.getStack().stackSize;
                     int restingItems = slotItemStack.stackSize - chestEmptyStacks;
                     ItemStack var10000;
                     if(restingItems > 0) {
                        slotItemStackCopy = slotItemStack.splitStack(restingItems);
                        var10000 = chestSlot.getStack();
                        var10000.stackSize += slotItemStack.stackSize;
                     } else {
                        var10000 = chestSlot.getStack();
                        var10000.stackSize += slotItemStack.stackSize;
                        slotItemStackCopy = null;
                     }

                     slot.putStack(slotItemStackCopy);
                     slot.onSlotChanged();
                     chestSlot.onSlotChanged();
                     return null;
                  }
               }
            }
         } else if(!this.mergeItemStack(slotItemStack, 0, playerInventorySize, true)) {
            return null;
         }

         if(slotItemStack.stackSize == 0) {
            slot.onPickupFromSlot(player, slotItemStack);
            slot.putStack((ItemStack)null);
         } else {
            slot.onSlotChanged();
         }

         return null;
      } else {
         return slotItemStackCopy;
      }
   }
}
