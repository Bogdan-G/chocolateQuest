package com.chocolate.chocolateQuest.gui.guinpc;

import com.chocolate.chocolateQuest.entity.ai.npcai.NpcAI;

class ButtonAI {

   public boolean isSelected = false;
   public int x;
   public String name;
   public NpcAI ai;


   public ButtonAI(int x, String name, NpcAI ai) {
      this.x = x;
      this.name = name;
      this.ai = ai;
   }

   public ButtonAI(NpcAI ai) {
      this.x = ai.hour / 1000;
      this.name = ai.toString();
      this.ai = ai;
   }
}
