package com.chocolate.chocolateQuest.entity.ai;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.ai.AIHumanAttackAggressive;
import net.minecraft.util.MathHelper;

public class AIHumanAttackAggressiveBackstab extends AIHumanAttackAggressive {

   public AIHumanAttackAggressiveBackstab(EntityHumanBase par1EntityLiving, float speed, boolean requireSight) {
      super(par1EntityLiving, speed, requireSight);
   }

   public boolean tryToMoveToEntity() {
      float targetAngle = (super.entityTarget.rotationYawHead - 180.0F) * 3.1416F / 180.0F;
      double cos = (double)MathHelper.cos(targetAngle);
      double sin = (double)MathHelper.sin(targetAngle);

      float angle;
      for(angle = super.owner.rotationYawHead - super.entityTarget.rotationYawHead; angle > 360.0F; angle -= 360.0F) {
         ;
      }

      while(angle < 0.0F) {
         angle += 360.0F;
      }

      angle = 180.0F - Math.abs(angle - 180.0F);
      double dist = Math.min(2.5D, (double)(Math.abs(angle) / 60.0F));
      double x = super.entityTarget.posX + -sin * dist;
      double y = super.entityTarget.posY;
      double z = super.entityTarget.posZ + cos * dist;
      return this.tryMoveToXYZ(x, y, z, 1.0F);
   }
}
