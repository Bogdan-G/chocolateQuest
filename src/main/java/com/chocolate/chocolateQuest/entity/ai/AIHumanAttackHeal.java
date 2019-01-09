package com.chocolate.chocolateQuest.entity.ai;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.ai.AIHumanAttackAggressive;
import com.chocolate.chocolateQuest.items.ItemStaffHeal;
import com.chocolate.chocolateQuest.packets.PacketSpawnParticlesAround;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class AIHumanAttackHeal extends AIHumanAttackAggressive {

   private int pathFindingCooldown;


   public AIHumanAttackHeal(EntityHumanBase par1EntityLiving, Class par2Class, float par3, boolean par4, World world) {
      this(par1EntityLiving, par3, par4);
      super.classTarget = par2Class;
      super.worldObj = world;
   }

   public AIHumanAttackHeal(EntityHumanBase par1EntityLiving, float par2, boolean par3) {
      super(par1EntityLiving, par2, par3);
   }

   public boolean shouldExecute() {
      EntityLivingBase var1 = super.owner.getAttackTarget();
      if(var1 == null) {
         return false;
      } else if(!super.owner.isSuitableTargetAlly(var1)) {
         return false;
      } else if(var1.getHealth() < var1.getMaxHealth() && var1 != super.owner) {
         super.entityTarget = var1;
         super.entityPathEntity = super.owner.getNavigator().getPathToEntityLiving(super.entityTarget);
         return super.entityPathEntity != null || super.owner.ridingEntity != null;
      } else {
         super.owner.setAttackTarget((EntityLivingBase)null);
         return false;
      }
   }

   public boolean continueExecuting() {
      EntityLivingBase target = super.owner.getAttackTarget();
      return target == null?false:(!super.owner.isSuitableTargetAlly(target)?false:(target == null?false:(!super.entityTarget.isEntityAlive()?false:(!super.requireSight?!super.owner.getNavigator().noPath():super.owner.isWithinHomeDistance(MathHelper.floor_double(super.entityTarget.posX), MathHelper.floor_double(super.entityTarget.posY), MathHelper.floor_double(super.entityTarget.posZ))))));
   }

   public void startExecuting() {
      super.owner.getNavigator().setPath(super.entityPathEntity, (double)super.moveSpeed);
      this.pathFindingCooldown = 0;
   }

   public void resetTask() {
      super.entityTarget = null;
      super.owner.getNavigator().clearPathEntity();
      super.owner.setDefending(false);
   }

   public void updateTask() {
      super.owner.getLookHelper().setLookPositionWithEntity(super.entityTarget, 30.0F, 30.0F);
      if((super.requireSight || super.owner.getEntitySenses().canSee(super.entityTarget)) && --this.pathFindingCooldown <= 0) {
         this.pathFindingCooldown = 4 + super.owner.getRNG().nextInt(7);
         if(super.owner.ridingEntity instanceof EntityLiving) {
            ((EntityLiving)super.owner.ridingEntity).getNavigator().tryMoveToEntityLiving(super.entityTarget, 1.2000000476837158D);
         }

         super.owner.getNavigator().tryMoveToEntityLiving(super.entityTarget, (double)super.moveSpeed);
      }

      super.owner.attackTime = Math.max(super.owner.attackTime - 1, 0);
      double bounds = this.getMinDistanceToInteract();
      double dist = super.owner.getDistanceSq(super.entityTarget.posX, super.entityTarget.boundingBox.minY, super.entityTarget.posZ);
      if(super.owner.isDefending() && dist <= bounds * 2.0D) {
         super.owner.setDefending(false);
      }

      if(dist <= bounds && super.owner.attackTime <= 0) {
         super.owner.attackTime = super.owner.getAttackSpeed();
         ItemStack staff = null;
         if(super.owner.getHeldItem() != null && super.owner.getHeldItem().getItem() == ChocolateQuest.staffHeal) {
            staff = super.owner.getHeldItem();
            super.owner.swingItem();
         } else if(super.owner.leftHandItem != null && super.owner.leftHandItem.getItem() == ChocolateQuest.staffHeal) {
            staff = super.owner.leftHandItem;
            super.owner.swingLeftHand();
         }

         super.entityTarget.heal(2.0F);
         ItemStaffHeal.applyPotionEffects(staff, super.entityTarget);
         if(!super.owner.worldObj.isRemote) {
            super.entityTarget.worldObj.playSoundEffect((double)((int)super.entityTarget.posX), (double)((int)super.entityTarget.posY), (double)((int)super.entityTarget.posZ), "chocolateQuest:magic", 1.0F, (1.0F + (super.entityTarget.worldObj.rand.nextFloat() - super.entityTarget.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
            PacketSpawnParticlesAround packet = new PacketSpawnParticlesAround((byte)0, super.entityTarget.posX, super.entityTarget.posY + 1.6D, super.entityTarget.posZ);
            ChocolateQuest.channel.sendToAllAround(super.entityTarget, packet, 64);
         }
      }

      if(super.entityTarget.getHealth() >= super.entityTarget.getMaxHealth()) {
         super.owner.setAttackTarget((EntityLivingBase)null);
      }

   }
}
