package com.chocolate.chocolateQuest.gui.guiParty;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.gui.guiParty.PartyAction;

public class PartyActionAI extends PartyAction {

   int AIMode;


   public PartyActionAI(String name, int icon, int AIMode) {
      super(name, icon);
      this.AIMode = AIMode;
   }

   public void execute(EntityHumanBase e) {
      e.AIMode = this.AIMode;
      e.setAIForCurrentMode();
   }
}
