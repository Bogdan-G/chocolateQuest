package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.magic.ElementDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class ElementDamageSourceBlast extends ElementDamageSource {

   public float onHitEntity(Entity source, Entity entityHit, float damage) {
      damage *= 0.5F;
      if(entityHit instanceof EntityLivingBase) {
         EntityLivingBase e = (EntityLivingBase)entityHit;
         float armour = (float)e.getTotalArmorValue() / 20.0F;
         damage += damage * armour * 2.5F;
      }

      return damage;
   }

   public DamageSource getIndirectDamage(Entity projectile, Entity shooter, String name) {
      return super.getIndirectDamage(projectile, shooter, name).setExplosion();
   }

   public DamageSource getDamageSource(Entity shooter, String name) {
      return super.getDamageSource(shooter, name).setExplosion();
   }
}
