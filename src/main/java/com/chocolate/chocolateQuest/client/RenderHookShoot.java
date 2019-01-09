package com.chocolate.chocolateQuest.client;

import com.chocolate.chocolateQuest.entity.projectile.EntityHookShoot;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public class RenderHookShoot extends Render {

   private static final ResourceLocation arrowTextures = new ResourceLocation("textures/entity/arrow.png");


   public RenderHookShoot(float f) {}

   public void doRenderFireball(EntityHookShoot entity, double x, double y, double z, float f, float f1) {
      Tessellator tessellator = Tessellator.instance;
      byte type = entity.getHookType();
      float f9;
      float vec3;
      float endX;
      float endY;
      float tymin;
      float var20;
      if(type != 3 && type != 5) {
         ResourceLocation var42 = arrowTextures;
         this.bindTexture(var42);
         GL11.glPushMatrix();
         GL11.glTranslated(x, y, z);
         GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * f1 - 90.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * f1, 0.0F, 0.0F, 1.0F);
         byte var44 = 0;
         vec3 = 0.0F;
         endX = 0.15625F;
         tymin = (float)(5 + var44 * 10) / 32.0F;
         endY = (float)(10 + var44 * 10) / 32.0F;
         var20 = 0.05625F;
         GL11.glEnable('\u803a');
         GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
         GL11.glScalef(var20, var20, var20);
         GL11.glTranslatef(-4.0F, 0.0F, 0.0F);
         GL11.glNormal3f(var20, 0.0F, 0.0F);
         tessellator.startDrawingQuads();
         tessellator.addVertexWithUV(-7.0D, -2.0D, -2.0D, (double)vec3, (double)tymin);
         tessellator.addVertexWithUV(-7.0D, -2.0D, 2.0D, (double)endX, (double)tymin);
         tessellator.addVertexWithUV(-7.0D, 2.0D, 2.0D, (double)endX, (double)endY);
         tessellator.addVertexWithUV(-7.0D, 2.0D, -2.0D, (double)vec3, (double)endY);
         tessellator.draw();
         GL11.glNormal3f(-var20, 0.0F, 0.0F);
         tessellator.startDrawingQuads();
         tessellator.addVertexWithUV(-7.0D, 2.0D, -2.0D, (double)vec3, (double)tymin);
         tessellator.addVertexWithUV(-7.0D, 2.0D, 2.0D, (double)endX, (double)tymin);
         tessellator.addVertexWithUV(-7.0D, -2.0D, 2.0D, (double)endX, (double)endY);
         tessellator.addVertexWithUV(-7.0D, -2.0D, -2.0D, (double)vec3, (double)endY);
         tessellator.draw();
         vec3 = 0.0F;
         endX = 0.5F;
         tymin = (float)(0 + var44 * 10) / 32.0F;
         endY = (float)(5 + var44 * 10) / 32.0F;

         for(int var48 = 0; var48 < 4; ++var48) {
            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glNormal3f(0.0F, 0.0F, var20);
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(-8.0D, -2.0D, 0.0D, (double)vec3, (double)tymin);
            tessellator.addVertexWithUV(8.0D, -2.0D, 0.0D, (double)endX, (double)tymin);
            tessellator.addVertexWithUV(8.0D, 2.0D, 0.0D, (double)endX, (double)endY);
            tessellator.addVertexWithUV(-8.0D, 2.0D, 0.0D, (double)vec3, (double)endY);
            tessellator.draw();
         }

         GL11.glDisable('\u803a');
         GL11.glPopMatrix();
      } else {
         GL11.glPushMatrix();
         GL11.glTranslatef((float)x, (float)y, (float)z);
         GL11.glEnable('\u803a');
         GL11.glBlendFunc(770, 771);
         GL11.glEnable(3042);
         f9 = 0.8F;
         GL11.glScalef(f9, f9, f9);
         this.bindTexture(BDHelper.getItemTexture());
         short f10 = 241;
         vec3 = (float)(f10 % 16 * 16 + 0) / 256.0F;
         endX = (float)(f10 % 16 * 16 + 16) / 256.0F;
         tymin = (float)(f10 / 16 * 16 + 0) / 256.0F;
         endY = (float)(f10 / 16 * 16 + 16) / 256.0F;
         var20 = 1.0F;
         float endZ = 0.5F;
         float f7 = 0.25F;
         GL11.glRotatef(180.0F - super.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(-super.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
         tessellator.startDrawingQuads();
         tessellator.setNormal(0.0F, 1.0F, 0.0F);
         tessellator.addVertexWithUV((double)(0.0F - endZ), (double)(0.0F - f7), 0.0D, (double)vec3, (double)endY);
         tessellator.addVertexWithUV((double)(var20 - endZ), (double)(0.0F - f7), 0.0D, (double)endX, (double)endY);
         tessellator.addVertexWithUV((double)(var20 - endZ), (double)(1.0F - f7), 0.0D, (double)endX, (double)tymin);
         tessellator.addVertexWithUV((double)(0.0F - endZ), (double)(1.0F - f7), 0.0D, (double)vec3, (double)tymin);
         tessellator.draw();
         GL11.glDisable('\u803a');
         GL11.glPopMatrix();
      }

      if(entity.getThrower() != null) {
         f9 = entity.getThrower().getSwingProgress(f1);
         float var43 = MathHelper.sin(MathHelper.sqrt_float(f9) * 3.1415927F);
         Vec3 var45 = Vec3.createVectorHelper(-0.5D, 0.03D, 0.8D);
         var45.rotateAroundX(-(entity.getThrower().prevRotationPitch + (entity.getThrower().rotationPitch - entity.getThrower().prevRotationPitch) * f1) * 3.1415927F / 180.0F);
         var45.rotateAroundY(-(entity.getThrower().prevRotationYaw + (entity.getThrower().rotationYaw - entity.getThrower().prevRotationYaw) * f1) * 3.1415927F / 180.0F);
         var45.rotateAroundY(var43 * 0.5F);
         var45.rotateAroundX(-var43 * 0.7F);
         double var46 = entity.getThrower().prevPosX + (entity.getThrower().posX - entity.getThrower().prevPosX) * (double)f1 + var45.xCoord;
         double var47 = entity.getThrower().prevPosY + (entity.getThrower().posY - entity.getThrower().prevPosY) * (double)f1 + var45.yCoord;
         double var49 = entity.getThrower().prevPosZ + (entity.getThrower().posZ - entity.getThrower().prevPosZ) * (double)f1 + var45.zCoord;
         double d6 = entity.getThrower() != Minecraft.getMinecraft().thePlayer?(double)entity.getThrower().getEyeHeight():0.0D;
         if(super.renderManager.options.thirdPersonView > 0 || entity.getThrower() != Minecraft.getMinecraft().thePlayer) {
            float startX = (entity.getThrower().prevRenderYawOffset + (entity.getThrower().renderYawOffset - entity.getThrower().prevRenderYawOffset) * f1) * 3.1415927F / 180.0F;
            double d7 = (double)MathHelper.sin(startX);
            double d8 = (double)MathHelper.cos(startX);
            var46 = entity.getThrower().prevPosX + (entity.getThrower().posX - entity.getThrower().prevPosX) * (double)f1 - d8 * 0.35D - d7 * 0.85D;
            var47 = entity.getThrower().prevPosY + d6 + (entity.getThrower().posY - entity.getThrower().prevPosY) * (double)f1 - 0.45D;
            var49 = entity.getThrower().prevPosZ + (entity.getThrower().posZ - entity.getThrower().prevPosZ) * (double)f1 - d7 * 0.35D + d8 * 0.85D;
         }

         double var50 = entity.prevPosX + (entity.posX - entity.prevPosX) * (double)f1;
         double startY = entity.prevPosY + (entity.posY - entity.prevPosY) * (double)f1 + 0.25D;
         double startZ = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double)f1;
         double despX = (double)((float)(var46 - var50));
         double despY = (double)((float)(var47 - startY));
         double despZ = (double)((float)(var49 - startZ));
         GL11.glDisable(3553);
         GL11.glDisable(2896);
         byte steps = 16;
         double hang = (double)(entity.getRadio() - entity.getDistanceToEntity(entity.getThrower()));
         if(hang < 0.2D) {
            hang = 0.0D;
         }

         tessellator.startDrawing(3);
         tessellator.setColorOpaque_I(entity.getRopeColor());

         for(int i = 0; i <= steps; ++i) {
            float stepVariation = (float)i / (float)steps;
            double varY = -Math.sin((double)i * 3.141592653589793D / (double)steps) * hang;
            tessellator.addVertex(x + despX * (double)stepVariation, y + despY * (double)stepVariation + varY, z + despZ * (double)stepVariation);
         }

         tessellator.draw();
         GL11.glEnable(2896);
         GL11.glEnable(3553);
      }

   }

   public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
      this.doRenderFireball((EntityHookShoot)entity, d, d1, d2, f, f1);
   }

   protected ResourceLocation getEntityTexture(Entity entity) {
      return null;
   }

}
