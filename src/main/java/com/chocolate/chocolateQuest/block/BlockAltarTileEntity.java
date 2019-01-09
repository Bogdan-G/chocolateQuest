package com.chocolate.chocolateQuest.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class BlockAltarTileEntity extends TileEntity {

   public ItemStack item;
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
      NBTTagCompound itemNBT = (NBTTagCompound)nbttagcompound.getTag("CItem");
      if(itemNBT != null) {
         this.item = ItemStack.loadItemStackFromNBT(itemNBT);
      }

   }

   public void writeToNBT(NBTTagCompound nbttagcompound) {
      super.writeToNBT(nbttagcompound);
      nbttagcompound.setInteger("rot", this.rotation);
      if(this.item != null) {
         NBTTagCompound itemNBT = new NBTTagCompound();
         nbttagcompound.setTag("CItem", this.item.writeToNBT(itemNBT));
      }

   }

   public Packet getDescriptionPacket() {
      NBTTagCompound data = new NBTTagCompound();
      data.setInteger("rot", this.rotation);
      if(this.item != null) {
         NBTTagCompound itemNBT = new NBTTagCompound();
         data.setTag("CItem", this.item.writeToNBT(itemNBT));
      }

      return new S35PacketUpdateTileEntity(super.xCoord, super.yCoord, super.zCoord, 1, data);
   }

   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
      NBTTagCompound tag = packet.func_148857_g();
      this.rotation = tag.getInteger("rot");
      NBTTagCompound itemNBT = (NBTTagCompound)tag.getTag("CItem");
      if(itemNBT != null) {
         this.item = ItemStack.loadItemStackFromNBT(itemNBT);
      }

   }

   @SideOnly(Side.CLIENT)
   public double getMaxRenderDistanceSquared() {
      return 6400.0D;
   }

   public AxisAlignedBB getRenderBoundingBox() {
      return super.getRenderBoundingBox().addCoord(0.0D, -1.0D, 0.0D);
   }
}
