package com.chocolate.chocolateQuest.entity.ai;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.ai.SelectorHurtAlly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;

public class AITargetNearestHurtAlly extends EntityAINearestAttackableTarget {

   public AITargetNearestHurtAlly(EntityHumanBase owner, Class targetClass) {
      super(owner, targetClass, 0, true, false, new SelectorHurtAlly(owner));
   }

   public boolean isSuitableTarget(EntityLivingBase par1EntityLivingBase, boolean par2) {
      return true;
   }
}
