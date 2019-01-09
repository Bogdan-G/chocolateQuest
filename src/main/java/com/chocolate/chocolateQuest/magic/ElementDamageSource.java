package com.chocolate.chocolateQuest.magic;

import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.world.World;

public class ElementDamageSource {

   public static final int DIAMOND_ARMOR_AMMOUNT = 20;


   public DamageSource getIndirectDamage(Entity projectile, Entity shooter, String name) {
      return new EntityDamageSourceIndirect(name, projectile, shooter);
   }

   public DamageSource getDamageSource(Entity shooter, String name) {
      return new EntityDamageSource(name, shooter);
   }

   public float onHitEntity(Entity source, Entity entityHit, float damage) {
      return damage;
   }

   public void onBlockHit(Entity shooter, World world, int x, int y, int z) {
      List list = world.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox((double)x, (double)y, (double)z, (double)(x + 1), (double)(y + 1), (double)(z + 1)));
      Iterator i$ = list.iterator();

      while(i$.hasNext()) {
         EntityLivingBase e = (EntityLivingBase)i$.next();
         e.attackEntityFrom(DamageSource.generic, 1.0F);
      }

   }

   public DamageSource getDamageSource(String name) {
      return new DamageSource(name);
   }
}
