package com.chocolate.chocolateQuest.gui.guinpc;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.ai.npcai.EnumNpcAI;
import com.chocolate.chocolateQuest.entity.ai.npcai.NpcAI;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.gui.GuiButtonMultiOptions;
import com.chocolate.chocolateQuest.gui.GuiTextFieldInteger;
import com.chocolate.chocolateQuest.gui.guinpc.GuiButtonAISlider;
import com.chocolate.chocolateQuest.gui.guinpc.GuiLinked;
import com.chocolate.chocolateQuest.gui.guinpc.GuiScrollOptions;
import com.chocolate.chocolateQuest.packets.PacketClientAsks;
import com.chocolate.chocolateQuest.packets.PacketEditNPC;
import com.chocolate.chocolateQuest.utils.AIPosition;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.nbt.NBTTagCompound;

public class GuiAIPositions extends GuiLinked {

   final int POSITIONS_ID = 0;
   final int SAVE = 1;
   final int DELETE = 2;
   final int GET_ITEM = 3;
   final int AI_BUTTON = 4;
   final int ADD_AI = 5;
   final int DELETE_AI = 6;
   final int EDIT_AI = 7;
   final int EDIT_AI_TYPE = 8;
   EntityHumanNPC npc;
   GuiScrollOptions positions;
   GuiTextField fieldName;
   GuiTextFieldInteger fieldX;
   GuiTextFieldInteger fieldY;
   GuiTextFieldInteger fieldZ;
   GuiTextFieldInteger fieldRot;
   GuiButton saveButton;
   GuiButton deleteButton;
   GuiButton getItemButton;
   GuiButtonAISlider AIButton;
   GuiTextField currentAIPosition;
   GuiButtonMultiOptions buttonAIType;


   public GuiAIPositions(EntityHumanNPC npc) {
      this.npc = npc;
   }

   public void initGui() {
      super.initGui();
      byte x = 25;
      byte y = 10;
      short buttonWidth = 180;
      byte buttonHeight = 20;
      byte buttonSeparation = 5;
      byte smallButtonWidth = 40;
      int y1;
      if(this.npc.AIPositions != null) {
         this.positions = new GuiScrollOptions(0, x, y, buttonWidth, 80, this.getPositionNames(), super.fontRendererObj, -1);
         super.buttonList.add(this.positions);
         int addAI = x + buttonWidth + buttonSeparation;
         y = 10;
         this.fieldName = new GuiTextField(super.fontRendererObj, addAI, y, buttonWidth, buttonHeight);
         super.textFieldList.add(this.fieldName);
         y1 = y + buttonHeight + buttonSeparation;
         this.fieldX = new GuiTextFieldInteger(super.fontRendererObj, addAI, y1, smallButtonWidth, buttonHeight);
         super.textFieldList.add(this.fieldX);
         this.fieldY = new GuiTextFieldInteger(super.fontRendererObj, addAI + smallButtonWidth + buttonSeparation, y1, smallButtonWidth, buttonHeight);
         super.textFieldList.add(this.fieldY);
         this.fieldZ = new GuiTextFieldInteger(super.fontRendererObj, addAI + (smallButtonWidth + buttonSeparation) * 2, y1, smallButtonWidth, buttonHeight);
         super.textFieldList.add(this.fieldZ);
         this.fieldRot = new GuiTextFieldInteger(super.fontRendererObj, addAI + (smallButtonWidth + buttonSeparation) * 3, y1, smallButtonWidth, buttonHeight);
         super.textFieldList.add(this.fieldRot);
         y1 += buttonHeight + buttonSeparation;
         this.saveButton = new GuiButton(1, addAI, y1, smallButtonWidth, buttonHeight, "Save");
         super.buttonList.add(this.saveButton);
         this.deleteButton = new GuiButton(2, addAI + smallButtonWidth + buttonSeparation, y1, smallButtonWidth, buttonHeight, "Delete");
         super.buttonList.add(this.deleteButton);
         this.getItemButton = new GuiButton(3, addAI + (smallButtonWidth + buttonSeparation) * 2, y1, smallButtonWidth, buttonHeight, "Get Item");
         super.buttonList.add(this.getItemButton);
         this.tooglePositionEdit(false);
      }

      this.AIButton = new GuiButtonAISlider(7, x, 100, 4.0F, 13.0F, 12.0F, 1.0F, this.npc.npcAI);
      super.buttonList.add(this.AIButton);
      short y2 = 135;
      GuiButton addAI1 = new GuiButton(5, x, y2, smallButtonWidth, buttonHeight, "Add AI");
      super.buttonList.add(addAI1);
      GuiButton deleteAI = new GuiButton(6, x + smallButtonWidth + buttonSeparation, y2, smallButtonWidth, buttonHeight, "Remove AI");
      super.buttonList.add(deleteAI);
      y1 = y2 + buttonHeight + buttonSeparation;
      this.buttonAIType = new GuiButtonMultiOptions(8, x, y1, buttonWidth, buttonHeight, EnumNpcAI.getNames(), 0);
      super.buttonList.add(this.buttonAIType);
      this.currentAIPosition = new GuiTextField(super.fontRendererObj, x + buttonWidth + buttonSeparation, y1, buttonWidth, buttonHeight);
      super.textFieldList.add(this.currentAIPosition);
   }

