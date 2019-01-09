package com.chocolate.chocolateQuest.client;

import com.chocolate.chocolateQuest.client.model.ModelGiantBoxer;
import com.chocolate.chocolateQuest.entity.boss.EntityBaseBoss;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderBossBiped extends RenderBiped {

   ResourceLocation texture = new ResourceLocation("chocolatequest:textures/entity/mandril.png");


   public RenderBossBiped(ModelBiped par1ModelBiped, float par2) {
      super(par1ModelBiped, par2);
   }

   public RenderBossBiped(ModelBiped par1ModelBiped, float par2, ResourceLocation texture) {
      super(par1ModelBiped, par2);
      this.texture = texture;
   }

   protected void func_82421_b() {
      super.field_82423_g = new ModelGiantBoxer(1.0F);
      super.field_82425_h = new ModelGiantBoxer(0.5F);
   }

   public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
      super.doRender(entity, d, d1, d2, f, f1);
   }

   protected void preRenderCallback(EntityLivingBase entity, float par2) {
      EntityBaseBoss e = (EntityBaseBoss)entity;
      float scale = e.getScaleSize() * 0.76F;
      GL11.glScalef(scale, scale, scale);
      GL11.glTranslatef(0.0F, 0.0F, -0.1F);
      super.preRenderCallback(entity, par2);
   }

   protected ResourceLocation getEntityTexture(Entity entity) {
      return this.texture;
   }
}
