package com.chocolate.chocolateQuest.entity.ai;

import com.chocolate.chocolateQuest.entity.boss.EntityBaseBoss;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.MathHelper;

public class AIFlyRoam extends EntityAIBase {

   private EntityBaseBoss owner;
   protected int defaultFlyHeight;
   int roamDistance = 60;
   float distanceMoved;
   int destX;
   int destY;
   int destZ;


   public AIFlyRoam(EntityBaseBoss par1EntityLiving, int flyHeight) {
      this.owner = par1EntityLiving;
      this.setMutexBits(4);
      this.defaultFlyHeight = flyHeight;
   }

   public boolean shouldExecute() {
      return this.owner.getAttackTarget() == null;
   }

   public boolean continueExecuting() {
      return null == this.owner.getAttackTarget();
   }

   public void startExecuting() {
      this.changeDest();
      super.startExecuting();
   }

   public void updateTask() {
      double px = this.owner.posX - (double)this.destX;
      double pz = this.owner.posZ - (double)this.destZ;
      double py = this.owner.posY - 90.0D;
      double angleEntityHome = -MathHelper.wrapAngleTo180_double(Math.toDegrees(Math.atan2(px, pz)) - 180.0D);
      double rotDiff = angleEntityHome - MathHelper.wrapAngleTo180_double((double)this.owner.rotationYaw);
      float rotAngle = 4.0F;
      float rotSpeed = 4.1F;
      if(rotDiff > (double)rotAngle || rotDiff < (double)(-rotAngle)) {
         this.owner.rotationYaw = this.owner.prevRotationYaw + rotSpeed;
      }

      double moveSpeed = this.owner.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
      double ry = Math.toRadians((double)(this.owner.rotationYaw - 180.0F));
      this.owner.motionX = Math.sin(ry) * moveSpeed;
      this.owner.motionZ = -Math.cos(ry) * moveSpeed;
      this.handleMotionY();
      if(this.owner.posX <= (double)(this.destX + 5) && this.owner.posX >= (double)(this.destX - 5) && this.owner.posZ <= (double)(this.destZ + 5) && this.owner.posZ >= (double)(this.destZ - 5)) {
         this.changeDest();
      }

   }

   protected void handleMotionY() {
      float d = MathHelper.cos(this.distanceMoved / 5.0F) * 2.0F;
      int blockX = MathHelper.floor_double(this.owner.posX + this.owner.motionX * 10.0D);
      int blockZ = MathHelper.floor_double(this.owner.posZ + this.owner.motionZ * 10.0D);
      int flyHeight = this.defaultFlyHeight;
      if(this.owner.posY > (double)this.owner.worldObj.getTopSolidOrLiquidBlock(MathHelper.floor_double(this.owner.posX), MathHelper.floor_double(this.owner.posZ))) {
         flyHeight = this.owner.worldObj.getTopSolidOrLiquidBlock(blockX, blockZ) + 8;
      }

      double extraMotionY = 0.0D;
      if(this.owner.posY < (double)flyHeight) {
         if((double)this.owner.rotationPitch > -0.3D) {
            this.owner.rotationPitch += 0.01F;
         }

         extraMotionY += 0.4000000059604645D;
         this.owner.motionX /= 2.0D;
         this.owner.motionZ /= 2.0D;
      } else if(this.owner.posY > (double)(flyHeight + 5)) {
         if((double)this.owner.rotationPitch < 0.3D) {
            this.owner.rotationPitch -= 0.01F;
         }

         extraMotionY -= 0.4000000059604645D;
         this.owner.motionX /= 2.0D;
         this.owner.motionZ /= 2.0D;
      } else {
         this.owner.rotationPitch = (float)Math.toDegrees((double)d);
      }

      double moveSpeed = this.owner.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
      this.owner.motionY = -Math.sin((double)this.owner.rotationPitch) * moveSpeed / 5.0D + extraMotionY;
      this.distanceMoved = (float)((double)this.distanceMoved + Math.abs(this.owner.motionX) + Math.abs(this.owner.motionZ));
   }

   protected void changeDest() {
      if(this.owner.hasHome()) {
         this.destX = this.owner.getHomePosition().posX + this.owner.getRNG().nextInt(this.roamDistance * 2) - this.roamDistance;
         this.destZ = this.owner.getHomePosition().posZ + this.owner.getRNG().nextInt(this.roamDistance * 2) - this.roamDistance;
      } else {
         this.destX = MathHelper.floor_double(this.owner.posX) + this.owner.getRNG().nextInt(this.roamDistance * 2) - this.roamDistance;
         this.destZ = MathHelper.floor_double(this.owner.posZ) + this.owner.getRNG().nextInt(this.roamDistance * 2) - this.roamDistance;
      }

   }

   public void resetTask() {
      this.destX = this.owner.getHomePosition().posX;
      this.destZ = this.owner.getHomePosition().posZ;
      super.resetTask();
   }
}
