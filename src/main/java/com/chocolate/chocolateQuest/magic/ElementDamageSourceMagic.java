package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.magic.ElementDamageSource;
import com.chocolate.chocolateQuest.utils.HelperDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class ElementDamageSourceMagic extends ElementDamageSource {

   public float onHitEntity(Entity source, Entity entityHit, float damage) {
      damage *= 0.75F;
      if(entityHit instanceof EntityLivingBase) {
         EntityLivingBase e = (EntityLivingBase)entityHit;
         float armour = (float)e.getTotalArmorValue() / 20.0F;
         damage += damage * armour;
      }

      return damage;
   }

   public DamageSource getIndirectDamage(Entity projectile, Entity shooter, String name) {
      return HelperDamageSource.causeProjectileMagicDamage(projectile, shooter);
   }

   public DamageSource getDamageSource(Entity shooter, String name) {
      return HelperDamageSource.causeMagicDamage(shooter);
   }

   public DamageSource getDamageSource(String name) {
      return super.getDamageSource(name).setMagicDamage();
   }
}
