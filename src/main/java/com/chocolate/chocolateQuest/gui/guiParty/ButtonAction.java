package com.chocolate.chocolateQuest.gui.guiParty;

import com.chocolate.chocolateQuest.gui.guiParty.PartyAction;

class ButtonAction {

   public int xPosition;
   public int yPosition;
   public PartyAction action;


   public ButtonAction(int x, int y, int action) {
      this.xPosition = x;
      this.yPosition = y;
      this.action = (PartyAction)PartyAction.actions.get(action);
   }

   public int getIcon() {
      return this.action.icon;
   }

   public String getName() {
      return this.action.name;
   }

   public boolean isMouseOver(int x, int y) {
      byte size = 16;
      boolean b = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + size && y < this.yPosition + size;
      return b;
   }
}
