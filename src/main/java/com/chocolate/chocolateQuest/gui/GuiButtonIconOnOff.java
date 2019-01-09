package com.chocolate.chocolateQuest.gui;

import com.chocolate.chocolateQuest.gui.GuiButtonIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiButtonIconOnOff extends GuiButtonIcon {

   boolean isOn;


   public GuiButtonIconOnOff(int id, int posX, int posY, float xIndex, float yIndex, float xSize, float ySize, String s, boolean isOn) {
      super(id, posX, posY, xIndex, yIndex, xSize, ySize, s);
      this.isOn = isOn;
      if(isOn) {
         ++super.yIndex;
      }

   }

   public void mouseReleased(int x, int y) {
      if(this.isOn) {
         --super.yIndex;
      } else {
         ++super.yIndex;
      }

      this.isOn = !this.isOn;
      super.mouseReleased(x, y);
   }
}
