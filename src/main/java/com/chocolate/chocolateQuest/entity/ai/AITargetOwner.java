package com.chocolate.chocolateQuest.entity.ai;

import com.chocolate.chocolateQuest.utils.PlayerManager;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.player.EntityPlayer;

public class AITargetOwner extends EntityAITarget {

   EntityLiving theEntity;
   EntityLivingBase theTarget;


   public AITargetOwner(EntityCreature par1EntityTameable) {
      super(par1EntityTameable, false);
      this.theEntity = par1EntityTameable;
      this.setMutexBits(1);
   }

   public boolean shouldExecute() {
      this.theTarget = this.getTarget();
      return this.theTarget != null?(this.theTarget == this.theEntity.ridingEntity?false:this.isSuitableTarget(this.theTarget, false)):false;
   }

   public EntityLivingBase getTarget() {
      EntityLivingBase entityliving = (EntityLivingBase)((IEntityOwnable)this.theEntity).getOwner();
      if(entityliving == null) {
         return null;
      } else {
         boolean flag = false;
         if(entityliving instanceof EntityPlayer) {
            EntityLivingBase target = PlayerManager.getTarget((EntityPlayer)entityliving);
            if(target != null) {
               return target;
            }
         }

         return entityliving.getAITarget() != null?entityliving.getAITarget():(entityliving.getLastAttacker() != null?entityliving.getLastAttacker():(entityliving instanceof EntityLiving?((EntityLiving)entityliving).getAttackTarget():null));
      }
   }

   public void startExecuting() {
      super.taskOwner.setAttackTarget(this.theTarget);
      super.startExecuting();
   }
}
