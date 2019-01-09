package com.chocolate.chocolateQuest.entity.ai;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.ai.AIInteractBase;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class AIHumanAttackEvasive extends AIInteractBase {

   World worldObj;


   public AIHumanAttackEvasive(EntityHumanBase par1EntityLiving, float par2) {
      super(par1EntityLiving);
      super.owner = par1EntityLiving;
      this.worldObj = par1EntityLiving.worldObj;
   }

   public boolean shouldExecute() {
      if(super.shouldExecute()) {
         EntityLivingBase entityliving = super.owner.getAttackTarget();
         if(super.owner.party != null && super.owner.party.getLeader() != super.owner) {
            double distToleader = super.owner.getDistanceSqToEntity(super.owner.party.getLeader());
            if(distToleader > (double)(super.owner.partyDistanceToLeader * super.owner.partyDistanceToLeader * Math.max(1, 16 - super.owner.partyDistanceToLeader))) {
               return false;
            }
         }

         super.entityTarget = entityliving;
         return true;
      } else {
         return false;
      }
   }

   public boolean continueExecuting() {
      return this.shouldExecute() && super.continueExecuting();
   }

   public void resetTask() {
      super.resetTask();
      super.owner.getNavigator().clearPathEntity();
      super.owner.moveForwardHuman = 0.0F;
   }

   public void updateTask() {
      double distance = super.owner.getDistanceSq(super.entityTarget.posX, super.entityTarget.boundingBox.minY, super.entityTarget.posZ);
      boolean canSee = super.owner.getEntitySenses().canSee(super.entityTarget);
      super.owner.getLookHelper().setLookPositionWithEntity(super.entityTarget, 0.0F, 0.0F);
      super.owner.rotationYaw = super.owner.rotationYawHead;
      super.owner.moveForwardHuman = -1.0E-4F;
      boolean stayInFormation = false;
      if(canSee && distance < 64.0D) {
         double ry = Math.toRadians((double)(super.owner.rotationYaw - 180.0F));
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
            super.owner.moveForwardHuman = -0.1F;
         }
      } else if(super.owner.party != null) {
         this.stayInFormation();
         stayInFormation = true;
      }

      if(!this.attackTarget(distance)) {
         if(!stayInFormation) {
            this.tryMoveToXYZ(super.entityTarget.posX, super.entityTarget.posY, super.entityTarget.posZ, 1.0F);
         } else if(!stayInFormation) {
            this.getNavigator().clearPathEntity();
         }
      }

   }
}
