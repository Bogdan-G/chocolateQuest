package com.chocolate.chocolateQuest.entity.ai;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.ai.AIControlledBase;
import com.chocolate.chocolateQuest.utils.Vec4I;

public class AIHumanGoToPoint extends AIControlledBase {

   public Vec4I currentPos;


   public AIHumanGoToPoint(EntityHumanBase owner) {
      super(owner);
   }

   public boolean shouldExecute() {
      if(super.owner.currentPos != null) {
         ;
      }

      this.currentPos = super.owner.currentPos;
      return this.currentPos != null;
   }

   public boolean continueExecuting() {
      return super.owner.currentPos == this.currentPos;
   }

   public void resetTask() {
      this.currentPos = null;
      super.resetTask();
   }

   public void updateTask() {
      Vec4I p = this.currentPos;
      double dist = 0.0D;
      float width = 0.0F;
      if(super.owner.ridingEntity == null) {
         dist = super.owner.getDistanceSq((double)p.xCoord, (double)p.yCoord, (double)p.zCoord);
         width = super.owner.width + 1.0F;
      } else {
         dist = super.owner.ridingEntity.getDistanceSq((double)p.xCoord, (double)p.yCoord, (double)p.zCoord);
         width = super.owner.ridingEntity.width;
      }

      width *= width;
      if(dist > (double)(width + 1.0F)) {
         this.tryMoveToXYZ((double)p.xCoord, (double)p.yCoord, (double)p.zCoord, 1.0F);
      } else if(super.owner.currentPos == this.currentPos) {
         super.owner.currentPos = null;
      }

      super.updateTask();
   }
}
