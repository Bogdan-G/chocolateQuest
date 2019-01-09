package com.chocolate.chocolateQuest.gui.guinpc;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.npc.EntityHumanNPC;
import com.chocolate.chocolateQuest.gui.GuiButtonDisplayString;
import com.chocolate.chocolateQuest.gui.GuiButtonIcon;
import com.chocolate.chocolateQuest.gui.GuiButtonTextBoxList;
import com.chocolate.chocolateQuest.gui.guinpc.GuiEditAction;
import com.chocolate.chocolateQuest.gui.guinpc.GuiEditCondition;
import com.chocolate.chocolateQuest.gui.guinpc.GuiLinked;
import com.chocolate.chocolateQuest.gui.guinpc.GuiNPC;
import com.chocolate.chocolateQuest.gui.guinpc.GuiScrollOptions;
import com.chocolate.chocolateQuest.packets.PacketEditConversation;
import com.chocolate.chocolateQuest.quest.DialogAction;
import com.chocolate.chocolateQuest.quest.DialogCondition;
import com.chocolate.chocolateQuest.quest.DialogManager;
import com.chocolate.chocolateQuest.quest.DialogOption;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

public class GuiEditDialog extends GuiLinked {

   DialogOption editingDialog;
   GuiScrollOptions dialogs;
   GuiTextField textboxDialogFolder;
   GuiTextField textboxDialogName;
   GuiTextField textboxDialogPrompt;
   final int NUM_OPTIONS_FRONT;
   static final int EDIT_DIALOG_ID = 1;
   static final int REMOVE_DIALOG_ID = 2;
   static final int ADD_DIALOG_ID = 3;
   GuiButtonTextBoxList text;
   static final int TEXT_DOWN = 6;
   static final int TEXT_UP = 7;
   static final int TEXT_SAVE = 8;
   GuiScrollOptions actions;
   static final int EDIT_ACTION_ID = 11;
   static final int REMOVE_ACTION_ID = 12;
   static final int ADD_ACTION_ID = 13;
   GuiScrollOptions actionTypes;
   GuiScrollOptions conditions;
   static final int EDIT_CONDITION_ID = 21;
   static final int REMOVE_CONDITION_ID = 22;
   static final int ADD_CONDITION_ID = 23;
   GuiScrollOptions conditionTypes;
   GuiScrollOptions browseNames;
   int BROWNSE_NAME_SUGGESTION;
   int OPEN_NAME_SUGGESTION;
   static final int CURRENT_CONDITIONS_LIST = 26;
   static final int CURRENT_ACTIONS_LIST = 27;
   static final int CURRENT_DIALOGS_LIST = 28;
   EntityHumanNPC npc;
   static DialogAction clipBoardAction;
   static DialogCondition clipBoardCondition;
   static DialogOption clipBoardDialog;
   static final byte CLIPBOARD_TYPE_DIALOG = 0;
   static final byte CLIPBOARD_TYPE_ACTION = 1;
   static final byte CLIPBOARD_TYPE_CONDITION = 2;
   static final byte CLIPBOARD_TYPE_NONE = -1;
   byte clipboardType;
   String lang;


   public GuiEditDialog(GuiScreen prevGui, DialogOption option) {
      super(prevGui);
      this.NUM_OPTIONS_FRONT = 12;
      this.BROWNSE_NAME_SUGGESTION = 24;
      this.OPEN_NAME_SUGGESTION = 25;
      this.clipboardType = -1;
      this.editingDialog = option;
   }

   public GuiEditDialog(GuiScreen prevGui, DialogOption option, EntityHumanNPC npc) {
      this(prevGui, option);
      this.npc = npc;
   }

