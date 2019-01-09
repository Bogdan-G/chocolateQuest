package com.chocolate.chocolateQuest.gui.slot;

import com.chocolate.chocolateQuest.gui.guinpc.InventoryShop;
import com.chocolate.chocolateQuest.gui.guinpc.ShopRecipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotShop extends Slot {

   InventoryShop shopInventory;


   public SlotShop(InventoryShop shopInventory, int id, int x, int y) {
      super(shopInventory, id, x, y);
      this.shopInventory = shopInventory;
   }

   public boolean isItemValid(ItemStack is) {
      return false;
   }

   public void onPickupFromSlot(EntityPlayer player, ItemStack is) {
      super.onPickupFromSlot(player, is);
   }

   public void putStack(ItemStack is) {
      super.putStack(is);
   }

   public boolean canTakeStack(EntityPlayer player) {
      return true;
   }

   public ShopRecipe getRecipe() {
      return this.shopInventory.getShopRecipe(this.getSlotIndex());
   }

   public boolean hasRecipe(EntityPlayer player, boolean consumeItems) {
      InventoryPlayer playerInv = player.inventory;
      ShopRecipe recipe = this.getRecipe();
      if(recipe != null) {
         ItemStack[] tempRecipe = new ItemStack[recipe.costItems.length];

         int i;
         for(i = 0; i < tempRecipe.length; ++i) {
            tempRecipe[i] = recipe.costItems[i].copy();
         }

         for(i = 0; i < playerInv.getSizeInventory(); ++i) {
            ItemStack currentStack = playerInv.getStackInSlot(i);
            if(currentStack != null) {
               currentStack = new ItemStack(currentStack.getItem(), currentStack.stackSize, currentStack.getItemDamage());

               for(int t = 0; t < tempRecipe.length; ++t) {
                  if(this.areItemsEqual(currentStack, tempRecipe[t])) {
                     int recipeAmmount = tempRecipe[t].stackSize;
                     tempRecipe[t] = this.decreaseStackSize(currentStack.stackSize, tempRecipe[t]);
                     currentStack = this.decreaseStackSize(recipeAmmount, currentStack);
                     if(consumeItems) {
                        playerInv.setInventorySlotContents(i, currentStack);
                     }

                     if(currentStack == null) {
                        break;
                     }
                  }
               }

               tempRecipe = this.removeNullEntries(tempRecipe);
               if(tempRecipe == null) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public ItemStack decreaseStackSize(int stackDecrease, ItemStack stack) {
      int newSize = stack.stackSize - stackDecrease;
      if(newSize <= 0) {
         return null;
      } else {
         stack.stackSize = newSize;
         return stack;
      }
   }

   public boolean areItemsEqual(ItemStack is1, ItemStack is2) {
      return is1.isItemEqual(is2)?is1.stackTagCompound == is2.stackTagCompound:false;
   }

   public ItemStack[] removeNullEntries(ItemStack[] original) {
      int entries = 0;

      for(int newStacks = 0; newStacks < original.length; ++newStacks) {
         if(original[newStacks] != null) {
            ++entries;
         }
      }

      if(entries == 0) {
         return null;
      } else {
         ItemStack[] var7 = new ItemStack[entries];
         int i = 0;

         while(i < var7.length) {
            ItemStack nextStack = null;
            int t = 0;

            while(true) {
               if(t < original.length) {
                  if(original[t] == null) {
                     ++t;
                     continue;
                  }

                  nextStack = original[t];
                  original[t] = null;
               }

               var7[i] = nextStack;
               ++i;
               break;
            }
         }

         return var7;
      }
   }
}
