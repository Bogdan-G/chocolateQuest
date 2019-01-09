package com.chocolate.chocolateQuest.entity.ai;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;

public class AIHumanPanic extends EntityAIBase {

   private EntityHumanBase theEntityCreature;
   private double speed;
   private double randPosX;
   private double randPosY;
   private double randPosZ;
   private static final String __OBFID = "CL_00001604";


   public AIHumanPanic(EntityHumanBase human, double speed) {
      this.theEntityCreature = human;
      this.speed = speed;
      this.setMutexBits(1);
   }

   public boolean shouldExecute() {
      if(this.theEntityCreature.getAITarget() == null && !this.theEntityCreature.isBurning()) {
         return false;
      } else {
         Vec3 vec3 = RandomPositionGenerator.findRandomTarget(this.theEntityCreature, 5, 4);
         if(vec3 == null) {
            return false;
         } else {
            this.randPosX = vec3.xCoord;
            this.randPosY = vec3.yCoord;
            this.randPosZ = vec3.zCoord;
            return true;
         }
      }
   }

   public void startExecuting() {
      this.theEntityCreature.getNavigator().tryMoveToXYZ(this.randPosX, this.randPosY, this.randPosZ, this.speed);
   }

   public boolean continueExecuting() {
      return !this.theEntityCreature.getNavigator().noPath();
   }
}
