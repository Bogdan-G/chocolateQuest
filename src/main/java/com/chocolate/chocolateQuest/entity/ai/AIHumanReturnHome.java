package com.chocolate.chocolateQuest.entity.ai;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.ai.AIControlledBase;
import com.chocolate.chocolateQuest.utils.Vec3I;

public class AIHumanReturnHome extends AIControlledBase {

   Vec3I position;


   public AIHumanReturnHome(EntityHumanBase owner) {
      super(owner);
   }

   public boolean shouldExecute() {
      return super.owner.getAttackTarget() != null && super.owner.getAttackTarget().isEntityAlive()?false:(super.owner.getOwner() != null?false:(super.owner.party != null && super.owner.party.getLeader() != super.owner?false:!super.owner.isWithinHomeDistanceCurrentPosition()));
   }

   public void startExecuting() {}

   public void resetTask() {
      super.owner.getNavigator().clearPathEntity();
   }

   public void updateTask() {
      this.tryMoveToXYZ((double)super.owner.getHomePosition().posX, (double)super.owner.getHomePosition().posY, (double)super.owner.getHomePosition().posZ, 1.0F);
   }
}
