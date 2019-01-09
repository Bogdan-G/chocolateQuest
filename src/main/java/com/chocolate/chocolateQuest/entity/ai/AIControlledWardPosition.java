package com.chocolate.chocolateQuest.entity.ai;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.ai.AIControlledBase;
import com.chocolate.chocolateQuest.utils.Vec4I;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class AIControlledWardPosition extends AIControlledBase {

   public AIControlledWardPosition(EntityHumanBase owner) {
      super(owner);
   }

   public boolean shouldExecute() {
      if(super.owner.AIPosition == null) {
         return false;
      } else {
         Vec4I p = super.owner.AIPosition;
         return super.owner.getDistanceSq((double)p.xCoord + 0.5D, (double)p.yCoord + 0.5D, (double)p.zCoord + 0.5D) > (double)(super.owner.width * super.owner.width) || (int)super.owner.rotationYawHead != p.rot;
      }
   }

   public void resetTask() {
      super.owner.getNavigator().clearPathEntity();
      float rot = (float)Math.toRadians((double)super.owner.AIPosition.rot);
      super.owner.getLookHelper().setLookPosition(super.owner.posX - (double)(MathHelper.sin(rot) * 10.0F), super.owner.posY, super.owner.posZ + (double)(MathHelper.cos(rot) * 10.0F), 10.0F, 0.0F);
   }

   public void updateTask() {
      Vec4I p = super.owner.AIPosition;
      Object distCheckEntity = super.owner.ridingEntity != null?super.owner.ridingEntity:super.owner;
      double dist = ((Entity)distCheckEntity).getDistanceSq((double)p.xCoord + 0.5D, (double)p.yCoord, (double)p.zCoord + 0.5D);
      if(dist > (double)(super.owner.width + 1.0F)) {
         this.tryMoveToXYZ((double)p.xCoord + 0.5D, (double)p.yCoord + 0.5D, (double)p.zCoord + 0.5D, 1.0F);
      } else {
         super.owner.getNavigator().clearPathEntity();
         super.owner.setPosition((double)p.xCoord + 0.5D, (double)(p.yCoord + 1), (double)p.zCoord + 0.5D);
         super.owner.rotationYaw = (float)p.rot;
      }

      super.updateTask();
   }
}
