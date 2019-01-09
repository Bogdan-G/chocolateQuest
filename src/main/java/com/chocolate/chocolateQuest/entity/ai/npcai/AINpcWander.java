package com.chocolate.chocolateQuest.entity.ai.npcai;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.ai.npcai.AINpcGoToPosition;
import com.chocolate.chocolateQuest.utils.Vec4I;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;

public class AINpcWander extends AINpcGoToPosition {

   private double xPosition;
   private double yPosition;
   private double zPosition;
   float distance = 10.0F;


   public AINpcWander(EntityHumanBase owner, Vec4I p) {
      super(owner, p);
   }

   public boolean shouldExecute() {
      if(super.owner.getDistanceSq((double)super.position.xCoord + 0.5D, (double)super.position.yCoord, (double)super.position.zCoord + 0.5D) > (double)(this.distance * this.distance)) {
         this.xPosition = (double)super.position.xCoord;
         this.yPosition = (double)super.position.yCoord;
         this.zPosition = (double)super.position.zCoord;
         return true;
      } else if(super.owner.getRNG().nextInt(120) == 0) {
         Vec3 vec3 = RandomPositionGenerator.findRandomTarget(super.owner, 10, 7);
         if(vec3 == null) {
            return false;
         } else {
            this.xPosition = vec3.xCoord;
            this.yPosition = vec3.yCoord;
            this.zPosition = vec3.zCoord;
            return true;
         }
      } else {
         return false;
      }
   }

   public void startExecuting() {
      this.tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, 0.8F);
   }

   public boolean continueExecuting() {
      return !this.getNavigator().noPath();
   }

   public void updateTask() {}
}
