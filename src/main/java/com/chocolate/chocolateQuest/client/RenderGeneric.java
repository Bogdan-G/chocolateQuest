package com.chocolate.chocolateQuest.client;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderGeneric extends Render {

   protected ModelBase model;
   private ResourceLocation texture;


   public RenderGeneric(ModelBase par1ModelBase, float par2) {
      this.texture = new ResourceLocation("chocolatequest:textures/entity/dragonbd.png");
      this.model = par1ModelBase;
      super.shadowSize = par2;
   }

   public RenderGeneric(ModelBase par1ModelBase, float par2, ResourceLocation texture) {
      this(par1ModelBase, par2);
      this.texture = texture;
   }

   protected ResourceLocation getEntityTexture(Entity entity) {
      return this.texture;
   }

   public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) {
      GL11.glPushMatrix();
      GL11.glTranslatef((float)d0, (float)d1, (float)d2);
      GL11.glRotatef(-180.0F - f, 0.0F, 1.0F, 0.0F);
      this.bindTexture(this.texture);
      GL11.glScalef(-1.0F, -1.0F, 1.0F);
      this.model.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GL11.glPopMatrix();
   }
}
