package com.chocolate.chocolateQuest.entity.ai;

import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.util.AxisAlignedBB;

public class AITargetHurtBy extends EntityAITarget {

   boolean alertCompanions;


   public AITargetHurtBy(EntityCreature par1EntityLiving, boolean par2) {
      super(par1EntityLiving, false);
      this.alertCompanions = par2;
      this.setMutexBits(1);
   }

   public boolean shouldExecute() {
      return this.isSuitableTarget(super.taskOwner.getAITarget(), true);
   }

   public void startExecuting() {
      super.taskOwner.setAttackTarget(super.taskOwner.getAITarget());
      if(this.alertCompanions) {
         double targetDistance = this.getTargetDistance();
         List list = super.taskOwner.worldObj.getEntitiesWithinAABB(super.taskOwner.getClass(), AxisAlignedBB.getBoundingBox(super.taskOwner.posX, super.taskOwner.posY, super.taskOwner.posZ, super.taskOwner.posX + 1.0D, super.taskOwner.posY + 1.0D, super.taskOwner.posZ + 1.0D).expand(targetDistance, 4.0D, targetDistance));
         Iterator iterator = list.iterator();

         while(iterator.hasNext()) {
            Entity entity = (Entity)iterator.next();
            EntityLiving entityliving = (EntityLiving)entity;
            if(super.taskOwner != entityliving && entityliving.getAttackTarget() == null) {
               entityliving.setAttackTarget(super.taskOwner.getAITarget());
            }
         }
      }

      super.startExecuting();
   }
}
