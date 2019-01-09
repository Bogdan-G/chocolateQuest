package com.chocolate.chocolateQuest.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

@SideOnly(Side.CLIENT)
public class GuiButtonMultiOptions extends GuiButton {

   public int value = 0;
   String[] names;


   public GuiButtonMultiOptions(int id, int posX, int posY, int width, int height, String[] par5Str, int value) {
      super(id, posX, posY, width, height, par5Str[value]);
      this.names = par5Str;
      this.value = value;
   }

   public boolean mousePressed(Minecraft mc, int x, int y) {
      if(super.mousePressed(mc, x, y)) {
         this.setValue(++this.value);
         return true;
      } else {
         return false;
      }
   }

   public void setValue(int value) {
      if(value >= this.names.length) {
         value = 0;
      }

      this.value = value;
      super.displayString = this.names[value];
   }
}
