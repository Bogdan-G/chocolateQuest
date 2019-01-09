package com.chocolate.chocolateQuest.entity.handHelper;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.handHelper.HandHelper;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.item.ItemStack;

public class HandLead extends HandHelper {

   EntityCreature leashedCreature;


   public HandLead(EntityHumanBase owner, ItemStack itemStack) {
      super(owner, itemStack);
   }

   public void onUpdate() {
      super.onUpdate();
      if(!super.owner.worldObj.isRemote && this.leashedCreature == null && super.owner.ticksExisted % 100 == 0) {
         EntityCreature target = null;
         double dist = 256.0D;
         List list = super.owner.worldObj.getEntitiesWithinAABBExcludingEntity(super.owner, super.owner.boundingBox.expand(5.0D, 1.0D, 5.0D));
         Iterator f = list.iterator();

         while(f.hasNext()) {
            Entity e = (Entity)f.next();
            if(e instanceof EntityCreature) {
               EntityCreature f1 = (EntityCreature)e;
               if(super.owner.canTakeAsPet(f1)) {
                  if(f1.getLeashed()) {
                     if(f1.getLeashedToEntity() == super.owner) {
                        target = f1;
                        dist = 0.0D;
                     }
                  } else {
                     double currentDist = super.owner.getDistanceSqToEntity(f1);
                     if(currentDist < dist) {
                        target = f1;
                        dist = currentDist;
                     }
                  }
               }
            }
         }

         if(target != null) {
            this.leashedCreature = target;
            this.leashedCreature.setLeashedToEntity(super.owner, true);
            if(this.leashedCreature instanceof EntityWolf) {
               EntityWolf f2 = (EntityWolf)this.leashedCreature;
               if(!f2.isSitting()) {
                  f2.setSitting(false);
               }
            }
         }
      }

      if(this.leashedCreature != null) {
         EntityLivingBase target1 = super.owner.getAttackTarget();
         if(target1 != null) {
            this.leashedCreature.setAttackTarget(target1);
         }

         if(!this.leashedCreature.isEntityAlive()) {
            this.leashedCreature = null;
         } else if(this.leashedCreature.getLeashedToEntity() != super.owner) {
            this.leashedCreature = null;
         }
      }

   }

   public void attackEntity(Entity entity) {
      super.attackEntity(entity);
   }

   public boolean isTwoHanded() {
      return false;
   }
}
