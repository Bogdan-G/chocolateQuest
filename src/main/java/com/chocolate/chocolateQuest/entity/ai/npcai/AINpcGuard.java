package com.chocolate.chocolateQuest.entity.ai.npcai;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.ai.npcai.AINpcGoToPosition;
import com.chocolate.chocolateQuest.utils.Vec4I;

public class AINpcGuard extends AINpcGoToPosition {

   public AINpcGuard(EntityHumanBase owner, Vec4I p) {
      super(owner, p);
   }

   public boolean shouldExecute() {
      return true;
   }

   public void onUpdateAtPosition() {
      this.getNavigator().clearPathEntity();
      super.owner.rotationYaw = (float)super.position.rot;
   }
}
