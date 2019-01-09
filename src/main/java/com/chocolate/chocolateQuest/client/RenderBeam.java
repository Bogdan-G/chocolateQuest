package com.chocolate.chocolateQuest.client;

import com.chocolate.chocolateQuest.entity.projectile.EntityProjectileBeam;
import com.chocolate.chocolateQuest.magic.Elements;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderBeam extends Render {

   private static final ResourceLocation arrowTextures = new ResourceLocation("textures/blocks/water_flow.png");


   public void doRender(Entity entity, double x, double y, double z, float f, float f1) {
      Tessellator tessellator = Tessellator.instance;
      GL11.glPushMatrix();
      EntityProjectileBeam beam = (EntityProjectileBeam)entity;
      this.bindTexture(new ResourceLocation("chocolateQuest:textures/entity/shine.png"));
      float power = beam.damage * 0.015F;
      GL11.glTranslatef((float)x, (float)y - power * 0.5F, (float)z);
      GL11.glRotatef(-entity.rotationYaw, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(entity.rotationPitch, 1.0F, 0.0F, 0.0F);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      float length = beam.range;
      float tWidth = 0.125F;
      Elements element = beam.getElement();
      int idex = 7;
      if(element != null) {
         idex = 7 - element.ordinal();
         GL11.glColor4f(element.getColorX(), element.getColorY(), element.getColorZ(), 0.5F);
         if(element == Elements.water) {
            idex = 0;
            this.bindTexture(arrowTextures);
            tWidth = 1.0F;
         }
      }

      GL11.glDisable(3553);
      this.drawBox(tessellator, (double)(-power), (double)power, (double)(-power), (double)power, (double)length, 0.0D, 0.0D, 1.0D, 0.0D, 1.0D, 1.0F);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glEnable(3553);
      GL11.glMatrixMode(5890);
      GL11.glPushMatrix();
      float desp = (float)Minecraft.getSystemTime() * 0.012F * power;
      GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
      GL11.glTranslatef(desp, 0.0F, 0.0F);
      this.drawBox(tessellator, (double)(-power), (double)power, (double)(-power), (double)power, (double)length, 0.0D, 0.0D, (double)(0.5F * length), (double)(tWidth * (float)idex), (double)(tWidth * (float)(idex + 1)), 0.0F);
      GL11.glPopMatrix();
      GL11.glMatrixMode(5888);
      GL11.glPopMatrix();
   }

   public RenderBeam(float f) {}

   public void drawBox(Tessellator tessellator, double x0, double x1, double y0, double y1, double z0, double z1, double tx0, double tx1, double ty0, double ty1, float b) {
      tessellator.startDrawingQuads();
      if(b > 0.0F) {
         tessellator.setNormal(0.0F, 0.0F, 1.0F);
         tessellator.addVertexWithUV(x1, y0, z0, tx0, ty0);
         tessellator.addVertexWithUV(x0, y0, z0, tx1, ty0);
         tessellator.addVertexWithUV(x0, y1, z0, tx1, ty1);
         tessellator.addVertexWithUV(x1, y1, z0, tx0, ty1);
         tessellator.setNormal(0.0F, 0.0F, -1.0F);
         tessellator.addVertexWithUV(x0, y1, z1, tx0, ty1);
         tessellator.addVertexWithUV(x1, y1, z1, tx1, ty1);
         tessellator.addVertexWithUV(x1, y0, z1, tx1, ty0);
         tessellator.addVertexWithUV(x0, y0, z1, tx0, ty0);
      }

      tessellator.setNormal(-1.0F, 0.0F, 0.0F);
      tessellator.addVertexWithUV(x1, y0, z0, tx0, ty1);
      tessellator.addVertexWithUV(x1, y0, z1, tx1, ty1);
      tessellator.addVertexWithUV(x1, y1, z1, tx1, ty0);
      tessellator.addVertexWithUV(x1, y1, z0, tx0, ty0);
      tessellator.setNormal(1.0F, 0.0F, 0.0F);
      tessellator.addVertexWithUV(x0, y1, z1, tx1, ty0);
      tessellator.addVertexWithUV(x0, y0, z1, tx1, ty1);
      tessellator.addVertexWithUV(x0, y0, z0, tx0, ty1);
      tessellator.addVertexWithUV(x0, y1, z0, tx0, ty0);
      tessellator.setNormal(0.0F, -1.0F, 0.0F);
      tessellator.addVertexWithUV(x1, y0, z1, tx1, ty0);
      tessellator.addVertexWithUV(x1, y0, z0, tx0, ty0);
      tessellator.addVertexWithUV(x0, y0, z0, tx0, ty1);
      tessellator.addVertexWithUV(x0, y0, z1, tx1, ty1);
      tessellator.setNormal(0.0F, 1.0F, 0.0F);
      tessellator.addVertexWithUV(x0, y1, z0, tx0, ty1);
      tessellator.addVertexWithUV(x1, y1, z0, tx0, ty0);
      tessellator.addVertexWithUV(x1, y1, z1, tx1, ty0);
      tessellator.addVertexWithUV(x0, y1, z1, tx1, ty1);
      tessellator.draw();
   }

   protected ResourceLocation getEntityTexture(Entity entity) {
      return null;
   }

}
