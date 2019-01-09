package com.chocolate.chocolateQuest.client.rendererHuman;

import com.chocolate.chocolateQuest.client.model.ModelGremlin;
import com.chocolate.chocolateQuest.client.rendererHuman.RenderHuman;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderHumanGremlin extends RenderHuman {

   public RenderHumanGremlin(float f, ResourceLocation r) {
      super(new ModelGremlin(), f, r);
      super.featherY = -0.3F;
   }

   protected void func_82421_b() {
      super.field_82423_g = new ModelGremlin(1.0F);
      super.field_82425_h = new ModelGremlin(0.5F);
   }

   protected void preRenderCallback(EntityLivingBase entityliving, float f) {
      super.preRenderCallback(entityliving, f);
      GL11.glTranslatef(0.0F, 0.7F, 0.0F);
   }

   protected void renderCape(EntityHumanBase e) {
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, -0.07F, -0.1F);
      super.renderCape(e);
      GL11.glPopMatrix();
   }

   protected void setSitOffset() {
      GL11.glTranslatef(0.0F, 0.1F, 0.0F);
   }
}
