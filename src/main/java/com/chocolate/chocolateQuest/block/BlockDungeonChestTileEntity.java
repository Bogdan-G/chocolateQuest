package com.chocolate.chocolateQuest.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class BlockDungeonChestTileEntity extends TileEntity implements IInventory {

   ItemStack[] container = new ItemStack[this.getSizeInventory()];


   public Packet getDescriptionPacket() {
      NBTTagCompound data = new NBTTagCompound();
      this.writeInventoryToNBT(data);
      return new S35PacketUpdateTileEntity(super.xCoord, super.yCoord, super.zCoord, 1, data);
   }

   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
      NBTTagCompound nbt = pkt.func_148857_g();
      this.readInventoryFromNBT(nbt);
      super.onDataPacket(net, pkt);
   }

   public void readFromNBT(NBTTagCompound nbt) {
      super.readFromNBT(nbt);
      this.container = this.readInventoryFromNBT(nbt);
   }

   public void writeToNBT(NBTTagCompound nbt) {
      super.writeToNBT(nbt);
      this.writeInventoryToNBT(nbt);
   }

   public void writeInventoryToNBT(NBTTagCompound nbt) {
      NBTTagList list = new NBTTagList();

      for(int i = 0; i < this.container.length; ++i) {
         if(this.container[i] != null) {
            NBTTagCompound slotnbttagcompound = new NBTTagCompound();
            slotnbttagcompound.setByte("Slot", (byte)i);
            this.container[i].writeToNBT(slotnbttagcompound);
            list.appendTag(slotnbttagcompound);
         }
      }

      nbt.setTag("Items", list);
   }

   public ItemStack[] readInventoryFromNBT(NBTTagCompound nbt) {
      ItemStack[] container = new ItemStack[this.getSizeInventory()];
      NBTTagList list = nbt.getTagList("Items", nbt.getId());
      if(list != null) {
         for(int i = 0; i < list.tagCount(); ++i) {
            NBTTagCompound slotnbttagcompound = list.getCompoundTagAt(i);
            int j = slotnbttagcompound.getByte("Slot") & 255;
            if(j >= 0 && j < container.length) {
               container[j] = ItemStack.loadItemStackFromNBT(slotnbttagcompound);
            }
         }
      }

      return container;
   }

   public void closeInventory() {}

   public ItemStack decrStackSize(int slot, int amount) {
      if(this.container[slot] != null) {
         ItemStack itemstack1;
         if(this.container[slot].stackSize <= amount) {
            itemstack1 = this.container[slot];
            this.container[slot] = null;
            return itemstack1;
         } else {
            itemstack1 = this.container[slot].splitStack(amount);
            if(this.container[slot].stackSize == 0) {
               this.container[slot] = null;
            }

            return itemstack1;
         }
      } else {
         return null;
      }
   }

   public String getInventoryName() {
      return "Chest name";
   }

   public int getInventoryStackLimit() {
      return 64;
   }

   public int getSizeInventory() {
      return 27;
   }

   public ItemStack getStackInSlot(int slot) {
      return this.container[slot];
   }

   public ItemStack getStackInSlotOnClosing(int slot) {
      return null;
   }

   public boolean hasCustomInventoryName() {
      return false;
   }

   public boolean isItemValidForSlot(int slot, ItemStack stack) {
      return false;
   }

   public boolean isUseableByPlayer(EntityPlayer player) {
      return true;
   }

   public void openInventory() {}

   public void setInventorySlotContents(int slot, ItemStack stack) {
      this.container[slot] = stack;
      if(stack != null && stack.stackSize > this.getInventoryStackLimit()) {
         stack.stackSize = this.getInventoryStackLimit();
      }

   }
}
