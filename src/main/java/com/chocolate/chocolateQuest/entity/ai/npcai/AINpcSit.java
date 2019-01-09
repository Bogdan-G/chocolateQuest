package com.chocolate.chocolateQuest.entity.ai.npcai;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.ai.npcai.AINpcGoToPosition;
import com.chocolate.chocolateQuest.utils.Vec4I;
import net.minecraft.block.BlockStairs;
import net.minecraft.util.MathHelper;

public class AINpcSit extends AINpcGoToPosition {

   public AINpcSit(EntityHumanBase owner, Vec4I p) {
      super(owner, p);
   }

   public boolean shouldExecute() {
      return true;
   }

   public void resetTask() {
      super.owner.setSitting(false);
   }

   public void onUpdateAtPosition() {
      this.getNavigator().clearPathEntity();
      super.owner.rotationYaw = (float)super.position.rot;
      double x = (double)super.position.xCoord + 0.5D;
      double y = (double)((float)super.position.yCoord + 1.0F);
      double z = (double)super.position.zCoord + 0.5D;
      if(super.owner.worldObj.getBlock(MathHelper.floor_double(super.owner.posX), MathHelper.floor_double(super.owner.posY - 1.0D), MathHelper.floor_double(super.owner.posZ)) instanceof BlockStairs) {
         switch(super.owner.worldObj.getBlockMetadata(MathHelper.floor_double(super.owner.posX), MathHelper.floor_double(super.owner.posY - 1.0D), MathHelper.floor_double(super.owner.posZ))) {
         case 0:
            x -= (double)super.owner.width * 0.5D;
            break;
         case 1:
            x += (double)super.owner.width * 0.5D;
            break;
         case 2:
            z -= (double)super.owner.width * 0.5D;
            break;
         case 3:
            z += (double)super.owner.width * 0.5D;
         }
      }

      if(!super.owner.isSitting() && super.owner.getDistanceSq((double)super.position.xCoord + 0.5D, (double)super.position.yCoord, (double)super.position.zCoord + 0.5D) > 1.0D) {
         super.owner.setPosition(x, y, z);
      }

      super.owner.setSitting(true);
   }
}
