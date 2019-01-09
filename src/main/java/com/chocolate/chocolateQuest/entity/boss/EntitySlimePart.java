package com.chocolate.chocolateQuest.entity.boss;

import com.chocolate.chocolateQuest.entity.boss.EntitySlimeBoss;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntitySlimePart extends EntitySlime {

   EntityLivingBase owner;
   float healthValue;


   public EntitySlimePart(World par1World) {
      super(par1World);
      super.fallDistance = -10.0F;
   }

   public EntitySlimePart(World par1World, EntityLivingBase entity, float size) {
      this(par1World);
      this.owner = entity;
      this.setSlimeSize(Math.max(1, (int)size / 3));
      float health = 3.0F + size;
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue((double)health);
      this.setHealth(health);
      super.experienceValue = 0;
      this.healthValue = size;
   }

   public void onUpdate() {
      super.onUpdate();
      if(!super.worldObj.isRemote) {
         if(this.owner != null) {
            super.rotationYaw = (float)(-Math.atan2(this.owner.posX - super.posX, this.owner.posZ - super.posZ) * 180.0D / 3.141592653589793D);
            if(!this.owner.isEntityAlive()) {
               this.setDead();
            }
         } else {
            this.setDead();
         }
      }

   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(1.4D);
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0D);
      this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(15.0D);
   }

   public boolean attackEntityAsMob(Entity par1Entity) {
      return super.attackEntityAsMob(par1Entity);
   }

   public void applyEntityCollision(Entity par1Entity) {
      if(!(par1Entity instanceof EntitySlimeBoss) && par1Entity != this.owner) {
         if(par1Entity instanceof EntityLivingBase && par1Entity.getClass() != this.getClass()) {
            EntityLivingBase el1 = (EntityLivingBase)par1Entity;
            if(!el1.isOnSameTeam(this)) {
               ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 120, 1));
            }
         }
      } else if(super.ticksExisted > 10) {
         float el = this.healthValue * 0.5F;
         if(this.owner instanceof EntitySlimeBoss) {
            el = this.healthValue * 0.6F + (float)super.worldObj.difficultySetting.ordinal() * 0.1F;
         }

         ((EntityLivingBase)par1Entity).heal(el);
         this.setDead();
      }

      super.applyEntityCollision(par1Entity);
   }

   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
      return par1DamageSource.getEntity() instanceof EntitySlimeBoss?false:super.attackEntityFrom(par1DamageSource, par2);
   }

   public boolean canAttackClass(Class par1Class) {
      return super.canAttackClass(par1Class) && par1Class != this.getClass() && par1Class != EntitySlimePart.class;
   }

   protected void dropFewItems(boolean par1, int par2) {}

   public void setDead() {
      if(!super.worldObj.isRemote && !super.isDead && this.getHealth() <= 0.0F && this.getSlimeSize() > 1) {
         int size = MathHelper.floor_float(this.healthValue * 0.34F);

         for(int a = 0; a < 2; ++a) {
            EntitySlimePart part = new EntitySlimePart(super.worldObj, this.owner, (float)size);
            part.setPosition(super.posX, super.posY + 1.0D, super.posZ);
            part.motionX = super.rand.nextGaussian();
            part.motionZ = super.rand.nextGaussian();
            super.worldObj.spawnEntityInWorld(part);
         }
      }

      super.isDead = true;
   }

   public Team getTeam() {
      return this.owner != null?this.owner.getTeam():super.getTeam();
   }

   protected int getAttackStrength() {
      return 0;
   }
}
