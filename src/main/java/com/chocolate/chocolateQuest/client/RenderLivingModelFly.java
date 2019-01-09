package com.chocolate.chocolateQuest.client;

import com.chocolate.chocolateQuest.client.RenderLivingBossModel;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderLivingModelFly extends RenderLivingBossModel {

   public RenderLivingModelFly(ModelBase model, float f, ResourceLocation r) {
      super(model, f, r);
   }

   protected void preRenderCallback(EntityLivingBase entity, float par2) {
      GL11.glRotatef(entity.rotationPitch, 1.0F, 0.0F, 0.0F);
      super.preRenderCallback(entity, par2);
   }
}
