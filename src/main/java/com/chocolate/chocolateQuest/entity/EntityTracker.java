package com.chocolate.chocolateQuest.entity;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityTracker extends Entity implements IEntityAdditionalSpawnData {

   EntityLivingBase mainBody;
   private int maxRange = 5;


   public EntityTracker(World world) {
      super(world);
      super.isImmuneToFire = true;
      this.setSize(1.0F, 1.0F);
   }

   public EntityTracker(World world, EntityLivingBase main, int range) {
      super(world);
      super.isImmuneToFire = true;
      this.setSize(1.0F, 1.0F);
      this.mainBody = main;
      if(main != null) {
         this.setPosition(main.posX, main.posY, main.posZ);
      }

      this.maxRange = range;
   }

   public void onUpdate() {
      super.onUpdate();
      if(this.mainBody != null) {
         if(this.mainBody.isDead && !super.worldObj.isRemote) {
            this.setDead();
         }

         double y = 0.0D;
         if(this.mainBody instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)this.mainBody;
            if(!player.isUsingItem() && !super.worldObj.isRemote) {
               this.setDead();
            }

            y = super.worldObj.isRemote?(double)(-this.mainBody.height) * 0.9D:0.0D;
         } else if(super.ticksExisted > 200) {
            this.setDead();
         }

         if(this.mainBody instanceof EntityHumanBase && !super.worldObj.isRemote && !((EntityHumanBase)this.mainBody).isAiming()) {
            this.setDead();
         }

         this.setPositionAndRotation(this.mainBody.posX, this.mainBody.posY + y, this.mainBody.posZ, this.mainBody.rotationYawHead, this.mainBody.rotationPitch);
      } else {
         this.setDead();
      }

   }

   protected void readEntityFromNBT(NBTTagCompound var1) {
      this.setDead();
   }

   protected void writeEntityToNBT(NBTTagCompound var1) {}

   public void readSpawnData(ByteBuf additionalData) {
      if(!additionalData.readBoolean()) {
         int id = additionalData.readInt();
         Entity e = super.worldObj.getEntityByID(id);
         if(e instanceof EntityLivingBase) {
            this.mainBody = (EntityLivingBase)e;
         }

         this.maxRange = additionalData.readInt();
         this.setSize((float)(this.maxRange * 2), (float)(this.maxRange * 2));
      }
   }

   public void writeSpawnData(ByteBuf buffer) {
      buffer.writeBoolean(this.mainBody == null);
      if(this.mainBody != null) {
         buffer.writeInt(this.mainBody.getEntityId());
         buffer.writeInt(this.maxRange);
      }

   }

   protected void entityInit() {}

   public boolean canBeCollidedWith() {
      return false;
   }

   public int getRange() {
      return this.maxRange;
   }
}
