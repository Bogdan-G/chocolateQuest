package com.chocolate.chocolateQuest.gui;

import com.chocolate.chocolateQuest.gui.GuiButtonTextBoxList;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

class TextField extends GuiTextField {

   GuiButtonTextBoxList gui;


   public TextField(FontRenderer font, int x, int y, int width, int height, GuiButtonTextBoxList holder) {
      super(font, x, y, width, height);
      this.gui = holder;
   }

   public boolean textboxKeyTyped(char c, int i) {
      boolean b = super.textboxKeyTyped(c, i);
      this.gui.updateValues();
      return b;
   }

   public void setText(String p_146180_1_) {
      super.setText(p_146180_1_);
   }
}
