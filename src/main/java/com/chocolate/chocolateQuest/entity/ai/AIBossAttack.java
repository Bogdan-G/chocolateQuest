package com.chocolate.chocolateQuest.entity.ai;

import com.chocolate.chocolateQuest.entity.boss.EntityBaseBoss;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class AIBossAttack extends EntityAIBase {

   World worldObj;
   EntityBaseBoss attacker;
   EntityLivingBase entityTarget;
   float moveSpeed;
   boolean needsVision;
   PathEntity pathEntity;
   private int attackTimer;


   public AIBossAttack(EntityBaseBoss par1EntityLiving, float speed, boolean par3) {
      this.attacker = par1EntityLiving;
      this.worldObj = par1EntityLiving.worldObj;
      this.moveSpeed = speed;
      this.needsVision = par3;
      this.setMutexBits(3);
   }

   public boolean shouldExecute() {
      EntityLivingBase entityliving = this.attacker.getAttackTarget();
      if(entityliving == null) {
         return false;
      } else {
         this.entityTarget = entityliving;
         return true;
      }
   }

   public boolean continueExecuting() {
      EntityLivingBase entityliving = this.attacker.getAttackTarget();
      return entityliving == null?false:(!entityliving.isEntityEqual(this.entityTarget) && this.attacker.getDistanceSqToEntity(entityliving) < this.attacker.getDistanceSqToEntity(this.entityTarget)?false:(!this.entityTarget.isEntityAlive()?false:this.attacker.isWithinHomeDistance(MathHelper.floor_double(this.entityTarget.posX), MathHelper.floor_double(this.entityTarget.posY), MathHelper.floor_double(this.entityTarget.posZ))));
   }

   public void startExecuting() {
      if(!this.attacker.isAttacking()) {
         this.attacker.getNavigator().setPath(this.pathEntity, (double)this.moveSpeed);
         this.attackTimer = 0;
      }

   }

   public void resetTask() {
      this.entityTarget = null;
      this.attacker.getNavigator().clearPathEntity();
   }

   public void updateTask() {
      if(!this.attacker.attackInProgress()) {
         this.attacker.getLookHelper().setLookPositionWithEntity(this.entityTarget, 30.0F, 30.0F);
      }

      int angle = (int)MathHelper.wrapAngleTo180_double(BDHelper.getAngleBetweenEntities(this.attacker, this.entityTarget) - (double)this.attacker.rotationYaw);
      double d1 = this.attacker.getDistanceSq(this.entityTarget.posX, this.entityTarget.boundingBox.minY, this.entityTarget.posZ);
      if(this.attacker.shouldMoveToEntity(d1, this.entityTarget)) {
         float speed = this.moveSpeed;
         if(angle != 0) {
            speed = 0.0F;
         }

         this.attacker.getMoveHelper().setMoveTo(this.entityTarget.posX, this.entityTarget.posY, this.entityTarget.posZ, (double)speed);
         if(this.attacker.isCollidedHorizontally && this.attacker.onGround) {
            this.attacker.getJumpHelper().setJumping();
            this.attacker.motionY = (double)(this.attacker.getScaleSize() / 20.0F);
         }
      } else {
         this.attacker.getNavigator().clearPathEntity();
      }

      this.attacker.attackEntity(this.entityTarget, (float)d1);
   }
}
