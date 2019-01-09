package com.chocolate.chocolateQuest.entity.ai.npcai;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.ai.AIControlledBase;
import com.chocolate.chocolateQuest.utils.Vec4I;
import net.minecraft.entity.Entity;

public class AINpcGoToPosition extends AIControlledBase {

   Vec4I position;


   public AINpcGoToPosition(EntityHumanBase owner) {
      super(owner);
      this.position = owner.AIPosition;
   }

   public AINpcGoToPosition(EntityHumanBase owner, Vec4I p) {
      super(owner);
      this.position = p;
   }

   public boolean shouldExecute() {
      return super.owner.getDistanceSq((double)this.position.xCoord + 0.5D, (double)this.position.yCoord + 0.5D, (double)this.position.zCoord + 0.5D) > (double)(super.owner.width * super.owner.width);
   }

   public void resetTask() {
      super.owner.getNavigator().clearPathEntity();
      super.owner.rotationYaw = (float)this.position.rot;
   }

   public void updateTask() {
      Object distCheckEntity = super.owner.ridingEntity != null?super.owner.ridingEntity:super.owner;
      double dist = ((Entity)distCheckEntity).getDistanceSq((double)this.position.xCoord + 0.5D, (double)this.position.yCoord, (double)this.position.zCoord + 0.5D);
      if(dist > (double)(super.owner.width + 1.0F)) {
         this.tryMoveToXYZ((double)this.position.xCoord + 0.5D, (double)this.position.yCoord + 1.5D, (double)this.position.zCoord + 0.5D, 0.8F);
      } else {
         this.onUpdateAtPosition();
      }

   }

   public void onUpdateAtPosition() {
      double x = super.owner.posX;
      double y = super.owner.posY;
      double z = super.owner.posZ;
      super.owner.setPosition((double)((float)this.position.xCoord + 0.5F), (double)(this.position.yCoord + 1), (double)((float)this.position.zCoord + 0.5F));
      if(super.owner.worldObj.getCollidingBoundingBoxes(super.owner, super.owner.boundingBox).size() != 0 || super.owner.worldObj.isAnyLiquid(super.owner.boundingBox)) {
         super.owner.setPosition(x, y, z);
      }

      super.owner.rotationYaw = (float)this.position.rot;
   }
}
