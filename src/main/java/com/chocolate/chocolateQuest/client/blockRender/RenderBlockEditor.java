package com.chocolate.chocolateQuest.client.blockRender;

import com.chocolate.chocolateQuest.block.BlockEditorTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class RenderBlockEditor extends TileEntitySpecialRenderer {

   public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
      BlockEditorTileEntity eb = (BlockEditorTileEntity)tileentity;
      Tessellator tessellator = Tessellator.instance;
      GL11.glDisable(2884);
      this.bindTexture(TextureMap.locationBlocksTexture);
      GL11.glDisable(3553);
      GL11.glDisable(2896);
      double var10001 = (double)tileentity.xCoord;
      double var10002 = (double)tileentity.yCoord;
      int line = (int)(Minecraft.getMinecraft().thePlayer.getDistance(var10001, var10002, (double)tileentity.zCoord) * 0.8D);
      byte line1 = 3;
      GL11.glLineWidth((float)line1);
      tessellator.startDrawing(3);
      tessellator.setColorOpaque_I(16733525);
      tessellator.addVertex(x + 1.0D, y, z + 1.0D);
      tessellator.addVertex(x + (double)eb.red + 1.0D, y, z + 1.0D);
      tessellator.draw();
      tessellator.startDrawing(3);
      tessellator.setColorOpaque_I(16777184);
      tessellator.addVertex(x + 1.0D, y, z + 1.0D);
      tessellator.addVertex(x + 1.0D, y, z + 1.0D + (double)eb.yellow);
      tessellator.draw();
      tessellator.startDrawing(3);
      tessellator.setColorOpaque_I(16755370);
      tessellator.addVertex(x + 1.0D, y, z + 1.0D);
      tessellator.addVertex(x + 1.0D, y + (double)eb.height, z + 1.0D);
      GL11.glColor3f(1.0F, 1.0F, 0.8F);
      tessellator.draw();
      tessellator.startDrawing(3);
      tessellator.setColorOpaque_I(16777184);
      tessellator.addVertex(x + (double)eb.red + 1.0D, y + (double)eb.height, z + (double)eb.yellow + 1.0D);
      tessellator.addVertex(x + 1.0D, y + (double)eb.height, z + (double)eb.yellow + 1.0D);
      tessellator.draw();
      tessellator.startDrawing(3);
      tessellator.setColorOpaque_I(16733525);
      tessellator.addVertex(x + (double)eb.red + 1.0D, y + (double)eb.height, z + (double)eb.yellow + 1.0D);
      tessellator.addVertex(x + (double)eb.red + 1.0D, y + (double)eb.height, z + 1.0D);
      tessellator.draw();
      tessellator.startDrawing(3);
      tessellator.setColorOpaque_I(16755370);
      tessellator.addVertex(x + (double)eb.red + 1.0D, y + (double)eb.height, z + (double)eb.yellow + 1.0D);
      tessellator.addVertex(x + (double)eb.red + 1.0D, y, z + (double)eb.yellow + 1.0D);
      tessellator.draw();
      tessellator.startDrawing(3);
      tessellator.setColorOpaque_I(16733525);
      tessellator.addVertex(x + 1.0D, y + (double)eb.height, z + 1.0D);
      tessellator.addVertex(x + (double)eb.red + 1.0D, y + (double)eb.height, z + 1.0D);
      tessellator.draw();
      tessellator.startDrawing(3);
      tessellator.setColorOpaque_I(16777184);
      tessellator.addVertex(x + 1.0D, y + (double)eb.height, z + 1.0D);
      tessellator.addVertex(x + 1.0D, y + (double)eb.height, z + 1.0D + (double)eb.yellow);
      tessellator.draw();
      tessellator.startDrawing(3);
      tessellator.setColorOpaque_I(16733525);
      tessellator.addVertex(x + (double)eb.red + 1.0D, y, z + 1.0D);
      tessellator.addVertex(x + (double)eb.red + 1.0D, y + (double)eb.height, z + 1.0D);
      tessellator.draw();
      tessellator.startDrawing(3);
      tessellator.setColorOpaque_I(16777184);
      tessellator.addVertex(x + 1.0D, y, z + 1.0D + (double)eb.yellow);
      tessellator.addVertex(x + 1.0D, y + (double)eb.height, z + 1.0D + (double)eb.yellow);
      tessellator.draw();
      tessellator.startDrawing(3);
      tessellator.setColorOpaque_I(16777184);
      tessellator.addVertex(x + (double)eb.red + 1.0D, y, z + (double)eb.yellow + 1.0D);
      tessellator.addVertex(x + 1.0D, y, z + (double)eb.yellow + 1.0D);
      tessellator.draw();
      tessellator.startDrawing(3);
      tessellator.setColorOpaque_I(16733525);
      tessellator.addVertex(x + (double)eb.red + 1.0D, y, z + (double)eb.yellow + 1.0D + 1.0D);
      tessellator.addVertex(x + (double)eb.red + 1.0D, y, z + 1.0D);
      tessellator.draw();
      GL11.glEnable(2896);
      GL11.glEnable(3553);
   }

   public void renderPart() {}
}
