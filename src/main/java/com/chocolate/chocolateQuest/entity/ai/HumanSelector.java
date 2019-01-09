package com.chocolate.chocolateQuest.entity.ai;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class HumanSelector implements IEntitySelector {

   EntityHumanBase taskOwner;


   public HumanSelector(EntityHumanBase owner) {
      this.taskOwner = owner;
   }

   public boolean isEntityApplicable(Entity parEntity) {
      if(!(parEntity instanceof EntityLivingBase)) {
         return false;
      } else {
         EntityLivingBase entity = (EntityLivingBase)parEntity;
         return this.taskOwner.isSuitableTargetAlly(entity)?false:this.taskOwner.canSee(entity);
      }
   }
}
