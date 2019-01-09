package com.chocolate.chocolateQuest.entity.ai;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.ai.AIInteractBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;

public class AIHumanFlee extends AIInteractBase {

   private int attackCooldown;
   float moveSpeed;
   int x;
   int y;
   int z;
   int pathCD = 0;


   public AIHumanFlee(EntityHumanBase par1EntityLiving, float speed) {
      super(par1EntityLiving);
      this.moveSpeed = speed;
   }

   public boolean shouldExecute() {
      return super.shouldExecute();
   }

   public void startExecuting() {
      super.startExecuting();
      this.attackCooldown = 0;
   }

   public void resetTask() {
      super.resetTask();
   }

   public void updateTask() {
      double dist = super.owner.getDistanceSqToEntity(super.entityTarget);
      if(this.pathCD > 0) {
         --this.pathCD;
      }

      if(dist < 128.0D) {
         if(this.pathCD == 0) {
            Vec3 direction = Vec3.createVectorHelper(super.owner.posX + (super.owner.posX - super.entityTarget.posX), super.owner.posY + (super.owner.posY - super.entityTarget.posY), super.owner.posZ + (super.owner.posZ - super.entityTarget.posZ));
            Vec3 vec3 = RandomPositionGenerator.findRandomTargetBlockTowards(super.owner, 10, 10, direction);
            if(vec3 != null) {
               this.getNavigator().tryMoveToXYZ(vec3.xCoord, vec3.yCoord, vec3.zCoord, 1.2D);
               this.pathCD = 20;
            }
         }
      } else {
         this.stayInFormation();
      }

   }
}
