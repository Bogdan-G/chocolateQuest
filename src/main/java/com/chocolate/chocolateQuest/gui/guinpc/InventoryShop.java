package com.chocolate.chocolateQuest.gui.guinpc;

import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.gui.InventoryCargo;
import com.chocolate.chocolateQuest.gui.guinpc.ShopRecipe;
import net.minecraft.item.ItemStack;

public class InventoryShop extends InventoryCargo {

   EntityHumanNPC human;
   int tempid;
   ShopRecipe[] trades;
   final int NEW_TRADE_ITEM = 18;


   public InventoryShop(EntityHumanNPC human) {
      this.human = human;
      super.cargoItems = new ItemStack[this.getSizeInventory()];
      this.updateCargo();
   }

   public void updateCargo() {
      this.trades = this.human.getRecipes();
      if(this.trades != null) {
         for(int t = 0; t < super.cargoItems.length; ++t) {
            if(t < this.trades.length) {
               super.cargoItems[t] = this.trades[t].tradedItem;
            } else {
               super.cargoItems[t] = null;
            }
         }
      }

   }

   public boolean setShopRecipe(int i) {
      if(super.cargoItems[18] != null) {
         ItemStack tradedItem = super.cargoItems[18];
         int costItemsAmmount = 0;

         for(int costItems = 1; costItems < 4; ++costItems) {
            if(super.cargoItems[18 + costItems] != null) {
               ++costItemsAmmount;
            }
         }

         if(costItemsAmmount == 0) {
            return false;
         } else {
            ItemStack[] var8 = new ItemStack[costItemsAmmount];
            int current = 0;

            for(int f = 1; f < 4; ++f) {
               ItemStack currentItem = super.cargoItems[18 + f];
               if(currentItem != null) {
                  var8[current] = currentItem;
                  ++current;
               }
            }

            this.human.setRecipes(i, new ShopRecipe(tradedItem, var8));
            this.updateCargo();
            return true;
         }
      } else {
         return false;
      }
   }

   public void removeShopRecipe(int i) {
      if(super.cargoItems[i] != null) {
         this.trades[i] = null;
         this.trades = this.removeNullEntries(this.trades);
         this.human.setRecipes(this.trades);
         this.updateCargo();
      }

   }

   public ShopRecipe getShopRecipe(int i) {
      return i >= this.trades.length?null:this.trades[i];
   }

   public int getSizeInventory() {
      return 22;
   }

   public void updateInventory() {}

   public ItemStack decrStackSize(int i, int j) {
      if(i >= 18) {
         return super.decrStackSize(i, j);
      } else {
         ItemStack is = ItemStack.copyItemStack(super.cargoItems[i]);
         if(is.stackSize <= 0) {
            is.stackSize = 1;
         }

         return is;
      }
   }

   public void setInventorySlotContents(int i, ItemStack itemstack) {
      if(i >= 18) {
         super.setInventorySlotContents(i, itemstack);
      }

   }

   public ItemStack getStackInSlotOnClosing(int i) {
      return super.getStackInSlotOnClosing(i);
   }

   public ShopRecipe[] removeNullEntries(ShopRecipe[] original) {
      int entries = 0;

      for(int newStacks = 0; newStacks < original.length; ++newStacks) {
         if(original[newStacks] != null) {
            ++entries;
         }
      }

      if(entries == 0) {
         return null;
      } else {
         ShopRecipe[] var7 = new ShopRecipe[entries];
         int i = 0;

         while(i < var7.length) {
            ShopRecipe nextStack = null;
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
