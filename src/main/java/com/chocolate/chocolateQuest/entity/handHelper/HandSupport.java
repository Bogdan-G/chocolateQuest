package com.chocolate.chocolateQuest.entity.handHelper;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.handHelper.HandHelper;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class HandSupport extends HandHelper {

   public HandSupport(EntityHumanBase owner, ItemStack itemStack) {
      super(owner, itemStack);
   }

   public void onUpdate() {
      if(super.owner.ticksExisted % 100 == 0) {
         List list = super.owner.worldObj.getEntitiesWithinAABBExcludingEntity(super.owner, super.owner.boundingBox.expand(10.0D, 4.0D, 10.0D));

         for(int j = 0; j < list.size(); ++j) {
            Entity entity1 = (Entity)list.get(j);
            if(entity1 instanceof EntityLivingBase && super.owner.isOnSameTeam((EntityLivingBase)entity1)) {
               ((EntityLivingBase)entity1).addPotionEffect(this.getPotionEffect());
            }
         }
      }

      super.onUpdate();
   }

   public PotionEffect getPotionEffect() {
      return new PotionEffect(Potion.resistance.id, 101, 0);
   }
}
