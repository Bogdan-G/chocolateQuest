package com.chocolate.chocolateQuest.entity.ai;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;

public class AISpeakToPlayer extends EntityAIBase {

   EntityHumanBase human;
   Entity playerSpeakingTo;


   public AISpeakToPlayer(EntityHumanBase human) {
      this.human = human;
      this.setMutexBits(3);
   }

   public boolean shouldExecute() {
      return this.human.playerSpeakingTo != null;
   }

   public void startExecuting() {
      this.playerSpeakingTo = this.human.playerSpeakingTo;
      this.human.getNavigator().clearPathEntity();
      if(this.human.isSitting()) {
         this.human.setSitting(false);
      }

   }

   public boolean continueExecuting() {
      if(this.human.playerSpeakingTo == null) {
         return false;
      } else if(this.human.playerSpeakingTo != this.playerSpeakingTo) {
         return false;
      } else if(this.human.getDistanceSqToEntity(this.playerSpeakingTo) > 36.0D) {
         this.human.playerSpeakingTo = null;
         return false;
      } else {
         return true;
      }
   }

   public void resetTask() {}

   public void updateTask() {
      this.human.getLookHelper().setLookPosition(this.playerSpeakingTo.posX, this.playerSpeakingTo.posY + (double)this.playerSpeakingTo.getEyeHeight(), this.playerSpeakingTo.posZ, 10.0F, (float)this.human.getVerticalFaceSpeed());
   }
}