   public void initGui() {
      super.initGui();
      this.lang = super.mc.getLanguageManager().getCurrentLanguage().getLanguageCode();
      this.editingDialog.readText(this.lang);
      byte editX = 25;
      byte editY = 5;
      byte buttonsWidth = 110;
      byte buttonHeight = 20;
      byte separation = 5;
      byte editXSeparation = 50;
      byte editYSeparation = 22;
      byte smallButtonWidth = 40;
      int tabWidth = super.width - editX * 2 - smallButtonWidth - editXSeparation * 2;
      GuiButtonDisplayString button = new GuiButtonDisplayString(0, editX, editY, buttonsWidth, buttonHeight, "File");
      super.buttonList.add(button);
      button = new GuiButtonDisplayString(0, editX + buttonsWidth + editXSeparation, editY, buttonsWidth, buttonHeight, "Option name");
      super.buttonList.add(button);
      int var31 = editY + 15;
      this.textboxDialogFolder = new GuiTextField(super.fontRendererObj, editX, var31, buttonsWidth, buttonHeight);
      this.textboxDialogFolder.setMaxStringLength(64);
      this.textboxDialogFolder.setText(this.editingDialog.folder);
      super.textFieldList.add(this.textboxDialogFolder);
      this.textboxDialogName = new GuiTextField(super.fontRendererObj, editX + buttonsWidth + editXSeparation, var31, buttonsWidth, buttonHeight);
      this.textboxDialogName.setMaxStringLength(25);
      this.textboxDialogName.setText(this.editingDialog.name);
      super.textFieldList.add(this.textboxDialogName);
      GuiButton openNameSuggestions = new GuiButton(this.OPEN_NAME_SUGGESTION, editX + buttonsWidth + editXSeparation + buttonsWidth, var31, buttonHeight, buttonHeight, "+");
      super.buttonList.add(openNameSuggestions);
      var31 += 40;
      button = new GuiButtonDisplayString(0, editX, var31, buttonsWidth, buttonHeight, "Display name");
      super.buttonList.add(button);
      var31 += 15;
      this.textboxDialogPrompt = new GuiTextField(super.fontRendererObj, editX, var31, buttonsWidth, buttonHeight);
      this.textboxDialogPrompt.setMaxStringLength(64);
      this.textboxDialogPrompt.setText(this.editingDialog.prompt);
      super.textFieldList.add(this.textboxDialogPrompt);
      var31 += 40;
      int textWidth = super.width / 6 * 4;
      this.text = new GuiButtonTextBoxList(0, editX, var31, textWidth, 80, super.fontRendererObj, this.editingDialog.text);
      super.buttonList.add(this.text);
      GuiTextField[] upButton = this.text.textbox;
      int downButton = upButton.length;

      for(int saveButton = 0; saveButton < downButton; ++saveButton) {
         GuiTextField answers = upButton[saveButton];
         super.textFieldList.add(answers);
      }

      GuiButtonIcon var32 = new GuiButtonIcon(7, editX + textWidth, var31, 14.0F, 3.0F, 1.0F, 1.0F, "");
      super.buttonList.add(var32);
      GuiButtonIcon var33 = new GuiButtonIcon(6, editX + textWidth, var31 + 64, 14.0F, 4.0F, 1.0F, 1.0F, "");
      super.buttonList.add(var33);
      GuiButtonIcon var35 = new GuiButtonIcon(8, editX + textWidth, var31 + 32, 14.0F, 5.0F, 1.0F, 1.0F, "");
      super.buttonList.add(var35);
      button = new GuiButtonDisplayString(0, editX + textWidth, var31 + 44, 50, buttonHeight, "Save text");
      super.buttonList.add(button);
      var31 += 100;
      GuiButtonDisplayString var34 = new GuiButtonDisplayString(0, editX, var31, buttonsWidth, buttonHeight, "Answers");
      super.buttonList.add(var34);
      String[] dialogNames = this.getNames(this.editingDialog.options);
      this.dialogs = new GuiScrollOptions(28, editX + buttonsWidth + separation, var31, tabWidth, 80, dialogNames, super.fontRendererObj, -1);
      super.buttonList.add(this.dialogs);
      GuiButton addDialog = new GuiButton(3, editX, var31 + editYSeparation, 40, buttonHeight, "Add");
      super.buttonList.add(addDialog);
      GuiButton editDialog = new GuiButton(1, editX + editXSeparation, var31 + editYSeparation, 40, buttonHeight, "Edit");
      super.buttonList.add(editDialog);
      GuiButton removeDialog = new GuiButton(2, editX + editXSeparation, var31 + editYSeparation * 2, 40, buttonHeight, "Remove");
      super.buttonList.add(removeDialog);
      var31 += 100;
      GuiButtonDisplayString conditionName = new GuiButtonDisplayString(0, editX, var31, buttonsWidth, buttonHeight, "Conditions");
      super.buttonList.add(conditionName);
      String[] conditionNames = this.getNames(this.editingDialog.conditions);
      this.conditions = new GuiScrollOptions(26, editX + buttonsWidth + separation, var31, tabWidth, 80, conditionNames, super.fontRendererObj, -1);
      super.buttonList.add(this.conditions);
      GuiButton addCondition = new GuiButton(23, editX, var31 + editYSeparation, 40, buttonHeight, "Add");
      super.buttonList.add(addCondition);
      GuiButton editCondition = new GuiButton(21, editX + editXSeparation, var31 + editYSeparation, 40, buttonHeight, "Edit");
      super.buttonList.add(editCondition);
      GuiButton removeCondition = new GuiButton(22, editX + editXSeparation, var31 + editYSeparation * 2, 40, buttonHeight, "Remove");
      super.buttonList.add(removeCondition);
      var31 += 100;
      GuiButtonDisplayString actionName = new GuiButtonDisplayString(0, editX, var31, buttonsWidth, buttonHeight, "Actions");
      super.buttonList.add(actionName);
      String[] actionNames = this.getNames(this.editingDialog.actions);
      this.actions = new GuiScrollOptions(27, editX + buttonsWidth + separation, var31, tabWidth, 80, actionNames, super.fontRendererObj, -1);
      super.buttonList.add(this.actions);
      GuiButton addAction = new GuiButton(13, editX, var31 + editYSeparation, smallButtonWidth, buttonHeight, "Add");
      super.buttonList.add(addAction);
      GuiButton editAction = new GuiButton(11, editX + editXSeparation, var31 + editYSeparation, smallButtonWidth, buttonHeight, "Edit");
      super.buttonList.add(editAction);
      GuiButton removeAction = new GuiButton(12, editX + editXSeparation, var31 + editYSeparation * 2, smallButtonWidth, buttonHeight, "Remove");
      super.buttonList.add(removeAction);
      var31 += 100;
      super.maxScrollAmmount = var31 - super.height;
   }

