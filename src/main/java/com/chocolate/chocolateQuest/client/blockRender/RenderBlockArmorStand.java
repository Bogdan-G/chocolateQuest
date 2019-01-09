package com.chocolate.chocolateQuest.client.blockRender;

import com.chocolate.chocolateQuest.block.BlockArmorStandTileEntity;
import com.chocolate.chocolateQuest.client.RenderBanner;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class RenderBlockArmorStand extends TileEntitySpecialRenderer {

   RenderBanner render = new RenderBanner(0.0F);
   EntityHumanBase entity;


   public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
      BlockArmorStandTileEntity te = (BlockArmorStandTileEntity)tileentity;
      GL11.glPushMatrix();
      GL11.glTranslatef((float)x + 0.5F, (float)y, (float)z + 0.5F);
      GL11.glRotatef((float)(-te.rotation), 0.0F, 1.0F, 0.0F);
      if(te.cargoItems != null) {
         if(this.entity == null) {
            this.entity = new EntityHumanBase(Minecraft.getMinecraft().theWorld);
            this.entity.setCurrentItemOrArmor(0, (ItemStack)null);
         } else if(this.entity.worldObj != Minecraft.getMinecraft().theWorld) {
            this.entity = new EntityHumanBase(Minecraft.getMinecraft().theWorld);
            this.entity.setCurrentItemOrArmor(0, (ItemStack)null);
         }

         for(int i = 0; i < 4; ++i) {
            this.entity.setCurrentItemOrArmor(i + 1, te.cargoItems[i]);
         }

         this.entity.setCurrentItemOrArmor(0, te.cargoItems[4]);
         RenderManager.instance.renderEntityWithPosYaw(this.entity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
      }

      GL11.glPopMatrix();
   }

   public void drawBox(double x0, double x1, double y0, double y1, double z0, double z1, double tx0, double tx1, double ty0, double ty1) {
      Tessellator tessellator = Tessellator.instance;
      tessellator.startDrawingQuads();
      tessellator.addVertexWithUV(x1, y0, z0, tx0, ty0);
      tessellator.addVertexWithUV(x0, y0, z0, tx1, ty0);
      tessellator.addVertexWithUV(x0, y1, z0, tx1, ty1);
      tessellator.addVertexWithUV(x1, y1, z0, tx0, ty1);
      tessellator.addVertexWithUV(x0, y0, z1, tx0, ty0);
      tessellator.addVertexWithUV(x1, y0, z1, tx1, ty0);
      tessellator.addVertexWithUV(x1, y1, z1, tx1, ty1);
      tessellator.addVertexWithUV(x0, y1, z1, tx0, ty1);
      tessellator.addVertexWithUV(x1, y1, z0, tx1, ty1);
      tessellator.addVertexWithUV(x1, y1, z1, tx0, ty1);
      tessellator.addVertexWithUV(x1, y0, z1, tx0, ty0);
      tessellator.addVertexWithUV(x1, y0, z0, tx1, ty0);
      tessellator.addVertexWithUV(x0, y1, z1, tx1, ty1);
      tessellator.addVertexWithUV(x0, y1, z0, tx0, ty1);
      tessellator.addVertexWithUV(x0, y0, z0, tx0, ty0);
      tessellator.addVertexWithUV(x0, y0, z1, tx1, ty0);
      tessellator.addVertexWithUV(x0, y0, z1, tx1, ty1);
      tessellator.addVertexWithUV(x0, y0, z0, tx0, ty1);
      tessellator.addVertexWithUV(x1, y0, z0, tx0, ty0);
      tessellator.addVertexWithUV(x1, y0, z1, tx1, ty0);
      tessellator.addVertexWithUV(x0, y1, z1, tx1, ty1);
      tessellator.addVertexWithUV(x1, y1, z1, tx1, ty0);
      tessellator.addVertexWithUV(x1, y1, z0, tx0, ty0);
      tessellator.addVertexWithUV(x0, y1, z0, tx0, ty1);
      tessellator.draw();
   }
}
