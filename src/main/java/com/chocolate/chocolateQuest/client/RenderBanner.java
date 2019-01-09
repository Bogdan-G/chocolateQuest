package com.chocolate.chocolateQuest.client;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.client.itemsRender.RenderItemBase;
import com.chocolate.chocolateQuest.entity.EntityCursor;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderBanner extends Render {

   private float field_40269_a;


   public RenderBanner(float f) {
      this.field_40269_a = f;
   }

   public void doRender(Entity entity, double x, double y, double z, float f, float f1) {
      EntityCursor e = (EntityCursor)entity;
      if(e.type == 0) {
         int width = 0;
         if(e.item != null) {
            width = e.item.getItemDamage() % 16;
         }

         this.renderBanner(x, y, z, entity.rotationYaw, width, super.renderManager.renderEngine);
      } else {
         float width1 = 1.0F;
         if(e.followEntity != null) {
            width1 = e.followEntity.width;
         }

         this.renderSelector(x, y, z, width1, super.renderManager.renderEngine, e.color);
      }

   }

   public void renderBanner(double x, double y, double z, float rotation, int spriteIndex, TextureManager renderEngine) {
      this.renderBanner(x, y, z, rotation, spriteIndex, renderEngine, 1.0F);
   }

   public void renderBanner(double x, double y, double z, float rotation, int spriteIndex, TextureManager renderEngine, float scale) {
      GL11.glPushMatrix();
      GL11.glTranslatef((float)x, (float)y, (float)z);
      GL11.glRotatef(-rotation, 0.0F, 1.0F, 0.0F);
      GL11.glScalef(scale, scale * 2.0F, scale);
      GL11.glDisable(2896);
      renderEngine.bindTexture(BDHelper.getItemTexture());
      GL11.glDisable(2884);
      float i1 = (float)(spriteIndex % 16 * 16 + 0) / 256.0F;
      float i2 = (float)(spriteIndex % 16 * 16 + 16) / 256.0F;
      float i3 = (float)((spriteIndex / 16 * 2 + 2) * 16) / 256.0F;
      float i4 = (float)((spriteIndex / 16 * 2 + 4) * 16) / 256.0F;
      float f5 = 0.5F;
      Tessellator tessellator = Tessellator.instance;
      tessellator.startDrawingQuads();
      double desp = 0.05D;
      double timeDiff = 1500.0D;
      double d1 = desp + Math.cos((double)System.currentTimeMillis() / timeDiff) * desp;
      double d2 = desp + Math.cos(((double)System.currentTimeMillis() + timeDiff) / timeDiff) * desp;
      tessellator.addVertexWithUV((double)(-f5), 0.10000000149011612D, d1, (double)i1, (double)i4);
      tessellator.addVertexWithUV((double)f5, 0.10000000149011612D, d2, (double)i2, (double)i4);
      tessellator.addVertexWithUV((double)f5, 1.0D, 0.001D, (double)i2, (double)i3);
      tessellator.addVertexWithUV((double)(-f5), 1.0D, 0.001D, (double)i1, (double)i3);
      tessellator.draw();
      GL11.glEnable(2884);
      GL11.glEnable(2896);
      GL11.glTranslatef(-0.5F, 0.0F, 0.0F);
      ItemStack is = new ItemStack(ChocolateQuest.banner, 1, spriteIndex);
      RenderItemBase.doRenderItem(is.getIconIndex(), is, 0, false);
      GL11.glPopMatrix();
   }

   public void renderSelector(double x, double y, double z, float width, TextureManager renderEngine, int color) {
      GL11.glPushMatrix();
      GL11.glTranslated((double)((float)x), (double)((float)y), (double)((float)z));
      GL11.glColor4f(BDHelper.getColorRed(color), BDHelper.getColorGreen(color), BDHelper.getColorBlue(color), 1.0F);
      renderEngine.bindTexture(BDHelper.getItemTexture());
      GL11.glDisable(2884);
      GL11.glDisable(2896);
      GL11.glScalef(width, width, width);
      float i1 = 0.0F;
      float i2 = 0.125F;
      float i3 = 0.6875F;
      float i4 = i3 + 0.125F;
      float f5 = 1.0F;
      Tessellator tessellator = Tessellator.instance;
      f5 = 1.0F;
      tessellator.startDrawingQuads();
      tessellator.addVertexWithUV((double)(-f5), 0.0D, (double)(-f5), (double)i1, (double)i4);
      tessellator.addVertexWithUV((double)f5, 0.0D, (double)(-f5), (double)i2, (double)i4);
      tessellator.addVertexWithUV((double)f5, 0.0D, (double)f5, (double)i2, (double)i3);
      tessellator.addVertexWithUV((double)(-f5), 0.0D, (double)f5, (double)i1, (double)i3);
      tessellator.draw();
      GL11.glEnable(2884);
      GL11.glEnable(2896);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
   }

   protected ResourceLocation getEntityTexture(Entity entity) {
      return null;
   }
}
