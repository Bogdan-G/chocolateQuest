package com.chocolate.chocolateQuest.gui;

import com.chocolate.chocolateQuest.utils.BDHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiButtonIcon extends GuiButton {

   float xIndex;
   float yIndex;
   float xSize;
   float ySize;


   public GuiButtonIcon(int id, int posX, int posY, float xIndex, float yIndex, float xSize, float ySize, String s) {
      this(id, posX, posY, xIndex, yIndex, xSize, ySize, s, 1.0F);
   }

   public GuiButtonIcon(int id, int posX, int posY, float xIndex, float yIndex, float xSize, float ySize, String s, float scale) {
      super(id, posX, posY, (int)(xSize * 16.0F * scale), (int)(ySize * 16.0F * scale), s);
      this.xIndex = xIndex;
      this.yIndex = yIndex;
      this.xSize = xSize;
      this.ySize = ySize;
   }

   public void drawButton(Minecraft par1Minecraft, int x, int y) {
      if(super.visible) {
         FontRenderer font = Minecraft.getMinecraft().fontRenderer;
         Minecraft.getMinecraft().renderEngine.bindTexture(BDHelper.guiButtonsTexture);
         int textColor = 16777215;
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         boolean mouseOver = x >= super.xPosition && y >= super.yPosition && x < super.xPosition + super.width && y < super.yPosition + super.height;
         if(mouseOver) {
            GL11.glColor4f(0.9F, 0.9F, 1.0F, 1.0F);
            textColor = 16777079;
         }

         drawTexturedRect(super.xPosition, super.yPosition, super.zLevel, super.width, super.height, this.xIndex, this.yIndex, this.xSize, this.ySize);
         this.drawString(font, super.displayString, super.xPosition + 16, super.yPosition + 4, textColor);
      }

   }

   public static void drawTexturedRect(int x, int y, float z, int size, int height, float indexX, float indexZ, float iconWidth, float iconHeight) {
      float f = 0.0625F;
      Tessellator tessellator = Tessellator.instance;
      tessellator.startDrawingQuads();
      tessellator.addVertexWithUV((double)x, (double)(y + height), (double)z, (double)((indexX + 0.0F) * f), (double)((indexZ + iconHeight) * f));
      tessellator.addVertexWithUV((double)(x + size), (double)(y + height), (double)z, (double)((indexX + iconWidth) * f), (double)((indexZ + iconHeight) * f));
      tessellator.addVertexWithUV((double)(x + size), (double)(y + 0), (double)z, (double)((indexX + iconWidth) * f), (double)((indexZ + 0.0F) * f));
      tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), (double)z, (double)((indexX + 0.0F) * f), (double)((indexZ + 0.0F) * f));
      tessellator.draw();
   }
}
