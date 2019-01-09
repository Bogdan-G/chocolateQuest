package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.magic.ElementDamageSourceMagic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;

public class ElementDamageSourceLight extends ElementDamageSourceMagic {

   public float onHitEntity(Entity source, Entity entityHit, float damage) {
      if(entityHit instanceof EntityLivingBase && ((EntityLivingBase)entityHit).getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
         damage *= 1.8F;
      }

      return damage;
   }
}
