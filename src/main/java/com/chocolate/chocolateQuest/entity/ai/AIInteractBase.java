package com.chocolate.chocolateQuest.entity.ai;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.ai.AIControlledBase;
import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class AIInteractBase extends AIControlledBase {

   protected EntityLivingBase entityTarget;


   public AIInteractBase(EntityHumanBase par1EntityLiving) {
      super(par1EntityLiving);
      this.setMutexBits(11);
   }

   public boolean shouldExecute() {
      EntityLivingBase entityliving = super.owner.getAttackTarget();
      if(entityliving == null) {
         return false;
      } else if(!entityliving.isEntityAlive()) {
         super.owner.setAttackTarget((EntityLivingBase)null);
         return false;
      } else if(super.owner.isSuitableTargetAlly(entityliving)) {
         return false;
      } else {
         this.entityTarget = entityliving;
         return true;
      }
   }

   public void resetTask() {
      this.entityTarget = null;
   }

   public void updateTask() {
      double x = 0.0D;
      double y = 0.0D;
      double z = 0.0D;
      EntityLivingBase leader = super.owner.getLeader();
      Vec3 absPosition = leader.getLookVec();
      float angle = (float)super.owner.partyPositionAngle * 3.1416F / 180.0F;
      double cos = (double)MathHelper.cos(angle);
      double sin = (double)MathHelper.sin(angle);
      int dist = super.owner.partyDistanceToLeader;
      x = leader.posX + (absPosition.xCoord * cos - absPosition.zCoord * sin) * (double)dist;
      y = leader.posY;
      z = leader.posZ + (absPosition.zCoord * cos + absPosition.xCoord * sin) * (double)dist;
      this.tryMoveToXYZ(x, y, z, 1.2F);
   }

   public boolean attackTarget(double distance) {
      super.owner.attackTime = Math.max(super.owner.attackTime - 1, 0);
      double sumLengthBB = this.getMinDistanceToInteract() + super.owner.getAttackRangeBonus();
      if(super.owner.haveShied()) {
         double target;
         if(this.entityTarget instanceof EntityPlayer) {
            target = sumLengthBB * (double)(2.0F + (float)(3 - super.owner.worldObj.difficultySetting.ordinal()));
         } else {
            target = 0.0D;
         }

         if(super.owner.isDefending() && distance <= target && super.owner.attackTime <= 10 && super.owner.hurtTime == 0) {
            super.owner.setDefending(false);
         }
      }

      if(distance <= sumLengthBB) {
         super.owner.attackEntity(this.entityTarget);
      } else if(super.owner.isRanged()) {
         EntityLivingBase target1 = this.getFrontTarget();
         if(target1 != null && super.owner.isSuitableTargetAlly(target1) && target1 != super.owner.ridingEntity) {
            float moveStrafing = -MathHelper.sin((float)super.owner.partyPositionAngle) / 4.0F;
            double ry = -Math.toRadians(super.owner.rotationYaw + super.owner.moveStrafing > 0.0F?90.0D:-90.0D);
            int x = MathHelper.floor_double(super.owner.posX - Math.sin(ry) * 6.0D);
            int z = MathHelper.floor_double(super.owner.posZ + Math.cos(ry) * 6.0D);
            Material mat = super.owner.worldObj.getBlock(x, MathHelper.floor_double(super.owner.posY) - 1, z).getMaterial();
            boolean move = false;
            if(mat != Material.air && mat != Material.lava && mat.isSolid()) {
               move = true;
            } else {
               mat = super.owner.worldObj.getBlock(x, MathHelper.floor_double(super.owner.posY) - 2, z).getMaterial();
               if(mat.isSolid()) {
                  move = true;
               }
            }

            if(move) {
               super.owner.moveStrafing = moveStrafing;
            } else {
               super.owner.moveStrafing = 0.0F;
            }
         } else {
            super.owner.moveStrafing = 0.0F;
            super.owner.getLookHelper().setLookPositionWithEntity(this.entityTarget, 30.0F, 30.0F);
            if(!super.owner.attackEntityWithRangedAttack(this.entityTarget, (float)distance)) {
               return false;
            }
         }
      }

      return true;
   }

   public double getMinDistanceToInteract() {
      double attackerBB = (double)(super.owner.width * 2.2F);
      if(super.owner.ridingEntity != null) {
         attackerBB += (double)(super.owner.ridingEntity.width * 2.0F);
      }

      double targetBB = (double)(this.entityTarget.width * 2.2F);
      if(this.entityTarget.ridingEntity != null) {
         targetBB += (double)this.entityTarget.ridingEntity.width;
      }

      return attackerBB * targetBB + super.owner.rightHand.getAttackRangeBonus();
   }

   public EntityLivingBase getFrontTarget() {
      EntityLivingBase target = null;
      double arrowMotionX = this.entityTarget.posX - super.owner.posX;
      double arrowMotionZ = this.entityTarget.posZ - super.owner.posZ;
      float tRot = super.owner.rotationYaw;
      super.owner.rotationYaw = (float)(Math.atan2(arrowMotionZ, arrowMotionX) * 180.0D / 3.141592653589793D) - 90.0F;
      Object mop = null;
      double dist = 30.0D;
      float yOffset = 0.0F;
      Vec3 playerPos = Vec3.createVectorHelper(super.owner.posX, super.owner.posY - (double)yOffset, super.owner.posZ);
      Vec3 look = super.owner.getLookVec();
      Vec3 playerView = playerPos.addVector(look.xCoord * dist, look.yCoord * dist, look.zCoord * dist);
      List list = super.owner.worldObj.getEntitiesWithinAABBExcludingEntity(super.owner, super.owner.boundingBox.addCoord(look.xCoord * dist, look.yCoord * dist, look.zCoord * dist).expand(1.0D, 1.0D, 1.0D));
      dist *= dist;

      for(int j = 0; j < list.size(); ++j) {
         Entity entity1 = (Entity)list.get(j);
         if(entity1 instanceof EntityLivingBase && entity1.canBeCollidedWith()) {
            float f2 = 0.4F;
            AxisAlignedBB axisalignedbb = entity1.boundingBox.expand((double)entity1.width, (double)entity1.height, (double)entity1.width);
            MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(playerPos, playerView);
            if(movingobjectposition1 != null) {
               double tDist = super.owner.getDistanceSqToEntity(entity1);
               if(tDist < dist && target != super.owner.ridingEntity && target != super.owner.riddenByEntity) {
                  dist = tDist;
                  target = (EntityLivingBase)entity1;
               }
            }
         }
      }

      return target;
   }
}
