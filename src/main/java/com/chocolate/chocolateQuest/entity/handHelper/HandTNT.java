package com.chocolate.chocolateQuest.entity.handHelper;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.handHelper.HandHelper;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class HandTNT extends HandHelper {

   int explosionTimer = 0;
   final ItemStack whiteBlock;


   public HandTNT(EntityHumanBase owner, ItemStack itemStack) {
      super(owner, itemStack);
      this.whiteBlock = new ItemStack(Blocks.quartz_block);
   }

   public void onUpdate() {
      super.onUpdate();
      if(super.owner.isSwingInProgress(this) || this.explosionTimer > 0) {
         ++this.explosionTimer;
         if(this.explosionTimer > 30) {
            super.owner.worldObj.createExplosion(super.owner, super.owner.posX, super.owner.posY, super.owner.posZ, 5.0F, true);
            super.owner.setDead();
         }
      }

      if(super.owner.currentPos != null) {
         if(super.owner.getDistanceSq((double)super.owner.currentPos.xCoord, (double)super.owner.currentPos.yCoord, (double)super.owner.currentPos.zCoord) < 4.0D) {
            super.owner.worldObj.playSoundAtEntity(super.owner, "minecraft:random.fuse", 0.2F, 1.0F);
            if(this.explosionTimer == 0) {
               ++this.explosionTimer;
            }
         } else {
            this.explosionTimer = 0;
         }
      }

   }

   public void attackEntity(Entity entity) {
      if(this.explosionTimer == 0) {
         super.owner.worldObj.playSoundAtEntity(entity, "minecraft:random.fuse", 0.2F, 1.0F);
         super.owner.swingHand(this);
      }

   }

   public boolean isTwoHanded() {
      return true;
   }

   public ItemStack getItem() {
      return this.explosionTimer % 3 == 1?this.whiteBlock:super.getItem();
   }
}
