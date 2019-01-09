package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.magic.SpellBase;
import com.chocolate.chocolateQuest.particles.EffectManager;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class SpellSpray extends SpellBase {

   public void onUpdate(EntityLivingBase shooter, Elements element, ItemStack is, int angle) {
      World world = shooter.worldObj;
      float rotationYaw = (float)MathHelper.wrapAngleTo180_double((double)shooter.rotationYawHead);
      double armDist = 0.5D;
      double offsetY = 0.2D;
      if(shooter instanceof EntityHumanBase) {
         offsetY = 1.4D;
      }

      double posX = shooter.posX - Math.sin(Math.toRadians((double)(rotationYaw + (float)angle))) * armDist;
      double posY = shooter.posY + offsetY;
      double posZ = shooter.posZ + Math.cos(Math.toRadians((double)(rotationYaw + (float)angle))) * armDist;
      float x = (float)(-Math.sin(Math.toRadians((double)rotationYaw)));
      float z = (float)Math.cos(Math.toRadians((double)rotationYaw));
      double y = -Math.sin(Math.toRadians((double)shooter.rotationPitch));
      x = (float)((double)x * (1.0D - Math.abs(y)));
      z = (float)((double)z * (1.0D - Math.abs(y)));
      if(world.isRemote) {
         Random dist = shooter.getRNG();

         for(int list = 0; list < 4; ++list) {
            EffectManager.spawnElementParticle(0, world, posX, posY, posZ, ((double)(x + dist.nextFloat()) - 0.5D) / 3.0D, (y + (double)dist.nextFloat() - 0.5D) / 8.0D, ((double)(z + dist.nextFloat()) - 0.5D) / 3.0D, element);
         }
      } else {
         byte var33 = 5;
         List var32 = world.getEntitiesWithinAABBExcludingEntity(shooter, shooter.boundingBox.addCoord(shooter.getLookVec().xCoord * (double)var33, shooter.getLookVec().yCoord * (double)var33, shooter.getLookVec().zCoord * (double)var33).expand(1.0D, 1.0D, 1.0D));
         Iterator i$ = var32.iterator();

         while(i$.hasNext()) {
            Entity e = (Entity)i$.next();
            if(e instanceof EntityLivingBase && e != shooter.riddenByEntity && shooter.canEntityBeSeen(e)) {
               double d = posX - e.posX;
               double d2 = posZ - e.posZ;
               double rotDiff = Math.atan2(d, d2);
               rotDiff = rotDiff * 180.0D / 3.141592D;
               rotDiff = -MathHelper.wrapAngleTo180_double(rotDiff - 180.0D);
               rotDiff -= (double)rotationYaw;
               if(Math.abs(rotDiff) < 30.0D) {
                  float damage = this.getDamage(is) * 0.2F;
                  element.attackWithElement((EntityLivingBase)e, shooter, damage);
               }
            }
         }
      }

   }

   public int getRange(ItemStack itemstack) {
      return 5;
   }

   public boolean shouldUpdate() {
      return true;
   }

   public int getCastingTime() {
      return 45;
   }

   public int getCoolDown() {
      return 100;
   }

   public float getCost(ItemStack itemstack) {
      return 0.5F;
   }
}
