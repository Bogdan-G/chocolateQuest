package com.chocolate.chocolateQuest.client.model;

import com.chocolate.chocolateQuest.client.model.ModelHuman;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelWalker extends ModelHuman {

   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
      this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
      if(super.isChild) {
         float var8 = 2.0F;
         GL11.glPushMatrix();
         GL11.glScalef(1.5F / var8, 1.5F / var8, 1.5F / var8);
         GL11.glTranslatef(0.0F, 16.0F * par7, 0.0F);
         super.bipedHead.render(par7);
         GL11.glPopMatrix();
         GL11.glPushMatrix();
         GL11.glScalef(1.0F / var8, 1.0F / var8, 1.0F / var8);
         GL11.glTranslatef(0.0F, 24.0F * par7, 0.0F);
         super.bipedBody.render(par7);
         super.bipedRightArm.render(par7);
         super.bipedLeftArm.render(par7);
         super.bipedRightLeg.render(par7);
         super.bipedLeftLeg.render(par7);
         super.bipedHeadwear.render(par7);
         GL11.glPopMatrix();
      } else {
         super.bipedHead.render(par7);
         super.bipedBody.render(par7);
         super.bipedRightArm.render(par7);
         super.bipedLeftArm.render(par7);
         super.bipedRightLeg.render(par7);
         super.bipedLeftLeg.render(par7);
         super.bipedHeadwear.render(par7);
      }

   }
}
