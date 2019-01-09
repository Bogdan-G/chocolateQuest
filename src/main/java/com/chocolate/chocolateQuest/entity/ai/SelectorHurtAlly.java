package com.chocolate.chocolateQuest.entity.ai;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

class SelectorHurtAlly implements IEntitySelector {

   EntityHumanBase taskOwner;


   public SelectorHurtAlly(EntityHumanBase owner) {
      this.taskOwner = owner;
   }

   public boolean isEntityApplicable(Entity parEntity) {
      if(parEntity instanceof EntityLivingBase && parEntity != this.taskOwner) {
         EntityLivingBase entity = (EntityLivingBase)parEntity;
         return this.taskOwner.isSuitableTargetAlly(entity)?entity.getHealth() < entity.getMaxHealth():false;
      } else {
         return false;
      }
   }
}
