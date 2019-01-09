package com.chocolate.chocolateQuest.utils;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class HelperPlayer {

   public static Entity getTarget(EntityLivingBase ep, World world, double dist) {
      MovingObjectPosition mop = getMovingObjectPositionFromPlayer(ep, world, dist);
      return mop != null?mop.entityHit:null;
   }

   public static MovingObjectPosition getMovingObjectPositionFromPlayer(EntityLivingBase ep, World world, double dist) {
      return getMovingObjectPositionFromPlayer(ep, world, dist, 0.0D);
   }

   public static MovingObjectPosition getMovingObjectPositionFromPlayer(EntityLivingBase ep, World world, double dist, double bbExpand) {
      MovingObjectPosition mop = null;
      float yOffset = ep.getEyeHeight();
      Vec3 playerPos = Vec3.createVectorHelper(ep.posX, ep.posY + (double)yOffset, ep.posZ);
      Vec3 look = ep.getLookVec();
      Vec3 playerView;
      if(ep instanceof EntityPlayer) {
         mop = getBlockMovingObjectPositionFromPlayer(world, ep, dist, true);
         if(mop != null) {
            playerView = Vec3.createVectorHelper(ep.posX - (double)mop.blockX, ep.posY - (double)mop.blockY, ep.posZ - (double)mop.blockZ);
            dist = playerView.lengthVector();
         }
      } else {
         mop = world.rayTraceBlocks(playerPos, look, true);
      }

      playerView = playerPos.addVector(look.xCoord * dist, look.yCoord * dist, look.zCoord * dist);
      List list = world.getEntitiesWithinAABBExcludingEntity(ep, ep.boundingBox.addCoord(ep.getLookVec().xCoord * dist, ep.getLookVec().yCoord * dist, ep.getLookVec().zCoord * dist).expand(1.0D, 1.0D, 1.0D));
      MovingObjectPosition tempMop = null;
      double prevDist = dist * dist;

      for(int j = 0; j < list.size(); ++j) {
         Entity entity1 = (Entity)list.get(j);
         if(entity1.canBeCollidedWith() && entity1 != ep.ridingEntity && ep != ep.ridingEntity && entity1 instanceof EntityLivingBase) {
            float f2 = 0.4F;
            AxisAlignedBB axisalignedbb = entity1.boundingBox.expand(bbExpand, bbExpand, bbExpand);
            MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(playerPos, playerView);
            if(movingobjectposition1 != null) {
               movingobjectposition1.entityHit = entity1;
               double entityDist = entity1.getDistanceSqToEntity(ep);
               if(entityDist < prevDist) {
                  tempMop = movingobjectposition1;
                  prevDist = entityDist;
               }
            }
         }
      }

      if(tempMop != null) {
         return tempMop;
      } else {
         return mop;
      }
   }

   public static MovingObjectPosition getBlockMovingObjectPositionFromPlayer(World par1World, EntityLivingBase par2EntityPlayer, double reachDistance, boolean par3) {
      float f = 1.0F;
      float rotPitch = par2EntityPlayer.prevRotationPitch + (par2EntityPlayer.rotationPitch - par2EntityPlayer.prevRotationPitch) * f;
      float rotYaw = par2EntityPlayer.prevRotationYaw + (par2EntityPlayer.rotationYaw - par2EntityPlayer.prevRotationYaw) * f;
      double posX = par2EntityPlayer.prevPosX + (par2EntityPlayer.posX - par2EntityPlayer.prevPosX) * (double)f;
      double posY = par2EntityPlayer.prevPosY + (par2EntityPlayer.posY - par2EntityPlayer.prevPosY) * (double)f + 1.62D - (double)par2EntityPlayer.yOffset;
      double posZ = par2EntityPlayer.prevPosZ + (par2EntityPlayer.posZ - par2EntityPlayer.prevPosZ) * (double)f;
      Vec3 entityPos = Vec3.createVectorHelper(posX, posY, posZ);
      float zDesp = MathHelper.cos(-rotYaw * 0.017453292F - 3.1415927F);
      float xDesp = MathHelper.sin(-rotYaw * 0.017453292F - 3.1415927F);
      float yScale = -MathHelper.cos(-rotPitch * 0.017453292F);
      float lookY = MathHelper.sin(-rotPitch * 0.017453292F);
      float lookX = xDesp * yScale;
      float lookZ = zDesp * yScale;
      Vec3 look = entityPos.addVector((double)lookX * reachDistance, (double)lookY * reachDistance, (double)lookZ * reachDistance);
      return par1World.rayTraceBlocks(entityPos, look, par3);
   }

   public static MovingObjectPosition getBlockMovingObjectPositionFromPlayerWithSideOffset(World par1World, EntityLivingBase par2EntityPlayer, double reachDistance, boolean par3) {
      MovingObjectPosition mop = getBlockMovingObjectPositionFromPlayer(par1World, par2EntityPlayer, reachDistance, par3);
      if(mop == null) {
         return null;
      } else {
         switch(mop.sideHit) {
         case 0:
            --mop.blockY;
            --mop.hitVec.yCoord;
            break;
         case 1:
            ++mop.blockY;
            break;
         case 2:
            --mop.blockZ;
            mop.hitVec.zCoord -= 0.5D;
            break;
         case 3:
            mop.blockZ = (int)((double)mop.blockZ + 1.5D);
            mop.hitVec.zCoord += 0.5D;
            break;
         case 4:
            mop.blockX = (int)((double)mop.blockX + -0.5D);
            mop.hitVec.xCoord -= 0.5D;
            break;
         case 5:
            mop.blockX = (int)((double)mop.blockX + 1.5D);
            mop.hitVec.xCoord += 0.5D;
         }

         return mop;
      }
   }
}
