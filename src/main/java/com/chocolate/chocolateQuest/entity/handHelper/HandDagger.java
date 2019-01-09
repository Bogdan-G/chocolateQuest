package com.chocolate.chocolateQuest.entity.handHelper;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.handHelper.HandCQBlade;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;

public class HandDagger extends HandCQBlade {

   public HandDagger(EntityHumanBase owner, ItemStack itemStack) {
      super(owner, itemStack);
   }

   public void onUpdate() {
      super.onUpdate();
      if(super.owner.getAttackTarget() != null) {
         EntityLivingBase target = super.owner.getAttackTarget();

         float angle;
         for(angle = super.owner.rotationYawHead - target.rotationYawHead; angle > 360.0F; angle -= 360.0F) {
            ;
         }

         while(angle < 0.0F) {
            angle += 360.0F;
         }

         angle = Math.abs(angle - 180.0F);
         if(Math.abs(angle) > 130.0F) {
            super.owner.setSneaking(true);
         } else {
            super.owner.setSneaking(false);
         }

         if(super.owner.getRNG().nextInt(70) == 0 || super.owner.isCollidedHorizontally && super.owner.hurtResistantTime > 0) {
            Vec3 vec = super.owner.getLookVec();
            float dist = 1.0F;
            super.owner.motionX = vec.xCoord * (double)dist;
            super.owner.motionY = vec.yCoord * (double)dist * 0.6D;
            super.owner.motionZ = vec.zCoord * (double)dist;
            super.owner.motionY += 0.2D;
            super.owner.getNavigator().clearPathEntity();
         }
      }

   }
}
