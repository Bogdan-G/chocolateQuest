package com.chocolate.chocolateQuest.entity.ai;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.ai.AIControlledBase;

public class AIControlledSit extends AIControlledBase {

   boolean shouldLie;


   public AIControlledSit(EntityHumanBase owner) {
      super(owner);
      this.shouldLie = false;
   }

   public AIControlledSit(EntityHumanBase owner, boolean lie) {
      this(owner);
      this.shouldLie = lie;
   }

   public boolean shouldExecute() {
      return true;
   }

   public void updateTask() {
      if(!super.owner.isSitting()) {
         super.owner.setSitting(true);
      }

      super.owner.getNavigator().clearPathEntity();
   }

   public void resetTask() {
      super.owner.setSitting(false);
      super.resetTask();
   }

   public void startExecuting() {
      super.owner.setSitting(true);
      super.startExecuting();
   }
}
