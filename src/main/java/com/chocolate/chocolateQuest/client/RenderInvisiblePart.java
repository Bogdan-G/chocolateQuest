package com.chocolate.chocolateQuest.client;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderInvisiblePart extends Render {

   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {}

   protected ResourceLocation getEntityTexture(Entity entity) {
      return null;
   }
}
