package com.chocolate.chocolateQuest.entity.ai;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.ai.AIControlledBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.MathHelper;

public class AIControlledPath extends AIControlledBase {

   boolean pathDirection = false;
   int currentPathPoint = -1;


   public AIControlledPath(EntityHumanBase owner) {
      super(owner);
   }

   public boolean shouldExecute() {
      return super.owner.path != null;
   }

   public void updateTask() {
      if(super.owner.path != null) {
         if(this.currentPathPoint == -1) {
            this.setNearestPathPoint();
         } else if(this.currentPathPoint < super.owner.path.length) {
            int x = MathHelper.floor_double(super.owner.posX);
            int y = MathHelper.floor_double(super.owner.posY);
            int z = MathHelper.floor_double(super.owner.posZ);
            x -= super.owner.path[this.currentPathPoint].xCoord;
            y -= super.owner.path[this.currentPathPoint].yCoord;
            z -= super.owner.path[this.currentPathPoint].zCoord;
            double dist = (double)(x * x + y * y + z * z);
            if(super.owner.ridingEntity != null) {
               dist -= (double)(super.owner.ridingEntity.height * 2.0F + super.owner.ridingEntity.width * 2.0F);
            }

            if(dist < 4.0D) {
               this.nextPathPoint();
            } else {
               this.tryMoveToXYZ((double)super.owner.path[this.currentPathPoint].xCoord, (double)super.owner.path[this.currentPathPoint].yCoord, (double)super.owner.path[this.currentPathPoint].zCoord, 0.7F);
            }
         } else {
            this.setNearestPathPoint();
         }
      }

      super.updateTask();
   }

   public PathNavigate getNavigator() {
      return super.owner.ridingEntity != null && super.owner.ridingEntity instanceof EntityLiving && ((EntityLiving)super.owner.ridingEntity).getNavigator() != null?((EntityLiving)super.owner.ridingEntity).getNavigator():super.owner.getNavigator();
   }

   public void nextPathPoint() {
      if(this.currentPathPoint >= super.owner.path.length - 1) {
         this.pathDirection = false;
         this.currentPathPoint = super.owner.path.length - 1;
      }

      if(this.currentPathPoint == 0) {
         this.pathDirection = true;
         ++this.currentPathPoint;
      } else if(this.pathDirection) {
         ++this.currentPathPoint;
      } else {
         --this.currentPathPoint;
      }

   }

   public void setNearestPathPoint() {
      int closestPoint = -1;
      double minDistance = Double.MAX_VALUE;

      for(int i = 0; i < super.owner.path.length; ++i) {
         int x = MathHelper.floor_double(super.owner.posX);
         int y = MathHelper.floor_double(super.owner.posY);
         int z = MathHelper.floor_double(super.owner.posZ);
         x -= super.owner.path[i].xCoord;
         y -= super.owner.path[i].yCoord;
         z -= super.owner.path[i].zCoord;
         double dist = (double)(x * x + y * y + z * z);
         if(dist < minDistance) {
            closestPoint = i;
            minDistance = dist;
         }
      }

      this.currentPathPoint = closestPoint;
   }
}
