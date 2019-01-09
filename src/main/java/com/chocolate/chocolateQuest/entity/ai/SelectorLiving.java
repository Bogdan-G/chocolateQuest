package com.chocolate.chocolateQuest.entity.ai;

import com.chocolate.chocolateQuest.entity.ai.AITargetNearestAttackableForDragon;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;

class SelectorLiving implements IEntitySelector {

   EntityCreature taskOwner;
   AITargetNearestAttackableForDragon ownerAI;
   IEntitySelector selector;


   public SelectorLiving(EntityCreature par1EntityCreature, AITargetNearestAttackableForDragon par2, IEntitySelector parSelector) {
      this.taskOwner = par1EntityCreature;
      this.ownerAI = par2;
      this.selector = parSelector;
   }

   public boolean isEntityApplicable(Entity entity) {
      boolean flag = true;
      if(this.selector != null) {
         flag = this.selector.isEntityApplicable(entity);
      }

      return entity instanceof EntityLivingBase && flag?this.ownerAI.isSuitableTarget((EntityLivingBase)entity, false):false;
   }
}
