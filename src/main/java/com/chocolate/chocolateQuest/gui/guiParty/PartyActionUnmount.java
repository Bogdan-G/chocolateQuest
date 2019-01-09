package com.chocolate.chocolateQuest.gui.guiParty;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.gui.guiParty.PartyAction;
import net.minecraft.entity.Entity;

public class PartyActionUnmount extends PartyAction {

   public PartyActionUnmount(String name, int icon) {
      super(name, icon);
   }

   public void execute(EntityHumanBase e) {
      if(e.ridingEntity != null) {
         e.mountEntity((Entity)null);
      }

   }
}
