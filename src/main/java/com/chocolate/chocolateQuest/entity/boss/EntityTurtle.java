package com.chocolate.chocolateQuest.entity.boss;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.ai.AIBossAttack;
import com.chocolate.chocolateQuest.entity.ai.AILavaSwim;
import com.chocolate.chocolateQuest.entity.ai.AITargetHurtBy;
import com.chocolate.chocolateQuest.entity.boss.EntityBaseBoss;
import com.chocolate.chocolateQuest.entity.boss.EntityPart;
import com.chocolate.chocolateQuest.entity.boss.EntityTurtlePart;
import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class EntityTurtle extends EntityBaseBoss {

   private EntityTurtlePart[] body;
   double tempRYaw;
   double tempRPich;
   int rotAccel = 0;
   boolean rotDir = true;
   boolean bubbleAttack = false;
   int bubbleCD = 0;
   boolean hurt;
   int[] healPart;
   boolean hurtOnPartSoundFlag = false;
   double tempmx;
   double tempmz;
   double tempmy;


   public EntityTurtle(World par1World) {
      super(par1World);
      super.experienceValue = 5;
      this.body = new EntityTurtlePart[5];
      super.fireDefense = 20;
      super.blastDefense = 20;
      super.limitRotation = true;
   }

   protected void scaleAttributes() {
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2D + (double)super.lvl * 0.005D);
      this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue((double)(6.0F + super.lvl));
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D + (double)super.lvl * 200.0D);
   }

   public void addAITasks() {
      super.tasks.addTask(0, new AILavaSwim(this));
      super.tasks.addTask(1, new EntityAIMoveTowardsTarget(this, 1.0D, 1.0F));
      super.tasks.addTask(2, new AIBossAttack(this, 1.0F, false));
      super.targetTasks.addTask(1, new AITargetHurtBy(this, false));
      super.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
      super.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityMob.class, 0, true));
   }

   public float getMinSize() {
      return 2.0F;
   }

   public float getSizeVariation() {
      return 0.4F;
   }

   protected void resize() {
      this.setSize(super.size, super.size * 0.4F);
   }

   public void initBody() {
      super.initBody();
      float dist = super.size - super.size / 4.0F;
      if(!super.worldObj.isRemote) {
         this.body[0] = new EntityTurtlePart(super.worldObj, 0, 0.0F, super.size - super.size / 3.0F, super.size / 10.0F);
         this.body[0].setMainBody(this);
         this.body[0].setHead();
         this.body[0].setHeartsLife(25 + (int)super.lvl * 125);
         this.scalePart(this.body[0]);
         if(!super.worldObj.isRemote) {
            super.worldObj.spawnEntityInWorld(this.body[0]);
         }

         int[] pos = new int[]{45, -45, 135, -135};

         for(int i = 1; i <= 4; ++i) {
            this.body[i] = new EntityTurtlePart(super.worldObj, i, (float)pos[i - 1], dist);
            this.body[i].setMainBody(this);
            this.body[i].setPosition(super.posX, super.posY, super.posZ);
            this.body[i].setHeartsLife(25 + (int)super.lvl * 50);
            super.worldObj.spawnEntityInWorld(this.body[i]);
         }
      }

   }

   protected boolean isAIEnabled() {
      return !this.isAttacking() && !this.isHealing();
   }

   public void onUpdate() {
      int i;
      if(!super.worldObj.isRemote && this.getHealth() < 50.0F && !this.isHealing() && this.body[0] != null) {
         for(i = 0; i < 5; ++i) {
            if(this.body[i] != null) {
               this.body[i].setHeartsLife(125 + (int)super.lvl * 25);
            }
         }

         this.setHealing(true);
      }

      for(i = 0; i < this.body.length; ++i) {
         if(this.body[i] != null && !this.body[i].isEntityAlive()) {
            this.body[i] = null;
         }
      }

      if(this.isHealing()) {
         if(super.worldObj.isRemote) {
            super.worldObj.spawnParticle("heart", super.posX + super.rand.nextDouble() * 2.0D - 1.0D, super.posY + 1.0D + super.rand.nextDouble() * 2.0D, super.posZ + super.rand.nextDouble() * 2.0D - 1.0D, 0.0D, 1.0D, 0.0D);
         }

         this.heal(2.0F);
         if(this.getHealth() >= (float)(150 + (int)super.lvl * 50)) {
            this.setHealing(false);
         }
      }

      if(this.getAttackTarget() != null) {
         if(super.inWater || this.handleLavaMovement()) {
            if(super.posY + 3.0D < this.getAttackTarget().posY && super.motionY < 0.2D) {
               super.motionY += 0.03D;
            } else if(super.posY + 3.0D > this.getAttackTarget().posY && super.motionY > -0.2D) {
               super.motionY -= 0.03D;
            }
         }
      } else if(super.inWater && super.motionY < 0.1D) {
         super.motionY -= 0.03D;
      }

      super.onUpdate();
      if(this.isAttacking()) {
         this.doAttack();
      }

      if(this.bubbleAttack) {
         this.bubbleRay();
      }

      if(!this.isAttacking() && !this.isHealing()) {
         if(this.hideHead()) {
            if(this.body[0] != null) {
               this.hideHeadInShell();
            }

            for(i = 1; i < 5; ++i) {
               if(this.body[i] != null) {
                  this.body[i].staticPart = true;
               }
            }
         } else {
            for(i = 0; i < 5; ++i) {
               if(this.body[i] != null) {
                  this.body[i].staticPart = true;
               }
            }
         }
      } else {
         this.hidePartsInShell();
         this.hideHeadInShell();
      }

   }

   private void hidePartsInShell() {
      for(int i = 1; i < 5; ++i) {
         if(this.body[i] != null) {
            this.body[i].staticPart = false;
            this.body[i].setPositionAndRotation(super.posX, super.posY, super.posZ, super.rotationYaw, super.rotationPitch);
         }
      }

   }

   private void hideHeadInShell() {
      if(this.body[0] != null) {
         this.body[0].staticPart = false;
         this.body[0].setPositionAndRotation(super.posX, super.posY, super.posZ, super.rotationYaw, super.rotationPitch);
      }

   }

   public void attackEntity(Entity entity, float f) {
      if(!this.isAttacking() && !this.bubbleAttack && !this.isHealing()) {
         float width = super.size * 0.3F;
         width = width * width + super.width * super.width;
         int attackChance = (int)(f - width - entity.width * entity.width);
         attackChance = attackChance < 40?40:(attackChance > 80?80:attackChance);
         if(super.rand.nextInt(attackChance) == 0) {
            if(super.rand.nextInt(3) == 0) {
               this.setAttacking(true);
               this.rotDir = true;
            } else {
               double d = super.posX - entity.posX;
               double var10000 = super.posY - (entity.posY + (double)(entity.height / 2.0F));
               double d2 = super.posZ - entity.posZ;
               float angle = (float)Math.atan2(d, d2);

               for(angle = super.rotationYaw - entity.rotationYaw; angle > 360.0F; angle -= 360.0F) {
                  ;
               }

               while(angle < 0.0F) {
                  angle += 360.0F;
               }

               angle = Math.abs(angle - 180.0F);
               if(angle < 60.0F) {
                  this.startBubble();
               }
            }
         }
      }

   }

   protected boolean limitRotation() {
      return !this.isAttacking();
   }

   public void doAttack() {
      if(this.getAttackTarget() != null) {
         if(this.rotDir) {
            ++this.rotAccel;
         } else {
            --this.rotAccel;
            if(this.rotAccel > 80) {
               super.motionX = this.tempmx;
               super.motionZ = this.tempmz;
               super.motionY = this.tempmy;
            }
         }

         super.rotationYaw += (float)(this.rotAccel / 2);
         if(this.rotAccel >= 100) {
            this.rotDir = false;
         }

         if(this.rotAccel == 50 && this.getHealth() < 200.0F) {
            this.startBubble();
         }

         double d;
         double x;
         double y;
         if(this.rotAccel == 80) {
            EntityLivingBase list = this.getAttackTarget();
            d = super.posX - list.posX;
            x = super.posZ - list.posZ;
            y = super.posY - (list.posY + (double)(list.height / 2.0F));
            this.tempRYaw = Math.atan2(d, x);
            this.tempRPich = Math.atan2(y, (double)MathHelper.sqrt_double(d * d + x * x));
            this.tempmy = -Math.sin(this.tempRPich);
            this.tempmx = -Math.sin(this.tempRYaw);
            this.tempmz = -Math.cos(this.tempRYaw);
         }

         if(this.rotAccel >= 20) {
            List var11 = super.worldObj.getEntitiesWithinAABBExcludingEntity(this, super.boundingBox.expand(1.0D, 1.0D, 1.0D));
            d = 0.0D;

            for(int var12 = 0; var12 < var11.size(); ++var12) {
               Entity entity1 = (Entity)var11.get(var12);
               if(entity1.canBeCollidedWith() && !entity1.isEntityEqual(this) && entity1 != super.riddenByEntity && (!(entity1 instanceof EntityThrowable) || ((EntityThrowable)entity1).getThrower() != this)) {
                  AxisAlignedBB var13 = entity1.boundingBox;
                  this.attackEntityAsMob(entity1);
               }
            }

            if(this.rotAccel % 10 == 0) {
               x = super.posX -= (double)(MathHelper.cos(super.rotationYaw / 180.0F * 3.1415927F) * super.width);
               y = super.posY += (double)super.height;
               double z = super.posZ -= (double)(MathHelper.sin(super.rotationYaw / 180.0F * 3.1415927F) * super.width);
               EntityBaseBall ball = new EntityBaseBall(super.worldObj, this, 7);
               ball.setPosition(x, y, z);
               super.worldObj.spawnEntityInWorld(ball);
            }
         }

         if(this.rotAccel == 0) {
            this.setAttacking(false);
         }

      }
   }

   public void startBubble() {
      EntityLivingBase e = this.getAttackTarget();
      double px = super.posX - e.posX;
      double pz = super.posZ - e.posZ;
      double py = super.posY - e.posY;
      this.tempRYaw = Math.atan2(px, pz);
      this.tempRPich = Math.atan2(py, (double)MathHelper.sqrt_double(px * px + pz * pz));
      this.tempmy = -Math.sin(this.tempRPich);
      this.tempmx = -Math.sin(this.tempRYaw);
      this.tempmz = -Math.cos(this.tempRYaw);
      this.bubbleAttack = true;
      this.setHideHead(true);
   }

   public void bubbleRay() {
      if(this.bubbleCD >= 20) {
         this.bubbleCD = 0;
         super.worldObj.spawnEntityInWorld(new EntityBaseBall(super.worldObj, this, 7));
         this.bubbleAttack = false;
         this.bubbleCD = 0;
         if(!this.isAttacking() && super.rand.nextInt(3) == 0) {
            this.bubbleAttack = true;
         } else {
            this.setHideHead(false);
         }
      } else {
         ++this.bubbleCD;
      }

   }

   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
      if(this.isHealing()) {
         par2 /= 2.0F;
      }

      boolean flag = super.attackEntityFrom(par1DamageSource, par2);
      if(this.body != null) {
         for(int i = 0; i < 5; ++i) {
            if(this.body[i] != null) {
               if(this.body[i].isDead) {
                  this.hurt = true;
                  this.body[i] = null;
               }
            } else {
               this.hurt = true;
            }
         }
      }

      return flag;
   }

   public boolean attackFromPart(DamageSource par1DamageSource, float par2, EntityPart part) {
      this.hurtOnPartSoundFlag = true;
      return this.attackEntityFrom(par1DamageSource, par2);
   }

   public int getTotalArmorValue() {
      return 0;
   }

   protected void dropFewItems(boolean flag, int i) {
      super.dropFewItems(flag, i);
      if(!super.worldObj.isRemote) {
         int scalesDropped = 2;
         int partsLeft = 0;
         if(this.body[0] == null) {
            scalesDropped += 6;
            ++partsLeft;
         }

         for(int k = 1; k < this.body.length; ++k) {
            if(this.body[k] == null) {
               scalesDropped += 4;
               ++partsLeft;
            }
         }

         super.worldObj.spawnEntityInWorld(new EntityItem(super.worldObj, super.posX, super.posY, super.posZ, new ItemStack(ChocolateQuest.material, scalesDropped, 1)));
      }

      if(flag && (super.rand.nextInt(5) == 0 || super.rand.nextInt(1 + i) > 0)) {
         this.dropItem(Items.diamond, 2);
      }

   }

   protected boolean canDespawn() {
      return false;
   }

   public boolean isEntityEqual(Entity par1Entity) {
      boolean flag = false;
      if(this.body != null) {
         for(int i = 0; i < this.body.length && !flag; ++i) {
            flag = this.body[i] == par1Entity;
         }
      }

      return this == par1Entity || flag;
   }

   public boolean canBreatheUnderwater() {
      return true;
   }

   protected boolean isValidLightLevel() {
      int i = MathHelper.floor_double(super.posX);
      int j = MathHelper.floor_double(super.boundingBox.minY);
      int k = MathHelper.floor_double(super.posZ);
      if(super.worldObj.getSavedLightValue(EnumSkyBlock.Sky, i, j, k) > super.rand.nextInt(32)) {
         return false;
      } else {
         int l = super.worldObj.getBlockLightValue(i, j, k);
         if(super.worldObj.isThundering()) {
            int i1 = super.worldObj.skylightSubtracted;
            super.worldObj.skylightSubtracted = 10;
            l = super.worldObj.getBlockLightValue(i, j, k);
            super.worldObj.skylightSubtracted = i1;
         }

         return l <= super.rand.nextInt(8);
      }
   }

   protected String getHurtSound() {
      if(this.hurtOnPartSoundFlag) {
         this.hurtOnPartSoundFlag = false;
         return super.getHurtSound();
      } else {
         return "mob.blaze.hit";
      }
   }

   protected void entityInit() {
      super.entityInit();
      super.dataWatcher.addObject(16, Byte.valueOf((byte)0));
   }

   public boolean isHealing() {
      return super.dataWatcher.getWatchableObjectByte(16) == 0;
   }

   public void setHealing(boolean healing) {
      super.dataWatcher.updateObject(16, healing?Byte.valueOf((byte)0):Byte.valueOf((byte)1));
   }

   public void setPart(EntityPart entityTurtlePart, int partID) {
      this.body[partID] = (EntityTurtlePart)entityTurtlePart;
      this.scalePart(entityTurtlePart);
   }

   public boolean hideHead() {
      return this.getAnimFlag(1);
   }

   public void setHideHead(boolean b) {
      this.setAnimFlag(1, b);
   }

   protected void scalePart(EntityPart part) {
      float scale = super.size * 0.3F;
      part.setSize(scale, scale);
      part.setPosition(super.posX, super.posY, super.posZ);
   }

   public boolean canBePushed() {
      return false;
   }

   public boolean shouldMoveToEntity(double d1, Entity target) {
      return !this.isAttacking();
   }

   public EntityTurtlePart[] getBossParts() {
      return this.body;
   }

   protected int getDropMaterial() {
      return 1;
   }
}
