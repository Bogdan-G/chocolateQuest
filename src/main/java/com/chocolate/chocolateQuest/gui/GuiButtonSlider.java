package com.chocolate.chocolateQuest.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiButtonSlider extends GuiButton {

   int SLIDER_MAX_VALUE;
   public float sliderValue;
   public boolean dragging;
   boolean isInteger;
   String name;


   public GuiButtonSlider(int par1, int par2, int par3, String par5Str, float par6) {
      this(par1, par2, par3, par5Str, par6, 10);
   }

   public GuiButtonSlider(int par1, int par2, int par3, String par5Str, float par6, int maxValue) {
      this(par1, par2, par3, par5Str, par6, maxValue, false);
   }

   public GuiButtonSlider(int par1, int par2, int par3, String par5Str, float par6, int maxValue, boolean isInteger) {
      super(par1, par2, par3, 108, 20, par5Str);
      this.SLIDER_MAX_VALUE = 10;
      this.sliderValue = 1.0F;
      this.isInteger = false;
      this.sliderValue = par6;
      this.SLIDER_MAX_VALUE = maxValue;
      this.name = par5Str;
      this.isInteger = isInteger;
      float value = this.sliderValue * (float)this.SLIDER_MAX_VALUE;
      super.displayString = this.name + ": " + (isInteger?(float)((int)Math.ceil((double)value)):value);
   }

   protected void mouseDragged(Minecraft par1Minecraft, int par2, int par3) {
      if(super.enabled) {
         if(this.dragging) {
            this.sliderValue = (float)(par2 - (super.xPosition + 4)) / (float)(super.width - 8);
            if(this.sliderValue < 0.01F) {
               this.sliderValue = 0.01F;
            }

            if(this.sliderValue > 1.0F) {
               this.sliderValue = 1.0F;
            }

            float value = this.sliderValue * (float)this.SLIDER_MAX_VALUE;
            super.displayString = this.name + ": " + (this.isInteger?(float)((int)Math.ceil((double)value)):value);
         }

         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.drawTexturedModalRect(super.xPosition + (int)(this.sliderValue * (float)(super.width - 8)), super.yPosition, 0, 66, 4, 20);
         this.drawTexturedModalRect(super.xPosition + (int)(this.sliderValue * (float)(super.width - 8)) + 4, super.yPosition, 196, 66, 4, 20);
      }

   }

   public boolean mousePressed(Minecraft par1Minecraft, int x, int y) {
      if(super.mousePressed(par1Minecraft, x, y)) {
         this.sliderValue = (float)(x - (super.xPosition + 4)) / (float)(super.width - 8);
         if(this.sliderValue < 0.0F) {
            this.sliderValue = 0.0F;
         }

         if(this.sliderValue > 1.0F) {
            this.sliderValue = 1.0F;
         }

         this.dragging = !this.dragging;
         return true;
      } else {
         return false;
      }
   }

   public void mouseReleased(int x, int y) {
      this.dragging = false;
      super.mouseReleased(x, y);
   }
}
