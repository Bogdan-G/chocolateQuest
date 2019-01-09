package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
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
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class SpellRepel extends SpellBase {

   public void onShoot(EntityLivingBase shooter, Elements element, ItemStack is, int chargeTime) {
      World world = shooter.worldObj;
      float rotationYaw = (float)MathHelper.wrapAngleTo180_double((double)shooter.rotationYawHead);
      double armDist = 0.5D;
      double offsetY = 0.2D;
      if(shooter instanceof EntityHumanBase) {
         offsetY = 1.4D;
      }

      double posX = shooter.posX - Math.sin(Math.toRadians((double)rotationYaw)) * armDist;
      double posY = shooter.posY + offsetY;
      double posZ = shooter.posZ + Math.cos(Math.toRadians((double)rotationYaw)) * armDist;
      Vec3 v = shooter.getLookVec();
      double x = v.xCoord;
      double y = v.yCoord;
      double z = v.zCoord;
      float velocity = 1.0F + this.getDamage(is) / 4.0F;
      if(world.isRemote) {
         Random dist = shooter.getRNG();

         for(int list = 0; list < 8; ++list) {
            EffectManager.spawnElementParticle(0, world, posX, posY, posZ, (x * (double)velocity + (double)dist.nextFloat() - 0.5D) / 3.0D, (y + (double)dist.nextFloat() - 0.5D) / 8.0D, (z * (double)velocity + (double)dist.nextFloat() - 0.5D) / 3.0D, element);
         }
      } else {
         float var29 = (float)(3 + this.getExpansion(is));
         List var30 = world.getEntitiesWithinAABBExcludingEntity(shooter, shooter.boundingBox.addCoord(shooter.getLookVec().xCoord * (double)var29, shooter.getLookVec().yCoord * (double)var29, shooter.getLookVec().zCoord * (double)var29).expand(1.0D, 1.0D, 1.0D));
         Iterator i$ = var30.iterator();

         while(i$.hasNext()) {
            Entity e = (Entity)i$.next();
            if(e instanceof EntityLivingBase && e != shooter.riddenByEntity) {
               e.addVelocity(x * (double)velocity, 0.5D, z * (double)velocity);
            } else if(e instanceof EntityBaseBall) {
               e.addVelocity(x * (double)velocity, y * (double)velocity, z * (double)velocity);
               ((EntityBaseBall)e).setThrower(shooter);
            }
         }
      }

   }

   public int getRange(ItemStack itemstack) {
      return 5;
   }

   public boolean isProjectile() {
      return true;
   }

   public int getCastingTime() {
      return 4;
   }

   public int getCoolDown() {
      return 20;
   }

   public float getCost(ItemStack itemstack) {
      return 25.0F;
   }
}
