package com.chocolate.chocolateQuest.entity.handHelper;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.handHelper.HandHelper;
import com.chocolate.chocolateQuest.items.swords.ItemCQBlade;
import net.minecraft.item.ItemStack;

public class HandCQBlade extends HandHelper {

   boolean hasSkill = false;
   ItemCQBlade bladeItem;
   int cooldown = 0;


   public HandCQBlade(EntityHumanBase owner, ItemStack itemStack) {
      super(owner, itemStack);
      this.bladeItem = (ItemCQBlade)itemStack.getItem();
      this.hasSkill = this.bladeItem.hasSkill();
   }

   public void onUpdate() {
      super.onUpdate();
      if(this.hasSkill && super.owner.getAttackTarget() != null) {
         if(this.cooldown == 0) {
            this.cooldown = this.bladeItem.doSkill(super.owner);
         } else {
            --this.cooldown;
         }
      }

   }
}
