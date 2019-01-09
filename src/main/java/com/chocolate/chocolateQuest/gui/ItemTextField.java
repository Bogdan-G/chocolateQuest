package com.chocolate.chocolateQuest.gui;

import com.chocolate.chocolateQuest.gui.GuiButtonItemSearch;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

class ItemTextField extends GuiTextField {

   GuiButtonItemSearch gui;


   public ItemTextField(FontRenderer font, int x, int y, int width, int height, GuiButtonItemSearch holder) {
      super(font, x, y, width, height);
      this.gui = holder;
   }

   public boolean textboxKeyTyped(char c, int i) {
      boolean b = super.textboxKeyTyped(c, i);
      this.gui.updateItem();
      return b;
   }

   public void setText(String p_146180_1_) {
      super.setText(p_146180_1_);
   }
}
