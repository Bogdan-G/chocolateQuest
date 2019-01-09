package com.chocolate.chocolateQuest.entity.ai;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.ai.EnumAiState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIWatchClosest;

public class AIHumanIdleTalkClosest extends EntityAIWatchClosest {

   EntityHumanBase owner;


   public AIHumanIdleTalkClosest(EntityHumanBase par1EntityLiving, Class par2Class, float par3) {
      super(par1EntityLiving, par2Class, par3);
      this.owner = par1EntityLiving;
      this.setMutexBits(4);
   }

   public boolean shouldExecute() {
      boolean flag = super.shouldExecute();
      return this.owner.getNavigator().getPath() == null && this.owner.getAttackTarget() == null && flag?(this.owner.AIMode == EnumAiState.WARD.ordinal()?false:this.owner.isSuitableTargetAlly((EntityLiving)super.closestEntity) && this.owner.getDistanceToEntity(super.closestEntity) < 5.0F):false;
   }

   public void startExecuting() {
      super.startExecuting();
      this.owner.setSpeaking(true);
      this.handShake(60);
   }

   public void handShake(int chance) {
      if(super.closestEntity != null) {
         int rnd = this.owner.getRNG().nextInt(chance);
         if(rnd == 0) {
            this.owner.swingItem();
         } else if(rnd == 1) {
            this.owner.swingItem();
         } else if(rnd > chance - 10 && this.owner.getDistanceToEntity(super.closestEntity) < 3.0F) {
            this.owner.swingItem();
            ((EntityLivingBase)super.closestEntity).swingItem();
         }

      }
   }

   public void resetTask() {
      super.resetTask();
      this.owner.setSpeaking(false);
      this.handShake(40);
   }
}
