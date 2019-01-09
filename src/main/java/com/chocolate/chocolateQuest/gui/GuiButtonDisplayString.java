package com.chocolate.chocolateQuest.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

@SideOnly(Side.CLIENT)
public class GuiButtonDisplayString extends GuiButton {

   final int color;


   public GuiButtonDisplayString(int id, int posX, int posY, int width, int height, String par5Str) {
      this(id, posX, posY, width, height, par5Str, 16777079);
   }

   public GuiButtonDisplayString(int id, int posX, int posY, int width, int height, String par5Str, int color) {
      super(id, posX, posY, width, height, par5Str);
      this.color = color;
   }

   public void drawButton(Minecraft mc, int par2, int par3) {
      this.drawCenteredString(mc.fontRenderer, super.displayString, super.xPosition + super.width / 2, super.yPosition + (super.height - 8) / 2, this.color);
   }
}
