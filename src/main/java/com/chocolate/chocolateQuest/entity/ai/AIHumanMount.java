package com.chocolate.chocolateQuest.entity.ai;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.ai.AIInteractBase;
import net.minecraft.entity.EntityLivingBase;

public class AIHumanMount extends AIInteractBase {

   boolean requireSight;


   public AIHumanMount(EntityHumanBase par1EntityLiving, float moveSpeed, boolean par3) {
      super(par1EntityLiving);
      this.setMutexBits(3);
      this.requireSight = par3;
   }

   public boolean shouldExecute() {
      if(super.owner.entityToMount != null) {
         super.entityTarget = super.owner.entityToMount;
         return true;
      } else {
         EntityLivingBase var1 = super.owner.getAttackTarget();
         if(var1 == null) {
            return false;
         } else {
            boolean suitableMount = super.owner.isSuitableMount(var1);
            if(super.owner.isSuitableMount(var1) && super.owner.ridingEntity == null && var1.riddenByEntity == null) {
               super.entityTarget = var1;
               return true;
            } else {
               if(suitableMount) {
                  super.owner.setAttackTarget((EntityLivingBase)null);
               }

               return false;
            }
         }
      }
   }

   public boolean continueExecuting() {
      return super.owner.ridingEntity == null && super.entityTarget.riddenByEntity == null?(super.entityTarget == null?false:(!super.entityTarget.isEntityAlive()?false:(!this.requireSight?!super.owner.getNavigator().noPath():true))):false;
   }

   public void resetTask() {
      super.owner.setAttackTarget((EntityLivingBase)null);
      super.entityTarget = null;
      super.owner.getNavigator().clearPathEntity();
      super.owner.entityToMount = null;
   }

   public void updateTask() {
      super.owner.getLookHelper().setLookPositionWithEntity(super.entityTarget, 30.0F, 30.0F);
      if(this.requireSight || super.owner.getEntitySenses().canSee(super.entityTarget)) {
         super.owner.getNavigator().tryMoveToEntityLiving(super.entityTarget, 1.0D);
      }

      super.owner.attackTime = Math.max(super.owner.attackTime - 1, 0);
      double bounds = (double)(super.owner.width * 2.0F * super.entityTarget.width * 2.0F);
      double dist = super.owner.getDistanceSq(super.entityTarget.posX, super.entityTarget.boundingBox.minY, super.entityTarget.posZ);
      if(dist <= bounds) {
         super.owner.mountEntity(super.entityTarget);
         super.owner.setMountAI();
         super.owner.setAttackTarget((EntityLivingBase)null);
      }

   }
}
