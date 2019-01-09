package com.chocolate.chocolateQuest.client;

import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileMagicStormProjectile;
import com.chocolate.chocolateQuest.utils.BDHelper;
import java.util.Random;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public class RenderBallProjectile extends Render {

   public RenderBallProjectile(float f) {}

   public void doRenderFireball(EntityBaseBall entityball, double x, double y, double z, float f, float f1) {
      int spriteIndex = entityball.getTextureIndex();
      Tessellator tessellator = Tessellator.instance;
      float swingProgress;
      float f10;
      float vec3;
      float endX;
      float endY;
      float i4;
      float endZ;
      float f6;
      if(entityball.renderAsArrow()) {
         GL11.glPushMatrix();
         GL11.glTranslatef((float)x, (float)y, (float)z);
         GL11.glEnable('\u803a');
         GL11.glBlendFunc(770, 771);
         GL11.glEnable(3042);
         swingProgress = entityball.getBallSize();
         GL11.glScalef(swingProgress, swingProgress, swingProgress);
         this.bindTexture(BDHelper.texture);
         f10 = (float)(spriteIndex % 16 * 16 + 0) / 256.0F;
         vec3 = (float)(spriteIndex % 16 * 16 + 32) / 256.0F;
         endX = (float)(spriteIndex / 16 * 16 + 0) / 256.0F;
         i4 = (float)(spriteIndex / 16 * 16 + 16) / 256.0F;
         endY = 1.0F;
         f6 = 0.5F;
         endZ = 0.25F;
         GL11.glTranslatef(0.0F, entityball.height, entityball.getBallData().getZOffset());
         GL11.glDisable(2884);
         GL11.glRotatef(entityball.rotationYaw + 90.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(-entityball.rotationPitch, 0.0F, 0.0F, 1.0F);
         tessellator.startDrawingQuads();
         tessellator.setNormal(0.0F, 1.0F, 0.0F);
         tessellator.addVertexWithUV((double)(-endY), (double)(-f6), 0.0D, (double)vec3, (double)i4);
         tessellator.addVertexWithUV((double)endY, (double)(-f6), 0.0D, (double)f10, (double)i4);
         tessellator.addVertexWithUV((double)endY, (double)f6, 0.0D, (double)f10, (double)endX);
         tessellator.addVertexWithUV((double)(-endY), (double)f6, 0.0D, (double)vec3, (double)endX);
         tessellator.addVertexWithUV((double)(-endY), 0.0D, (double)(-f6), (double)vec3, (double)i4);
         tessellator.addVertexWithUV((double)endY, 0.0D, (double)(-f6), (double)f10, (double)i4);
         tessellator.addVertexWithUV((double)endY, 0.0D, (double)f6, (double)f10, (double)endX);
         tessellator.addVertexWithUV((double)(-endY), 0.0D, (double)f6, (double)vec3, (double)endX);
         tessellator.draw();
         GL11.glDisable('\u803a');
         GL11.glPopMatrix();
      } else {
         if(spriteIndex >= 0) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)x, (float)y, (float)z);
            GL11.glEnable('\u803a');
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            swingProgress = entityball.getBallSize();
            GL11.glScalef(swingProgress, swingProgress, swingProgress);
            this.bindTexture(BDHelper.texture);
            f10 = (float)(spriteIndex % 16 * 16 + 0) / 256.0F;
            vec3 = (float)(spriteIndex % 16 * 16 + 16) / 256.0F;
            endX = (float)(spriteIndex / 16 * 16 + 0) / 256.0F;
            i4 = (float)(spriteIndex / 16 * 16 + 16) / 256.0F;
            endY = 1.0F;
            f6 = 0.5F;
            endZ = 0.25F;
            GL11.glRotatef(180.0F - super.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-super.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
            GL11.glTranslatef(0.0F, 0.0F, entityball.getBallData().getZOffset());
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 1.0F, 0.0F);
            tessellator.addVertexWithUV((double)(0.0F - f6), 0.0D, 0.0D, (double)f10, (double)i4);
            tessellator.addVertexWithUV((double)(endY - f6), 0.0D, 0.0D, (double)vec3, (double)i4);
            tessellator.addVertexWithUV((double)(endY - f6), 1.0D, 0.0D, (double)vec3, (double)endX);
            tessellator.addVertexWithUV((double)(0.0F - f6), 1.0D, 0.0D, (double)f10, (double)endX);
            tessellator.draw();
            GL11.glDisable('\u803a');
            GL11.glPopMatrix();
         } else if(spriteIndex == -1 || spriteIndex == -2) {
            swingProgress = entityball.getThrower().getSwingProgress(f1);
            f10 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927F);
            Vec3 var57 = Vec3.createVectorHelper(-0.5D, 0.03D, 0.8D);
            var57.rotateAroundX(-(entityball.getThrower().prevRotationPitch + (entityball.getThrower().rotationPitch - entityball.getThrower().prevRotationPitch) * f1) * 3.1415927F / 180.0F);
            var57.rotateAroundY(-(entityball.getThrower().prevRotationYaw + (entityball.getThrower().rotationYaw - entityball.getThrower().prevRotationYaw) * f1) * 3.1415927F / 180.0F);
            var57.rotateAroundY(f10 * 0.5F);
            var57.rotateAroundX(-f10 * 0.7F);
            double var56 = entityball.getThrower().prevPosX + (entityball.getThrower().posX - entityball.getThrower().prevPosX) * (double)f1 + var57.xCoord;
            double var58 = entityball.getThrower().prevPosY + (entityball.getThrower().posY - entityball.getThrower().prevPosY) * (double)f1 + var57.yCoord;
            double var59 = entityball.getThrower().prevPosZ + (entityball.getThrower().posZ - entityball.getThrower().prevPosZ) * (double)f1 + var57.zCoord;
            if(spriteIndex == -2) {
               ProjectileMagicStormProjectile startX = (ProjectileMagicStormProjectile)entityball.getBallData();
               var56 = startX.x;
               var58 = startX.y;
               var59 = startX.z;
            }

            double var60 = entityball.prevPosX + (entityball.posX - entityball.prevPosX) * (double)f1;
            double startY = entityball.prevPosY + (entityball.posY - entityball.prevPosY) * (double)f1 + 0.25D;
            double startZ = entityball.prevPosZ + (entityball.posZ - entityball.prevPosZ) * (double)f1;
            double despX = (double)((float)(var56 - var60));
            double despY = (double)((float)(var58 - startY));
            double despZ = (double)((float)(var59 - startZ));
            short totalSteps = (short)(entityball.ticksExisted * 2);
            int color = entityball.getBallData().getRopeColor();
            float red = 0.4F;
            float green = 0.4F;
            float blue = 1.0F;
            tessellator.setColorRGBA_F(red, green, blue, 0.2F);
            GL11.glDisable(2884);
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glDisable(3008);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 1);
            float f2 = 0.5F;
            tessellator.setColorRGBA_F(0.9F * f2, 0.9F * f2, 1.0F * f2, 0.3F);
            Random rnd = new Random();
            int startingPos = Math.max(0, totalSteps - 60);

            for(int step = 1; step < 4; ++step) {
               tessellator.startDrawing(5);
               tessellator.setColorRGBA_F(red, green, blue, 0.2F);
               rnd.setSeed((long)entityball.getEntityId());
               double xdesp = Math.cos(-Math.toRadians((double)super.renderManager.playerViewY)) * (double)step * 0.02D;
               double zdesp = Math.sin(Math.toRadians((double)super.renderManager.playerViewY)) * (double)step * 0.02D;

               for(int i = startingPos; i <= totalSteps; ++i) {
                  float stepVariation = (float)i / (float)totalSteps;
                  double varY = 1.0D;
                  double diffx = (rnd.nextDouble() - 0.5D) * varY;
                  double diffz = (rnd.nextDouble() - 0.5D) * varY;
                  double diffy = (rnd.nextDouble() - 0.5D) * varY;
                  if(i == startingPos) {
                     stepVariation = 0.0F;
                     diffx = 0.0D;
                     diffz = 0.0D;
                     diffy = 0.0D;
                  }

                  tessellator.addVertex(x + despX - despX * (double)stepVariation + diffx - xdesp, y + despY - despY * (double)stepVariation + diffy, z + despZ - despZ * (double)stepVariation + diffz + zdesp);
                  tessellator.addVertex(x + despX - despX * (double)stepVariation + diffx + xdesp, y + despY - despY * (double)stepVariation + diffy, z + despZ - despZ * (double)stepVariation + diffz - zdesp);
               }

               tessellator.draw();
            }

            GL11.glEnable(3553);
            GL11.glEnable(2896);
         }

      }
   }

   public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
      this.doRenderFireball((EntityBaseBall)entity, d, d1, d2, f, f1);
   }

   protected ResourceLocation getEntityTexture(Entity entity) {
      return null;
   }
}
