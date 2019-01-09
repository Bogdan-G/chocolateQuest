package com.chocolate.chocolateQuest.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class BlockEditorTileEntity extends TileEntity {

   public int red = 15;
   public int yellow = 15;
   public int height = 20;
   public String name = "Template";


   public boolean anyPlayerInRange() {
      return super.worldObj.getClosestPlayer((double)super.xCoord + 0.5D, (double)super.yCoord + 0.5D, (double)super.zCoord + 0.5D, 16.0D) != null;
   }

   public void updateEntity() {
      super.updateEntity();
   }

   public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readFromNBT(par1NBTTagCompound);
      this.red = par1NBTTagCompound.getInteger("red");
      this.yellow = par1NBTTagCompound.getInteger("yellow");
      this.height = par1NBTTagCompound.getInteger("height");
      this.setName(par1NBTTagCompound.getString("name"));
   }

   public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeToNBT(par1NBTTagCompound);
      par1NBTTagCompound.setInteger("red", this.red);
      par1NBTTagCompound.setInteger("yellow", this.yellow);
      par1NBTTagCompound.setInteger("height", this.height);
      par1NBTTagCompound.setString("name", this.getName());
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Packet getDescriptionPacket() {
      NBTTagCompound var1 = new NBTTagCompound();
      this.writeToNBT(var1);
      return new S35PacketUpdateTileEntity(super.xCoord, super.yCoord, super.zCoord, 1, var1);
   }

   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
      NBTTagCompound tag = packet.func_148857_g();
      this.red = tag.getInteger("red");
      this.yellow = tag.getInteger("yellow");
      this.height = tag.getInteger("height");
      this.setName(tag.getString("name"));
   }

   public double getRenderDistance() {
      return (double)(64 + this.red + this.yellow + this.height);
   }

   @SideOnly(Side.CLIENT)
   public double func_82115_m() {
      return (double)((64 + this.red + this.yellow + this.height) * (64 + this.red + this.yellow + this.height));
   }

   public AxisAlignedBB getRenderBoundingBox() {
      return super.getRenderBoundingBox().addCoord((double)this.red, (double)this.height, (double)this.yellow);
   }
}
