package com.chocolate.chocolateQuest.entity;

import com.chocolate.chocolateQuest.entity.ai.AITargetOwner;
import com.chocolate.chocolateQuest.particles.EffectManager;
import cpw.mods.fml.common.registry.IThrowableEntity;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityBaiter extends EntityCreature implements IThrowableEntity, IEntityOwnable {

   int field_70724_aR;
   int lifeTime;
   EntityLivingBase summoner;
   boolean firstTick;


   protected void dropFewItems(boolean b, int i) {}

   protected void dropEquipment(boolean b, int i) {}

   public EntityBaiter(World par1World) {
      super(par1World);
      this.field_70724_aR = 0;
      this.lifeTime = 0;
      this.firstTick = true;
      super.experienceValue = 0;
      this.initTasks();

      for(int i = 0; i < super.equipmentDropChances.length; ++i) {
         super.equipmentDropChances[i] = 0.0F;
      }

   }

   public EntityBaiter(World world, EntityLivingBase summoner) {
      this(world);
      this.summoner = summoner;

      for(int i = 0; i < 5; ++i) {
         this.setCurrentItemOrArmor(i, summoner.getEquipmentInSlot(i));
      }

      super.posX = summoner.posX;
      super.posY = summoner.posY;
      super.posZ = summoner.posZ;
      this.setPosition(super.posX, super.posY, super.posZ);
      if(summoner.getLastAttacker() != null) {
         this.setAttackTarget(summoner.getLastAttacker());
      }

      if(summoner instanceof EntityLiving) {
         this.setAttackTarget(((EntityLiving)summoner).getAttackTarget());
      }

      this.setHealth(summoner.getHealth());
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(this.summoner == null?20.0D:(double)this.summoner.getMaxHealth());
      this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23D);
   }

   protected void initTasks() {
      super.tasks.addTask(0, new EntityAISwimming(this));
      super.tasks.addTask(1, new EntityAIAttackOnCollide(this, EntityLiving.class, 1.0D, true));
      super.targetTasks.addTask(1, new AITargetOwner(this));
   }

   public void onLivingUpdate() {
      if(this.getThrower() != null) {
         if(this.firstTick) {
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(this.summoner == null?20.0D:(double)this.summoner.getMaxHealth());
            double dist = 40.0D;
            AxisAlignedBB var3 = super.boundingBox.expand(dist, 0.0D, dist);
            List list = super.worldObj.getEntitiesWithinAABB(EntityLiving.class, var3);
            Iterator i$ = list.iterator();

            while(i$.hasNext()) {
               Entity e = (Entity)i$.next();
               if(e instanceof EntityLiving) {
                  EntityLiving el = (EntityLiving)e;
                  double x;
                  double y;
                  double z;
                  if(el.getAttackTarget() == this.getThrower() || el.getAITarget() == this.getThrower()) {
                     if(!super.worldObj.isRemote) {
                        el.setAttackTarget(this);
                        el.setRevengeTarget(this);
                     } else {
                        x = el.posX;
                        y = el.posY + (double)el.height;
                        z = el.posZ;
                        EffectManager.spawnParticle(8, super.worldObj, x + super.rand.nextGaussian() * (double)super.width, y + (double)super.height, z + super.rand.nextGaussian() * (double)super.width, super.posX - x, super.posY - y, super.posZ - z);
                     }
                  }

                  if(super.worldObj.isRemote && e != this.getThrower() && e != this) {
                     x = el.posX;
                     y = el.posY + (double)el.height;
                     z = el.posZ;
                     EffectManager.spawnParticle(8, super.worldObj, x + super.rand.nextGaussian() * (double)super.width, y + (double)(el.height / 2.0F), z + super.rand.nextGaussian() * (double)super.width, super.posX - x, super.posY - y, super.posZ - z);
                  }
               }
            }

            this.firstTick = false;
         }

         if(this.getThrower().isDead) {
            this.setDead();
         }
      } else {
         this.setDead();
      }

      ++this.lifeTime;
      if(this.lifeTime > 300) {
         this.setDead();
      }

      if(this.field_70724_aR > 0) {
         --this.field_70724_aR;
      }

      super.onLivingUpdate();
   }

   public void setDead() {
      if(super.worldObj.isRemote) {
         for(int r = 0; r < 30; ++r) {
            super.worldObj.spawnParticle("smoke", super.posX + (double)super.rand.nextFloat() - 0.5D, super.posY + (double)(super.rand.nextFloat() * 2.0F), super.posZ + (double)super.rand.nextFloat() - 0.5D, 0.0D, 0.0D, 0.0D);
         }
      }

      super.setDead();
   }

   public boolean attackEntityAsMob(Entity entity) {
      if(this.field_70724_aR <= 0) {
         this.lifeTime += 50;
         if(entity instanceof EntityLiving) {
            ((EntityLiving)entity).setAttackTarget(this);
         }

         this.swingItem();
         if(super.worldObj.isRemote) {
            double x = entity.posX;
            double y = entity.posY;
            double z = entity.posZ;
            EffectManager.spawnParticle(8, super.worldObj, x, y + (double)super.height, z, super.posX - x, super.posY - y - (double)(super.height / 2.0F), super.posZ - z);
            this.field_70724_aR = 20;
         }
      }

      return false;
   }

   public boolean isAIEnabled() {
      return true;
   }

   public String getCommandSenderName() {
      return this.getThrower() != null?this.getThrower().getCommandSenderName():"Bait";
   }

   public Entity getThrower() {
      return this.summoner;
   }

   public void setThrower(Entity entity) {
      this.summoner = (EntityLivingBase)entity;
   }

   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
      this.lifeTime = (int)((float)this.lifeTime + par2 * 5.0F);
      super.attackEntityFrom(par1DamageSource, par2);
      return false;
   }

   public EntityLivingBase getOwner() {
      return this.summoner;
   }

   public String func_152113_b() {
      return this.summoner.getCommandSenderName();
   }
}
