package com.chocolate.chocolateQuest.entity.boss;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.boss.AttackKick;
import com.chocolate.chocolateQuest.entity.boss.EntityBaseBoss;
import com.chocolate.chocolateQuest.packets.PacketEntityAnimation;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class AttackKickQuadruped extends AttackKick {

   public int kickTimeBack;
   public byte kickTypeBack;
   float legOffset = 0.5F;


   public AttackKickQuadruped(EntityBaseBoss owner) {
      super(owner);
   }

   public void onUpdate() {
      if(super.kickTime > 0) {
         --super.kickTime;
         if(super.kickTime == this.getSpeed() / 4) {
            this.doKickDamage(super.kickType);
         }
      }

      if(this.kickTimeBack > 0) {
         --this.kickTimeBack;
         if(this.kickTimeBack == this.getSpeed() / 4) {
            this.doKickDamage(this.kickTypeBack);
         }
      }

   }

   public void attackTarget(Entity entity) {
      int angle = (int)MathHelper.wrapAngleTo180_double(BDHelper.getAngleBetweenEntities(super.owner, entity) - (double)super.owner.rotationYaw);
      byte type = -1;
      if(angle < 90 && angle > -90) {
         if(super.kickTime > 0) {
            return;
         }

         if(angle < 80 && angle > 0) {
            type = 3;
         } else if(angle > -80 && angle < 0) {
            type = 1;
         }
      } else {
         if(this.kickTimeBack > 0) {
            return;
         }

         if(angle < -100) {
            type = 2;
         } else if(angle > 100) {
            type = 4;
         }
      }

      if(type != -1) {
         this.kick(type);
      }

   }

   public void kick(byte type) {
      if(!super.owner.worldObj.isRemote) {
         PacketEntityAnimation packet = new PacketEntityAnimation(super.owner.getEntityId(), type);
         ChocolateQuest.channel.sendToAllAround(super.owner, packet, 64);
      }

      if(type != 1 && type != 3) {
         this.kickTimeBack = this.getSpeed();
         this.kickTypeBack = type;
      } else {
         super.kickTime = this.getSpeed();
         super.kickType = type;
      }

   }

   public boolean isAttackInProgress() {
      return super.kickTime > 0 || this.kickTimeBack > 0;
   }

   public double getAngleBetweenEntities(Entity entity, Entity target, byte kickType) {
      float dist = super.owner.width * this.legOffset;
      float rotationYaw = super.owner.rotationYaw * 3.141592F / 180.0F;
      double despX = -Math.sin((double)rotationYaw) * (double)dist;
      double despZ = Math.cos((double)rotationYaw) * (double)dist;
      if(kickType == 4 || kickType == 2) {
         despX = -despX;
         despZ = -despZ;
      }

      double d = entity.posX + despX - target.posX;
      double d2 = entity.posZ + despZ - target.posZ;
      double angle = Math.atan2(d, d2);
      angle = angle * 180.0D / 3.141592D;
      angle = -MathHelper.wrapAngleTo180_double(angle - 180.0D);
      return angle;
   }
}
