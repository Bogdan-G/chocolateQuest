package com.chocolate.chocolateQuest.gui;

import com.chocolate.chocolateQuest.gui.GuiButtonAIMode;
import com.chocolate.chocolateQuest.utils.BDHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiButtonBattleAIMode extends GuiButtonAIMode {

   public GuiButtonBattleAIMode(int id, int posX, int posY, int width, int height, String[] par5Str, FontRenderer font, int value) {
      super(id, posX, posY, width, height, par5Str, font, value);
   }

   public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      Minecraft.getMinecraft().renderEngine.bindTexture(BDHelper.guiButtonsTexture);
      int buttonHeight = super.height / super.modeNames.length;

      for(int i = 0; i < super.modeNames.length; ++i) {
         this.isMouseOver(i, par2, par3);
         int y = super.yPosition + buttonHeight * i;
         this.drawTexturedRect(super.xPosition, y, super.width, buttonHeight, 5, i, 1, 1);
      }

      this.drawTexturedRect(super.xPosition, super.yPosition + buttonHeight * super.selectedMode, super.width, buttonHeight, 4, 1, 1, 1);
   }
}
