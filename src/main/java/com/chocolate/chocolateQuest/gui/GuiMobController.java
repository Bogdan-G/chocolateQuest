package com.chocolate.chocolateQuest.gui;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.gui.GuiButtonDisplayString;
import com.chocolate.chocolateQuest.packets.PacketController;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;

public class GuiMobController extends GuiScreen {

   final int MOVE = 0;
   final int WARD_POSITION = 1;
   final int TEAM_EDITOR = 2;
   final int CLEAR = 3;
   final int DO_NOTHING = 999;


   public void initGui() {
      super.initGui();
      byte x = 10;
      byte buttonSeparation = 4;
      short buttonsWidth = 150;
      byte buttonHeight = 20;
      byte yPos = 0;
      GuiButtonDisplayString name = new GuiButtonDisplayString(999, x, yPos, buttonsWidth, buttonHeight, "Modes");
      super.buttonList.add(name);
      int yPos1 = yPos + buttonHeight;
      GuiButton button = new GuiButton(0, x, yPos1, buttonsWidth, buttonHeight, StatCollector.translateToLocal("item.move.name") + " (Q)");
      super.buttonList.add(button);
      yPos1 += buttonHeight + buttonSeparation;
      button = new GuiButton(1, x, yPos1, buttonsWidth, buttonHeight, StatCollector.translateToLocal("item.ward.name") + " (W)");
      super.buttonList.add(button);
      yPos1 += buttonHeight + buttonSeparation;
      button = new GuiButton(2, x, yPos1, buttonsWidth, buttonHeight, StatCollector.translateToLocal("item.team.name") + " (E)");
      super.buttonList.add(button);
      yPos1 += buttonHeight + buttonSeparation + 10;
      button = new GuiButton(3, x, yPos1, buttonsWidth, buttonHeight, StatCollector.translateToLocal("item.reset.name") + " (R)");
      super.buttonList.add(button);
   }

   protected void actionPerformed(GuiButton button) {
      super.actionPerformed(button);
      this.actionPerformed(button.id);
   }

   public void actionPerformed(int id) {
      if(id == 0 || id == 1 || id == 2 || id == 3) {
         PacketController packet = new PacketController((byte)id);
         ChocolateQuest.channel.sendPaquetToServer(packet);
         Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
      }

   }

   protected void keyTyped(char c, int i) {
      super.keyTyped(c, i);
      if(i == 16) {
         this.actionPerformed(0);
         super.mc.thePlayer.addChatMessage(new ChatComponentText("Set mode: move"));
      }

      if(i == 17) {
         this.actionPerformed(1);
         super.mc.thePlayer.addChatMessage(new ChatComponentText("Set mode: guard"));
      }

      if(i == 18) {
         this.actionPerformed(2);
         super.mc.thePlayer.addChatMessage(new ChatComponentText("Set mode: team editor"));
      }

      if(i == 19) {
         this.actionPerformed(3);
         super.mc.thePlayer.addChatMessage(new ChatComponentText("Cleared entities"));
      }

   }
}