   protected void actionPerformed(GuiButton button) {
      super.actionPerformed(button);
      DialogOption names;
      if(button.id == 3) {
         names = new DialogOption();
         this.editingDialog.addDialog(names);
         names.folder = this.editingDialog.folder;
         super.mc.displayGuiScreen(new GuiEditDialog(this, names));
      }

      if(this.editingDialog.options != null && this.dialogs.selectedMode >= 0 && this.dialogs.selectedMode < this.editingDialog.options.length) {
         if(button.id == 1) {
            names = this.editingDialog.options[this.dialogs.selectedMode];
            super.mc.displayGuiScreen(new GuiEditDialog(this, names));
         }

         if(button.id == 2) {
            names = this.editingDialog.options[this.dialogs.selectedMode];
            this.editingDialog.removeDialog(names);
            this.dialogs.setModeNames(this.getNames(this.editingDialog.options));
         }
      }

      String[] names1;
      if(button.id == 13) {
         names1 = this.getNames(DialogAction.actions);
         this.actionTypes = new GuiScrollOptions(super.STATIC_ID, 30, 30, super.width - 60, super.height - 60, names1, super.fontRendererObj, -1, 12);
         this.setFrontButton(this.actionTypes);
      }

      DialogAction names2;
      if(button == this.actionTypes) {
         names2 = DialogAction.actions[DialogAction.getIDByName(this.actionTypes.getSelected())].getNewInstance();
         this.editingDialog.addAction(names2);
         this.actionTypes = null;
         this.setFrontButton((GuiButton)null);
         this.actions.setModeNames(this.getNames(this.editingDialog.actions));
      } else {
         if(this.editingDialog.actions != null && this.actions.selectedMode >= 0 && this.actions.selectedMode < this.editingDialog.actions.size()) {
            if(button.id == 11) {
               names2 = (DialogAction)this.editingDialog.actions.get(this.actions.selectedMode);
               super.mc.displayGuiScreen(new GuiEditAction(this, names2));
            }

            if(button.id == 12) {
               names2 = (DialogAction)this.editingDialog.actions.get(this.actions.selectedMode);
               this.editingDialog.removeAction(names2);
               this.actions.setModeNames(this.getNames(this.editingDialog.actions));
            }
         }

         if(button.id == 23) {
            names1 = this.getNames(DialogCondition.conditions);
            this.conditionTypes = new GuiScrollOptions(super.STATIC_ID, 30, 30, super.width - 60, super.height - 60, names1, super.fontRendererObj, -1, 12);
            this.setFrontButton(this.conditionTypes);
         }

         DialogCondition names3;
         if(button == this.conditionTypes) {
            names3 = DialogCondition.conditions[this.conditionTypes.selectedMode].getNewInstance();
            this.editingDialog.addCondition(names3);
            this.conditionTypes = null;
            this.setFrontButton((GuiButton)null);
            this.conditions.setModeNames(this.getNames(this.editingDialog.conditions));
         } else {
            if(this.editingDialog.conditions != null && this.conditions.selectedMode >= 0 && this.conditions.selectedMode < this.editingDialog.conditions.size()) {
               if(button.id == 21) {
                  names3 = (DialogCondition)this.editingDialog.conditions.get(this.conditions.selectedMode);
                  super.mc.displayGuiScreen(new GuiEditCondition(this, names3));
               }

               if(button.id == 22) {
                  names3 = (DialogCondition)this.editingDialog.conditions.get(this.conditions.selectedMode);
                  this.editingDialog.removeCondition(names3);
                  this.conditions.setModeNames(this.getNames(this.editingDialog.conditions));
               }
            }

            if(button.id == this.OPEN_NAME_SUGGESTION) {
               names1 = DialogManager.getOptionNames(this.editingDialog.folder);
               if(names1 != null) {
                  this.browseNames = new GuiScrollOptions(super.STATIC_ID, 30, 30, super.width - 60, super.height - 60, names1, super.fontRendererObj, -1, 12);
                  this.setFrontButton(this.browseNames);
               }
            }

            if(button == this.browseNames) {
               this.textboxDialogName.setText(this.browseNames.modeNames[this.browseNames.selectedMode]);
               this.onDataChange();
               this.browseNames = null;
               this.setFrontButton((GuiButton)null);
            } else {
               if(button.id == 6) {
                  this.text.scrollDown();
               }

               if(button.id == 7) {
                  this.text.scrollUp();
               }

               if(button.id == 8) {
                  this.editingDialog.saveText();
               } else if(button.id == 28) {
                  this.clipboardType = 0;
               } else if(button.id == 27) {
                  this.clipboardType = 1;
               } else if(button.id == 26) {
                  this.clipboardType = 2;
               } else {
                  this.clipboardType = -1;
               }

            }
         }
      }
   }

