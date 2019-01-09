package com.chocolate.chocolateQuest.gui.guinpc;

import com.chocolate.chocolateQuest.gui.guinpc.GuiNPC;
import com.chocolate.chocolateQuest.gui.guinpc.GuiScrollOptions;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.File;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

@SideOnly(Side.CLIENT)
public class GuiScrollConversation extends GuiScrollOptions {

   File file;
   public File[] files;


   public GuiScrollConversation(int id, int posX, int posY, int width, int height, String[] par5Str, FontRenderer font, int value) {
      super(id, posX, posY, width, height, par5Str, font, value);
   }

   public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
      super.drawButton(par1Minecraft, par2, par3);
      if(super.selectedMode < super.modeNames.length && super.selectedMode >= 0) {
         int buttonHeight = super.height / super.MAX_ENTRIES;
         int var10000 = super.yPosition + buttonHeight * (super.selectedMode - super.scrollAmmount);
      }

   }

   public void drawText(Minecraft par1Minecraft, int buttonHeight) {
      for(int i = 0; i < super.MAX_ENTRIES && super.scrollAmmount + i < super.modeNames.length; ++i) {
         int y = super.yPosition + buttonHeight * i;
         int color = 16777215;
         if(i == super.selectedMode) {
            color = 16776960;
         }

         GuiNPC.drawTextWithSpecialChars(super.modeNames[super.scrollAmmount + i], super.xPosition + 5, y + buttonHeight / 2 - 5, super.font, color);
      }

   }

   public void drawSelected(int x, int y) {}
}
