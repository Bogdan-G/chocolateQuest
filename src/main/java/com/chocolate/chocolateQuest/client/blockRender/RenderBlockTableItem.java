package com.chocolate.chocolateQuest.client.blockRender;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.client.ClientProxy;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class RenderBlockTableItem implements ISimpleBlockRenderingHandler {

   public int getRenderId() {
      return ClientProxy.tableRenderID;
   }

   public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
      Tessellator tessellator = Tessellator.instance;
      tessellator.startDrawingQuads();
      this.renderBlock();
      tessellator.draw();
   }

   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
      ChocolateQuest.table.setBlockBoundsBasedOnState(world, x, y, z);
      this.renderBlock();
      return false;
   }

   public boolean shouldRender3DInInventory(int modelId) {
      return true;
   }

   public void renderBlock() {
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
      this.drawBox(tessellator, 0.4D, 0.6D, 0.0D, 0.92D, 0.4D, 0.6D, tx0, tx1, ty0, ty1, 1.0F);
   }

   public void drawBox(Tessellator tessellator, double x0, double x1, double y0, double y1, double z0, double z1, double tx0, double tx1, double ty0, double ty1, float b) {
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
      tessellator.addVertexWithUV(x1, y0, z0, tx0, ty1);
      tessellator.addVertexWithUV(x1, y0, z0, tx0, ty0);
      tessellator.addVertexWithUV(x0, y0, z1, tx1, ty0);
      tessellator.setNormal(0.0F, 1.0F, 0.0F);
      tessellator.addVertexWithUV(x0, y1, z1, tx0, ty0);
      tessellator.addVertexWithUV(x1, y1, z1, tx0, ty1);
      tessellator.addVertexWithUV(x1, y1, z0, tx1, ty1);
      tessellator.addVertexWithUV(x0, y1, z0, tx1, ty0);
   }
}
