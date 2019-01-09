package com.chocolate.chocolateQuest.entity.boss;

import com.chocolate.chocolateQuest.entity.boss.EntityBaseBoss;
import com.chocolate.chocolateQuest.entity.boss.EntityPart;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityPartRidable extends EntityPart {

   public EntityPartRidable(World par1World) {
      super(par1World);
   }

   public EntityPartRidable(World world, EntityBaseBoss entity, int partID, float rotationYawOffset, float distanceToMainBody, float heightOffset) {
      super(world, entity, partID, rotationYawOffset, distanceToMainBody, heightOffset);
   }

   public AxisAlignedBB getBoundingBox() {
      return super.boundingBox;
   }

   public AxisAlignedBB getCollisionBox(Entity entity) {
      return entity.boundingBox;
   }

   public void applyEntityCollision(Entity entity) {
      super.applyEntityCollision(entity);
   }

   public void onCollideWithPlayer(EntityPlayer par1EntityPlayer) {
      super.onCollideWithPlayer(par1EntityPlayer);
      if(par1EntityPlayer.posY - super.posY - (double)super.height > 0.0D) {
         par1EntityPlayer.motionX = super.posX - super.prevPosX;
         par1EntityPlayer.motionY += Math.max(0.0D, super.motionY / 2.0D);
         par1EntityPlayer.motionZ = super.posZ - super.prevPosZ;
      }

   }
}
