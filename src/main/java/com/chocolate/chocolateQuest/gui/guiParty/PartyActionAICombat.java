package com.chocolate.chocolateQuest.gui.guiParty;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.gui.guiParty.PartyAction;

public class PartyActionAICombat extends PartyAction {

   int AIMode;


   public PartyActionAICombat(String name, int icon, int AIMode) {
      super(name, icon);
      this.AIMode = AIMode;
   }

   public void execute(EntityHumanBase e) {
      e.AICombatMode = this.AIMode;
      e.setAIForCurrentMode();
   }
}
