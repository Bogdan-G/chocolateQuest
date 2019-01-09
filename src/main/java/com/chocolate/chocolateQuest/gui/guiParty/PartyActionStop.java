package com.chocolate.chocolateQuest.gui.guiParty;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.gui.guiParty.PartyAction;
import net.minecraft.entity.EntityLivingBase;

public class PartyActionStop extends PartyAction {

   public PartyActionStop(String name, int icon) {
      super(name, icon);
   }

   public void execute(EntityHumanBase e) {
      e.currentPos = null;
      e.setAttackTarget((EntityLivingBase)null);
   }
}
