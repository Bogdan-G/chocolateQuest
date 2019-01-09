package com.chocolate.chocolateQuest.entity.handHelper;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.handHelper.HandHelper;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class HandHealer extends HandHelper {

   public HandHealer(EntityHumanBase owner, ItemStack itemStack) {
      super(owner, itemStack);
   }

   public void onUpdate() {
      List list = super.owner.worldObj.getEntitiesWithinAABBExcludingEntity(super.owner, super.owner.boundingBox.expand(16.0D, 1.0D, 16.0D));
      EntityLivingBase closest = null;
      double dist = 324.0D;
      Iterator i$ = list.iterator();

      while(i$.hasNext()) {
         Entity e = (Entity)i$.next();
         if(e instanceof EntityLivingBase && this.isEntityApplicable(e)) {
            double tDist = super.owner.getDistanceSqToEntity(e);
            if(tDist < dist) {
               closest = (EntityLivingBase)e;
               dist = tDist;
            }
         }
      }

      if(closest != null) {
         super.owner.setAttackTarget(closest);
      }

   }

   public boolean isHealer() {
      return true;
   }

   public boolean isEntityApplicable(Entity parEntity) {
      if(parEntity instanceof EntityLivingBase && parEntity != super.owner) {
         EntityLivingBase entity = (EntityLivingBase)parEntity;
         return super.owner.isSuitableTargetAlly(entity)?entity.getHealth() < entity.getMaxHealth():false;
      } else {
         return false;
      }
   }
}
