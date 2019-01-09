package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.magic.ElementDamageSource;
import com.chocolate.chocolateQuest.utils.HelperDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;

public class ElementDamageSourceNature extends ElementDamageSource {

   public float onHitEntity(Entity source, Entity entityHit, float damage) {
      return damage;
   }

   public DamageSource getIndirectDamage(Entity projectile, Entity shooter, String name) {
      return HelperDamageSource.causeProjectilePhysicalDamage(projectile, shooter);
   }

   public DamageSource getDamageSource(Entity shooter, String name) {
      return HelperDamageSource.causePhysicalDamage(shooter);
   }

   public DamageSource getDamageSource(String name) {
      return super.getDamageSource(name);
   }
}
