package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.magic.ElementDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

public class ElementDamageSourceDark extends ElementDamageSource {

   public float onHitEntity(Entity source, Entity entityHit, float damage) {
      int withering = (int)(damage / 4.0F);
      if(withering >= 1) {
         damage -= (float)withering;
         ((EntityLivingBase)entityHit).addPotionEffect(new PotionEffect(Potion.wither.id, withering * 60, 0));
      }

      return damage;
   }

   public DamageSource getIndirectDamage(Entity projectile, Entity shooter, String name) {
      return super.getIndirectDamage(projectile, shooter, name).setMagicDamage();
   }

   public DamageSource getDamageSource(Entity shooter, String name) {
      return super.getDamageSource(shooter, name).setMagicDamage();
   }

   public DamageSource getDamageSource(String name) {
      return super.getDamageSource(name).setMagicDamage();
   }
}
