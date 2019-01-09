package com.chocolate.chocolateQuest.entity.ai;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import net.minecraft.block.material.Material;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class AIHumanPotion extends EntityAIBase {

   protected World worldObj;
   protected EntityHumanBase owner;
   protected int attackCooldown;


   public AIHumanPotion(EntityHumanBase par1EntityLiving) {
      this.owner = par1EntityLiving;
      this.worldObj = par1EntityLiving.worldObj;
      this.setMutexBits(3);
   }

   public boolean shouldExecute() {
      return this.owner.potionCount > 0 && (double)this.owner.getHealth() <= Math.max((double)this.owner.getMaxHealth() * 0.1D, 6.0D);
   }

   public void startExecuting() {
      this.owner.getNavigator().clearPathEntity();
      this.attackCooldown = 0;
      this.owner.setDefending(true);
   }

   public void resetTask() {
      this.owner.getNavigator().clearPathEntity();
      this.owner.setDefending(false);
      this.owner.moveForwardHuman = 0.0F;
      this.owner.setEating(false);
   }

   public void updateTask() {
      boolean flag = true;
      byte timeTillPotion = 90;
      if(this.owner.getAttackTarget() != null && this.attackCooldown < timeTillPotion) {
         this.owner.getLookHelper().setLookPositionWithEntity(this.owner.getAttackTarget(), 30.0F, 30.0F);
         this.owner.rotationYaw = this.owner.rotationYawHead;
         double ry = Math.toRadians((double)(this.owner.rotationYaw - 180.0F));
         int x = MathHelper.floor_double(this.owner.posX - Math.sin(ry) * 3.0D);
         int z = MathHelper.floor_double(this.owner.posZ + Math.cos(ry) * 3.0D);
         Material mat = this.owner.worldObj.getBlock(x, MathHelper.floor_double(this.owner.posY) - 1, z).getMaterial();
         boolean move = false;
         if(mat != Material.air && mat != Material.lava && mat.isSolid()) {
            move = true;
         } else {
            mat = this.owner.worldObj.getBlock(x, MathHelper.floor_double(this.owner.posY) - 2, z).getMaterial();
            if(mat.isSolid()) {
               move = true;
            }
         }

         if(move) {
            this.owner.moveForwardHuman = -0.25F;
         } else {
            this.owner.moveForwardHuman = 0.0F;
         }

         if(this.owner.getDistanceSqToEntity(this.owner.getAttackTarget()) > 100.0D || !this.owner.getEntitySenses().canSee(this.owner.getAttackTarget())) {
            this.attackCooldown = timeTillPotion;
            this.owner.moveForwardHuman = 0.0F;
         }

         if(this.owner.isCollidedHorizontally) {
            this.attackCooldown += 5;
         }

         double dist = this.owner.getDistanceSqToEntity(this.owner.getAttackTarget());
      }

      ++this.attackCooldown;
      if(this.attackCooldown > timeTillPotion) {
         if(this.owner.onGround) {
            this.owner.motionX = 0.0D;
            this.owner.motionZ = 0.0D;
         }

         if(!this.owner.isEating()) {
            this.owner.setEating(true);
            if(this.owner.isDefending()) {
               this.owner.toogleBlocking();
            }
         }

         this.owner.swingItem();
         if(this.attackCooldown > timeTillPotion + 50) {
            this.owner.worldObj.playSoundAtEntity(this.owner, "random.burp", 0.5F, this.owner.worldObj.rand.nextFloat() * 0.1F + 0.9F);
            this.owner.heal(20.0F);
            --this.owner.potionCount;
            this.owner.setEating(false);
         } else {
            this.owner.worldObj.playSoundAtEntity(this.owner, "random.drink", 0.5F, this.owner.worldObj.rand.nextFloat() * 0.1F + 0.9F);
         }
      }

   }
}
