package com.chocolate.chocolateQuest.gui.guinpc;

import com.chocolate.chocolateQuest.utils.BDHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiScrollOptions extends GuiButton {

   FontRenderer font;
   int selectedMode;
   public String[] modeNames;
   int scrollAmmount;
   boolean drag;
   int MAX_ENTRIES;
   int SCROLL_WIDTH;
   int MAX_ENTRIES_FINAL;


   public GuiScrollOptions(int id, int posX, int posY, int width, int height, FontRenderer font) {
      this(id, posX, posY, width, height, new String[]{"Empty"}, font, 0);
   }

   public GuiScrollOptions(int id, int posX, int posY, int width, int height, String[] par5Str, FontRenderer font, int value) {
      this(id, posX, posY, width, height, par5Str, font, value, 4);
   }

   public GuiScrollOptions(int id, int posX, int posY, int width, int height, String[] par5Str, FontRenderer font, int value, int maxEntries) {
      super(id, posX, posY, width, height, "");
      this.selectedMode = 0;
      this.drag = false;
      this.MAX_ENTRIES = 4;
      this.SCROLL_WIDTH = 8;
      this.MAX_ENTRIES_FINAL = 4;
      this.font = font;
      this.selectedMode = value;
      this.MAX_ENTRIES_FINAL = maxEntries;
      this.setModeNames(par5Str);
   }

   public void setModeNames(String[] modeNames) {
      this.modeNames = modeNames;
      this.MAX_ENTRIES = Math.min(Math.max(1, modeNames.length), this.MAX_ENTRIES_FINAL);
      if(this.selectedMode > this.MAX_ENTRIES) {
         this.selectedMode = -1;
      }

      this.scrollAmmount = 0;
   }

   public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
      if(super.enabled) {
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         Minecraft.getMinecraft().renderEngine.bindTexture(BDHelper.guiButtonsTexture);
         int buttonHeight = super.height / this.MAX_ENTRIES;

         int currentPosition;
         int scrollY;
         for(currentPosition = 0; currentPosition < this.MAX_ENTRIES; ++currentPosition) {
            scrollY = super.yPosition + buttonHeight * currentPosition;
            this.drawTexturedRect(super.xPosition, scrollY, super.width, buttonHeight, 6.0F, 0.0F, 3.0F, 1.0F);
         }

         currentPosition = this.selectedMode - this.scrollAmmount;
         if(currentPosition >= 0 && currentPosition < this.MAX_ENTRIES) {
            scrollY = super.xPosition;
            int currentY = super.yPosition + buttonHeight * currentPosition;
            this.drawSelected(scrollY, currentY);
         }

         if(this.modeNames.length - this.MAX_ENTRIES > 0) {
            this.drawTexturedRect(super.xPosition + super.width - this.SCROLL_WIDTH, super.yPosition, this.SCROLL_WIDTH, super.height, 15.5F, 2.0F, 0.5F, 4.0F);
            scrollY = this.scrollAmmount * (super.height - this.SCROLL_WIDTH) / (this.modeNames.length - this.MAX_ENTRIES);
            this.drawTexturedRect(super.xPosition + super.width - this.SCROLL_WIDTH, super.yPosition + scrollY, this.SCROLL_WIDTH, this.SCROLL_WIDTH, 15.0F, 2.0F, 0.5F, 0.5F);
         }

         this.drawText((Minecraft)null, buttonHeight);
         this.mouseDragged(par1Minecraft, par2, par3);
      }

   }

   public void drawText(Minecraft par1Minecraft, int buttonHeight) {
      for(int i = 0; i < this.MAX_ENTRIES && this.scrollAmmount + i < this.modeNames.length; ++i) {
         int y = super.yPosition + buttonHeight * i;
         this.drawString(this.font, this.modeNames[this.scrollAmmount + i], super.xPosition + 5, y + buttonHeight / 2 - 5, 16777215);
      }

   }

   public void drawSelected(int x, int y) {
      int buttonHeight = super.height / this.MAX_ENTRIES;
      this.drawTexturedRect(x, y, super.width, buttonHeight, 6.0F, 1.0F, 3.0F, 1.0F);
   }

   public void drawTexturedRect(int x, int y, int size, int height, float indexX, float indexZ, float iconWidth, float iconHeight) {
      float f = 0.0625F;
      Tessellator tessellator = Tessellator.instance;
      tessellator.startDrawingQuads();
      tessellator.addVertexWithUV((double)x, (double)(y + height), (double)super.zLevel, (double)((indexX + 0.0F) * f), (double)((indexZ + iconHeight) * f));
      tessellator.addVertexWithUV((double)(x + size), (double)(y + height), (double)super.zLevel, (double)((indexX + iconWidth) * f), (double)((indexZ + iconHeight) * f));
      tessellator.addVertexWithUV((double)(x + size), (double)(y + 0), (double)super.zLevel, (double)((indexX + iconWidth) * f), (double)((indexZ + 0.0F) * f));
      tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), (double)super.zLevel, (double)((indexX + 0.0F) * f), (double)((indexZ + 0.0F) * f));
      tessellator.draw();
   }

   protected void mouseDragged(Minecraft par1Minecraft, int x, int y) {
      if(this.drag && super.enabled && super.visible && x >= super.xPosition && y >= super.yPosition && x < super.xPosition + super.width && y < super.yPosition + super.height) {
         this.handleMouseScroll(x, y);
      } else {
         this.drag = false;
      }

      super.mouseDragged(par1Minecraft, x, y);
   }

   public void mouseReleased(int x, int y) {
      this.drag = false;
   }

   public boolean mousePressed(Minecraft par1Minecraft, int x, int y) {
      if(super.mousePressed(par1Minecraft, x, y)) {
         if(this.handleMouseScroll(x, y)) {
            return false;
         } else {
            this.selectedMode = (y - super.yPosition) * this.MAX_ENTRIES / super.height + this.scrollAmmount;
            return true;
         }
      } else {
         return false;
      }
   }

   public boolean handleMouseScroll(int x, int y) {
      if(x - super.xPosition > super.width - this.SCROLL_WIDTH) {
         this.scrollAmmount = (y - super.yPosition) * (this.modeNames.length - this.MAX_ENTRIES + 1) / super.height;
         return true;
      } else {
         return false;
      }
   }

   public void scrollDown() {
      if(this.selectedMode < this.modeNames.length - 1) {
         ++this.selectedMode;
         if(this.selectedMode - this.MAX_ENTRIES >= this.scrollAmmount) {
            ++this.scrollAmmount;
         }
      }

   }

   public void scrollUp() {
      if(this.selectedMode > 0) {
         --this.selectedMode;
         if(this.selectedMode < this.scrollAmmount) {
            --this.scrollAmmount;
         }
      }

   }

   public String getSelected() {
      return this.modeNames[this.selectedMode];
   }

   public int getSelectedIndex() {
      return this.selectedMode;
   }
}
