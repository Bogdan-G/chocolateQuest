package com.chocolate.chocolateQuest.gui;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.items.gun.ILoadableGun;
import java.util.Random;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class InventoryBag_Backup implements IInventory {

   ItemStack[] cargoItems;
   ItemStack container;
   EntityPlayer player;
   int tempid;


   public InventoryBag_Backup(ItemStack items, EntityPlayer player) {
      this.player = player;
      this.container = items;
      this.cargoItems = new ItemStack[this.getSizeInventory()];
      if(items.stackTagCompound == null) {
         items.stackTagCompound = new NBTTagCompound();
      }

      NBTTagList nbttaglist = items.stackTagCompound.getTagList("Items", items.stackTagCompound.getId());
      if(nbttaglist != null) {
         for(int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound slotnbttagcompound = nbttaglist.getCompoundTagAt(i);
            int j = slotnbttagcompound.getByte("Slot") & 255;
            if(j >= 0 && j < this.cargoItems.length) {
               this.cargoItems[j] = ItemStack.loadItemStackFromNBT(slotnbttagcompound);
            }
         }
      } else {
         this.cargoItems = new ItemStack[this.getSizeInventory()];
         this.markDirty();
      }

      this.tempid = (new Random()).nextInt();
      this.container.stackTagCompound.setInteger("tempid", this.tempid);
   }

   public static ItemStack[] getCargo(ItemStack is) {
      if(is.stackTagCompound == null) {
         is.stackTagCompound = new NBTTagCompound();
      }

      ItemStack[] cargoItems = new ItemStack[getSizeInventory(is)];
      NBTTagList nbttaglist = is.stackTagCompound.getTagList("Items", is.stackTagCompound.getId());
      if(nbttaglist != null) {
         for(int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound slotnbttagcompound = nbttaglist.getCompoundTagAt(i);
            int j = slotnbttagcompound.getByte("Slot") & 255;
            if(j >= 0 && j < cargoItems.length) {
               cargoItems[j] = ItemStack.loadItemStackFromNBT(slotnbttagcompound);
               if(cargoItems[j] == null && !slotnbttagcompound.hasNoTags()) {
                  cargoItems[j] = new ItemStack(ChocolateQuest.spell, 1, slotnbttagcompound.getShort("Damage"));
               }
            }
         }
      } else {
         cargoItems = new ItemStack[getSizeInventory(is)];
      }

      return cargoItems;
   }

   public static void saveCargo(ItemStack container, ItemStack[] cargoItems) {
      NBTTagList nbttaglist = new NBTTagList();

      for(int i = 0; i < cargoItems.length; ++i) {
         if(cargoItems[i] != null) {
            NBTTagCompound slotnbttagcompound = new NBTTagCompound();
            slotnbttagcompound.setByte("Slot", (byte)i);
            cargoItems[i].writeToNBT(slotnbttagcompound);
            nbttaglist.appendTag(slotnbttagcompound);
         }
      }

      container.stackTagCompound.setTag("Items", nbttaglist);
   }

   public static int getSizeInventory(ItemStack is) {
      return is.getItem() instanceof ILoadableGun?((ILoadableGun)is.getItem()).getAmmoLoaderAmmount(is):is.getItemDamage() * 9 + 9;
   }

   public int getSizeInventory() {
      return getSizeInventory(this.container);
   }

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
      if(this.cargoItems[i] != null && this.cargoItems[i].getItem() == this.container.getItem()) {
         ItemStack temp = this.cargoItems[i];
         this.cargoItems[i] = null;
         this.saveToNBT(this.container);
         return temp;
      } else {
         return null;
      }
   }

   public void setInventorySlotContents(int i, ItemStack itemstack) {
      this.cargoItems[i] = itemstack;
   }

   public boolean hasCustomInventoryName() {
      return false;
   }

   public String getInventoryName() {
      return "Bag";
   }

   public int getInventoryStackLimit() {
      return 64;
   }

   public void markDirty() {
      this.closeInventory();
      this.saveToNBT(this.container);
   }

   public boolean isUseableByPlayer(EntityPlayer entityplayer) {
      return entityplayer.inventory.getCurrentItem() == null?false:(entityplayer.inventory.getItemStack() != null && entityplayer.inventory.getItemStack() == this.container?false:this.container.isItemEqual(entityplayer.inventory.getCurrentItem()));
   }

   public void openInventory() {}

   public void closeInventory() {
      ItemStack bag = this.getBag();
      this.saveToNBT(bag);
   }

   public void saveToNBT(ItemStack container) {
      if(container != null) {
         if(container.stackTagCompound != null && container.stackTagCompound.getInteger("tempid") == this.tempid) {
            saveCargo(container, this.cargoItems);
         }
      }
   }

   public ItemStack getBag() {
      ItemStack container = null;

      for(int currentItemStack = 0; currentItemStack < this.player.inventory.getSizeInventory(); ++currentItemStack) {
         ItemStack currentItemStack1 = this.player.inventory.getStackInSlot(currentItemStack);
         if(currentItemStack1 != null && currentItemStack1.stackTagCompound != null && currentItemStack1.stackTagCompound.getInteger("tempid") == this.tempid) {
            container = currentItemStack1;
            break;
         }
      }

      if(container == null) {
         ItemStack var4 = this.player.inventory.getItemStack();
         if(var4 != null && var4.stackTagCompound != null && var4.stackTagCompound.getInteger("tempid") == this.tempid) {
            container = var4;
         }
      }

      return container;
   }

   public boolean isItemValidForSlot(int i, ItemStack itemstack) {
      return true;
   }
}
