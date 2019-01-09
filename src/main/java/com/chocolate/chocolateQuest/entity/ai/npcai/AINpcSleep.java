package com.chocolate.chocolateQuest.entity.ai.npcai;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.ai.npcai.AINpcGoToPosition;
import com.chocolate.chocolateQuest.utils.Vec4I;

public class AINpcSleep extends AINpcGoToPosition {

   public AINpcSleep(EntityHumanBase owner, Vec4I p) {
      super(owner, p);
      this.setMutexBits(7);
   }

   public boolean shouldExecute() {
      if(super.owner.getDistanceSq((double)super.position.xCoord + 0.5D, (double)super.position.yCoord, (double)super.position.zCoord + 0.5D) > 1.0D) {
         super.owner.setSleeping(false);
      }

      return true;
   }

   public void resetTask() {
      super.owner.setSleeping(false);
   }

   public void onUpdateAtPosition() {
      this.getNavigator().clearPathEntity();
      if(!super.owner.isSleeping() && super.owner.getDistanceSq((double)super.position.xCoord + 0.5D, (double)super.position.yCoord, (double)super.position.zCoord + 0.5D) > 1.0D) {
         super.onUpdateAtPosition();
      }

      super.owner.setSleeping(true);
   }
}
