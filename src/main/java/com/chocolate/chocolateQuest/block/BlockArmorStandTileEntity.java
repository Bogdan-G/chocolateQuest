package com.chocolate.chocolateQuest.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class BlockArmorStandTileEntity extends TileEntity implements IInventory {

   public ItemStack[] cargoItems = new ItemStack[6];
   public int rotation = 0;


   public boolean anyPlayerInRange() {
      return super.worldObj.getClosestPlayer((double)super.xCoord + 0.5D, (double)super.yCoord + 0.5D, (double)super.zCoord + 0.5D, 16.0D) != null;
   }

   public void updateEntity() {
      super.updateEntity();
   }

   public void readFromNBT(NBTTagCompound nbttagcompound) {
      super.readFromNBT(nbttagcompound);
      this.rotation = nbttagcompound.getInteger("rot");

      for(int i = 0; i < this.cargoItems.length; ++i) {
         NBTTagCompound itemNBT = (NBTTagCompound)nbttagcompound.getTag("CItem" + i);
         if(itemNBT != null) {
            this.cargoItems[i] = ItemStack.loadItemStackFromNBT(itemNBT);
         }
      }

   }

   public void writeToNBT(NBTTagCompound nbttagcompound) {
      super.writeToNBT(nbttagcompound);
      nbttagcompound.setInteger("rot", this.rotation);

      for(int i = 0; i < this.cargoItems.length; ++i) {
         if(this.cargoItems[i] != null) {
            NBTTagCompound itemNBT = new NBTTagCompound();
            nbttagcompound.setTag("CItem" + i, this.cargoItems[i].writeToNBT(itemNBT));
         }
      }

   }

   public Packet getDescriptionPacket() {
      NBTTagCompound data = new NBTTagCompound();
      data.setInteger("rot", this.rotation);

      for(int i = 0; i < this.cargoItems.length; ++i) {
         if(this.cargoItems[i] != null) {
            NBTTagCompound itemNBT = new NBTTagCompound();
            data.setTag("CItem" + i, this.cargoItems[i].writeToNBT(itemNBT));
         }
      }

      return new S35PacketUpdateTileEntity(super.xCoord, super.yCoord, super.zCoord, 1, data);
   }

   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
      NBTTagCompound tag = packet.func_148857_g();
      this.rotation = tag.getInteger("rot");

      for(int i = 0; i < this.cargoItems.length; ++i) {
         NBTTagCompound item = (NBTTagCompound)tag.getTag("CItem" + i);
         if(item != null) {
            this.cargoItems[i] = ItemStack.loadItemStackFromNBT(item);
         }
      }

   }

   @SideOnly(Side.CLIENT)
   public double getMaxRenderDistanceSquared() {
      return 6400.0D;
   }

   public int getSizeInventory() {
      return this.cargoItems.length;
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
      if(itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
         itemstack.stackSize = this.getInventoryStackLimit();
      }

      this.updateInventory();
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
      return "NPC inventory";
   }

   public boolean hasCustomInventoryName() {
      return false;
   }

   public void markDirty() {}

   public void openInventory() {}
}
