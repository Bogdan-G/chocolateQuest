package com.chocolate.chocolateQuest.entity.ai;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.ai.AIControlledBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class AIControlledFollowOwner extends AIControlledBase {

   protected EntityLivingBase target;
   World theWorld;
   private PathNavigate ownerPathfinder;
   float maxDist;
   float minDist;
   private boolean teleports = false;
   private int pathFindingCooldown;


   public AIControlledFollowOwner(EntityHumanBase par1EntityTameable, float minDist, float maxDist) {
      super(par1EntityTameable);
      this.theWorld = par1EntityTameable.worldObj;
      this.ownerPathfinder = par1EntityTameable.getNavigator();
      this.minDist = minDist;
      this.maxDist = maxDist;
      this.setMutexBits(4);
      this.teleports = false;
   }

   public boolean shouldExecute() {
      EntityLivingBase entityliving = this.getOwner();
      if(entityliving == null) {
         return false;
      } else {
         double dist = super.owner.getDistanceSqToEntity(entityliving);
         double minDist = (double)(this.minDist * this.minDist);
         if(super.owner.getAttackTarget() == null) {
            if(dist < minDist) {
               return false;
            }
         } else if(dist < minDist * 3.0D) {
            return false;
         }

         this.target = entityliving;
         return true;
      }
   }

   public EntityLivingBase getOwner() {
      return (EntityLivingBase)super.owner.getOwner();
   }

   public boolean continueExecuting() {
      return !this.getNavigator().noPath() && super.owner.getDistanceSqToEntity(this.target) > (double)(this.minDist * this.minDist);
   }

   public void startExecuting() {
      this.pathFindingCooldown = 0;
   }

   public void resetTask() {
      this.target = null;
      this.getNavigator().clearPathEntity();
   }

   public void updateTask() {
      super.owner.getLookHelper().setLookPositionWithEntity(this.target, 10.0F, (float)super.owner.getVerticalFaceSpeed());
      if(--this.pathFindingCooldown <= 0) {
         this.pathFindingCooldown = 10;
         double dist = super.owner.getDistanceSqToEntity(this.target);
         if(dist > 100.0D) {
            super.owner.startSprinting();
         }

         if(!this.tryMoveToXYZ(this.target.posX, this.target.posY, this.target.posZ, 1.15F) && dist >= (double)(this.maxDist * this.maxDist)) {
            int i = MathHelper.floor_double(this.target.posX) - 2;
            int j = MathHelper.floor_double(this.target.posZ) - 2;
            int k = MathHelper.floor_double(this.target.boundingBox.minY);

            for(int l = 0; l <= 4; ++l) {
               for(int i1 = 0; i1 <= 4; ++i1) {
                  if((l < 1 || i1 < 1 || l > 3 || i1 > 3) && World.doesBlockHaveSolidTopSurface(this.theWorld, i + l, k - 1, j + i1) && !this.theWorld.isBlockNormalCubeDefault(i + l, k, j + i1, true) && !this.theWorld.isBlockNormalCubeDefault(i + l, k + 1, j + i1, true)) {
                     if(super.owner.ridingEntity != null) {
                        super.owner.ridingEntity.setLocationAndAngles((double)((float)(i + l) + 0.5F), (double)k, (double)((float)(j + i1) + 0.5F), super.owner.rotationYaw, super.owner.rotationPitch);
                     }

                     super.owner.setLocationAndAngles((double)((float)(i + l) + 0.5F), (double)k, (double)((float)(j + i1) + 0.5F), super.owner.rotationYaw, super.owner.rotationPitch);
                     this.ownerPathfinder.clearPathEntity();
                     return;
                  }
               }
            }
         }
      }

   }
}
