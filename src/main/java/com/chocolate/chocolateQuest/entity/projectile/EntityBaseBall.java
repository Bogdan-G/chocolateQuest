package com.chocolate.chocolateQuest.entity.projectile;

import com.chocolate.chocolateQuest.entity.projectile.ProjectileBase;
import com.chocolate.chocolateQuest.entity.projectile.ProjectileDummy;
import com.chocolate.chocolateQuest.magic.Elements;
import cpw.mods.fml.common.registry.IThrowableEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityBaseBall extends EntityThrowable implements IThrowableEntity {

   private ProjectileBase ballData;
   public ItemStack item;
   int field_70195_i;
   EntityLivingBase shootingEntity;
   float damageMultiplier;
   final int TYPE;
   final int LEVEL;
   final int ELEMENT;


   public EntityBaseBall(World par1World) {
      super(par1World);
      this.item = null;
      this.field_70195_i = 0;
      this.damageMultiplier = 1.0F;
      this.TYPE = 10;
      this.LEVEL = 11;
      this.ELEMENT = 12;
   }

   public EntityBaseBall(World world, EntityLivingBase entityliving) {
      this(world, entityliving, 0);
   }

   public EntityBaseBall(World world, EntityLivingBase entityliving, int type) {
      this(world, entityliving, type, 0);
   }

   public EntityBaseBall(World world, EntityLivingBase entityliving, int type, int lvl, Elements element) {
      super(world, entityliving);
      this.item = null;
      this.field_70195_i = 0;
      this.damageMultiplier = 1.0F;
      this.TYPE = 10;
      this.LEVEL = 11;
      this.ELEMENT = 12;
      this.setlvl(lvl);
      this.setElement(element);
      this.shootingEntity = entityliving;
      this.setType(type);
      float s = this.getBallData().getSizeBB();
      this.setSize(s, s);
      this.playShootSound();
      this.getBallData().onSpawn();
   }

   public EntityBaseBall(World world, EntityLivingBase entityliving, int type, int lvl) {
      this(world, entityliving, type, lvl, Elements.physic);
   }

   public EntityBaseBall(World world, EntityLivingBase entityliving, int type, ItemStack item, int lvl) {
      this(world, entityliving, type, lvl);
      this.item = item;
   }

   public EntityBaseBall(World world, EntityLivingBase entityliving, double x, double y, double z, int type, int lvl) {
      this(world, entityliving, x, y, z, type, lvl, 0.0F);
   }

   public EntityBaseBall(World world, EntityLivingBase entityliving, double x, double y, double z, int type, int lvl, float accuracy) {
      this(world, entityliving, x, y, z, type, lvl, accuracy, 1.0F);
   }

   public EntityBaseBall(World world, EntityLivingBase entityliving, double x, double y, double z, int type, int lvl, float accuracy, float speed) {
      this(world, entityliving, type, lvl);
      this.setThrowableHeading(x, y, z, speed, accuracy);
   }

   protected void entityInit() {
      super.dataWatcher.addObject(10, Byte.valueOf((byte)0));
      super.dataWatcher.addObject(11, Byte.valueOf((byte)0));
      super.dataWatcher.addObject(12, Byte.valueOf((byte)0));
   }

   public void playShootSound() {
      if(!super.worldObj.isRemote) {
         this.getBallData().onSpawn();
      }

   }

   public void setType(int par) {
      super.dataWatcher.updateObject(10, Byte.valueOf((byte)par));
   }

   public byte getType() {
      return super.dataWatcher.getWatchableObjectByte(10);
   }

   public void setlvl(int par) {
      super.dataWatcher.updateObject(11, Byte.valueOf((byte)par));
   }

   public byte getlvl() {
      return super.dataWatcher.getWatchableObjectByte(11);
   }

   public void setElement(Elements par) {
      super.dataWatcher.updateObject(12, Byte.valueOf((byte)par.ordinal()));
   }

   public Elements getElement() {
      return Elements.values()[super.dataWatcher.getWatchableObjectByte(12)];
   }

   @SideOnly(Side.CLIENT)
   public boolean isInRangeToRenderDist(double par1) {
      double d1 = super.boundingBox.getAverageEdgeLength() * 4.0D;
      d1 *= 64.0D;
      return par1 < d1 * d1;
   }

   public void onUpdate() {
      if(this.getBallData().longRange()) {
         super.motionX *= 1.01D;
         super.motionY *= 1.01D;
         super.motionZ *= 1.01D;
      }

      if(this.getThrower() != null && this.getThrower().isDead) {
         this.setDead();
      } else {
         if(this.field_70195_i++ > this.getBallData().getMaxLifeTime()) {
            this.setDead();
         }

         super.onUpdate();
         this.getBallData().onUpdateInAir();
      }

   }

   protected float getGravityVelocity() {
      return this.getBallData().getGravityVelocity();
   }

   protected void onImpact(MovingObjectPosition par1MovingObjectPosition) {
      this.getBallData().onImpact(par1MovingObjectPosition);
   }

   public double getMountedYOffset() {
      return -0.40000001192092893D;
   }

   public double getYOffset() {
      return this.ballData.getYOffset();
   }

   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
      par1NBTTagCompound.setByte("Type", this.getType());
      par1NBTTagCompound.setByte("lvl", this.getlvl());
   }

   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
      this.setType(par1NBTTagCompound.getByte("Type"));
      this.setlvl(par1NBTTagCompound.getByte("lvl"));
      this.setDead();
   }

   public boolean canBeCollidedWith() {
      return true;
   }

   public boolean attackEntityFrom(DamageSource source, float damage) {
      this.getBallData().attackFrom(source, damage);
      this.setBeenAttacked();
      if(source.getEntity() != null && this.getBallData().canBounce()) {
         Vec3 var3 = source.getEntity().getLookVec();
         if(var3 != null) {
            super.motionX = var3.xCoord;
            super.motionY = var3.yCoord;
            super.motionZ = var3.zCoord;
         }

         return true;
      } else {
         return false;
      }
   }

   public Random getRNG() {
      return super.rand;
   }

   public void setDead() {
      this.getBallData().onDead();
      super.setDead();
   }

   public void setDamageMultiplier(float damage) {
      this.damageMultiplier = damage;
   }

   public float getDamageMultiplier() {
      return this.damageMultiplier;
   }

   public int getTextureIndex() {
      return this.getBallData().getTextureIndex();
   }

   public float getBallSize() {
      return this.getBallData().getSize();
   }

   public boolean renderAsArrow() {
      return this.getBallData().renderAsArrow();
   }

   public ProjectileBase getBallData() {
      if(this.ballData == null || this.ballData instanceof ProjectileDummy) {
         this.ballData = ProjectileBase.getBallData(this);
         this.setSize(this.ballData.getSizeBB(), this.ballData.getSizeBB());
      }

      return this.ballData;
   }

   public void setBallData(ProjectileBase data) {
      this.ballData = data;
   }

   public void setInmuneToFire(boolean fire) {
      super.isImmuneToFire = fire;
   }

   public EntityLivingBase getThrower() {
      return this.shootingEntity;
   }

   public void setThrower(Entity entity) {
      if(entity instanceof EntityLivingBase) {
         this.shootingEntity = (EntityLivingBase)entity;
      }

   }
}
