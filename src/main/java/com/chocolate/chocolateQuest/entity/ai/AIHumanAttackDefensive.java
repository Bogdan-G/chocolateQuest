package com.chocolate.chocolateQuest.entity.ai;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.ai.AIInteractBase;
import com.chocolate.chocolateQuest.entity.ai.EnumAiState;
import net.minecraft.entity.EntityLivingBase;

public class AIHumanAttackDefensive extends AIInteractBase {

   private int attackCooldown;
   float moveSpeed;


   public AIHumanAttackDefensive(EntityHumanBase par1EntityLiving, float speed) {
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
      boolean canSeeTarget = super.owner.getEntitySenses().canSee(super.entityTarget);
      super.owner.getLookHelper().setLookPositionWithEntity(super.entityTarget, 30.0F, 30.0F);
      double dist = super.owner.getDistanceSq(super.entityTarget.posX, super.entityTarget.boundingBox.minY, super.entityTarget.posZ);
      boolean goToTarget = true;
      if(super.owner.getLeader() != null) {
         if(super.owner.getLeader().getDistanceToEntity(super.entityTarget) > (float)(super.owner.partyDistanceToLeader * super.owner.partyDistanceToLeader)) {
            this.stayInFormation();
            goToTarget = false;
         }
      } else if((super.owner.AIMode == EnumAiState.WARD.ordinal() || super.owner.AIMode == EnumAiState.PATH.ordinal()) && super.owner.hasHome() && !super.owner.isWithinHomeDistanceCurrentPosition()) {
         goToTarget = false;
         super.owner.setAttackTarget((EntityLivingBase)null);
      }

      if(goToTarget) {
         boolean havePath = super.owner.onGround;
         if(canSeeTarget && --this.attackCooldown <= 0) {
            if(dist > 16.0D) {
               super.owner.startSprinting();
            }

            this.getNavigator().tryMoveToEntityLiving(super.entityTarget, (double)this.moveSpeed);
         }
      }

      if(canSeeTarget) {
         this.attackTarget(dist);
      }

   }
}
