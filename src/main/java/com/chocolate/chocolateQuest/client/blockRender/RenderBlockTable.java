package com.chocolate.chocolateQuest.client.blockRender;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.block.BlockAltarTileEntity;
import com.chocolate.chocolateQuest.client.RenderBanner;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import org.lwjgl.opengl.GL11;

public class RenderBlockTable extends TileEntitySpecialRenderer {

   RenderBanner render = new RenderBanner(0.0F);
   EntityItem entityitem;


   public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
      GL11.glPushMatrix();
      GL11.glTranslatef((float)x, (float)y, (float)z);
      this.bindTexture(TextureMap.locationBlocksTexture);
      BlockAltarTileEntity te = (BlockAltarTileEntity)tileentity;
      ChocolateQuest.table.setBlockBoundsBasedOnState(tileentity.getWorldObj(), tileentity.xCoord, tileentity.yCoord, tileentity.zCoord);
      Tessellator tessellator = Tessellator.instance;
      IIcon icon = Blocks.planks.getBlockTextureFromSide(0);
      double tx0 = (double)icon.getMinU();
      double tx1 = (double)icon.getMaxU();
      double ty0 = (double)icon.getMinV();
      double ty1 = (double)icon.getMaxV();
      double y0 = 0.9D;
      double y1 = 1.0D;
      double x0 = ChocolateQuest.table.getBlockBoundsMinX();
      double x1 = ChocolateQuest.table.getBlockBoundsMaxX();
      double z0 = ChocolateQuest.table.getBlockBoundsMinZ();
      double z1 = ChocolateQuest.table.getBlockBoundsMaxZ();
      this.drawBox(tessellator, x0, x1, y0, y1, z0, z1, tx0, tx1, ty0, ty1, 1.0F);
      if(ChocolateQuest.table.getBlockBoundsMinY() < 0.1D) {
         this.drawBox(tessellator, 0.375D, 0.625D, 0.0D, 0.92D, 0.375D, 0.625D, tx0, tx1, ty0, ty1, 1.0F);
      }

      if(te.item != null) {
         if(this.entityitem == null) {
            this.entityitem = new EntityItem(tileentity.getWorldObj(), 0.0D, 0.0D, 0.0D, te.item);
            this.entityitem.hoverStart = 0.3F;
         } else {
            this.entityitem.rotationYaw = (float)te.rotation;
            this.entityitem.setEntityItemStack(te.item);
         }

         RenderManager.instance.renderEntityWithPosYaw(this.entityitem, 0.5D, 1.0D, 0.5D, 0.0F, 0.0F);
      }

      GL11.glPopMatrix();
   }

   public void drawBox(Tessellator tessellator, double x0, double x1, double y0, double y1, double z0, double z1, double tx0, double tx1, double ty0, double ty1, float b) {
      tessellator.startDrawingQuads();
      tessellator.setNormal(0.0F, 0.0F, 1.0F);
      tessellator.addVertexWithUV(x1, y0, z0, tx0, ty0);
      tessellator.addVertexWithUV(x0, y0, z0, tx1, ty0);
      tessellator.addVertexWithUV(x0, y1, z0, tx1, ty1);
      tessellator.addVertexWithUV(x1, y1, z0, tx0, ty1);
      tessellator.setNormal(0.0F, 0.0F, -1.0F);
      tessellator.addVertexWithUV(x0, y0, z1, tx0, ty0);
      tessellator.addVertexWithUV(x1, y0, z1, tx1, ty0);
      tessellator.addVertexWithUV(x1, y1, z1, tx1, ty1);
      tessellator.addVertexWithUV(x0, y1, z1, tx0, ty1);
      tessellator.setNormal(-1.0F, 0.0F, 0.0F);
      tessellator.addVertexWithUV(x1, y1, z0, tx1, ty0);
      tessellator.addVertexWithUV(x1, y1, z1, tx0, ty0);
      tessellator.addVertexWithUV(x1, y0, z1, tx0, ty1);
      tessellator.addVertexWithUV(x1, y0, z0, tx1, ty1);
      tessellator.setNormal(1.0F, 0.0F, 0.0F);
      tessellator.addVertexWithUV(x0, y1, z0, tx0, ty0);
      tessellator.addVertexWithUV(x0, y0, z0, tx0, ty1);
      tessellator.addVertexWithUV(x0, y0, z1, tx1, ty1);
      tessellator.addVertexWithUV(x0, y1, z1, tx1, ty0);
      tessellator.setNormal(0.0F, -1.0F, 0.0F);
      tessellator.addVertexWithUV(x0, y0, z1, tx1, ty1);
      tessellator.addVertexWithUV(x0, y0, z0, tx0, ty1);
      tessellator.addVertexWithUV(x1, y0, z0, tx0, ty0);
      tessellator.addVertexWithUV(x1, y0, z1, tx1, ty0);
      tessellator.setNormal(0.0F, 1.0F, 0.0F);
      tessellator.addVertexWithUV(x0, y1, z1, tx0, ty0);
      tessellator.addVertexWithUV(x1, y1, z1, tx0, ty1);
      tessellator.addVertexWithUV(x1, y1, z0, tx1, ty1);
      tessellator.addVertexWithUV(x0, y1, z0, tx1, ty0);
      tessellator.draw();
   }
}
