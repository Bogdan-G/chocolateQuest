package com.chocolate.chocolateQuest.gui.guinpc;

import com.chocolate.chocolateQuest.gui.GuiButtonDisplayString;
import com.chocolate.chocolateQuest.gui.GuiButtonMultiOptions;
import com.chocolate.chocolateQuest.gui.GuiButtonTextBox;
import com.chocolate.chocolateQuest.gui.guinpc.GuiLinked;
import com.chocolate.chocolateQuest.quest.DialogCondition;
import com.chocolate.chocolateQuest.utils.BDHelper;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiEditCondition extends GuiLinked {

   DialogCondition editingAction;
   GuiButtonTextBox textboxName;
   GuiButtonTextBox textboxValue;
   GuiButtonMultiOptions operatorValue;


   public GuiEditCondition(GuiScreen prevGui, DialogCondition action) {
      super(prevGui);
      this.editingAction = action;
      super.maxScrollAmmount = 10;
   }

   public void initGui() {
      super.initGui();
      int buttonsWidth = super.width - 60;
      byte buttonHeight = 20;
      byte buttonSeparation = 10;
      int yPos = 10;
      GuiButtonDisplayString name = new GuiButtonDisplayString(0, 30, yPos, super.width / 2, 20, BDHelper.StringColor("l") + "Condition: " + BDHelper.StringColor("r") + DialogCondition.conditions[this.editingAction.getType()].name);
      super.buttonList.add(name);
      if(this.editingAction.hasName()) {
         yPos += buttonHeight + buttonSeparation;
         name = new GuiButtonDisplayString(0, 30, yPos, buttonsWidth, 20, this.editingAction.getNameForName());
         super.buttonList.add(name);
         yPos += buttonHeight;
         this.textboxName = this.addButon(30, yPos, this.editingAction.getSelectorForName(), buttonsWidth, buttonHeight);
         this.textboxName.textbox.setMaxStringLength(255);
         this.textboxName.textbox.setText(this.editingAction.name);
         yPos += this.textboxName.height + buttonSeparation;
      }

      if(this.editingAction.hasOperator()) {
         name = new GuiButtonDisplayString(0, 30, yPos, buttonsWidth, 20, this.editingAction.getNameForOperator());
         super.buttonList.add(name);
         yPos += buttonHeight;
         this.operatorValue = new GuiButtonMultiOptions(0, 30, yPos, buttonsWidth, buttonHeight, this.editingAction.getOptionsForOperator(), this.editingAction.operator);
         super.buttonList.add(this.operatorValue);
         yPos += this.operatorValue.height + buttonSeparation;
      }

      if(this.editingAction.hasValue()) {
         name = new GuiButtonDisplayString(0, 30, yPos, buttonsWidth, 20, this.editingAction.getNameForValue());
         super.buttonList.add(name);
         yPos += buttonHeight;
         this.textboxValue = this.addButon(30, yPos, this.editingAction.getSelectorForValue(), buttonsWidth, buttonHeight);
         this.textboxValue.textbox.setMaxStringLength(255);
         this.textboxValue.textbox.setText(this.editingAction.value + "");
         yPos += this.textboxValue.height + buttonSeparation;
      }

      ArrayList suggestions = new ArrayList();
      this.editingAction.getSuggestions(suggestions);

      for(Iterator i$ = suggestions.iterator(); i$.hasNext(); yPos += buttonHeight) {
         String s = (String)i$.next();
         name = new GuiButtonDisplayString(0, 30, yPos, buttonsWidth, 20, s);
         super.buttonList.add(name);
      }

   }

   protected void actionPerformed(GuiButton button) {
      super.actionPerformed(button);
      this.updateValues();
   }

   protected void keyTyped(char c, int i) {
      super.keyTyped(c, i);
      this.updateValues();
   }

   public void updateValues() {
      if(this.textboxName != null) {
         this.editingAction.name = this.textboxName.getValue();
      }

      if(this.textboxValue != null) {
         this.editingAction.value = BDHelper.getIntegerFromString(this.textboxValue.getValue());
      }

      if(this.operatorValue != null) {
         this.editingAction.operator = this.operatorValue.value;
      }

   }
}
