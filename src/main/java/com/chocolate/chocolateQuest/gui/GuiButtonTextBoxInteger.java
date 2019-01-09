package com.chocolate.chocolateQuest.gui;

import com.chocolate.chocolateQuest.gui.GuiButtonTextBox;
import com.chocolate.chocolateQuest.gui.GuiTextFieldInteger;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.FontRenderer;

@SideOnly(Side.CLIENT)
public class GuiButtonTextBoxInteger extends GuiButtonTextBox {

   public GuiButtonTextBoxInteger(int id, int posX, int posY, int width, int height, FontRenderer font) {
      super(id, posX, posY, width, height, font);
      super.textbox = new GuiTextFieldInteger(font, posX, posY, width, height);
   }

   public GuiButtonTextBoxInteger(int id, int posX, int posY, int width, int height, FontRenderer font, int defaultValue) {
      this(id, posX, posY, width, height, font);
      super.textbox.setText(defaultValue + "");
   }
}
