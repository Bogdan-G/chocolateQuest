package com.chocolate.chocolateQuest.gui.guinpc;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.gui.guinpc.GuiLinked;
import com.chocolate.chocolateQuest.gui.guinpc.GuiScrollFiles;
import com.chocolate.chocolateQuest.packets.PacketEditNPC;
import com.chocolate.chocolateQuest.packets.PacketSaveNPC;
import com.chocolate.chocolateQuest.utils.BDHelper;
import java.io.File;
import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;

public class GuiImportNPC extends GuiLinked {

   GuiTextField textboxFile;
   GuiScrollFiles loadFolder;
   final int LOAD = 0;
   final int SAVE = 1;
   final int IMPORT = 2;
   EntityHumanNPC npc;


   public GuiImportNPC(EntityHumanNPC npc, GuiScreen screen) {
      super(screen);
      super.hasNavigationMenu = false;
      this.npc = npc;
      super.maxScrollAmmount = 0;
   }

   public void initGui() {
      super.initGui();
      File file = new File(BDHelper.getAppDir(), BDHelper.getQuestDir() + "npcExport/");
      if(!file.exists()) {
         file.mkdirs();
      }

      int buttonsWidth = super.width - 40;
      this.loadFolder = new GuiScrollFiles(2, 25, 10, buttonsWidth, super.height - 90, file, super.fontRendererObj, 8);
      super.buttonList.add(this.loadFolder);
      GuiButton b2 = new GuiButton(0, 25, super.height - 80, buttonsWidth, 20, "Load");
      super.buttonList.add(b2);
      this.textboxFile = new GuiTextField(super.fontRendererObj, 25, super.height - 50, buttonsWidth, 20);
      this.textboxFile.setMaxStringLength(25);
      super.textFieldList.add(this.textboxFile);
      GuiButton b3 = new GuiButton(1, 25, super.height - 30, buttonsWidth, 20, "Save");
      super.buttonList.add(b3);
   }

   protected void actionPerformed(GuiButton button) {
      if(button.id == 1) {
         String file = this.textboxFile.getText();
         PacketSaveNPC tag = new PacketSaveNPC(this.npc, file);
         ChocolateQuest.channel.sendPaquetToServer(tag);
      }

      if(button.id == 0) {
         File file1 = this.loadFolder.getSelectedFile();
         if(file1 != null) {
            NBTTagCompound tag1 = BDHelper.readCompressed(file1);
            if(tag1 != null) {
               double x = this.npc.posX;
               double y = this.npc.posY;
               double z = this.npc.posZ;
               this.npc.readEntityFromSpawnerNBT(tag1, MathHelper.floor_double(this.npc.posX), MathHelper.floor_double(this.npc.posY), MathHelper.floor_double(this.npc.posZ));
               this.npc.setPosition(x, y, z);
               this.npc.writeEntityToNBT(tag1);
               PacketEditNPC packet = new PacketEditNPC(this.npc, tag1, (byte)0);
               ChocolateQuest.channel.sendPaquetToServer(packet);
            }
         }
      }

      super.actionPerformed(button);
      if(button.id == 2) {
         this.textboxFile.setText(this.loadFolder.getFileString());
      }

   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   public void saveNPC(NBTTagCompound tag) {
      String name = this.textboxFile.getText();

      try {
         if(name.length() > 0 && !name.equals(" ")) {
            BDHelper.writeCompressed(tag, new File(BDHelper.getAppDir(), BDHelper.getQuestDir() + "npcExport/" + name));
            this.loadFolder.updateFiles();
         }
      } catch (IOException var4) {
         var4.printStackTrace();
      }

   }
}
