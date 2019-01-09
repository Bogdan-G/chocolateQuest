package com.chocolate.chocolateQuest.client;

import com.chocolate.chocolateQuest.entity.EntityTracker;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Sphere;

public class RenderBubbleShield extends Render {

   static Sphere bubble = new Sphere();


   public RenderBubbleShield(float f) {}

   public void doRender(Entity entity, double x, double y, double z, float f, float f1) {
      Tessellator tessellator = Tessellator.instance;
      GL11.glPushMatrix();
      EntityTracker beam = (EntityTracker)entity;
      this.bindTexture(new ResourceLocation("chocolateQuest:textures/entity/shine.png"));
      GL11.glTranslatef((float)x, (float)y + 0.8F, (float)z);
      GL11.glRotatef(-entity.rotationYaw, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(entity.rotationPitch, 1.0F, 0.0F, 0.0F);
      GL11.glDisable(3553);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 1);
      GL11.glColor4f(0.2F, 0.2F, 1.0F, 0.6F);
      renderBubble((float)beam.getRange(), beam.getRange() * 5);
      GL11.glDisable(3042);
      GL11.glEnable(3553);
      GL11.glPopMatrix();
   }

   protected ResourceLocation getEntityTexture(Entity entity) {
      return null;
   }

   public static void renderBubble(float widht, int divisions) {
      GL11.glDisable(2884);
      GL11.glDepthMask(false);
      GL11.glScalef(-1.0F, -1.0F, 1.0F);
      bubble.draw(widht, divisions, divisions);
      GL11.glDepthMask(true);
      GL11.glEnable(2884);
   }

}
