package com.chocolate.chocolateQuest.entity.ai;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIControlledByPlayer;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.MathHelper;

public class AIAnimalMountedByEntity extends EntityAIControlledByPlayer {

   private EntityLiving entityHost;
   private final float maxSpeed;
   private float currentSpeed = 0.0F;
   float acceleration = 0.02F;
   EntityHumanBase rider;
   float prevRotation = 0.0F;


   public AIAnimalMountedByEntity(EntityLiving par1EntityLiving, float maxSpeed) {
      super(par1EntityLiving, maxSpeed);
      this.entityHost = par1EntityLiving;
      this.maxSpeed = maxSpeed;
   }

   public boolean shouldExecute() {
      if(!(this.entityHost.riddenByEntity instanceof EntityHumanBase)) {
         return false;
      } else {
         EntityHumanBase e = (EntityHumanBase)this.entityHost.riddenByEntity;
         if(e.currentPos != null) {
            return false;
         } else {
            this.rider = e;
            return false;
         }
      }
   }

   public void resetTask() {
      this.currentSpeed = 0.0F;
   }

   public void updateTask() {
      if(this.entityHost.getNavigator().getPath() != null) {
         this.entityHost.getNavigator().clearPathEntity();
      }

      float rotVariation = MathHelper.wrapAngleTo180_float(this.rider.rotationYawHead - this.prevRotation);
      float minVariation = 5.0F;
      if(rotVariation > minVariation) {
         rotVariation = minVariation;
      }

      if(rotVariation < -minVariation) {
         rotVariation = -minVariation;
      }

      this.entityHost.rotationYaw = MathHelper.wrapAngleTo180_float(this.prevRotation + rotVariation);
      this.rider.rotationYaw = this.entityHost.rotationYaw;
      this.prevRotation = this.entityHost.rotationYaw;
      this.updateAcceleration();
      this.currentSpeed += this.acceleration;
      this.currentSpeed = Math.max(this.currentSpeed, 0.01F);
      this.currentSpeed = Math.min(this.currentSpeed, this.maxSpeed);
      this.checkToJump(this.currentSpeed);
      this.entityHost.moveEntityWithHeading(0.0F, this.currentSpeed);
   }

   public void updateAcceleration() {
      EntityHumanBase rider = (EntityHumanBase)this.entityHost.riddenByEntity;
      if(rider.getAttackTarget() != null) {
         EntityLivingBase target = rider.getAttackTarget();
         double rotDiff = BDHelper.getAngleBetweenEntities(this.entityHost, target);
         double rot = MathHelper.wrapAngleTo180_double(rotDiff - (double)this.entityHost.rotationYaw);
         rot = Math.abs(rot);
         if(rot > 20.0D) {
            this.acceleration = -0.01F;
         } else {
            this.acceleration = 0.025F;
            this.boostSpeed();
         }
      }

   }

   public void checkToJump(float currentSpeedTemp) {
      int i = MathHelper.floor_double(this.entityHost.posX);
      int j = MathHelper.floor_double(this.entityHost.posY);
      int k = MathHelper.floor_double(this.entityHost.posZ);
      float despX = MathHelper.sin(this.entityHost.rotationYaw * 3.1415927F / 180.0F);
      float despZ = MathHelper.cos(this.entityHost.rotationYaw * 3.1415927F / 180.0F);
      float f7 = this.entityHost.getAIMoveSpeed() * currentSpeedTemp / Math.max(currentSpeedTemp, 1.0F);
      currentSpeedTemp *= f7;
      despX = -(currentSpeedTemp * despX);
      despZ = currentSpeedTemp * despZ;
      if(MathHelper.abs(despX) > MathHelper.abs(despZ)) {
         if(despX < 0.0F) {
            despX -= this.entityHost.width / 2.0F;
         }

         if(despX > 0.0F) {
            despX += this.entityHost.width / 2.0F;
         }

         despZ = 0.0F;
      } else {
         despX = 0.0F;
         if(despZ < 0.0F) {
            despZ -= this.entityHost.width / 2.0F;
         }

         if(despZ > 0.0F) {
            despZ += this.entityHost.width / 2.0F;
         }
      }

      int xNext = MathHelper.floor_double(this.entityHost.posX + (double)despX);
      int zNext = MathHelper.floor_double(this.entityHost.posZ + (double)despZ);
      PathPoint pathpoint = new PathPoint(MathHelper.floor_float(this.entityHost.width + 1.0F), MathHelper.floor_float(this.entityHost.height + this.rider.height + 1.0F), MathHelper.floor_float(this.entityHost.width + 1.0F));
      if(i != xNext || k != zNext) {
         Block block = this.entityHost.worldObj.getBlock(i, j, k);
         Block nextBlockID = this.entityHost.worldObj.getBlock(i, j - 1, k);
         boolean flag = this.isBlockHalf(block) && this.isBlockHalf(nextBlockID);
         if(!flag && PathFinder.func_82565_a(this.entityHost, xNext, j, zNext, pathpoint, false, false, true) == 0 && PathFinder.func_82565_a(this.entityHost, i, j + 1, k, pathpoint, false, false, true) == 1 && PathFinder.func_82565_a(this.entityHost, xNext, j + 1, zNext, pathpoint, false, false, true) == 1) {
            this.entityHost.getJumpHelper().setJumping();
         }
      }

   }

   private boolean isBlockHalf(Block par1) {
      return par1.getRenderType() == 10 || par1 instanceof BlockSlab;
   }
}
