package com.chocolate.chocolateQuest.gui;

import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

public class GuiTextFieldInteger extends GuiTextField {

   public GuiTextFieldInteger(FontRenderer font, int x, int y, int width, int height) {
      super(font, x, y, width, height);
   }

   public boolean textboxKeyTyped(char c, int i) {
      return c != 48 && c != 49 && c != 50 && c != 51 && c != 52 && c != 53 && c != 54 && c != 55 && c != 56 && c != 57 && c != 45 && i != 14 && i != 203 && i != 205 && i != 201?false:super.textboxKeyTyped(c, i);
   }

   public int getIntegerValue() {
      return BDHelper.getIntegerFromString(this.getText());
   }
}
