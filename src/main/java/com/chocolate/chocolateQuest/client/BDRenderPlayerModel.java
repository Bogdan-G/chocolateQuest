package com.chocolate.chocolateQuest.client;

import com.chocolate.chocolateQuest.items.swords.ItemBaseBroadSword;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class BDRenderPlayerModel extends ModelBiped {

   public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      if(entity instanceof EntityPlayer) {
         EntityPlayer ep = (EntityPlayer)entity;
         if(ep.inventory.getCurrentItem() != null) {
            ItemStack is = ep.inventory.getCurrentItem();
            if(is.getItem() instanceof ItemBaseBroadSword) {
               this.setTwoHandedAngles(f5);
            }

            if(ep.isBlocking()) {
               this.setShiedRotation(f5);
            }
         }
      }

   }

   public void setTwoHandedAngles(float time) {
      float f7 = 0.0F;
      float rotationYaw = 0.0F;
      float swing = MathHelper.sin(super.onGround * 3.1415927F);
      super.bipedRightArm.rotateAngleZ = 0.0F;
      super.bipedLeftArm.rotateAngleZ = -0.3F;
      super.bipedRightArm.rotateAngleY = rotationYaw - 0.6F;
      super.bipedLeftArm.rotateAngleY = rotationYaw + 0.6F;
      super.bipedRightArm.rotateAngleX = -0.8F + swing;
      super.bipedLeftArm.rotateAngleX = -0.8F + swing;
      super.bipedRightArm.rotateAngleZ += MathHelper.cos(time * 0.09F) * 0.05F + 0.05F;
      super.bipedLeftArm.rotateAngleZ -= MathHelper.cos(time * 0.09F) * 0.05F + 0.05F;
      super.bipedRightArm.rotateAngleX += MathHelper.sin(time * 0.067F) * 0.05F;
      super.bipedLeftArm.rotateAngleX -= MathHelper.sin(time * 0.067F) * 0.05F;
      float f6 = 1.0F - super.onGround;
      f6 *= f6;
      f6 *= f6;
      f6 = 1.0F - f6;
      f7 = MathHelper.sin(f6 * 3.1415927F);
      float f8 = MathHelper.sin(super.onGround * 3.1415927F) * -(super.bipedHead.rotateAngleX - 0.7F) * 0.75F;
      super.bipedRightArm.rotateAngleX = (float)((double)super.bipedRightArm.rotateAngleX - ((double)f7 * 1.2D + (double)f8));
      super.bipedRightArm.rotateAngleY += super.bipedBody.rotateAngleY * 2.0F;
      super.bipedRightArm.rotateAngleZ = MathHelper.sin(super.onGround * 3.1415927F) * -0.4F;
      super.bipedLeftArm.rotateAngleX = (float)((double)super.bipedRightArm.rotateAngleX - ((double)f7 * 1.2D + (double)f8));
      super.bipedLeftArm.rotateAngleY += super.bipedBody.rotateAngleY * 2.0F;
      super.bipedLeftArm.rotateAngleZ = MathHelper.sin(super.onGround * 3.1415927F) * -0.4F;
   }

   public void setShiedRotation(float time) {
      float f7 = 0.0F;
      float f9 = 0.0F;
      super.bipedLeftArm.rotateAngleZ = -0.7F;
      super.bipedLeftArm.rotateAngleY = 1.2F;
      super.bipedLeftArm.rotateAngleX = -1.5707964F + super.bipedHead.rotateAngleX;
      super.bipedLeftArm.rotateAngleX -= f7 * 1.2F - f9 * 0.4F;
      super.bipedLeftArm.rotateAngleZ -= MathHelper.cos(time * 0.09F) * 0.05F + 0.05F;
      super.bipedLeftArm.rotateAngleX -= MathHelper.sin(time * 0.067F) * 0.05F;
   }
}
