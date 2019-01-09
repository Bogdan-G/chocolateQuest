package com.chocolate.chocolateQuest.client;

import com.chocolate.chocolateQuest.entity.EntityPortal;
import com.chocolate.chocolateQuest.utils.BDHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderEntityPortal extends Render {

   public static final ResourceLocation enderPortalEndSkyTextures = new ResourceLocation("textures/environment/end_sky.png");
   public static final ResourceLocation endPortalTextures = new ResourceLocation("textures/entity/end_portal.png");


   public RenderEntityPortal() {
      super.shadowSize = 0.0F;
   }

   public void doRender(EntityPortal entity, double x, double y, double z, float f, float f1) {
      float yOffset;
      if(entity.type != 2) {
         GL11.glPushMatrix();
         GL11.glEnable(2884);
         super.renderManager.renderEngine.bindTexture(BDHelper.getItemTexture());
         GL11.glTranslatef((float)x, (float)y, (float)z);
         GL11.glRotatef(-entity.rotationYaw, 0.0F, 1.0F, 0.0F);
         GL11.glDisable(2896);
         GL11.glEnable(3042);

         for(int f2 = 0; f2 < 6; ++f2) {
            GL11.glMatrixMode(5890);
            GL11.glPushMatrix();
            if(f2 == 0) {
               this.bindTexture(enderPortalEndSkyTextures);
               GL11.glBlendFunc(770, 771);
               GL11.glTexGeni(8193, 9472, 9216);
               GL11.glTexGeni(8192, 9472, 9216);
               GL11.glEnable(3168);
               GL11.glEnable(3169);
               yOffset = 4.0F - (float)(Minecraft.getSystemTime() % 10000L) / 10000.0F * 4.0F;
               GL11.glScalef(yOffset, yOffset, yOffset);
               GL11.glRotatef(yOffset, 0.0F, 1.0F, 0.0F);
            } else {
               this.bindTexture(endPortalTextures);
               GL11.glBlendFunc(1, 1);
               GL11.glScalef((float)f2, (float)f2, (float)f2);
               GL11.glTranslatef((float)f2 * 0.8F, (float)(Minecraft.getSystemTime() % 70000L) / 7000.0F * (float)f2, 0.0F);
            }

            Tessellator var18 = Tessellator.instance;
            if(entity.type == 0) {
               var18.startDrawingQuads();
               var18.addVertexWithUV(-0.5D, 0.0D, 0.001D, 0.0D, 0.0D);
               var18.addVertexWithUV(0.5D, 0.0D, 0.001D, 1.0D, 0.0D);
               var18.addVertexWithUV(0.5D, 2.0D, 0.001D, 1.0D, 1.0D);
               var18.addVertexWithUV(-0.5D, 2.0D, 0.001D, 0.0D, 1.0D);
               var18.draw();
            } else {
               var18.startDrawingQuads();
               var18.addVertexWithUV(-0.5D, 0.0010000000474974513D, 0.5D, 0.0D, 1.0D);
               var18.addVertexWithUV(0.5D, 0.0010000000474974513D, 0.5D, 1.0D, 1.0D);
               var18.addVertexWithUV(0.5D, 0.0010000000474974513D, -0.5D, 1.0D, 0.0D);
               var18.addVertexWithUV(-0.5D, 0.0010000000474974513D, -0.5D, 0.0D, 0.0D);
               var18.draw();
            }

            GL11.glPopMatrix();
            GL11.glMatrixMode(5888);
            GL11.glDisable(3168);
            GL11.glDisable(3169);
            GL11.glEnable(2896);
         }

         GL11.glDisable(3042);
         GL11.glPopMatrix();
      } else if(Minecraft.getMinecraft().thePlayer.capabilities.isCreativeMode) {
         GL11.glPushMatrix();
         float var19 = 0.026666671F;
         yOffset = 1.0F;
         GL11.glTranslatef((float)x + 0.0F, (float)y + yOffset, (float)z);
         GL11.glNormal3f(0.0F, 1.0F, 0.0F);
         GL11.glRotatef(-super.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(super.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
         GL11.glScalef(-var19, -var19, var19);
         GL11.glDisable(2896);
         GL11.glDisable(3553);
         FontRenderer fontrenderer = this.getFontRendererFromRenderManager();
         String s = entity.name;
         GL11.glDepthMask(false);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         boolean byte0 = false;
         byte var20 = -10;
         Tessellator tessellator = Tessellator.instance;
         tessellator.startDrawingQuads();
         double width = (double)(fontrenderer.getStringWidth(s) / 2);
         tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
         tessellator.addVertex(-width - 1.0D, (double)(-1 + var20), 0.0D);
         tessellator.addVertex(-width - 1.0D, (double)(8 + var20), 0.0D);
         tessellator.addVertex(width + 1.0D, (double)(8 + var20), 0.0D);
         tessellator.addVertex(width + 1.0D, (double)(-1 + var20), 0.0D);
         tessellator.draw();
         GL11.glEnable(3553);
         fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, var20, 553648127);
         GL11.glDepthMask(true);
         fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, var20, -1);
         GL11.glPopMatrix();
      }

   }

   protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
      return this.getEntityTexture((EntityPortal)p_110775_1_);
   }

   public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) {
      this.doRender((EntityPortal)entity, d0, d1, d2, f, f1);
   }

}
