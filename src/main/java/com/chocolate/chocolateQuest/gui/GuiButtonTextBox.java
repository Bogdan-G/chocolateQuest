package com.chocolate.chocolateQuest.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;

@SideOnly(Side.CLIENT)
public class GuiButtonTextBox extends GuiButton {

   public GuiTextField textbox;
   protected FontRenderer fontRenderer;


   public GuiButtonTextBox(int id, int posX, int posY, int width, int height, FontRenderer font) {
      super(id, posX, posY, width, height, "");
      this.fontRenderer = font;
      this.textbox = new GuiTextField(font, posX, posY, width, height);
   }

   public void drawButton(Minecraft mc, int x, int y) {
      super.drawButton(mc, x, y);
      this.textbox.xPosition = super.xPosition;
      this.textbox.yPosition = super.yPosition;
      this.textbox.drawTextBox();
   }

   public String getValue() {
      String s = this.textbox.getText();
      if(s.contains("-")) {
         boolean negative = s.startsWith("-");
         s = s.replace("-", "");
         if(negative) {
            s = "-".concat(s);
         }
      }

      return s;
   }

   public void setText(String text) {
      this.textbox.setText(text);
   }
}
