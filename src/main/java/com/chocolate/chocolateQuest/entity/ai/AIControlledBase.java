package com.chocolate.chocolateQuest.entity.ai;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.utils.Vec4I;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class AIControlledBase extends EntityAIBase {

   protected EntityHumanBase owner;
   private Vec4I position;
   private int pathFindingCooldown;
   public int pathBlockedTime = 0;


   public AIControlledBase(EntityHumanBase owner) {
      this.owner = owner;
      this.setMutexBits(3);
   }

   public boolean shouldExecute() {
      return true;
   }

   public boolean stayInFormation() {
      if(this.owner.getLeader() != null) {
         double x = 0.0D;
         double y = 0.0D;
         double z = 0.0D;
         EntityLivingBase leader = this.owner.getLeader();
         Vec3 absPosition = leader.getLookVec();
         float angle = (float)this.owner.partyPositionAngle * 3.1416F / 180.0F;
         double cos = (double)MathHelper.cos(angle);
         double sin = (double)MathHelper.sin(angle);
         int dist = this.owner.partyDistanceToLeader;
         x = leader.posX + (absPosition.xCoord * cos - absPosition.zCoord * sin) * (double)dist;
         y = leader.posY;
         z = leader.posZ + (absPosition.zCoord * cos + absPosition.xCoord * sin) * (double)dist;
         this.tryMoveToXYZ(x, y, z, 1.15F);
         return true;
      } else {
         return false;
      }
   }

   public boolean tryMoveToXYZ(double x, double y, double z, float moveSpeed) {
      if(this.owner.ridingEntity != null) {
         ++moveSpeed;
      }

      --this.pathFindingCooldown;
      if(this.pathFindingCooldown > 0 && !this.getNavigator().noPath()) {
         return true;
      } else {
         this.pathFindingCooldown = 5 + this.owner.getRNG().nextInt(10);
         if(this.getNavigator().tryMoveToXYZ(x, y, z, (double)moveSpeed) && !this.getNavigator().noPath()) {
            this.position = null;
            return true;
         } else if(this.position == null) {
            Vec3 var10 = Vec3.createVectorHelper(x - this.owner.posX, y - this.owner.posY, z - this.owner.posZ);
            var10 = var10.normalize();
            var10 = Vec3.createVectorHelper(var10.xCoord * 10.0D + this.owner.posX, var10.yCoord * 10.0D + this.owner.posY, var10.zCoord * 10.0D + this.owner.posZ);
            if(this.owner.worldObj.isAirBlock(MathHelper.floor_double(var10.xCoord), MathHelper.floor_double(var10.yCoord - 1.0D), MathHelper.floor_double(var10.zCoord))) {
               Vec3 direction = Vec3.createVectorHelper(x, y, z);
               var10 = RandomPositionGenerator.findRandomTargetBlockTowards(this.owner, 10, 3, direction);
               if(var10 == null) {
                  return false;
               }
            }

            this.position = new Vec4I(MathHelper.floor_double(var10.xCoord), MathHelper.floor_double(var10.yCoord), MathHelper.floor_double(var10.zCoord), 0);
            return true;
         } else if(this.getNavigator().tryMoveToXYZ((double)this.position.xCoord, (double)this.position.yCoord, (double)this.position.zCoord, (double)moveSpeed)) {
            PathPoint path = this.getNavigator().getPath().getFinalPathPoint();
            if(this.owner.getDistanceSq((double)path.xCoord, (double)path.yCoord, (double)path.zCoord) <= 1.0D) {
               this.pathFindingCooldown = 10;
               this.position = null;
            }

            return true;
         } else {
            if(this.owner.canTeleport() && (this.owner.onGround || this.owner.isInWater())) {
               this.owner.setPosition((double)this.position.xCoord, (double)(this.position.yCoord + 1), (double)this.position.zCoord);
            }

            this.position = null;
            return false;
         }
      }
   }

   public PathNavigate getNavigator() {
      return this.owner.ridingEntity != null && this.owner.ridingEntity instanceof EntityLiving && ((EntityLiving)this.owner.ridingEntity).getNavigator() != null?((EntityLiving)this.owner.ridingEntity).getNavigator():this.owner.getNavigator();
   }
}