   public String[] getPositionNames() {
      String[] positionsNames;
      if(this.npc.AIPositions.size() > 0) {
         positionsNames = new String[this.npc.AIPositions.size()];

         for(int i = 0; i < positionsNames.length; ++i) {
            positionsNames[i] = ((AIPosition)this.npc.AIPositions.get(i)).name;
         }
      } else {
         positionsNames = new String[0];
      }

      return positionsNames;
   }

   protected void actionPerformed(GuiButton button) {
      super.actionPerformed(button);
      if(this.positions != null) {
         int ai = this.positions.getSelectedIndex();
         if(ai < this.npc.AIPositions.size()) {
            AIPosition packet;
            if(button.id == 0) {
               packet = (AIPosition)this.npc.AIPositions.get(ai);
               this.fieldX.setText("" + packet.xCoord);
               this.fieldY.setText("" + packet.yCoord);
               this.fieldZ.setText("" + packet.zCoord);
               this.fieldRot.setText("" + packet.rot);
               this.fieldName.setText(packet.name);
               this.tooglePositionEdit(true);
            } else if(button.id == 1) {
               packet = new AIPosition(this.fieldX.getIntegerValue(), this.fieldY.getIntegerValue(), this.fieldZ.getIntegerValue(), this.fieldRot.getIntegerValue(), this.fieldName.getText());
               this.npc.AIPositions.set(ai, packet);
               this.positions.setModeNames(this.getPositionNames());
            } else if(button.id == 2) {
               this.npc.AIPositions.remove(ai);
               String[] var6;
               if(this.npc.AIPositions != null && this.npc.AIPositions.size() > 0) {
                  var6 = new String[this.npc.AIPositions.size()];

                  for(int i = 0; i < var6.length; ++i) {
                     var6[i] = ((AIPosition)this.npc.AIPositions.get(i)).name;
                  }
               } else {
                  var6 = new String[0];
               }

               this.positions.setModeNames(var6);
               this.getItemButton.enabled = false;
               this.tooglePositionEdit(false);
            } else if(button.id == 3) {
               PacketClientAsks var7 = new PacketClientAsks((byte)5, this.npc.getEntityId(), ai);
               ChocolateQuest.channel.sendPaquetToServer(var7);
            }
         }
      }

      NpcAI var5;
      if(button.id == 7) {
         var5 = this.AIButton.getSelectedAI();
         if(var5 != null) {
            this.buttonAIType.value = var5.type;
            this.currentAIPosition.setText(var5.position);
            this.buttonAIType.setValue(var5.type);
         }
      } else if(button.id == 5) {
         this.AIButton.addAI(new NpcAI());
      } else if(button.id == 6) {
         this.AIButton.removeSelectedAI();
      } else if(button.id == 8) {
         var5 = this.AIButton.getSelectedAI();
         if(var5 != null) {
            var5.type = this.buttonAIType.value;
         }
      }

   }

   protected void keyTyped(char c, int i) {
      super.keyTyped(c, i);
      if(this.currentAIPosition.isFocused()) {
         NpcAI ai = this.AIButton.getSelectedAI();
         if(ai != null) {
            ai.position = this.currentAIPosition.getText();
         }
      }

   }

   public void tooglePositionEdit(boolean enabled) {
      this.fieldX.setVisible(enabled);
      this.fieldY.setVisible(enabled);
      this.fieldZ.setVisible(enabled);
      this.fieldRot.setVisible(enabled);
      this.fieldName.setVisible(enabled);
      this.saveButton.visible = enabled;
      this.deleteButton.visible = enabled;
      this.getItemButton.visible = enabled;
   }

   public void onGuiClosed() {
      this.npc.npcAI = this.AIButton.getAI();
      NBTTagCompound tag = new NBTTagCompound();
      this.npc.writeAIToNBT(tag);
      if(this.npc.npcAI == null) {
         tag.setBoolean("NpcAI", false);
      }

      PacketEditNPC packet = new PacketEditNPC(this.npc, tag, (byte)0);
      ChocolateQuest.channel.sendPaquetToServer(packet);
      super.onGuiClosed();
   }
}
