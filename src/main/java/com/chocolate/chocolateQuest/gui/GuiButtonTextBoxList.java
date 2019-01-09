package com.chocolate.chocolateQuest.gui;

import com.chocolate.chocolateQuest.gui.TextField;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;

@SideOnly(Side.CLIENT)
public class GuiButtonTextBoxList extends GuiButton {

   public GuiTextField[] textbox;
   FontRenderer fontRenderer;
   int scroll = 0;
   List values = new ArrayList();
   final int TEXT_BOXES = 4;


   public GuiButtonTextBoxList(int id, int posX, int posY, int width, int height, FontRenderer font, String[] values) {
      super(id, posX, posY, width, height, "");
      this.fontRenderer = font;
      this.textbox = new GuiTextField[4];

      for(int i = 0; i < this.textbox.length; ++i) {
         this.textbox[i] = new TextField(font, posX, posY + i * 20, width, height / 4, this);
         this.textbox[i].setMaxStringLength(128);
      }

      this.setValues(values);
   }

   public void drawButton(Minecraft mc, int x, int y) {}

   public String[] getValues() {
      for(int last = this.values.size(); last > 1 && ((String)this.values.get(last - 1)).isEmpty(); last = this.values.size()) {
         this.values.remove(last - 1);
      }

      if(this.values.size() == 0) {
         return new String[]{"Nothing to tell you"};
      } else {
         String[] s = new String[this.values.size()];

         for(int i = 0; i < s.length; ++i) {
            s[i] = (String)this.values.get(i);
         }

         return s;
      }
   }

   public void setValues(String[] text) {
      if(text == null) {
         text = new String[]{""};
      }

      this.values.clear();
      String[] arr$ = text;
      int len$ = text.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         String s = arr$[i$];
         this.values.add(s);
      }

      this.setTextBoxValues();
   }

   public void setTextBoxValues() {
      for(int i = 0; i < this.textbox.length; ++i) {
         String s = "";
         if(this.scroll + i < this.values.size()) {
            s = (String)this.values.get(this.scroll + i);
         }

         if(s == null) {
            s = "";
         }

         this.textbox[i].setText(s);
      }

   }

   public void updateValues() {
      for(int i = 0; i < 4; ++i) {
         if(this.values.size() > this.scroll + i) {
            this.values.set(this.scroll + i, this.textbox[i].getText());
         } else {
            this.values.add(this.textbox[i].getText());
         }
      }

   }

   public boolean mousePressed(Minecraft mc, int x, int y) {
      if(super.mousePressed(mc, x, y)) {
         this.updateValues();
         return true;
      } else {
         return false;
      }
   }

   public void scrollUp() {
      if(this.scroll > 0) {
         --this.scroll;
      }

      this.setTextBoxValues();
   }

   public void scrollDown() {
      ++this.scroll;
      if(this.values.size() > this.scroll) {
         this.values.add("");
      }

      this.setTextBoxValues();
   }
}
