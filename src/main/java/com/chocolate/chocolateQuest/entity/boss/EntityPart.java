package com.chocolate.chocolateQuest.entity.boss;

import com.chocolate.chocolateQuest.entity.boss.EntityBaseBoss;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityPart extends Entity implements IEntityAdditionalSpawnData {

   EntityBaseBoss mainBody;
   int maxHealth;
   public boolean staticPart;
   public float rotationYawOffset;
   public float distanceToMainBody;
   public float heightOffset;
   int partID;
   int ownerID;


   public EntityPart(World world) {
      super(world);
      this.maxHealth = 100;
      this.staticPart = true;
      this.rotationYawOffset = 0.0F;
      this.distanceToMainBody = 0.0F;
      this.heightOffset = 0.0F;
      this.partID = 0;
      this.ownerID = 0;
      super.isImmuneToFire = true;
      this.setSize(1.0F, 1.0F);
   }

   public EntityPart(World world, EntityBaseBoss main, int partID, float rotationYawOffset, float distanceToMainBody) {
      this(world);
      this.rotationYawOffset = rotationYawOffset;
      this.distanceToMainBody = distanceToMainBody;
      this.partID = partID;
      this.mainBody = main;
      if(main != null) {
         this.setPosition(main.posX, main.posY, main.posZ);
      }

   }

   public EntityPart(World world, EntityBaseBoss main, int partID, float rotationYawOffset, float distanceToMainBody, float heightOffset) {
      this(world, main, partID, rotationYawOffset, distanceToMainBody);
      this.heightOffset = heightOffset;
   }

   public void setSize(float par1, float par2) {
      super.setSize(par1, par2);
   }

   protected void entityInit() {}

   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
      if(!super.worldObj.isRemote && this.mainBody != null) {
         Entity e = par1DamageSource.getEntity();
         boolean ret = this.mainBody.attackFromPart(par1DamageSource, par2, this);
         if(ret && e instanceof EntityPlayer) {
            ItemStack is = ((EntityPlayer)e).getCurrentEquippedItem();
            if(is != null) {
               is.getItem().hitEntity(is, this.mainBody, (EntityPlayer)e);
            }
         }

         return ret;
      } else {
         return false;
      }
   }

   public void onUpdate() {
      if(!super.worldObj.isRemote) {
         if(this.mainBody == null) {
            Entity hx = super.worldObj.getEntityByID(this.ownerID);
            if(hx instanceof EntityBaseBoss) {
               this.setMainBody((EntityBaseBoss)hx);
            } else {
               this.setDead();
            }
         } else if(this.mainBody.isDead) {
            this.setDead();
         }
      }

      super.onUpdate();
      if(this.mainBody != null) {
         super.motionX = this.mainBody.motionX;
         super.motionZ = this.mainBody.motionZ;
         super.motionY = this.mainBody.motionY;
         if(this.staticPart) {
            double hx1 = -Math.sin(Math.toRadians((double)(this.mainBody.rotationYaw + this.rotationYawOffset))) * (double)this.distanceToMainBody;
            double hz = Math.cos(Math.toRadians((double)(this.mainBody.rotationYaw + this.rotationYawOffset))) * (double)this.distanceToMainBody;
            this.setPositionAndRotation(this.mainBody.posX + hx1 + super.motionX, this.mainBody.posY + (double)this.heightOffset + super.motionY, this.mainBody.posZ + hz + super.motionZ, this.mainBody.rotationYaw, this.mainBody.rotationPitch);
         }

         if(this.mainBody.motionY > 0.0D) {
            super.posY += 20.0D;
         }
      }

   }

   public void setPosition(double x, double y, double z) {
      super.setPosition(x, y, z);
   }

   public void setPositionAndRotation(double x, double y, double z, float yaw, float pitch) {
      super.setPositionAndRotation(x, y, z, yaw, pitch);
   }

   public boolean canBeCollidedWith() {
      return true;
   }

   public boolean canBePushed() {
      return false;
   }

   public void setMainBody(EntityBaseBoss body) {
      this.mainBody = body;
      this.mainBody.setPart(this, this.partID);
   }

   public EntityBaseBoss getMainBody() {
      return this.mainBody;
   }

   public boolean isEntityEqual(Entity par1Entity) {
      return this == par1Entity || this.mainBody == par1Entity;
   }

   protected void readEntityFromNBT(NBTTagCompound var1) {
      this.setDead();
   }

   protected void writeEntityToNBT(NBTTagCompound var1) {}

   public void readSpawnData(ByteBuf additionalData) {
      if(!additionalData.readBoolean()) {
         int id = additionalData.readInt();
         Entity e = super.worldObj.getEntityByID(id);
         this.distanceToMainBody = additionalData.readFloat();
         this.rotationYawOffset = additionalData.readFloat();
         this.heightOffset = additionalData.readFloat();
         this.staticPart = additionalData.readByte() == 1;
         this.partID = additionalData.readByte();
         if(e instanceof EntityBaseBoss) {
            this.setMainBody((EntityBaseBoss)e);
         }

      }
   }

   public void writeSpawnData(ByteBuf buffer) {
      buffer.writeBoolean(this.mainBody == null);
      if(this.mainBody != null) {
         buffer.writeInt(this.mainBody.getEntityId());
         buffer.writeFloat(this.distanceToMainBody);
         buffer.writeFloat(this.rotationYawOffset);
         buffer.writeFloat(this.heightOffset);
         buffer.writeByte(this.staticPart?1:0);
         buffer.writeByte(this.partID);
      }

   }

   public void setDead() {
      super.setDead();
   }
}
