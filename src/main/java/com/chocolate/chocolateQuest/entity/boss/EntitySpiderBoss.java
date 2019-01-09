package com.chocolate.chocolateQuest.entity.boss;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.ai.AIBossAttack;
import com.chocolate.chocolateQuest.entity.ai.AITargetHurtBy;
import com.chocolate.chocolateQuest.entity.boss.AttackPunch;
import com.chocolate.chocolateQuest.entity.boss.EntityBaseBoss;
import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.entity.projectile.EntityHookShoot;
import com.chocolate.chocolateQuest.packets.PacketAttackToXYZ;
import com.chocolate.chocolateQuest.particles.EffectManager;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntitySpiderBoss extends EntityBaseBoss {

   public AttackPunch rightHand;
   public AttackPunch leftHand;
   public int attackTimeLeft = 0;
   public int attackTimeRight = 0;
   public int attackSpeed = 10;
   int projectileCoolDown;
   EntityHookShoot web;
   EntityHookShoot escapeWeb;
   boolean hasPoison = true;


   public EntitySpiderBoss(World par1World) {
      super(par1World);
      super.ridableBB = false;
      this.leftHand = new AttackPunch(this, (byte)5, 0.2F, 2.0F);
      this.leftHand.setAngle(-55, 2, 0.4F);
      this.rightHand = new AttackPunch(this, (byte)6, 0.2F, 2.0F);
      this.rightHand.setAngle(55, 2, 0.4F);
      super.projectileDefense = 10;
      super.magicDefense = -20;
      super.limitRotation = true;
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(46.0D);
   }

   protected void scaleAttributes() {
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2D + (double)super.lvl * 0.03D);
      this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(10.0D + (double)super.lvl * 0.8D);
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(200.0D + (double)super.lvl * 250.0D);
   }

   public void addAITasks() {
      super.tasks.addTask(0, new EntityAISwimming(this));
      super.tasks.addTask(2, new AIBossAttack(this, 1.0F, false));
      super.targetTasks.addTask(1, new AITargetHurtBy(this, false));
      super.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
      super.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityMob.class, 0, true));
   }

   public float getMinSize() {
      return 0.6F;
   }

   public float getSizeVariation() {
      return 0.4F;
   }

   public void onUpdate() {
      if(super.worldObj.isRemote) {
         if(this.getHealth() < this.getMaxHealth() / 3.0F) {
            float setHookDead = super.rotationYaw * 3.1416F / 180.0F;
            double scale = (double)(super.width / 2.0F);
            double posX = super.posX - (double)MathHelper.sin(setHookDead) * scale;
            double posY = super.posY + (double)super.height * 0.3D;
            double posZ = super.posZ + (double)MathHelper.cos(setHookDead) * scale;
            float desp = super.width * 0.05F;
            EffectManager.spawnParticle(2, super.worldObj, posX + super.rand.nextGaussian() * (double)desp, posY + 0.2D, posZ + super.rand.nextGaussian() * (double)desp, 0.0D + (double)(super.rand.nextFloat() / 8.0F), 0.3D + (double)(super.rand.nextFloat() / 4.0F), 0.0D + (double)(super.rand.nextFloat() / 8.0F));
         }
      } else if(this.getAttackTarget() == null && this.escapeWeb == null && !super.worldObj.canBlockSeeTheSky(MathHelper.floor_double(super.posX), MathHelper.floor_double(super.posY), MathHelper.floor_double(super.posZ))) {
         this.escapeWeb = new EntityHookShoot(super.worldObj, this, 3);
         this.escapeWeb.motionY = 2.6D;
         this.escapeWeb.motionX = 0.0D;
         this.escapeWeb.motionZ = 0.0D;
         super.worldObj.spawnEntityInWorld(this.escapeWeb);
      }

      if(this.web != null && this.web.isReeling()) {
         boolean setHookDead1 = false;
         if(this.web.hookedEntity != null) {
            if(this.getAttackTarget() != this.web.hookedEntity && this.web.hookedEntity instanceof EntityLivingBase) {
               this.setAttackTarget((EntityLivingBase)this.web.hookedEntity);
            }
         } else if(this.getAttackTarget() != null && this.getDistanceSqToEntity(this.web) < this.getDistanceSqToEntity(this.getAttackTarget())) {
            setHookDead1 = true;
         }

         if(this.web.ticksExisted > 100 || setHookDead1) {
            this.web.setDead();
            this.web = null;
         }
      }

      if(this.escapeWeb != null) {
         if(!this.escapeWeb.isReeling()) {
            this.escapeWeb.motionY -= 0.2D;
         }

         if(this.escapeWeb.getRadio() > super.size) {
            this.escapeWeb.setRadio(this.escapeWeb.getRadio() - 1.0F);
         }

         if(this.escapeWeb.ticksExisted > 100 && super.onGround || this.escapeWeb.hookedEntity != null || this.escapeWeb.ticksExisted > 300) {
            this.escapeWeb.setDead();
         }

         if(!this.escapeWeb.isEntityAlive()) {
            this.escapeWeb = null;
         }

         if(super.isCollidedHorizontally || super.isCollidedVertically) {
            super.motionY = 0.0D;
         }
      }

      if(!super.isDead) {
         this.rightHand.onUpdate();
         this.leftHand.onUpdate();
      }

      super.onUpdate();
   }

   protected boolean interact(EntityPlayer player) {
      if(this.hasPoison && this.getHealth() < this.getMaxHealth() / 3.0F) {
         float angle;
         for(angle = super.rotationYaw - player.rotationYaw; angle > 360.0F; angle -= 360.0F) {
            ;
         }

         while(angle < 0.0F) {
            angle += 360.0F;
         }

         angle = Math.abs(angle - 180.0F);
         if(angle < 45.0F && player.getHeldItem() != null && player.getHeldItem().getItem() == Items.glass_bottle) {
            --player.getHeldItem().stackSize;
            if(player.getHeldItem().stackSize == 0) {
               player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
            }

            for(int i = 0; i < player.inventory.getSizeInventory(); ++i) {
               if(player.inventory.getStackInSlot(i) == null) {
                  player.inventory.setInventorySlotContents(i, new ItemStack(ChocolateQuest.material, 1, 6));
                  break;
               }
            }

            this.hasPoison = false;
         }
      }

      return super.interact(player);
   }

   public void attackToXYZ(byte arm, double x, double y, double z) {
      if(!super.worldObj.isRemote) {
         PacketAttackToXYZ packet = new PacketAttackToXYZ(this.getEntityId(), arm, x, y, z);
         ChocolateQuest.channel.sendToAllAround(this, packet, 64);
      }

      if(arm == 5) {
         this.leftHand.swingArmTo(x, y, z);
      } else {
         this.rightHand.swingArmTo(x, y, z);
      }

   }

   public void attackEntity(Entity target, float par2) {
      if(!this.attackInProgress()) {
         if(this.projectileCoolDown < 60) {
            ++this.projectileCoolDown;
            if(this.projectileCoolDown == 54) {
               this.swingItem();
            }
         } else {
            this.projectileCoolDown = super.rand.nextInt(30);
            if(super.rand.nextInt(3) == 0 && this.web == null) {
               this.shootWeb();
            } else if(!super.worldObj.isRemote) {
               EntityBaseBall posY = new EntityBaseBall(super.worldObj, this, 0, 0);
               posY.setThrowableHeading(target.posX - super.posX, target.posY - super.posY, target.posZ - super.posZ, 1.0F, 0.0F);
               posY.posY -= (double)(super.height / 2.0F);
               super.worldObj.spawnEntityInWorld(posY);
            }
         }
      }

      if(super.ticksExisted % (this.attackSpeed + 1) == 0 && !super.worldObj.isRemote && (!this.leftHand.attackInProgress() || !this.rightHand.attackInProgress())) {
         double var10000 = super.posY + (double)super.size;
         int angle = (int)MathHelper.wrapAngleTo180_double(BDHelper.getAngleBetweenEntities(this, target) - (double)super.rotationYaw);
         double targetY = target.posY + (double)target.height;
         double dx = super.posX - target.posX;
         double dy = super.posY + (double)super.size - targetY;
         double dz = super.posZ - target.posZ;
         double distToHead = dx * dx + dy * dy + dz * dz - (double)(super.width * super.width);
         if(distToHead < (this.rightHand.getArmLength() + 1.0D) * (this.rightHand.getArmLength() + 1.0D)) {
            boolean targetDown = super.posY - target.posY - (double)target.height > 0.0D;
            boolean handFlag = super.rand.nextBoolean();
            boolean ARM_LEFT = false;
            boolean ARM_RIGHT = true;
            handFlag = super.rand.nextBoolean();
            if(!targetDown) {
               if(!handFlag) {
                  if(this.leftHand.attackInProgress() || angle > 20) {
                     handFlag = true;
                  }
               } else if(this.rightHand.attackInProgress() || angle < -20) {
                  handFlag = false;
               }
            }

            if(angle > -110 && angle < 110 || targetDown) {
               if(!handFlag && !this.leftHand.attackInProgress()) {
                  this.leftHand.attackTarget(target);
               } else if(handFlag && !this.rightHand.attackInProgress()) {
                  this.rightHand.attackTarget(target);
               }
            }
         }
      }

      super.attackEntity(target, par2);
   }

   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
      if(this.escapeWeb == null && super.rand.nextInt(10) == 0) {
         this.shootEscapeWeb();
      }

      return super.attackEntityFrom(par1DamageSource, par2);
   }

   public void shootWeb() {
      if(!super.worldObj.isRemote) {
         boolean pos = false;
         if(this.web != null) {
            this.web.setDead();
            this.web = null;
         }

         EntityLivingBase t = this.getAttackTarget();
         this.web = new EntityHookShoot(super.worldObj, this, 3);
         this.web.setThrowableHeading(t.posX - super.posX, t.posY - super.posY - (double)(super.height / 2.0F), t.posZ - super.posZ, 1.0F, 0.0F);
         super.worldObj.spawnEntityInWorld(this.web);
      }

   }

   public void shootEscapeWeb() {
      if(!super.worldObj.isRemote) {
         boolean pos = false;
         if(this.escapeWeb != null) {
            this.escapeWeb.setDead();
            this.escapeWeb = null;
         }

         this.escapeWeb = new EntityHookShoot(super.worldObj, this, 5);
         this.escapeWeb.motionY = 2.5D;
         this.escapeWeb.motionX = super.rand.nextGaussian();
         this.escapeWeb.motionZ = super.rand.nextGaussian();
         super.worldObj.spawnEntityInWorld(this.escapeWeb);
      }

   }

   public void setInWeb() {}

   public boolean attackInProgress() {
      return this.leftHand.isAttacking || this.rightHand.isAttacking || super.isSwingInProgress;
   }

   public boolean isSneaking() {
      return true;
   }

   public double getArmLength() {
      return (double)super.size;
   }

   protected void resize() {
      this.setSize(super.size * 1.4F, super.size);
   }

   protected void fall(float par1) {}

   protected String getLivingSound() {
      return "mob.spider.say";
   }

   protected String getHurtSound() {
      return "mob.spider.say";
   }

   protected String getDeathSound() {
      return "mob.spider.death";
   }

   protected int getDropMaterial() {
      return 4;
   }
}
