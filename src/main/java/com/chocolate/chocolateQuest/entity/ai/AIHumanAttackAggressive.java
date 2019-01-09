package com.chocolate.chocolateQuest.entity.ai;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.ai.AIInteractBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.world.World;

public class AIHumanAttackAggressive extends AIInteractBase {

   protected World worldObj;
   protected int attackTick;
   protected float moveSpeed;
   protected boolean requireSight;
   protected boolean isPlayerTarget;
   PathEntity entityPathEntity;
   Class classTarget;
   private int pathFindingCooldown;


   public AIHumanAttackAggressive(EntityHumanBase par1EntityLiving, Class par2Class, float speed, boolean requiresSight) {
      this(par1EntityLiving, speed, requiresSight);
      this.classTarget = par2Class;
   }

   public AIHumanAttackAggressive(EntityHumanBase par1EntityLiving, float speed, boolean requireSight) {
      super(par1EntityLiving);
      this.isPlayerTarget = false;
      super.owner = par1EntityLiving;
      this.worldObj = par1EntityLiving.worldObj;
      this.moveSpeed = speed;
      this.requireSight = requireSight;
   }

   public boolean shouldExecute() {
      if(super.shouldExecute()) {
         EntityLivingBase var1 = super.owner.getAttackTarget();
         super.entityTarget = var1;
         return true;
      } else {
         return false;
      }
   }

   public boolean continueExecuting() {
      EntityLivingBase var1 = super.owner.getAttackTarget();
      return var1 == null?false:(var1 != super.entityTarget?false:super.entityTarget.isEntityAlive());
   }

   public void startExecuting() {
      if(super.entityTarget instanceof EntityPlayer) {
         this.isPlayerTarget = true;
      }

      this.pathFindingCooldown = 0;
   }

   public void resetTask() {
      super.resetTask();
      super.owner.setDefending(false);
      this.entityPathEntity = null;
   }

   public void updateTask() {
      double dist = super.owner.getDistanceSq(super.entityTarget.posX, super.entityTarget.boundingBox.minY, super.entityTarget.posZ);
      super.owner.getLookHelper().setLookPositionWithEntity(super.entityTarget, 30.0F, 30.0F);
      boolean canSeeTarget = super.owner.getEntitySenses().canSee(super.entityTarget);
      boolean shouldStayAway = false;
      boolean havePath = super.owner.onGround;
      if(dist <= super.owner.getDistanceToAttack() + this.getMinDistanceToInteract() && canSeeTarget) {
         this.getNavigator().clearPathEntity();
      } else if(--this.pathFindingCooldown <= 0) {
         int inteligenceLevel = super.owner.getInteligence();
         this.pathFindingCooldown = inteligenceLevel + super.owner.getRNG().nextInt(inteligenceLevel + 2);
         if(dist > 16.0D) {
            super.owner.startSprinting();
         }

         if(!this.tryToMoveToEntity()) {
            shouldStayAway = true;
         }

         if(shouldStayAway) {
            ;
         }
      }

      this.attackTarget(dist);
   }

   public boolean tryToMoveToEntity() {
      return this.tryMoveToXYZ(super.entityTarget.posX, super.entityTarget.posY, super.entityTarget.posZ, 1.0F);
   }
}
