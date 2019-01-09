package com.chocolate.chocolateQuest.gui;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.block.BlockEditorTileEntity;
import com.chocolate.chocolateQuest.packets.PacketEditorGUIClose;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.world.World;

public class GuiEditor extends GuiScreen {

   private GuiTextField textboxSX;
   private GuiTextField textboxSY;
   private GuiTextField textboxHeight;
   private GuiTextField textboxFile;
   private GuiButton buttonOK;
   private GuiButton buttonExit;
   private GuiButton buttonPaste;
   World world;
   int posX;
   int posY;
   int posZ;
   int x;
   int y;
   int z;
   BlockEditorTileEntity block;
   byte ACTION_UPDATE = 0;
   byte ACTION_EXPORT = 1;


   public GuiEditor(World world, int x, int y, int z) {
      this.posX = x;
      this.posY = y;
      this.posZ = z;
      this.block = (BlockEditorTileEntity)world.getTileEntity(x, y, z);
      this.x = this.block.red;
      this.y = this.block.height;
      this.z = this.block.yellow;
      this.world = world;
   }

   protected void actionPerformed(GuiButton guibutton) {
      if(guibutton.displayString == this.buttonOK.displayString) {
         boolean sx = true;
         boolean sz = true;
         boolean sy = true;

         try {
            int sx1 = Integer.parseInt(this.textboxSX.getText().trim());
            int sz1 = Integer.parseInt(this.textboxSY.getText().trim());
            int sy1 = Integer.parseInt(this.textboxHeight.getText().trim());
         } catch (Exception var6) {
            ;
         }

         this.sendPacket(this.ACTION_EXPORT);
      } else if(guibutton.displayString == this.buttonExit.displayString) {
         this.sendPacket(this.ACTION_UPDATE);
         super.mc.displayGuiScreen((GuiScreen)null);
      } else if(guibutton.displayString == this.buttonPaste.displayString) {
         ;
      }

   }

   public void sendPacket(byte action) {
      try {
         this.block.red = Integer.parseInt(this.textboxSX.getText().trim());
         this.block.yellow = Integer.parseInt(this.textboxSY.getText().trim());
         this.block.height = Integer.parseInt(this.textboxHeight.getText().trim());
      } catch (Exception var6) {
         ;
      }

      this.block.setName(this.textboxFile.getText());
      ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
      DataOutputStream outputStream = new DataOutputStream(bos);

      try {
         outputStream.writeByte(0);
         outputStream.writeInt(this.posX);
         outputStream.writeInt(this.posY);
         outputStream.writeInt(this.posZ);
         outputStream.writeInt(this.block.red);
         outputStream.writeInt(this.block.yellow);
         outputStream.writeInt(this.block.height);
         outputStream.writeUTF(this.block.name);
      } catch (Exception var5) {
         var5.printStackTrace();
      }

      ChocolateQuest.channel.sendPaquetToServer(new PacketEditorGUIClose(this.posX, this.posY, this.posZ, this.block.red, this.block.yellow, this.block.height, this.block.name, action));
   }

   public void initGui() {
      super.initGui();
      super.buttonList.clear();
      this.buttonOK = new GuiButton(0, super.width / 2 - 150, 40, "Export");
      super.buttonList.add(this.buttonOK);
      this.buttonExit = new GuiButton(1, super.width / 2 - 150, 200, "Close");
      super.buttonList.add(this.buttonExit);
      this.textboxSX = new GuiTextField(super.fontRendererObj, super.width / 2 - 100, 70, 20, 20);
      this.textboxSX.setText("" + this.x);
      this.textboxSX.setFocused(true);
      this.textboxSX.setMaxStringLength(3);
      this.textboxSY = new GuiTextField(super.fontRendererObj, super.width / 2 - 100, 100, 20, 20);
      this.textboxSY.setText("" + this.z);
      this.textboxSY.setMaxStringLength(3);
      this.textboxHeight = new GuiTextField(super.fontRendererObj, super.width / 2 - 100, 130, 20, 20);
      this.textboxHeight.setText("" + this.y);
      this.textboxHeight.setMaxStringLength(3);
      this.textboxFile = new GuiTextField(super.fontRendererObj, super.width / 2 - 100, 160, 130, 20);
      this.textboxFile.setText(this.block.getName());
      this.textboxFile.setMaxStringLength(20);
   }

   public void drawScreen(int i, int j, float f) {
      this.drawDefaultBackground();
      super.drawScreen(i, j, f);
      this.drawString(super.fontRendererObj, "Size X", super.width / 2 - 160, 70, 16711680);
      this.drawString(super.fontRendererObj, "Size Z", super.width / 2 - 160, 100, 16776960);
      this.drawString(super.fontRendererObj, "Height", super.width / 2 - 160, 130, 16777215);
      this.drawString(super.fontRendererObj, "File name", super.width / 2 - 160, 160, 16777215);
      this.textboxSX.drawTextBox();
      this.textboxSY.drawTextBox();
      this.textboxHeight.drawTextBox();
      this.textboxFile.drawTextBox();
      super.drawScreen(i, j, f);
   }

   protected void keyTyped(char c, int i) {
      if(this.textboxSX.isFocused()) {
         this.textboxSX.textboxKeyTyped(c, i);
      }

      if(this.textboxSY.isFocused()) {
         this.textboxSY.textboxKeyTyped(c, i);
      }

      if(this.textboxHeight.isFocused()) {
         this.textboxHeight.textboxKeyTyped(c, i);
      }

      if(this.textboxFile.isFocused()) {
         this.textboxFile.textboxKeyTyped(c, i);
      }

      super.keyTyped(c, i);
   }

   private void setFalse() {
      this.textboxSX.setFocused(false);
      this.textboxSY.setFocused(false);
      this.textboxHeight.setFocused(false);
      this.textboxFile.setFocused(false);
   }

   protected void mouseClicked(int i, int j, int k) {
      super.mouseClicked(i, j, k);
      if(j > 70 && j < 90) {
         this.setFalse();
         this.textboxSX.setFocused(true);
      }

      if(j > 100 && j < 120) {
         this.setFalse();
         this.textboxSY.setFocused(true);
      }

      if(j > 130 && j < 150) {
         this.setFalse();
         this.textboxHeight.setFocused(true);
      }

      if(j > 170 && j < 190) {
         this.setFalse();
         this.textboxFile.setFocused(true);
      }

   }
}
