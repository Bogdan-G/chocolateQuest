package com.chocolate.chocolateQuest.entity.ai;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.ai.AIControlledBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class AIControlledFormation extends AIControlledBase {

   protected World worldObj;
   protected int attackCooldown;
   double x = 0.0D;
   double y = 0.0D;
   double z = 0.0D;


   public AIControlledFormation(EntityHumanBase par1EntityLiving) {
      super(par1EntityLiving);
      this.worldObj = par1EntityLiving.worldObj;
      this.setMutexBits(3);
   }

   public boolean shouldExecute() {
      if(super.owner.getLeader() == null) {
         return false;
      } else {
         EntityLivingBase leader = super.owner.getLeader();
         Vec3 absPosition = leader.getLookVec();
         float angle = (float)super.owner.partyPositionAngle * 3.1416F / 180.0F;
         double cos = (double)MathHelper.cos(angle);
         double sin = (double)MathHelper.sin(angle);
         int dist = super.owner.partyDistanceToLeader;
         this.x = leader.posX + (absPosition.xCoord * cos - absPosition.zCoord * sin) * (double)dist;
         this.y = leader.posY;
         this.z = leader.posZ + (absPosition.zCoord * cos + absPosition.xCoord * sin) * (double)dist;
         Object distCheckEntity = super.owner.ridingEntity != null?super.owner.ridingEntity:super.owner;
         double distToPoint = ((Entity)distCheckEntity).getDistanceSq(this.x, this.y, this.z);
         if(distToPoint > 9.0D) {
            super.owner.startSprinting();
         }

         return distToPoint > (double)(((Entity)distCheckEntity).width * ((Entity)distCheckEntity).width);
      }
   }

   public void startExecuting() {
      this.getNavigator().clearPathEntity();
   }

   public void resetTask() {
      if(super.owner.getLeader() != null) {
         super.owner.rotationYaw = super.owner.getLeader().rotationYawHead;
         super.owner.rotationYawHead = super.owner.getLeader().rotationYawHead;
      }

      this.getNavigator().clearPathEntity();
   }

   public void updateTask() {
      this.tryMoveToXYZ(this.x, this.y, this.z, 1.15F);
   }

   public boolean stayInFormation() {
      return false;
   }
}
