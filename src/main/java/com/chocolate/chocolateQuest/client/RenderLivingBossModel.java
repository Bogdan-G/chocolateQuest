package com.chocolate.chocolateQuest.client;

import com.chocolate.chocolateQuest.client.RenderLivingModel;
import com.chocolate.chocolateQuest.entity.boss.EntityBaseBoss;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderLivingBossModel extends RenderLivingModel {

   public RenderLivingBossModel(ModelBase model, float f, ResourceLocation r) {
      super(model, f, r);
   }

   protected void preRenderCallback(EntityLivingBase entity, float par2) {
      super.preRenderCallback(entity, par2);
      float scale = ((EntityBaseBoss)entity).getScaleSize();
      GL11.glScalef(scale, scale, scale);
   }
}
