package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.magic.ElementDamageSource;
import com.chocolate.chocolateQuest.utils.HelperDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class ElementDamageSourceFire extends ElementDamageSource {

   public float onHitEntity(Entity source, Entity entityHit, float damage) {
      float fireDamage = damage * 0.25F;
      if(fireDamage >= 1.0F) {
         entityHit.setFire(1 + (int)Math.floor((double)fireDamage));
      }

      return damage;
   }

   public void onBlockHit(Entity shooter, World world, int x, int y, int z) {
      if(world.getBlock(x, y, z) == Blocks.air) {
         world.setBlock(x, y, z, Blocks.fire);
      }

   }

   public DamageSource getIndirectDamage(Entity projectile, Entity shooter, String name) {
      return HelperDamageSource.causeFireProjectileDamage(projectile, shooter);
   }

   public DamageSource getDamageSource(Entity shooter, String name) {
      return HelperDamageSource.causeFireDamage(shooter);
   }
}
