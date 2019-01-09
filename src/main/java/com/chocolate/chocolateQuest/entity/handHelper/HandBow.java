package com.chocolate.chocolateQuest.entity.handHelper;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.handHelper.HandRanged;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class HandBow extends HandRanged {

   public HandBow(EntityHumanBase owner, ItemStack itemStack) {
      super(owner, itemStack);
      super.range = 4096.0F;
      super.aimDelay = 10;
      super.isMeleWeapon = false;
   }

   public void doRangeAttack(Entity target, float f) {
      EntityArrow arrow = new EntityArrow(super.owner.worldObj, super.owner, 0.0F);
      arrow.setPosition(super.owner.posX, super.owner.posY + (double)super.owner.getEyeHeight(), super.owner.posZ);
      if(!super.owner.worldObj.isRemote) {
         if(target.posY < super.owner.posY) {
            arrow.setIsCritical(true);
         }

         float dist = MathHelper.sqrt_float(f);
         double mx = target.motionX;
         double mz = target.motionZ;
         double distFactor = (mx * mx + mz * mz) * 100.0D * (double)dist;
         double arrowMotionX = target.posX - super.owner.posX + target.motionX * distFactor;
         double arrowMotionZ = target.posZ - super.owner.posZ + target.motionZ * distFactor;
         double arrowMotionY = target.posY + (double)target.getEyeHeight() - arrow.posY + (double)dist * 0.2D;
         float hFact = 1.0F;
         if(dist > 14.0F) {
            hFact = 1.0F + dist * 0.025F;
         }

         if(dist > 50.0F) {
            arrowMotionY += (double)(dist * 0.11F);
         } else if(dist > 25.0F) {
            arrowMotionY += (double)(dist * 0.07F);
         }

         float damage = 2.0F + (float)super.owner.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
         int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, super.currentItem);
         if(k > 0) {
            damage = (float)((double)damage + (double)k * 0.5D + 0.5D);
         }

         int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, super.currentItem);
         if(l > 0) {
            arrow.setKnockbackStrength(l);
         }

         if(EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, super.currentItem) > 0) {
            arrow.setFire(100);
         }

         arrow.setThrowableHeading(arrowMotionX, arrowMotionY, arrowMotionZ, 1.0F, super.owner.accuracy);
         arrow.setDamage((double)damage);
         arrow.motionX *= (double)Math.max(1.0F, hFact);
         arrow.motionZ *= (double)Math.max(1.0F, hFact);
         arrow.motionY *= (double)Math.max(1.0F, hFact);
         super.owner.worldObj.spawnEntityInWorld(arrow);
         super.owner.worldObj.playSoundAtEntity(super.owner, "random.bow", 1.0F, 1.0F / (super.owner.getRNG().nextFloat() * 0.4F + 0.8F));
      }

   }

   public double getDistanceToStopAdvancing() {
      return this.getRange();
   }

   public double getRange() {
      return (double)super.range;
   }
}
