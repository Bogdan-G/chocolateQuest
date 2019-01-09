package com.chocolate.chocolateQuest.entity.ai;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.utils.Vec3I;
import net.minecraft.block.material.Material;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class AIFirefighter extends EntityAIBase {

   World worldObj;
   EntityHumanBase owner;
   float moveSpeed;
   PathEntity entityPathEntity;
   Vec3I nearestFire;
   protected int field_75445_i;


   public AIFirefighter(EntityHumanBase par1EntityLiving, float par2, boolean par3) {
      this.owner = par1EntityLiving;
      this.worldObj = par1EntityLiving.worldObj;
      this.moveSpeed = par2;
      this.setMutexBits(3);
   }

   public boolean shouldExecute() {
      int x = MathHelper.floor_double(this.owner.posX);
      int y = MathHelper.floor_double(this.owner.posY);
      int z = MathHelper.floor_double(this.owner.posZ);
      boolean flag = false;
      if(this.nearestFire == null) {
         for(int i = -8; i < 14; ++i) {
            for(int k = -8; k < 14; ++k) {
               for(int j = -2; j < 4; ++j) {
                  if(this.worldObj.getBlock(x + i, y + j, z + k).getMaterial() == Material.fire) {
                     if(this.nearestFire != null) {
                        if(this.owner.getDistanceSq((double)(x + i), (double)(y + j), (double)(z + k)) < this.owner.getDistanceSq((double)this.nearestFire.xCoord, (double)this.nearestFire.yCoord, (double)this.nearestFire.zCoord)) {
                           this.nearestFire = new Vec3I(x + i, y + j, k + z);
                        }
                     } else {
                        this.nearestFire = new Vec3I(x + i, y + j, k + z);
                     }
                  }
               }
            }
         }
      }

      return this.nearestFire != null;
   }

   public void startExecuting() {}

   public void resetTask() {}

   public void updateTask() {
      if(this.nearestFire != null) {
         if(this.owner.getDistanceSq((double)this.nearestFire.xCoord, (double)this.nearestFire.yCoord, (double)this.nearestFire.zCoord) < 16.0D) {
            this.worldObj.setBlockToAir(this.nearestFire.xCoord, this.nearestFire.yCoord, this.nearestFire.zCoord);
            this.owner.swingItem();
            this.nearestFire = null;
         } else if(this.owner.getNavigator().getPathToXYZ((double)this.nearestFire.xCoord, (double)this.nearestFire.yCoord, (double)this.nearestFire.zCoord) != null) {
            this.owner.getNavigator().tryMoveToXYZ((double)this.nearestFire.xCoord, (double)this.nearestFire.yCoord, (double)this.nearestFire.zCoord, (double)this.moveSpeed);
         } else {
            this.nearestFire = null;
         }
      }

   }
}
