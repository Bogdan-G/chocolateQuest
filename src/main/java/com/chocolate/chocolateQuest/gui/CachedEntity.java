package com.chocolate.chocolateQuest.gui;

import net.minecraft.entity.EntityLivingBase;

class CachedEntity {

   EntityLivingBase entity;
   float trackingHealth = 0.0F;
   float lastDamage = 0.0F;
   int damageTimer = 0;


   public CachedEntity(EntityLivingBase entity) {
      this.entity = entity;
      this.trackingHealth = entity.getHealth();
   }

   public void onUpdate() {
      if(this.damageTimer > 0) {
         --this.damageTimer;
         if(this.damageTimer == 0) {
            this.lastDamage = 0.0F;
         }
      }

   }
}
