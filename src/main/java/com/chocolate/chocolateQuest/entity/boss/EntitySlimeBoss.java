package com.chocolate.chocolateQuest.entity.boss;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.ai.AIBossAttack;
import com.chocolate.chocolateQuest.entity.ai.AILavaSwim;
import com.chocolate.chocolateQuest.entity.boss.AttackKick;
import com.chocolate.chocolateQuest.entity.boss.EntityBaseBoss;
import com.chocolate.chocolateQuest.entity.boss.EntitySlimePart;
import com.chocolate.chocolateQuest.packets.PacketEntityAnimation;
import com.chocolate.chocolateQuest.particles.EffectManager;
import com.chocolate.chocolateQuest.utils.BDHelper;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntitySlimeBoss extends EntityBaseBoss {

   public AttackKick kickHelper = new AttackKick(this);
   public int slimePoolAttackTime;
   public int slimePoolChargeTime = 30;
   public int slimePoolAttackTimeMax = 100;
   final byte SLIMEPOOL = 5;


   public EntitySlimeBoss(World par1World) {
      super(par1World);
      this.kickHelper.setSpeed(16 + (int)(super.size * 4.0F));
      super.projectileDefense = 30;
      super.physicDefense = 0;
      super.fireDefense = 20;
      super.blastDefense = -50;
      super.limitRotation = true;
      super.xpRatio = 1.5F;
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(25.0D);
   }

   protected void scaleAttributes() {
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D + (double)super.lvl * 0.012D);
      this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0D + (double)super.lvl * 0.3D);
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(150.0D + (double)super.lvl * 125.0D);
   }

   public void addAITasks() {
      super.tasks.addTask(0, new AILavaSwim(this));
      super.tasks.addTask(0, new EntityAISwimming(this));
      super.tasks.addTask(1, new AIBossAttack(this, 1.0F, false));
      super.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
      super.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityLivingBase.class, 0, true));
      super.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
   }

   public float getMinSize() {
      return 1.4F;
   }

   public float getSizeVariation() {
      return 0.5F;
   }

   public void animationBoss(byte animType) {
      switch(animType) {
      case 5:
         this.startPoolAttack();
      default:
         this.kickHelper.kick(animType);
      }
   }

   public void onLivingUpdate() {
      if(super.worldObj.isRemote) {
         if(this.slimePoolAttackTime > 0) {
            double list = (double)super.width * 0.75D;
            if(this.slimePoolAttackTime < this.slimePoolAttackTimeMax - this.slimePoolChargeTime) {
               for(int entity1 = 0; entity1 < 3; ++entity1) {
                  EffectManager.spawnParticle(0, super.worldObj, super.posX + super.rand.nextGaussian() * list, super.posY + 0.3D, super.posZ + super.rand.nextGaussian() * list, 0.2D + (double)(super.rand.nextFloat() / 8.0F), 0.6D + (double)(super.rand.nextFloat() / 4.0F), 0.2D + (double)(super.rand.nextFloat() / 8.0F));
               }
            }

            float var6 = super.size / 2.0F;
            EffectManager.spawnParticle(1, super.worldObj, super.posX + super.rand.nextGaussian() * (double)var6, super.posY + 0.2D, super.posZ + super.rand.nextGaussian() * (double)var6, 0.2D + (double)(super.rand.nextFloat() / 8.0F), 0.6D + (double)(super.rand.nextFloat() / 4.0F), 0.2D + (double)(super.rand.nextFloat() / 8.0F));
         } else {
            float var4 = super.size / 2.0F;
            EffectManager.spawnParticle(2, super.worldObj, super.posX + super.rand.nextGaussian() * (double)var4, super.posY + 0.2D, super.posZ + super.rand.nextGaussian() * (double)var4, 0.2D + (double)(super.rand.nextFloat() / 8.0F), 0.6D + (double)(super.rand.nextFloat() / 4.0F), 0.2D + (double)(super.rand.nextFloat() / 8.0F));
         }
      }

      if(this.getHealth() < this.getMaxHealth() / 10.0F && super.ticksExisted % 20 == 0) {
         List var5 = super.worldObj.getEntitiesWithinAABB(EntitySlimePart.class, super.boundingBox.expand(16.0D, 4.0D, 16.0D));

         for(int j = 0; j < var5.size(); ++j) {
            Entity var7 = (Entity)var5.get(j);
            this.setAttackTarget((EntityLivingBase)var7);
         }
      }

      if(!super.isDead) {
         this.kickHelper.onUpdate();
         if(this.isAttacking()) {
            if(super.onGround) {
               this.setAttacking(false);
            } else {
               this.damageNearby(1.0D);
            }
         }

         if(this.slimePoolAttackTime > 0) {
            if(this.slimePoolAttackTime > 40 && this.slimePoolAttackTime % 30 == 0) {
               super.worldObj.playSoundEffect((double)((int)super.posX), (double)((int)super.posY), (double)((int)super.posZ), "chocolatequest:bubble_explode", 4.0F, (1.0F + (super.worldObj.rand.nextFloat() - super.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
            }

            --this.slimePoolAttackTime;
            if(this.slimePoolAttackTime < this.slimePoolAttackTimeMax - this.slimePoolChargeTime) {
               this.damagePool();
            }
         }
      }

      super.onLivingUpdate();
   }

   public boolean attackInProgress() {
      return this.kickHelper.isAttackInProgress() || this.slimePoolAttackTime > 0;
   }

   public void attackEntity(Entity entity, float f) {
      if(this.slimePoolAttackTime <= 0) {
         if(f > 64.0F * super.width / 2.0F + super.width * super.width && super.onGround && super.rand.nextInt(10) == 0) {
            int width = (int)MathHelper.wrapAngleTo180_double(BDHelper.getAngleBetweenEntities(this, entity) - (double)super.rotationYaw);
            if(Math.abs(width) < 2) {
               this.jumpToTarget(entity);
            }
         }

         float width1 = super.width + entity.width;
         width1 *= width1;
         if(entity.getClass() == EntitySlimePart.class) {
            if(f < width1) {
               entity.applyEntityCollision(this);
               this.swingItem();
            }

         } else {
            if(super.ticksExisted % 20 == 0 && !super.worldObj.isRemote) {
               boolean targetUp = super.posY - entity.posY + (double)super.height <= 0.0D && f < super.size * super.size * 2.0F;
               if((super.rand.nextInt(6) == 0 || targetUp) && f < width1 * 2.0F) {
                  this.startPoolAttack();
                  return;
               }

               if((double)f < (double)width1 * 1.5D) {
                  this.kickHelper.attackTarget(entity);
               }
            }

            super.attackEntity(entity, f);
         }
      }
   }

   public void startPoolAttack() {
      if(!super.worldObj.isRemote) {
         PacketEntityAnimation packet = new PacketEntityAnimation(this.getEntityId(), (byte)5);
         ChocolateQuest.channel.sendToAllAround(this, packet, 64);
      }

      if(this.slimePoolAttackTime == 0) {
         this.slimePoolAttackTime = this.slimePoolAttackTimeMax;
      }

   }

   public boolean jumpToTarget(Entity entity) {
      if(!this.isAttacking()) {
         float rotation = super.rotationYaw * 3.141592F / 180.0F;
         double vx = entity.posX - super.posX;
         double vy = entity.posY - super.posY;
         double vz = entity.posZ - super.posZ;
         super.motionX = vx / 4.0D;
         super.motionY = vy / 5.0D + (double)(entity.height / 6.0F);
         super.motionZ = vz / 4.0D;
         this.setAttacking(true);
         return true;
      } else {
         return false;
      }
   }

   public void damageNearby(double expand) {
      List list = super.worldObj.getEntitiesWithinAABBExcludingEntity(this, super.boundingBox.expand(expand, expand, expand));

      for(int j = 0; j < list.size(); ++j) {
         Entity entity1 = (Entity)list.get(j);
         if(entity1.canBeCollidedWith() && !this.isEntityEqual(entity1) && entity1 != super.riddenByEntity) {
            this.attackEntityAsMob(entity1);
            this.setAttacking(false);
         }
      }

   }

   public void damagePool() {
      double expand = (double)super.width * 1.35D;
      List list = super.worldObj.getEntitiesWithinAABBExcludingEntity(this, super.boundingBox.expand(expand, expand, expand));

      for(int j = 0; j < list.size(); ++j) {
         Entity entity1 = (Entity)list.get(j);
         if(entity1 instanceof EntityLivingBase && entity1.canBeCollidedWith() && !this.isEntityEqual(entity1) && entity1 != super.riddenByEntity) {
            this.attackEntityAsMob(entity1, 0.6F);
            this.setAttacking(false);
         }
      }

   }

   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
      boolean flag = super.attackEntityFrom(par1DamageSource, par2);
      if(!super.worldObj.isRemote && flag && this.getHealth() > 0.0F && super.hurtTime != 0 && !par1DamageSource.damageType.equals(DamageSource.inWall)) {
         float damage = (float)((double)par2 * BDHelper.getDamageReductionForElement(this, BDHelper.getElementFromDamageSource(par1DamageSource), false));
         EntitySlimePart part = new EntitySlimePart(super.worldObj, this, damage);
         part.setPosition(super.posX, super.posY + (double)super.height, super.posZ);
         part.motionX = super.rand.nextGaussian() * 2.0D;
         part.motionZ = super.rand.nextGaussian() * 2.0D;
         part.motionY = 1.0D;
         super.worldObj.spawnEntityInWorld(part);
      }

      return flag;
   }

   protected void fall(float par1) {}

   protected String getHurtSound() {
      return "mob.slime.big";
   }

   protected String getDeathSound() {
      return "mob.slime.big";
   }

   protected int getDropMaterial() {
      return 3;
   }
}
