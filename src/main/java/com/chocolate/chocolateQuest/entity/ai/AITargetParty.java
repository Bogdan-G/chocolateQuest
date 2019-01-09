package com.chocolate.chocolateQuest.entity.ai;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;

public class AITargetParty extends EntityAITarget {

   EntityHumanBase owner;
   EntityLivingBase theTarget;


   public AITargetParty(EntityHumanBase human) {
      super(human, false);
      this.owner = human;
      this.setMutexBits(1);
   }

   public boolean shouldExecute() {
      EntityLivingBase var1 = this.getTarget();
      if(this.owner.isSuitableMount(var1)) {
         return false;
      } else {
         this.theTarget = var1;
         return this.theTarget != null;
      }
   }

   public EntityLivingBase getTarget() {
      return this.owner.party != null?this.owner.party.getTarget():null;
   }

   public void startExecuting() {
      this.owner.setAttackTarget(this.theTarget);
      super.startExecuting();
   }
}
