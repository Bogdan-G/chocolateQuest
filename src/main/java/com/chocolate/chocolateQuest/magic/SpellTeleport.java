package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.magic.SpellProjectile;
import com.chocolate.chocolateQuest.packets.PacketSpawnParticlesAround;
import com.chocolate.chocolateQuest.utils.HelperPlayer;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class SpellTeleport extends SpellProjectile {

   public void onShoot(EntityLivingBase shooter, Elements element, ItemStack is, int chargeTime) {
      World world = shooter.worldObj;
      double startX = shooter.posX;
      double startY = shooter.posY;
      double startZ = shooter.posZ;
      Random random;
      int i;
      if(world.isRemote) {
         random = shooter.getRNG();

         for(i = 0; i < 8; ++i) {
            shooter.worldObj.spawnParticle("portal", shooter.posX + ((double)random.nextFloat() - 0.5D), shooter.posY + ((double)random.nextFloat() - 0.5D), shooter.posZ + ((double)random.nextFloat() - 0.5D), ((double)random.nextFloat() - 0.5D) / 2.0D, ((double)random.nextFloat() - 0.5D) / 2.0D, ((double)random.nextFloat() - 0.5D) / 2.0D);
         }
      } else if(!world.isRemote) {
         PacketSpawnParticlesAround var15 = new PacketSpawnParticlesAround(PacketSpawnParticlesAround.getParticleFromName(element.getParticle()), shooter.posX, shooter.posY, shooter.posZ);
         ChocolateQuest.channel.sendToAllAround(shooter, var15, 64);
      }

      if(shooter instanceof EntityPlayer) {
         int var16 = 8 + 10 * this.getExpansion(is) + (int)this.getDamage(is);
         MovingObjectPosition var17 = HelperPlayer.getBlockMovingObjectPositionFromPlayerWithSideOffset(shooter.worldObj, shooter, (double)var16, true);
         if(var17 != null) {
            shooter.setPosition(var17.hitVec.xCoord, var17.hitVec.yCoord, var17.hitVec.zCoord);
         } else {
            Vec3 i1 = shooter.getLookVec();
            shooter.setPosition(shooter.posX + i1.xCoord * (double)var16, shooter.posY + i1.yCoord * (double)var16, shooter.posZ + i1.zCoord * (double)var16);
         }
      } else {
         EntityLivingBase var19 = shooter;
         EntityLiving var18 = (EntityLiving)shooter;
         if(var18.getAttackTarget() != null) {
            var19 = var18.getAttackTarget();
         }

         this.damageNearby(world, shooter, element);

         for(int var20 = 0; var20 < 6 && !this.castTeleport(shooter, var19); ++var20) {
            ;
         }
      }

      shooter.worldObj.playSoundEffect(startX, startY, startZ, "mob.endermen.portal", 1.0F, 1.0F);
      shooter.worldObj.playSoundAtEntity(shooter, "mob.endermen.portal", 1.0F, 1.0F);
      if(world.isRemote) {
         random = shooter.getRNG();

         for(i = 0; i < 16; ++i) {
            shooter.worldObj.spawnParticle("portal", shooter.posX + ((double)random.nextFloat() - 0.5D), shooter.posY + (double)random.nextFloat(), shooter.posZ + ((double)random.nextFloat() - 0.5D), ((double)random.nextFloat() - 0.5D) / 2.0D, ((double)random.nextFloat() - 0.5D) / 2.0D, ((double)random.nextFloat() - 0.5D) / 2.0D);
         }
      }

   }

   public int getCastingTime() {
      return 3;
   }

   protected void damageNearby(World world, EntityLivingBase shooter, Elements element) {
      byte dist = 5;
      List list = world.getEntitiesWithinAABBExcludingEntity(shooter, shooter.boundingBox.expand((double)dist, 1.0D, (double)dist));
      Iterator i$ = list.iterator();

      while(i$.hasNext()) {
         Entity e = (Entity)i$.next();
         if(e instanceof EntityLivingBase && e != shooter.riddenByEntity) {
            element.attackWithElement((EntityLivingBase)e, shooter, 1.0F);
         }
      }

   }

   protected boolean castTeleport(EntityLivingBase shooter, Entity target) {
      World worldObj = shooter.worldObj;
      float dist = 16.0F;
      Random rand = shooter.getRNG();
      double dX = target.posX + (rand.nextDouble() - 0.5D) * (double)dist;
      double dY = target.posY;
      double dZ = target.posZ + (rand.nextDouble() - 0.5D) * (double)dist;
      double startX = shooter.posX;
      double startY = shooter.posY;
      double startZ = shooter.posZ;
      boolean flag = false;
      int i = MathHelper.floor_double(dX);
      int j = MathHelper.floor_double(dY);
      int k = MathHelper.floor_double(dZ);
      if(worldObj.blockExists(i, j, k)) {
         boolean isNonSolidBlock = false;

         while(!isNonSolidBlock && j < 255) {
            Material mat = worldObj.getBlock(i, j - 1, k).getMaterial();
            if(!mat.isSolid()) {
               ++dY;
               ++j;
            } else {
               isNonSolidBlock = true;
            }
         }

         if(isNonSolidBlock) {
            shooter.setPosition(dX, dY, dZ);
            if(worldObj.getCollidingBoundingBoxes(shooter, shooter.boundingBox).size() == 0 && !worldObj.isAnyLiquid(shooter.boundingBox)) {
               flag = true;
            }
         }
      }

      if(!flag) {
         shooter.setPosition(startX, startY, startZ);
         return false;
      } else {
         return true;
      }
   }

   public int getCoolDown() {
      return 400;
   }

   public int getRange(ItemStack itemstack) {
      return 2;
   }

   public float getCost(ItemStack itemstack) {
      return 100.0F;
   }
}
