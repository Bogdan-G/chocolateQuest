package com.chocolate.chocolateQuest.gui.guinpc;

import com.chocolate.chocolateQuest.gui.GuiButtonDisplayString;
import com.chocolate.chocolateQuest.gui.GuiButtonMultiOptions;
import com.chocolate.chocolateQuest.gui.GuiButtonTextBox;
import com.chocolate.chocolateQuest.gui.guinpc.GuiButtonSoulBottleSearch;
import com.chocolate.chocolateQuest.gui.guinpc.GuiEditCondition;
import com.chocolate.chocolateQuest.gui.guinpc.GuiLinked;
import com.chocolate.chocolateQuest.gui.guinpc.GuiScrollOptions;
import com.chocolate.chocolateQuest.quest.DialogAction;
import com.chocolate.chocolateQuest.quest.DialogCondition;
import com.chocolate.chocolateQuest.utils.BDHelper;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiEditAction extends GuiLinked {

   DialogAction editingAction;
   GuiButtonTextBox textboxName;
   GuiButtonTextBox textboxSurName;
   GuiButtonTextBox textboxValue;
   GuiButtonMultiOptions operatorValue;
   final int buttonsWidth;
   final int buttonHeight;
   GuiScrollOptions conditions;
   static final int EDIT_CONDITION_ID = 11;
   static final int REMOVE_CONDITION_ID = 12;
   static final int ADD_CONDITION_ID = 13;
   static final int CURRENT_CONDITION_LIST = 14;
   GuiScrollOptions conditionTypes;


   public GuiEditAction(GuiScreen prevGui, DialogAction action) {
      super(prevGui);
      this.editingAction = action;
      super.maxScrollAmmount = 10;
      this.buttonsWidth = super.width - 60;
      this.buttonHeight = 20;
   }

   public void initGui() {
      super.initGui();
      byte buttonSeparation = 10;
      int buttonsWidth = super.width - 60;
      byte buttonHeight = 20;
      byte yPos = 10;
      GuiButtonDisplayString name = new GuiButtonDisplayString(0, 30, yPos, super.width / 2, 20, BDHelper.StringColor("l") + "Action: " + BDHelper.StringColor("r") + DialogAction.actions[this.editingAction.getType()].name);
      super.buttonList.add(name);
      int yPos1 = yPos + buttonHeight + buttonSeparation;
      if(this.editingAction.hasName()) {
         name = new GuiButtonDisplayString(0, 30, yPos1, buttonsWidth, 20, this.editingAction.getNameForName());
         super.buttonList.add(name);
         yPos1 += buttonHeight;
         this.textboxName = this.addButon(30, yPos1, this.editingAction.getSelectorForName(), buttonsWidth, buttonHeight);
         this.textboxName.textbox.setMaxStringLength(Math.max(255, this.editingAction.name.length()));
         this.textboxName.setText(this.editingAction.name);
         yPos1 += this.textboxName.height + buttonSeparation;
      }

      if(this.editingAction.hasOperator()) {
         name = new GuiButtonDisplayString(0, 30, yPos1, buttonsWidth, 20, this.editingAction.getNameForOperator());
         super.buttonList.add(name);
         yPos1 += buttonHeight;
         this.operatorValue = new GuiButtonMultiOptions(0, 30, yPos1, buttonsWidth, buttonHeight, this.editingAction.getOptionsForOperator(), this.editingAction.operator);
         super.buttonList.add(this.operatorValue);
         yPos1 += this.operatorValue.height + buttonSeparation;
      }

      if(this.editingAction.hasValue()) {
         name = new GuiButtonDisplayString(0, 30, yPos1, buttonsWidth, 20, this.editingAction.getNameForValue());
         super.buttonList.add(name);
         yPos1 += buttonHeight;
         this.textboxValue = this.addButon(30, yPos1, this.editingAction.getSelectorForValue(), buttonsWidth, buttonHeight);
         this.textboxValue.textbox.setMaxStringLength(255);
         this.textboxValue.textbox.setText(this.editingAction.value + "");
         if(this.textboxName instanceof GuiButtonSoulBottleSearch) {
            ((GuiButtonSoulBottleSearch)this.textboxName).entityTag = this.editingAction.actionTag;
         }

         yPos1 += this.textboxValue.height + buttonSeparation;
      }

      if(this.editingAction.hasSurname()) {
         name = new GuiButtonDisplayString(0, 30, yPos1, buttonsWidth, 20, this.editingAction.getNameForSurname());
         super.buttonList.add(name);
         yPos1 += buttonHeight;
         this.textboxSurName = this.addButon(30, yPos1, this.editingAction.getSelectorForSurname(), buttonsWidth, buttonHeight);
         this.textboxSurName.textbox.setMaxStringLength(Math.max(255, this.editingAction.surname.length()));
         this.textboxSurName.setText(this.editingAction.surname);
         yPos1 += this.textboxSurName.height + buttonSeparation;
      }

      name = new GuiButtonDisplayString(0, 30, yPos1, buttonsWidth, 20, "Conditions");
      super.buttonList.add(name);
      yPos1 += buttonHeight;
      byte editX = 25;
      byte smallButtonWidth = 40;
      byte horizontalSeparation = 10;
      byte verticalSeparation = 4;
      GuiButtonDisplayString actionName = new GuiButtonDisplayString(0, editX, yPos1, buttonsWidth, buttonHeight, "Actions");
      super.buttonList.add(actionName);
      String[] actionNames = this.getNames(this.editingAction.conditions);
      this.conditions = new GuiScrollOptions(14, editX + smallButtonWidth + horizontalSeparation, yPos1, super.width - editX * 2 - smallButtonWidth - horizontalSeparation, 80, actionNames, super.fontRendererObj, -1);
      super.buttonList.add(this.conditions);
      GuiButton addCondition = new GuiButton(13, editX, yPos1, smallButtonWidth, buttonHeight, "Add");
      super.buttonList.add(addCondition);
      GuiButton editCondition = new GuiButton(11, editX, yPos1 + verticalSeparation + buttonHeight, smallButtonWidth, buttonHeight, "Edit");
      super.buttonList.add(editCondition);
      GuiButton removeCondition = new GuiButton(12, editX, yPos1 + (verticalSeparation + buttonHeight) * 2, smallButtonWidth, buttonHeight, "Remove");
      super.buttonList.add(removeCondition);
      yPos1 += 100;
      ArrayList suggestions = new ArrayList();
      this.editingAction.getSuggestions(suggestions);

      for(Iterator i$ = suggestions.iterator(); i$.hasNext(); yPos1 += buttonHeight) {
         String s = (String)i$.next();
         name = new GuiButtonDisplayString(0, 30, yPos1, buttonsWidth, 20, s);
         super.buttonList.add(name);
      }

      super.maxScrollAmmount = Math.max(0, yPos1 - super.height);
   }

   protected void actionPerformed(GuiButton button) {
      super.actionPerformed(button);
      if(button.id == 13) {
         String[] newAction = this.getNames(DialogCondition.conditions);
         this.conditionTypes = new GuiScrollOptions(super.STATIC_ID, 30, 30, super.width - 60, super.height - 60, newAction, super.fontRendererObj, -1, 12);
         this.setFrontButton(this.conditionTypes);
      }

      DialogCondition newAction1;
      if(button == this.conditionTypes) {
         newAction1 = DialogCondition.conditions[this.conditionTypes.selectedMode].getNewInstance();
         this.editingAction.addCondition(newAction1);
         this.conditionTypes = null;
         this.setFrontButton((GuiButton)null);
         this.conditions.setModeNames(this.getNames(this.editingAction.conditions));
      } else {
         if(this.editingAction.conditions != null && this.conditions.selectedMode >= 0 && this.conditions.selectedMode < this.editingAction.conditions.size()) {
            if(button.id == 11) {
               newAction1 = (DialogCondition)this.editingAction.conditions.get(this.conditions.selectedMode);
               super.mc.displayGuiScreen(new GuiEditCondition(this, newAction1));
            }

            if(button.id == 12) {
               newAction1 = (DialogCondition)this.editingAction.conditions.get(this.conditions.selectedMode);
               this.editingAction.removeCondition(newAction1);
               this.conditions.setModeNames(this.getNames(this.editingAction.conditions));
            }
         }

         this.updateValues();
      }
   }

   protected void keyTyped(char c, int i) {
      super.keyTyped(c, i);
      if(this.textboxName != null) {
         this.editingAction.name = this.textboxName.getValue();
      }

      if(this.textboxSurName != null) {
         this.editingAction.surname = this.textboxSurName.getValue();
      }

      if(this.textboxValue != null) {
         try {
            this.editingAction.value = Integer.valueOf(this.textboxValue.getValue()).intValue();
         } catch (Exception var4) {
            ;
         }
      }

      this.updateValues();
   }

   public void updateValues() {
      if(this.textboxName != null) {
         this.editingAction.name = this.textboxName.getValue();
      }

      if(this.textboxSurName != null) {
         this.editingAction.surname = this.textboxSurName.getValue();
      }

      if(this.textboxValue != null) {
         this.editingAction.value = BDHelper.getIntegerFromString(this.textboxValue.getValue());
      }

      if(this.operatorValue != null) {
         this.editingAction.operator = this.operatorValue.value;
      }

      if(this.textboxName instanceof GuiButtonSoulBottleSearch) {
         this.editingAction.actionTag = ((GuiButtonSoulBottleSearch)this.textboxName).entityTag;
      }

   }
}
