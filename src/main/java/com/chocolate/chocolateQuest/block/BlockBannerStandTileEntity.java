package com.chocolate.chocolateQuest.block;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.EntityDecoration;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class BlockBannerStandTileEntity extends TileEntity {

   public boolean hasFlag = false;
   public int rotation = 0;
   public ItemStack item;


   public AxisAlignedBB getRenderBoundingBox() {
      return super.getRenderBoundingBox().addCoord(0.0D, 1.0D, 0.0D);
   }

   public void updateEntity() {
      if(!super.worldObj.isRemote) {
         if(this.hasFlag) {
            EntityDecoration e = new EntityDecoration(super.worldObj);
            e.setPosition((double)((float)super.xCoord + 0.5F), (double)super.yCoord, (double)((float)super.zCoord + 0.5F));
            e.rotationYaw = (float)this.rotation;
            e.type = this.item.getItemDamage();
            super.worldObj.spawnEntityInWorld(e);
            this.hasFlag = false;
         }

         super.worldObj.setBlockToAir(super.xCoord, super.yCoord, super.zCoord);
      }

   }

   public void readFromNBT(NBTTagCompound nbttagcompound) {
      super.readFromNBT(nbttagcompound);
      this.rotation = nbttagcompound.getInteger("rot");
      this.hasFlag = nbttagcompound.getBoolean("flag");
      NBTTagCompound itemNBT = (NBTTagCompound)nbttagcompound.getTag("Item");
      if(itemNBT != null) {
         this.item = ItemStack.loadItemStackFromNBT(itemNBT);
      } else if(this.hasFlag) {
         this.item = new ItemStack(ChocolateQuest.banner, 1, nbttagcompound.getInteger("type"));
      }

      if(this.item != null && this.item.getItem() != ChocolateQuest.banner) {
         this.item = new ItemStack(ChocolateQuest.banner, 1, this.item.getItemDamage());
      }

   }

   public void writeToNBT(NBTTagCompound nbttagcompound) {
      super.writeToNBT(nbttagcompound);
      nbttagcompound.setInteger("rot", this.rotation);
      if(this.item != null) {
         NBTTagCompound itemNBT = new NBTTagCompound();
         nbttagcompound.setTag("Item", this.item.writeToNBT(itemNBT));
         nbttagcompound.setInteger("type", this.item.getItemDamage());
      }

      nbttagcompound.setBoolean("flag", this.hasFlag);
   }

   public Packet getDescriptionPacket() {
      NBTTagCompound data = new NBTTagCompound();
      data.setInteger("rot", this.rotation);
      if(this.item != null) {
         NBTTagCompound itemNBT = new NBTTagCompound();
         data.setTag("Item", this.item.writeToNBT(itemNBT));
      }

      data.setBoolean("flag", this.hasFlag);
      return new S35PacketUpdateTileEntity(super.xCoord, super.yCoord, super.zCoord, 1, data);
   }

   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
      NBTTagCompound tag = packet.func_148857_g();
      this.rotation = tag.getInteger("rot");
      NBTTagCompound itemNBT = (NBTTagCompound)tag.getTag("Item");
      if(itemNBT != null) {
         this.item = ItemStack.loadItemStackFromNBT(itemNBT);
      }

      this.hasFlag = tag.getBoolean("flag");
   }

   @SideOnly(Side.CLIENT)
   public double getMaxRenderDistanceSquared() {
      return 6400.0D;
   }
}
