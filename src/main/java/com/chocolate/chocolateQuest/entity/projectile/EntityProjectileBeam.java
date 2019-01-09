package com.chocolate.chocolateQuest.entity.projectile;

import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.particles.EffectManager;
import com.chocolate.chocolateQuest.utils.HelperPlayer;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityProjectileBeam extends Entity implements IEntityAdditionalSpawnData {

   Elements element;
   int elementID;
   EntityLivingBase mainBody;
   public float rotationYawOffset;
   public float distanceToMainBody;
   public float heightOffset;
   int ownerID;
   public int maxRange;
   public float range;
   public float damage;
   public final float DAMAGE_MULTIPLIER;
   public static final int BEAM_DURATION_ON_MOBS = 40;


   public EntityProjectileBeam(World world) {
      super(world);
      this.rotationYawOffset = 0.0F;
      this.distanceToMainBody = 0.0F;
      this.heightOffset = 0.0F;
      this.ownerID = 0;
      this.maxRange = 16;
      this.range = 16.0F;
      this.damage = 2.0F;
      this.DAMAGE_MULTIPLIER = 0.4F;
      super.isImmuneToFire = true;
      this.setSize(0.5F, 0.5F);
      this.distanceToMainBody = 3.0F;
   }

   public EntityProjectileBeam(World world, EntityLivingBase main, float rotationYawOffset, float distanceToMainBody, Elements element) {
      this(world);
      this.rotationYawOffset = rotationYawOffset;
      this.distanceToMainBody = distanceToMainBody;
      if(main != null) {
         this.setPosition(main.posX, main.posY + (double)this.heightOffset, main.posZ);
      }

      this.mainBody = main;
      this.element = element;
      this.elementID = element.ordinal();
   }

   public EntityProjectileBeam(World world, EntityLivingBase main, float rotationYawOffset, float distanceToMainBody, float heightOffset, Elements element) {
      this(world, main, rotationYawOffset, distanceToMainBody, element);
      this.heightOffset = heightOffset;
   }

   public void setMaxRange(int range) {
      this.maxRange = range;
   }

   public void setDamage(float damage) {
      this.damage = damage;
   }

   public void setSize(float par1, float par2) {
      super.setSize(par1, par2);
   }

   public void onUpdate() {
      Elements element = this.getElement();
      super.onUpdate();
      if(this.mainBody != null) {
         double rPitch = Math.toRadians((double)this.mainBody.rotationPitch);
         double rYawSide = Math.toRadians((double)(this.mainBody.rotationYawHead + this.rotationYawOffset));
         double hx = -Math.sin(rYawSide);
         hx *= Math.cos(rPitch);
         double hz = Math.cos(rYawSide);
         hz *= Math.cos(rPitch);
         hx *= (double)this.distanceToMainBody;
         hz *= (double)this.distanceToMainBody;
         double hy = -Math.sin(rPitch) * (double)this.distanceToMainBody;
         double yO = 0.0D;
         this.setPositionAndRotation(this.mainBody.posX + hx, this.mainBody.posY + hy + (double)this.heightOffset + yO, this.mainBody.posZ + hz, this.mainBody.rotationYawHead, this.mainBody.rotationPitch);
         if(this.mainBody instanceof EntityPlayer) {
            EntityPlayer shooter = (EntityPlayer)this.mainBody;
            if(!shooter.isUsingItem()) {
               this.setDead();
            }
         } else if(super.ticksExisted > 40) {
            this.setDead();
         }

         EntityLivingBase var32 = this.mainBody;
         MovingObjectPosition mop = this.getMovingObjectPositionFromPlayer(var32, var32.worldObj, (double)this.maxRange, 0.0D);
         if(mop != null) {
            double x = (double)mop.blockX;
            double y = (double)mop.blockY;
            double z = (double)mop.blockZ;
            if(this.getElement() == Elements.water) {
               Material dist = super.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ).getMaterial();
               if(dist == Material.fire) {
                  super.worldObj.setBlockToAir(mop.blockX, mop.blockY, mop.blockZ);
               }

               if(dist == Material.lava) {
                  super.worldObj.setBlock(mop.blockX, mop.blockY, mop.blockZ, Blocks.obsidian);
               }

               if(mop.entityHit != null) {
                  mop.entityHit.extinguish();
               }
            }

            switch(mop.sideHit) {
            case 0:
               y += -1.0D;
            case 1:
               ++y;
            case 2:
               z += 0.0D;
               break;
            case 3:
               ++z;
               break;
            case 4:
               x += 0.0D;
               break;
            case 5:
               ++x;
            }

            double var31 = this.getDistance(x, y, z);
            if(mop.entityHit != null) {
               var31 -= (double)(mop.entityHit.width / 2.0F);
            }

            this.range = (float)var31;
            float s;
            if(mop.entityHit instanceof EntityLivingBase) {
               s = 0.2F * this.damage;
               element.attackWithElement((EntityLivingBase)mop.entityHit, this.mainBody, s);
            }

            if(super.worldObj.isRemote) {
               s = 0.2F;
               double ry = Math.cos(Math.toRadians((double)super.rotationPitch));
               double rx = -Math.sin(Math.toRadians((double)super.rotationYaw)) * var31 * ry;
               double rz = Math.cos(Math.toRadians((double)super.rotationYaw)) * var31 * ry;
               ry = -Math.sin(Math.toRadians((double)super.rotationPitch)) * var31;
               EffectManager.spawnElementParticle(0, super.worldObj, super.posX + rx, super.posY + ry, super.posZ + rz, (double)((super.rand.nextFloat() - 0.5F) * s), 0.1D, (double)((super.rand.nextFloat() - 0.5F) * s), element);
            }
         } else {
            this.range = (float)this.maxRange;
         }
      } else {
         this.setDead();
      }

   }

   protected void readEntityFromNBT(NBTTagCompound var1) {
      this.setDead();
   }

   protected void writeEntityToNBT(NBTTagCompound var1) {}

   public void readSpawnData(ByteBuf additionalData) {
      if(!additionalData.readBoolean()) {
         int id = additionalData.readInt();
         Entity e = super.worldObj.getEntityByID(id);
         if(e instanceof EntityLivingBase) {
            this.mainBody = (EntityLivingBase)e;
         }

         this.distanceToMainBody = additionalData.readFloat();
         this.rotationYawOffset = additionalData.readFloat();
         this.heightOffset = additionalData.readFloat();
         this.elementID = additionalData.readInt();
         this.maxRange = additionalData.readInt();
         this.damage = additionalData.readFloat();
      }
   }

   public void writeSpawnData(ByteBuf buffer) {
      buffer.writeBoolean(this.mainBody == null);
      if(this.mainBody != null) {
         buffer.writeInt(this.mainBody.getEntityId());
         buffer.writeFloat(this.distanceToMainBody);
         buffer.writeFloat(this.rotationYawOffset);
         buffer.writeFloat(this.heightOffset);
         buffer.writeInt(this.elementID);
         buffer.writeInt(this.maxRange);
         buffer.writeFloat(this.damage);
      }

   }

   protected void entityInit() {}

   public MovingObjectPosition getMovingObjectPositionFromPlayer(EntityLivingBase ep, World world, double dist, double removeMe) {
      MovingObjectPosition mop = null;
      float yOffset = ep.getEyeHeight();
      Vec3 playerPos = Vec3.createVectorHelper(ep.posX, ep.posY + (double)yOffset, ep.posZ);
      Vec3 look = ep.getLookVec();
      mop = HelperPlayer.getBlockMovingObjectPositionFromPlayer(world, ep, dist, true);
      Vec3 playerView;
      if(mop != null) {
         playerView = Vec3.createVectorHelper(ep.posX - (double)mop.blockX, ep.posY - (double)mop.blockY, ep.posZ - (double)mop.blockZ);
         dist = playerView.lengthVector();
      }

      playerView = playerPos.addVector(look.xCoord * dist, look.yCoord * dist, look.zCoord * dist);
      List list = world.getEntitiesWithinAABBExcludingEntity(ep, ep.boundingBox.addCoord(ep.getLookVec().xCoord * dist, ep.getLookVec().yCoord * dist, ep.getLookVec().zCoord * dist).expand(1.0D, 1.0D, 1.0D));
      MovingObjectPosition tempMop = null;
      double prevDist = dist * dist;

      for(int j = 0; j < list.size(); ++j) {
         Entity entity1 = (Entity)list.get(j);
         if(entity1 != ep.ridingEntity && ep != ep.riddenByEntity && entity1 != this) {
            float f2 = 0.4F;
            AxisAlignedBB axisalignedbb = entity1.boundingBox;
            MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(playerPos, playerView);
            if(movingobjectposition1 != null) {
               movingobjectposition1.entityHit = entity1;
               movingobjectposition1.blockX = MathHelper.floor_double(entity1.posX);
               movingobjectposition1.blockY = MathHelper.floor_double(entity1.posY + (double)(entity1.height / 2.0F));
               movingobjectposition1.blockZ = MathHelper.floor_double(entity1.posZ);
               double entityDist = entity1.getDistanceSqToEntity(ep) - (double)(entity1.width * entity1.width);
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

   public Elements getElement() {
      if(this.element == null) {
         this.element = Elements.values()[this.elementID];
      }

      return this.element;
   }

   public boolean canBeCollidedWith() {
      return false;
   }

   public double getYOffset() {
      return super.getYOffset();
   }

   public double getMountedYOffset() {
      return super.getMountedYOffset();
   }
}
