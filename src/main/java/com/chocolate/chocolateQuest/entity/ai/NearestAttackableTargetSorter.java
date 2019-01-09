package com.chocolate.chocolateQuest.entity.ai;

import java.util.Comparator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

class NearestAttackableTargetSorter implements Comparator<Entity> {

   private EntityLivingBase targetEntity;


   public NearestAttackableTargetSorter(EntityLivingBase owner) {
      this.targetEntity = owner;
   }

   public int compare(Entity o1, Entity o2) {
      double dist1 = o1.getDistanceSqToEntity(this.targetEntity);
      double dist2 = o2.getDistanceSqToEntity(this.targetEntity);
      return dist1 < dist2?-1:(dist1 == dist2?0:1);
   }
}
