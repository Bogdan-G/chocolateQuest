package com.chocolate.chocolateQuest.entity.boss;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.ai.AIBossAttack;
import com.chocolate.chocolateQuest.entity.ai.AILavaSwim;
import com.chocolate.chocolateQuest.entity.boss.AttackKickQuadruped;
import com.chocolate.chocolateQuest.entity.boss.EntityBaseBoss;
import com.chocolate.chocolateQuest.entity.boss.EntityPart;
import com.chocolate.chocolateQuest.entity.boss.EntityPartRidable;
import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.packets.PacketEntityAnimation;
import com.chocolate.chocolateQuest.utils.BDHelper;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityBull extends EntityBaseBoss {

   public int smashTime;
   public final int smashSpeed = 30;
   final byte CHARGE_ANIM = 5;
   final byte SMASH = 6;
   public boolean charge;
   public int chargeTime = 0;
   public int chargeTimeMax = 50;
   private float damageRight;
   private float damageLeft;
   private float damageHorn;
   public AttackKickQuadruped kickHelper = new AttackKickQuadruped(this);
   public EntityPart head;
   private boolean headHit = false;


   public EntityBull(World par1World) {
      super(par1World);
      super.experienceValue = 5;
      super.size = super.rand.nextFloat() * 4.0F + 1.0F;
      super.stepHeight = 2.0F;
      this.resize();
      this.kickHelper.setSpeed(16 + (int)(super.size * 5.0F));
      super.limitRotation = true;
   }

   protected void scaleAttributes() {
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D + 0.01D * (double)super.lvl);
      this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(14.0D + 0.8D * (double)super.lvl);
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(300.0D + 200.0D * (double)super.lvl);
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
      return 0.6F;
   }

   public float getSizeVariation() {
      return 0.4F;
   }

   public void initBody() {
      this.head = new EntityPartRidable(super.worldObj, this, 0, 0.0F, super.size - super.size * 0.2F, super.size);
      this.head.setSize(super.size / 3.0F, super.size / 3.0F);
      if(!super.worldObj.isRemote) {
         super.worldObj.spawnEntityInWorld(this.head);
      }

      super.initBody();
   }

   public void setPart(EntityPart entityPart, int partID) {
      super.setPart(entityPart, partID);
      entityPart.setSize(super.size / 3.0F, super.size / 3.0F);
   }

   public boolean attackFromPart(DamageSource par1DamageSource, float par2, EntityPart part) {
      this.headHit = true;
      boolean b = super.attackFromPart(par1DamageSource, par2, part);
      this.headHit = false;
      return b;
   }

   public void animationBoss(byte animType) {
      switch(animType) {
      case 5:
         this.startCharging();
         return;
      case 6:
         this.startSmashing();
         return;
      default:
         this.kickHelper.kick(animType);
      }
   }

   public void onLivingUpdate() {
      super.onLivingUpdate();
      if(!super.isDead) {
         this.updateCharge();
         this.updateSmash();
         this.kickHelper.onUpdate();
      }

   }

   public boolean attackInProgress() {
      return this.kickHelper.isAttackInProgress() || this.smashTime > 0;
   }

   public boolean shouldMoveToEntity(double d1, Entity target) {
      return !this.attackInProgress() && !this.charge?super.shouldMoveToEntity(d1, target):false;
   }

   public void attackEntity(Entity entity, float f) {
      float width = super.width + entity.width;
      boolean targetUp = super.posY - entity.posY + (double)super.height <= 0.0D && f < super.size * super.size * 2.0F;
      if(!this.isAttacking() && (f > (super.width + 8.0F) * (super.width + 8.0F) || targetUp)) {
         int angle = (int)MathHelper.wrapAngleTo180_double(BDHelper.getAngleBetweenEntities(this, entity) - (double)super.rotationYaw);
         if(angle < 1 && angle > -1 || targetUp) {
            this.startCharging();
         }
      }

      if(super.ticksExisted % 30 == 0 && !super.worldObj.isRemote && !this.charge && this.smashTime == 0 && f < (width + 2.0F) * (width + 2.0F)) {
         if(super.rand.nextInt(4) == 0 && this.kickHelper.kickTime == 0) {
            this.startSmashing();
         } else {
            this.kickHelper.attackTarget(entity);
         }
      }

      super.attackEntity(entity, f);
   }

   public void updateSmash() {
      if(this.smashTime > 0) {
         --this.smashTime;
         if(this.smashTime == 1) {
            float rotation = super.rotationYawHead * 3.141592F / 180.0F;
            double d = (double)super.size;
            List list = super.worldObj.getEntitiesWithinAABBExcludingEntity(this, super.boundingBox.expand(d, 0.0D, d).addCoord(0.0D, -2.0D, 0.0D));
            byte min = -60;
            byte max = 60;

            double z;
            for(int ball = 0; ball < list.size(); ++ball) {
               Entity legAngle = (Entity)list.get(ball);
               if(legAngle instanceof EntityLivingBase && legAngle.canBeCollidedWith() && !this.isEntityEqual(legAngle) && legAngle != super.riddenByEntity) {
                  int x = (int)MathHelper.wrapAngleTo180_double(BDHelper.getAngleBetweenEntities(this, legAngle) - (double)super.rotationYaw);
                  if(x >= min && x <= max && this.attackEntityAsMob(legAngle)) {
                     float dist = 1.0F;
                     z = -Math.sin((double)rotation) * (double)dist;
                     double z1 = Math.cos((double)rotation) * (double)dist;
                     legAngle.motionX += z;
                     legAngle.motionZ += z1;
                  }
               }
            }

            if(!super.worldObj.isRemote) {
               EntityBaseBall var15 = new EntityBaseBall(super.worldObj, this, 4, 2);
               float var16 = 0.5F;
               double var17 = super.posX - (double)(MathHelper.sin(rotation + 0.5F) * super.width);
               z = super.posZ + (double)(MathHelper.cos(rotation + 0.5F) * super.width);
               var15.posX = var17;
               var15.posY = super.posY + 1.0D;
               var15.posZ = z;
               super.worldObj.spawnEntityInWorld(var15);
               var15 = new EntityBaseBall(super.worldObj, this, 4, 2);
               var17 = super.posX - (double)(MathHelper.sin(rotation - 0.5F) * super.width);
               z = super.posZ + (double)(MathHelper.cos(rotation - 0.5F) * super.width);
               var15.posX = var17;
               var15.posZ = z;
               super.worldObj.spawnEntityInWorld(var15);
            }
         }
      }

   }

   public void startSmashing() {
      if(!super.worldObj.isRemote) {
         PacketEntityAnimation packet = new PacketEntityAnimation(this.getEntityId(), (byte)6);
         ChocolateQuest.channel.sendToAllAround(this, packet, 64);
      }

      if(this.smashTime == 0) {
         this.smashTime = 30;
      }

   }

   public void updateCharge() {
      if(this.charge) {
         ++this.chargeTime;
         float speed = Math.min(0.5F, 0.1F + (float)this.chargeTime / (float)this.chargeTimeMax);
         float rotation = super.rotationYaw * 3.141592F / 180.0F;
         double dx = (double)(-MathHelper.sin(rotation) * speed);
         double dz = (double)(MathHelper.cos(rotation) * speed);
         super.motionX = dx;
         super.motionZ = dz;
         if(this.chargeTime >= this.chargeTimeMax) {
            this.chargeTime = 0;
            this.charge = false;
         }

         List list = super.worldObj.getEntitiesWithinAABBExcludingEntity(this, super.boundingBox.expand(0.2D, 0.2D, 0.2D));

         for(int j = 0; j < list.size(); ++j) {
            Entity entity1 = (Entity)list.get(j);
            if(entity1.canBeCollidedWith() && !entity1.isEntityEqual(this) && entity1 != super.riddenByEntity && this.attackEntityAsMob(entity1)) {
               float dist = 1.0F;
               double x = -Math.sin((double)rotation) * (double)dist;
               double z = Math.cos((double)rotation) * (double)dist;
               entity1.motionX += x;
               entity1.motionZ += z;
            }
         }
      }

   }

   public void startCharging() {
      if(!super.worldObj.isRemote) {
         PacketEntityAnimation packet = new PacketEntityAnimation(this.getEntityId(), (byte)5);
         ChocolateQuest.channel.sendToAllAround(this, packet, 64);
      }

      if(this.chargeTime == 0) {
         this.charge = true;
      }

   }

   protected boolean isAIEnabled() {
      return true;
   }

   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
      if(par1DamageSource.getEntity() == this) {
         return false;
      } else {
         boolean flag = super.attackEntityFrom(par1DamageSource, par2);
         if(!par1DamageSource.isProjectile() && par1DamageSource.getEntity() != null && flag) {
            Entity attacker = par1DamageSource.getEntity();
            double dist = this.getDistanceSqToEntity(attacker);
            if(!super.worldObj.isRemote && this.smashTime <= 0 && !this.charge && dist < (double)(super.width * super.width * 2.0F * 2.0F)) {
               this.kickHelper.attackTarget(attacker);
            }

            if(this.headHit) {
               this.damageHorn += par2;
               if((double)this.damageHorn > (double)this.getMaxHealth() * 0.2D) {
                  this.setHornBroken(true);
               }
            } else {
               int angle = (int)MathHelper.wrapAngleTo180_double(BDHelper.getAngleBetweenEntities(this, attacker) - (double)super.rotationYaw);
               if(angle > 45 && angle < 135) {
                  this.damageRight += par2;
                  if((double)this.damageRight > (double)this.getMaxHealth() * 0.3D) {
                     this.setHurtRight(true);
                  }
               }

               if(angle < -45 && angle > -135) {
                  this.damageLeft += par2;
                  if((double)this.damageLeft > (double)this.getMaxHealth() * 0.3D) {
                     this.setHurtLeft(true);
                  }
               }
            }
         }

         return flag;
      }
   }

   protected void resize() {
      this.setSize(super.size, super.size * 1.4F);
   }

   public boolean isHurtLeft() {
      return this.getAnimFlag(1);
   }

   public void setHurtLeft(boolean attacking) {
      this.setAnimFlag(1, attacking);
   }

   public boolean isHurtRight() {
      return this.getAnimFlag(2);
   }

   public void setHurtRight(boolean attacking) {
      this.setAnimFlag(2, attacking);
   }

   public boolean isHornBroken() {
      return this.getAnimFlag(3);
   }

   public void setHornBroken(boolean attacking) {
      this.setAnimFlag(3, attacking);
   }

   protected String getLivingSound() {
      return "chocolatequest:bull_speak";
   }

   protected String getHurtSound() {
      return "chocolatequest:bull_hurt";
   }

   protected String getDeathSound() {
      return "chocolatequest:bull_death";
   }

   protected int getDropMaterial() {
      return 2;
   }

   protected void dropFewItems(boolean flag, int i) {
      this.entityDropItem(this.getBossMedal(), 0.2F);
      int ammount = 2 + (int)(this.getMonsterDificulty() * this.getMonsterDificulty()) / 2;
      if(this.isHurtRight()) {
         ammount = (int)((float)ammount + 1.0F + this.getMonsterDificulty());
      }

      if(this.isHurtLeft()) {
         ammount = (int)((float)ammount + 1.0F + this.getMonsterDificulty());
      }

      while(ammount > 0) {
         this.entityDropItem(new ItemStack(ChocolateQuest.material, Math.min(64, ammount), this.getDropMaterial()), 0.2F);
         ammount -= 64;
      }

      if(this.isHornBroken()) {
         this.entityDropItem(new ItemStack(ChocolateQuest.material, 1 + (int)Math.floor((double)(this.getMonsterDificulty() / 3.0F)), 7), 0.2F);
      }

   }
}
