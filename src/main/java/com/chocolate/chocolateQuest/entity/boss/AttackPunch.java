package com.chocolate.chocolateQuest.entity.boss;

import com.chocolate.chocolateQuest.entity.boss.EntityBaseBoss;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class AttackPunch {

   public double[] swingDest;
   public double[] swingStart;
   public int attackTime;
   public boolean isAttacking;
   byte handFlag;
   EntityBaseBoss owner;
   int attackSpeed;
   private float shoulderHeight;
   private float armLength;
   private float distanceToBody;
   private int angleOffset;
   private int heightOffset;
   public double posX;
   public double posY;
   public double posZ;
   public double prevPosX;
   public double prevPosY;
   public double prevPosZ;


   public AttackPunch(EntityBaseBoss owner, byte handFlag) {
      this.swingDest = new double[3];
      this.swingStart = new double[3];
      this.attackTime = 0;
      this.isAttacking = false;
      this.attackSpeed = 20;
      this.shoulderHeight = 1.0F;
      this.armLength = 1.0F;
      this.distanceToBody = 0.4F;
      this.heightOffset = -1;
      this.owner = owner;
      this.handFlag = handFlag;
   }

   public AttackPunch(EntityBaseBoss owner, byte handFlag, float shoulderHeight, float armScale) {
      this(owner, handFlag);
      this.shoulderHeight = shoulderHeight;
      this.armLength = armScale;
   }

   public int getSpeed() {
      return this.attackSpeed;
   }

   public void setAngle(int angle, int height, float distanceToBody) {
      this.angleOffset = angle;
      this.heightOffset = height;
      this.distanceToBody = distanceToBody;
   }

   public void swingArmTo(double x, double y, double z) {
      this.attackTime = this.getSpeed();
      this.swingStart[0] = this.swingDest[0];
      this.swingStart[1] = this.swingDest[1];
      this.swingStart[2] = this.swingDest[2];
      this.swingDest[0] = x;
      this.swingDest[1] = y;
      this.swingDest[2] = z;
      this.isAttacking = true;
   }

   public void attackTarget(Entity target) {
      double posY = this.owner.posY + (double)this.getShoulderHeight();
      double targetY = target.posY + (double)(target.height / 2.0F);
      double dx = target.posX - this.owner.posX;
      double dy = targetY - posY;
      double dz = target.posZ - this.owner.posZ;
      Vec3 v = Vec3.createVectorHelper(dx, dy, dz);
      v = v.normalize();
      double distToHead = this.owner.getDistance(target.posX, targetY - (double)this.getShoulderHeight(), target.posZ);
      double scale = Math.min(this.getArmLength(), distToHead);
      float armSpeed = 5.0F;
      dx = v.xCoord * scale;
      dy = v.yCoord * scale;
      dz = v.zCoord * scale;
      this.owner.attackToXYZ(this.handFlag, dx, dy, dz);
   }

   public void doPunchDamage() {
      double d = (double)(this.owner.size / 10.0F);
      double posX = this.owner.posX + this.posX;
      double posY = this.owner.posY + (double)this.getShoulderHeight() + this.posY;
      double posZ = this.owner.posZ + this.posZ;
      List list = this.owner.worldObj.getEntitiesWithinAABBExcludingEntity(this.owner, AxisAlignedBB.getBoundingBox(posX - d, posY - d, posZ - d, posX + d, posY + d, posZ + d));

      for(int j = 0; j < list.size(); ++j) {
         Entity entity1 = (Entity)list.get(j);
         if(entity1.canBeCollidedWith() && !this.owner.isEntityEqual(entity1) && this.owner.attackEntityAsMob(entity1)) {
            entity1.motionX += this.swingDest[0] / 4.0D;
            entity1.motionY += this.swingDest[1] / 4.0D;
            entity1.motionZ += this.swingDest[2] / 4.0D;
         }
      }

   }

   public void onUpdate() {
      this.moveHands(this.swingDest, this.swingStart, this.attackTime);
      boolean forceDefensive = false;
      Vec3 v;
      float scale;
      if(this.attackTime > 0) {
         --this.attackTime;
         this.doPunchDamage();
         if(this.isAttacking) {
            if(this.attackTime == 0) {
               v = this.getDefaultPosition();
               scale = (float)(this.getArmLength() * (double)this.distanceToBody);
               this.swingArmTo(v.xCoord * (double)scale, v.yCoord * (double)scale, v.zCoord * (double)scale);
               this.isAttacking = false;
            }
         } else {
            forceDefensive = true;
         }
      } else {
         forceDefensive = true;
      }

      if(forceDefensive) {
         v = this.getDefaultPosition();
         scale = (float)(this.getArmLength() * (double)this.distanceToBody);
         this.swingDest[0] = v.xCoord * (double)scale;
         this.swingDest[1] = v.yCoord * (double)scale;
         this.swingDest[2] = v.zCoord * (double)scale;
      }

   }

   public void moveHands(double[] dest, double[] start, int armSwing) {
      this.posX = this.owner.posX;
      this.posY = this.owner.posY + (double)this.getShoulderHeight();
      this.posZ = this.owner.posZ;
      int swing = this.getSpeed() - armSwing;
      double dx = start[0] + (dest[0] - start[0]) / (double)this.getSpeed() * (double)swing;
      double dy = start[1] + (dest[1] - start[1]) / (double)this.getSpeed() * (double)swing;
      double dz = start[2] + (dest[2] - start[2]) / (double)this.getSpeed() * (double)swing;
      this.setPosition(dx, dy, dz);
   }

   public float getShoulderHeight() {
      return this.owner.size * this.shoulderHeight;
   }

   public double getArmLength() {
      return (double)(this.owner.size * this.armLength);
   }

   public void setPosition(double x, double y, double z) {
      this.prevPosX = this.posX;
      this.posX = x;
      this.prevPosY = this.posY;
      this.posY = y;
      this.prevPosZ = this.posZ;
      this.posZ = z;
   }

   public boolean attackInProgress() {
      return this.attackTime > 0;
   }

   protected Vec3 getDefaultPosition() {
      double posX = -Math.sin(Math.toRadians((double)(this.owner.rotationYawHead + (float)this.angleOffset)));
      double posY = (double)((float)this.heightOffset + MathHelper.cos((float)this.owner.ticksExisted / 20.0F) / 4.0F);
      double posZ = Math.cos(Math.toRadians((double)(this.owner.rotationYawHead + (float)this.angleOffset)));
      return Vec3.createVectorHelper(posX, posY, posZ).normalize();
   }
}
