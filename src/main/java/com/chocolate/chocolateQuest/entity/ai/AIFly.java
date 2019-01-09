package com.chocolate.chocolateQuest.entity.ai;

import com.chocolate.chocolateQuest.entity.boss.EntityWyvern;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class AIFly extends EntityAIBase {

   private EntityWyvern owner;
   protected EntityLivingBase entityTarget;
   int timeFollowedByEntity = 0;
   int randomCounter = 0;
   int fireBreathTime = 0;
   int fireballTime = 0;
   int chargeTime;


   public AIFly(EntityWyvern par1EntityLiving) {
      this.owner = par1EntityLiving;
      this.setMutexBits(4);
      par1EntityLiving.getNavigator().setCanSwim(true);
   }

   public boolean shouldExecute() {
      EntityLivingBase var1 = this.owner.getAttackTarget();
      if(var1 == null) {
         return false;
      } else if(!var1.isEntityAlive()) {
         this.owner.setAttackTarget((EntityLivingBase)null);
         return false;
      } else if(var1 == this.owner.riddenByEntity) {
         this.owner.setAttackTarget((EntityLivingBase)null);
         return false;
      } else {
         this.entityTarget = var1;
         return true;
      }
   }

   public boolean continueExecuting() {
      EntityLivingBase var1 = this.owner.getAttackTarget();
      return var1 == null?false:(var1 != this.entityTarget?false:(!this.entityTarget.isEntityAlive()?false:this.owner.isWithinHomeDistance(MathHelper.floor_double(this.entityTarget.posX), MathHelper.floor_double(this.entityTarget.posY), MathHelper.floor_double(this.entityTarget.posZ))));
   }

   public int getChargeTime() {
      return this.chargeTime;
   }

   public void updateTask() {
      this.owner.getLookHelper().setLookPositionWithEntity(this.entityTarget, 30.0F, 30.0F);
      double dist = (double)this.owner.getDistanceToEntity(this.entityTarget);
      boolean canSee = this.owner.getEntitySenses().canSee(this.entityTarget);
      double angle = Math.abs(BDHelper.getAngleBetweenEntities(this.owner, this.entityTarget));
      double rotDif = Math.abs(angle - Math.abs(MathHelper.wrapAngleTo180_double((double)this.owner.rotationYaw)));
      if(!this.owner.onGround) {
         this.owner.getNavigator().clearPathEntity();
         if(rotDif < 15.0D + dist) {
            this.chargeTime = Math.min(this.chargeTime + 4, 120);
         } else {
            this.chargeTime = Math.max(this.chargeTime - 1, 0);
         }

         boolean maxHeight = true;
         int currentSpeed;
         if(this.entityTarget.posY + (double)this.entityTarget.height - 1.0D + Math.min(8.0D, dist / 3.0D) - this.owner.posY <= 0.0D && (!this.owner.isCollidedHorizontally || this.owner.onGround)) {
            this.owner.rotationPitch = 0.0F;
            this.owner.motionY = -0.3D;
         } else {
            int ry = MathHelper.floor_double(this.owner.posX + this.owner.motionX * 10.0D);
            int blockY = MathHelper.floor_double(this.owner.posY);
            currentSpeed = MathHelper.floor_double(this.owner.posZ + this.owner.motionZ * 10.0D);
            if((this.owner.worldObj.canBlockSeeTheSky(ry, blockY, currentSpeed) || this.owner.worldObj.isAirBlock(ry, blockY + 4, currentSpeed)) && this.owner.posY < 250.0D) {
               if(this.owner.rotationPitch > -0.3F) {
                  this.owner.rotationPitch -= 0.001F;
               }

               this.owner.motionY = 0.3D;
            } else {
               if(this.owner.rotationPitch < 0.3F) {
                  this.owner.rotationPitch += 0.001F;
               }

               this.owner.motionY = -0.3D;
            }
         }

         double var14 = Math.toRadians((double)(this.owner.rotationYaw - 180.0F));
         currentSpeed = Math.max(this.chargeTime / 2, 10);
         float moveSpeed = (float)this.owner.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
         this.owner.motionX = Math.sin(var14) * (double)currentSpeed / 30.0D * (double)moveSpeed;
         this.owner.motionZ = -Math.cos(var14) * (double)currentSpeed / 30.0D * (double)moveSpeed;
         float maxRot = (float)(180.0D - rotDif) / 30.0F;
         if(this.owner.rotationYawHead + maxRot > this.owner.rotationYaw) {
            this.owner.rotationYaw += maxRot;
         } else if(this.owner.rotationYawHead - maxRot < this.owner.rotationYaw) {
            this.owner.rotationYaw -= maxRot;
         }

         if(dist > 20.0D && this.owner.getRNG().nextInt(50) == 0) {
            this.shootFireballAtTarget();
         }
      } else {
         this.owner.rotationPitch = 0.0F;
         if(dist > 4.9D && this.owner.getNavigator().noPath()) {
            this.owner.getNavigator().tryMoveToEntityLiving(this.entityTarget, 1.0D);
         }
      }

      if(rotDif > 30.0D) {
         ++this.timeFollowedByEntity;
         if(this.timeFollowedByEntity > 120) {
            if(dist < 8.0D) {
               this.fireBreath();
            } else if(this.owner.getRNG().nextInt(50) == 0) {
               this.shootFireballAtTarget();
            }
         }
      } else {
         this.timeFollowedByEntity = 0;
      }

      if(dist < 8.0D && rotDif < 20.0D) {
         ++this.randomCounter;
         if(this.randomCounter > 20) {
            this.fireBreath();
         }
      } else {
         this.randomCounter = 0;
      }

      if(dist < 4.9D && canSee) {
         if(this.owner.attackTime <= 0) {
            this.owner.attackEntityAsMob(this.entityTarget);
            this.owner.swingItem();
            this.owner.attackTime = 40;
         } else if(this.owner.attackTime > 0) {
            --this.owner.attackTime;
         }
      }

   }

   public void fireBreath() {
      this.owner.openMouth();
      World world = this.owner.worldObj;
      double x = this.entityTarget.posX - this.owner.posX;
      double y = this.entityTarget.boundingBox.minY + (double)(this.entityTarget.height / 2.0F) - (this.owner.posY + (double)(this.owner.height / 2.0F));
      double z = this.entityTarget.posZ - this.owner.posZ;
      this.owner.fireBreath(x, y, z);
      world.playSoundEffect((double)((int)this.owner.posX), (double)((int)this.owner.posY), (double)((int)this.owner.posZ), "fire.fire", 4.0F, (1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);
   }

   public void shootFireballAtTarget() {
      this.owner.openMouth();
      World world = this.owner.worldObj;
      double x = this.entityTarget.posX - this.owner.posX;
      double y = this.entityTarget.boundingBox.minY + (double)(this.entityTarget.height / 2.0F) - (this.owner.posY + 2.0D);
      double z = this.entityTarget.posZ - this.owner.posZ;
      this.owner.shootFireball(x, y, z);
      world.playSoundEffect((double)((int)this.owner.posX), (double)((int)this.owner.posY), (double)((int)this.owner.posZ), "mob.ghast.fireball", 4.0F, (1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);
   }
}
