package com.chocolate.chocolateQuest.entity.boss;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.boss.EntityBaseBoss;
import com.chocolate.chocolateQuest.packets.PacketEntityAnimation;
import com.chocolate.chocolateQuest.utils.BDHelper;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class AttackKick {

   public int kickTime;
   public byte kickType;
   public int kickSpeed = 30;
   EntityBaseBoss owner;


   public AttackKick(EntityBaseBoss owner) {
      this.owner = owner;
   }

   public void setSpeed(int speed) {
      this.kickSpeed = speed;
   }

   public int getSpeed() {
      return this.kickSpeed;
   }

   public void onUpdate() {
      if(this.kickTime > 0) {
         --this.kickTime;
         if(this.kickTime == this.getSpeed() / 3) {
            this.doKickDamage(this.kickType);
         }
      }

   }

   public void attackTarget(Entity entity) {
      int angle = (int)MathHelper.wrapAngleTo180_double(BDHelper.getAngleBetweenEntities(this.owner, entity) - (double)this.owner.rotationYaw);
      boolean type = false;
      byte type1;
      if(angle > 0) {
         if(angle < 90) {
            type1 = 3;
         } else {
            type1 = 4;
         }
      } else if(angle > -90) {
         type1 = 1;
      } else {
         type1 = 2;
      }

      this.kick(type1);
   }

   public void kick(byte type) {
      if(!this.owner.worldObj.isRemote) {
         PacketEntityAnimation packet = new PacketEntityAnimation(this.owner.getEntityId(), type);
         ChocolateQuest.channel.sendToAllAround(this.owner, packet, 64);
      }

      if(this.kickTime == 0) {
         this.kickTime = this.getSpeed();
         this.kickType = type;
      }

   }

   public void doKickDamage(byte kickType) {
      double d = Math.max(2.5D, (double)this.owner.width * 0.8D);
      List list = this.owner.worldObj.getEntitiesWithinAABBExcludingEntity(this.owner, this.owner.boundingBox.expand(d, 0.0D, d).addCoord(0.0D, -2.0D, 0.0D));
      short min = -180;
      short max = 180;
      switch(kickType) {
      case 1:
         min = -70;
         max = 5;
         break;
      case 2:
         min = -180;
         max = -70;
         break;
      case 3:
         min = -5;
         max = 70;
         break;
      case 4:
         min = 70;
         max = 180;
      }

      for(int j = 0; j < list.size(); ++j) {
         Entity entity1 = (Entity)list.get(j);
         if(entity1.canBeCollidedWith() && !this.owner.isEntityEqual(entity1) && entity1 != this.owner.riddenByEntity) {
            int angle = (int)MathHelper.wrapAngleTo180_double(this.getAngleBetweenEntities(this.owner, entity1, kickType) - (double)this.owner.rotationYaw);
            if(angle >= min && angle <= max && this.owner.attackEntityAsMob(entity1)) {
               float rotation = this.owner.rotationYaw;
               if(kickType == 2 || kickType == 4) {
                  rotation -= 180.0F;
               }

               rotation = rotation * 3.141592F / 180.0F;
               float dist = Math.max(1.5F, this.owner.width / 3.0F);
               double dx = -Math.sin((double)rotation) * (double)dist;
               double dy = (double)(this.owner.size / 20.0F);
               double dz = Math.cos((double)rotation) * (double)dist;
               entity1.motionX += dx;
               entity1.motionY += dy;
               entity1.motionZ += dz;
            }
         }
      }

   }

   public double getAngleBetweenEntities(Entity entity, Entity target, byte kickType) {
      double d = entity.posX - target.posX;
      double d2 = entity.posZ - target.posZ;
      double angle = Math.atan2(d, d2);
      angle = angle * 180.0D / 3.141592D;
      angle = -MathHelper.wrapAngleTo180_double(angle - 180.0D);
      return angle;
   }

   public boolean isAttackInProgress() {
      return this.kickTime > 0;
   }
}
