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
public class GuiButtonAwakements extends GuiButton {

   int xpRequired = 0;


   public GuiButtonAwakements(int id, int posX, int posY, int width, int height, String s) {
      super(id, posX, posY, width, height, s);
   }

   public void drawButton(Minecraft par1Minecraft, int x, int y) {
      FontRenderer font = Minecraft.getMinecraft().fontRenderer;
      GL11.glColor4f(1.0F, 0.9F, 0.6F, 1.0F);
      Minecraft.getMinecraft().renderEngine.bindTexture(BDHelper.guiButtonsTexture);
      int textColor = 16777215;
      if(!super.enabled) {
         GL11.glColor4f(0.5F, 0.5F, 0.5F, 1.0F);
         textColor = 8947797;
      } else {
         boolean ammount = x >= super.xPosition && y >= super.yPosition && x < super.xPosition + super.width && y < super.yPosition + super.height;
         if(ammount) {
            GL11.glColor4f(1.0F, 0.8F, 0.9F, 1.0F);
            textColor = 16777079;
         }
      }

      this.drawTexturedRect(super.xPosition, super.yPosition, super.width, super.height, 6, 0, 3, 1);
      this.drawString(font, super.displayString, super.xPosition + 2, super.yPosition + 1, textColor);
      if(super.enabled || this.xpRequired > 0) {
         String ammount1 = "" + this.xpRequired;
         int fontWidth = font.getStringWidth(ammount1);
         this.drawString(font, ammount1, super.xPosition + super.width - fontWidth - 1, super.yPosition + super.height - font.FONT_HEIGHT, '\uff00');
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

   public void disable() {
      super.displayString = "";
      this.xpRequired = 0;
      super.enabled = false;
   }
}
