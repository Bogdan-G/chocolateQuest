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
public class GuiButtonAIMode extends GuiButton {

   FontRenderer font;
   int selectedMode = 0;
   String[] modeNames;


   public GuiButtonAIMode(int id, int posX, int posY, int width, int height, String[] par5Str, FontRenderer font, int value) {
      super(id, posX, posY, width, height, "");
      super.displayString = "";
      this.font = font;
      this.modeNames = par5Str;
      this.selectedMode = value;
   }

   public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      Minecraft.getMinecraft().renderEngine.bindTexture(BDHelper.guiButtonsTexture);
      int buttonHeight = super.height / this.modeNames.length;

      int i;
      int y;
      for(i = 0; i < this.modeNames.length; ++i) {
         y = super.yPosition + buttonHeight * i;
         this.isMouseOver(i, par2, par3);
         this.drawTexturedRect(super.xPosition, y, super.width, buttonHeight, 6, 0, 3, 1);
      }

      this.drawTexturedRect(super.xPosition, super.yPosition + buttonHeight * this.selectedMode, super.width, buttonHeight, 6, 1, 3, 1);

      for(i = 0; i < this.modeNames.length; ++i) {
         y = super.yPosition + buttonHeight * i;
         this.drawString(this.font, this.modeNames[i], super.xPosition + 5, y + buttonHeight / 2 - 5, 16777215);
      }

   }

   public void drawTexturedRect(int x, int y, int size, int height, int indexX, int indexZ, int iconWidth, int iconHeight) {
      float f = 0.0625F;
      Tessellator tessellator = Tessellator.instance;
      tessellator.startDrawingQuads();
      tessellator.addVertexWithUV((double)x, (double)(y + height), (double)super.zLevel, (double)((float)(indexX + 0) * f), (double)((float)(indexZ + iconHeight) * f));
      tessellator.addVertexWithUV((double)(x + size), (double)(y + height), (double)super.zLevel, (double)((float)(indexX + iconWidth) * f), (double)((float)(indexZ + iconHeight) * f));
      tessellator.addVertexWithUV((double)(x + size), (double)(y + 0), (double)super.zLevel, (double)((float)(indexX + iconWidth) * f), (double)((float)(indexZ + 0) * f));
      tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), (double)super.zLevel, (double)((float)(indexX + 0) * f), (double)((float)(indexZ + 0) * f));
      tessellator.draw();
   }

   public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3) {
      if(super.mousePressed(par1Minecraft, par2, par3)) {
         this.selectedMode = (par3 - super.yPosition) * this.modeNames.length / super.height;
         return true;
      } else {
         return false;
      }
   }

   public boolean isMouseOver(int selectedMode, int x, int y) {
      if(x >= super.xPosition && y >= super.yPosition && x < super.xPosition + super.width && y < super.yPosition + super.height && selectedMode == (y - super.yPosition) * this.modeNames.length / super.height) {
         GL11.glColor4f(0.9F, 0.9F, 1.0F, 1.0F);
      } else {
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      }

      return false;
   }
}