   protected void keyTyped(char c, int i) {
      super.keyTyped(c, i);
      if(!this.textboxDialogFolder.isFocused() && !this.textboxDialogName.isFocused()) {
         this.editingDialog.text = this.text.getValues();
         this.editingDialog.prompt = this.textboxDialogPrompt.getText();
      } else {
         this.editingDialog.folder = this.textboxDialogFolder.getText();
         this.editingDialog.readText(this.lang);
         this.onDataChange();
      }

      if(i == 46 && Keyboard.isKeyDown(29)) {
         if(this.clipboardType == 0 && this.dialogs.selectedMode < this.editingDialog.options.length) {
            clipBoardDialog = this.editingDialog.options[this.dialogs.selectedMode];
         }

         if(this.clipboardType == 1 && this.actions.selectedMode < this.editingDialog.actions.size()) {
            clipBoardAction = (DialogAction)this.editingDialog.actions.get(this.actions.selectedMode);
         }

         if(this.clipboardType == 2 && this.actions.selectedMode < this.editingDialog.conditions.size()) {
            clipBoardCondition = (DialogCondition)this.editingDialog.conditions.get(this.conditions.selectedMode);
         }
      }

      if(i == 47 && Keyboard.isKeyDown(29)) {
         String[] names;
         if(this.clipboardType == 0 && clipBoardDialog != null) {
            this.editingDialog.addDialog(clipBoardDialog.copy());
            names = this.getNames(this.editingDialog.options);
            this.dialogs.setModeNames(names);
         }

         if(this.clipboardType == 1 && clipBoardAction != null) {
            this.editingDialog.addAction(clipBoardAction.copy());
            names = this.getNames(this.editingDialog.actions);
            this.actions.setModeNames(names);
         }

         if(this.clipboardType == 2 && clipBoardCondition != null) {
            this.editingDialog.addCondition(clipBoardCondition.copy());
            names = this.getNames(this.editingDialog.conditions);
            this.conditions.setModeNames(names);
         }
      }

   }

   protected void onDataChange() {
      this.editingDialog.name = this.textboxDialogName.getText();
      this.editingDialog.readText(this.lang);
      this.text.setValues(this.editingDialog.text);
      this.textboxDialogPrompt.setText(this.editingDialog.prompt);
   }

   protected void mouseClicked(int x, int y, int r) {
      super.mouseClicked(x, y, r);
   }

   public void drawScreen(int x, int y, float fl) {
      super.drawScreen(x, y, fl);
   }

   public void closeGUI() {
      super.mc.displayGuiScreen(super.prevGui);
      if((super.prevGui == null || super.prevGui instanceof GuiNPC) && this.npc != null) {
         PacketEditConversation packet = new PacketEditConversation(this.npc, new int[0]);
         ChocolateQuest.channel.sendPaquetToServer(packet);
      }

   }
}
