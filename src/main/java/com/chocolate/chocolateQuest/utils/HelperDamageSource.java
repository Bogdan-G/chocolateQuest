package com.chocolate.chocolateQuest.utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;

public class HelperDamageSource {

   public static DamageSource causeProjectilePhysicalDamage(Entity projectile, Entity shooter) {
      return (new EntityDamageSourceIndirect("generic", projectile, shooter)).setProjectile();
   }

   public static DamageSource causePhysicalDamage(Entity shooter) {
      return new EntityDamageSource("generic", shooter);
   }

   public static DamageSource causeProjectileMagicDamage(Entity projectile, Entity shooter) {
      return (new EntityDamageSourceIndirect("magic", projectile, shooter)).setMagicDamage().setProjectile();
   }

   public static DamageSource causeMagicDamage(Entity shooter) {
      return (new EntityDamageSource("magic", shooter)).setMagicDamage();
   }

   public static DamageSource causeFireProjectileDamage(Entity projectile, Entity shooter) {
      return (new EntityDamageSourceIndirect(DamageSource.inFire.damageType, projectile, shooter)).setProjectile().setFireDamage();
   }

   public static DamageSource causeFireDamage(Entity shooter) {
      return (new EntityDamageSource(DamageSource.inFire.damageType, shooter)).setFireDamage();
   }

   public static DamageSource causeProjectileThunderDamage(Entity projectile, Entity shooter) {
      return (new EntityDamageSourceIndirect("magic", projectile, shooter)).setProjectile().setExplosion();
   }

   public static DamageSource causeThunderDamage(Entity shooter) {
      return (new EntityDamageSource("magic", shooter)).setExplosion();
   }

   public static boolean attackEntityWithoutKnockBack(Entity entity, DamageSource ds, float damage) {
      boolean damaged;
      if(entity instanceof EntityLivingBase) {
         AttributeModifier kbMod = new AttributeModifier("TemKBResist", 1.0D, 0);
         ((EntityLivingBase)entity).getEntityAttribute(SharedMonsterAttributes.knockbackResistance).applyModifier(kbMod);
         damaged = entity.attackEntityFrom(ds, damage);
         ((EntityLivingBase)entity).getEntityAttribute(SharedMonsterAttributes.knockbackResistance).removeModifier(kbMod);
      } else {
         damaged = entity.attackEntityFrom(ds, damage);
      }

      return damaged;
   }
}
