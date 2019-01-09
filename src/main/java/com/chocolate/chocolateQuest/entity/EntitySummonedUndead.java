package com.chocolate.chocolateQuest.entity;

import com.chocolate.chocolateQuest.entity.ai.AIFollowOwner;
import com.chocolate.chocolateQuest.entity.ai.AITargetOwner;
import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.misc.EnumVoice;
import com.chocolate.chocolateQuest.particles.EffectManager;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntitySummonedUndead extends EntityCreature implements IEntityOwnable, IAnimals {

   public static final byte SKELETON = 0;
   public static final byte GOLEM = 1;
   public static final byte HOUND = 2;
   public static final byte WITHER = 3;
   EntityLivingBase summoner;
   int lifeTime;
   int maxLifeTime;
   private boolean firstTick;


   public EntitySummonedUndead(World par1World) {
      super(par1World);
      this.lifeTime = 0;
      this.maxLifeTime = 1200;
      this.firstTick = true;
      super.experienceValue = 0;
      this.initTasks();

      for(int i = 0; i < super.equipmentDropChances.length; ++i) {
         super.equipmentDropChances[i] = 0.0F;
      }

   }

   public EntitySummonedUndead(World world, EntityLivingBase summoner, int lvl) {
      this(world);
      this.summoner = summoner;
      this.setlvl((byte)lvl);
   }

   public EntitySummonedUndead(World world, EntityLivingBase summoner, int lvl, byte type) {
      this(world);
      this.summoner = summoner;
      this.setlvl((byte)lvl);
      this.setType(type);
      if(type == 2) {
         this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.33D);
      }

   }

   public EntitySummonedUndead(World world, EntityLivingBase summoner, int lvl, float damage, Elements element, byte type) {
      this(world, summoner, lvl);
      this.setElement((byte)element.ordinal());
      this.setType(type);
      if(type == 1) {
         this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D + (double)(lvl * 10) + (double)(damage * 5.0F));
         this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue((double)damage * 0.5D);
      } else if(type == 2) {
         this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D + (double)(lvl * 5));
         this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue((double)damage * 1.2D);
         this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23D + (double)damage * 0.01D + (double)lvl * 0.025D);
      } else {
         this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D + (double)(lvl * 5));
         this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue((double)damage);
      }

      this.heal(100.0F);
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
      this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D);
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0D);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23D);
   }

   protected void initTasks() {
      super.tasks.addTask(0, new EntityAISwimming(this));
      super.tasks.addTask(1, new EntityAIAttackOnCollide(this, EntityLivingBase.class, 1.0D, true));
      super.tasks.addTask(2, new AIFollowOwner(this, 1.0F, 8.0F, 50.0F, true));
      super.targetTasks.addTask(1, new AITargetOwner(this));
   }

   protected void entityInit() {
      super.entityInit();
      super.dataWatcher.addObject(16, Byte.valueOf((byte)0));
      super.dataWatcher.addObject(17, Byte.valueOf((byte)-1));
      super.dataWatcher.addObject(18, Byte.valueOf((byte)0));
   }

   public Elements getElement() {
      byte b = super.dataWatcher.getWatchableObjectByte(17);
      return b < 0?null:Elements.values()[b];
   }

   public int getType() {
      return super.dataWatcher.getWatchableObjectByte(18);
   }

   public void setType(byte lvl) {
      super.dataWatcher.updateObject(18, Byte.valueOf(lvl));
   }

   public void setElement(byte lvl) {
      super.dataWatcher.updateObject(17, Byte.valueOf(lvl));
   }

   public int getlvl() {
      return super.dataWatcher.getWatchableObjectByte(16);
   }

   public void setlvl(byte lvl) {
      super.dataWatcher.updateObject(16, Byte.valueOf(lvl));
   }

   public boolean isAIEnabled() {
      return true;
   }

   public void onLivingUpdate() {
      if(this.summoner != null) {
         if(this.summoner.isDead) {
            this.setDead();
         }

         if(this.firstTick && this.getType() == 1) {
            double element = 20.0D;
            AxisAlignedBB g = super.boundingBox.expand(element, 0.0D, element);
            List b = super.worldObj.getEntitiesWithinAABB(EntityLiving.class, g);
            Iterator x = b.iterator();

            while(x.hasNext()) {
               Entity y = (Entity)x.next();
               if(y instanceof EntityLiving) {
                  EntityLiving z = (EntityLiving)y;
                  if((z.getAttackTarget() == this.getOwner() || z.getAITarget() == this.getOwner()) && !super.worldObj.isRemote) {
                     z.setAttackTarget(this);
                     z.setRevengeTarget(this);
                  }

                  if(super.worldObj.isRemote && y != this.getOwner() && y != this) {
                     double x1 = z.posX;
                     double y1 = z.posY + (double)z.height;
                     double z1 = z.posZ;
                     EffectManager.spawnParticle(8, super.worldObj, x1 + super.rand.nextGaussian() * (double)super.width, y1 + (double)(z.height / 2.0F), z1 + super.rand.nextGaussian() * (double)super.width, super.posX - x1, super.posY - y1, super.posZ - z1);
                  }
               }
            }

            this.firstTick = false;
         }
      } else if(!super.worldObj.isRemote) {
         this.setDead();
      }

      if(this.lifeTime > this.maxLifeTime) {
         this.setDead();
      }

      if(super.worldObj.isRemote) {
         Elements element1 = this.getElement();
         if(element1 != null) {
            float r = element1.getColorX();
            float g1 = element1.getColorY();
            float b1 = element1.getColorZ();
            float x2 = super.rand.nextFloat() - 0.5F;
            float y2 = super.rand.nextFloat() * 2.0F;
            float z2 = super.rand.nextFloat() - 0.5F;
            super.worldObj.spawnParticle("mobSpell", super.posX + (double)x2, super.posY + (double)y2, super.posZ + (double)z2, (double)r, (double)g1, (double)b1);
         }
      }

      ++this.lifeTime;
      super.onLivingUpdate();
   }

   protected String getLivingSound() {
      return EnumVoice.SKELETON.say;
   }

   protected String getHurtSound() {
      return EnumVoice.SKELETON.hurt;
   }

   protected String getDeathSound() {
      return EnumVoice.SKELETON.death;
   }

   public boolean attackEntityAsMob(Entity entity) {
      boolean b = false;
      float damage = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
      Elements element = this.getElement();
      if(element == null) {
         b = entity.attackEntityFrom(DamageSource.causeMobDamage(this), damage);
      } else if(entity instanceof EntityLivingBase) {
         b = element.attackWithElement((EntityLivingBase)entity, this, damage);
      }

      if(b && !super.worldObj.isRemote) {
         this.swingItem();
         if(this.summoner != null && this.isReanimatedCreature()) {
            EntityBaseBall ball = new EntityBaseBall(super.worldObj, this.summoner, 9);
            ball.setlvl(this.getlvl());
            ball.setPosition(super.posX, super.posY + (double)super.height, super.posZ);
            super.worldObj.spawnEntityInWorld(ball);
            if(this.getType() == 3 && entity instanceof EntityLivingBase) {
               ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.wither.id, 200));
            }
         }
      }

      return b;
   }

   public boolean isReanimatedCreature() {
      return this.getElement() == null;
   }

   public boolean attackEntityFrom(DamageSource damagesource, int i) {
      if(damagesource.getEntity() != this.summoner && super.attackEntityFrom(damagesource, (float)i)) {
         Entity entity = damagesource.getEntity();
         if(super.riddenByEntity != entity && super.ridingEntity != entity) {
            if(entity instanceof EntityPlayer) {
               super.entityToAttack = entity;
            }

            if(this.getHealth() <= 0.0F) {
               this.setDead();
            }

            return true;
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   public void setDead() {
      if(super.worldObj.isRemote) {
         for(int r = 0; r < 30; ++r) {
            super.worldObj.spawnParticle("smoke", super.posX + (double)super.rand.nextFloat() - 0.5D, super.posY + (double)(super.rand.nextFloat() * 2.0F), super.posZ + (double)super.rand.nextFloat() - 0.5D, 0.0D, 0.0D, 0.0D);
         }
      }

      super.setDead();
   }

   public EnumCreatureAttribute getCreatureAttribute() {
      return this.getElement() != null?EnumCreatureAttribute.UNDEFINED:EnumCreatureAttribute.UNDEAD;
   }

   public EntityLivingBase getOwner() {
      return this.summoner;
   }

   public boolean isEntityEqual(Entity par1Entity) {
      return this.getOwner() == par1Entity || super.isEntityEqual(par1Entity);
   }

   public String func_152113_b() {
      return null;
   }
}
