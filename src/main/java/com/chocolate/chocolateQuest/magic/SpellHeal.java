package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.magic.SpellProjectile;
import com.chocolate.chocolateQuest.utils.HelperPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class SpellHeal extends SpellProjectile {

   public void onShoot(EntityLivingBase shooter, Elements element, ItemStack is, int chargeTime) {
      World world = shooter.worldObj;
      Object target = null;
      if(shooter instanceof EntityPlayer) {
         MovingObjectPosition type = HelperPlayer.getMovingObjectPositionFromPlayer(shooter, world, (double)(15 + this.getExpansion(is) * 10));
         if(type != null) {
            target = type.entityHit;
         }
      } else {
         target = this.getRecomendedTarget((EntityHumanBase)shooter);
      }

      if(!world.isRemote && target instanceof EntityLivingBase) {
         int type1 = this.getType();
         EntityBaseBall ball = new EntityBaseBall(world, (EntityLivingBase)target, 9, 2, element);
         ball.setDamageMultiplier(this.getDamage(is) * 0.25F);
         ball.setPosition(shooter.posX, shooter.posY + (double)shooter.getEyeHeight(), shooter.posZ);
         world.spawnEntityInWorld(ball);
      }

   }

   public boolean shouldStartCasting(ItemStack is, EntityLivingBase shooter, Entity target) {
      return shooter instanceof EntityHumanBase?this.getRecomendedTarget((EntityHumanBase)shooter) != null:false;
   }

   public EntityLivingBase getRecomendedTarget(EntityHumanBase shooter) {
      EntityHumanBase entity = null;
      double minHealth = 99999.0D;
      if(shooter.party != null) {
         for(int health = 0; health < shooter.party.getMembersLength(); ++health) {
            EntityHumanBase health1 = shooter.party.getMember(health);
            if(health1 != null) {
               double health2 = (double)health1.getHealth();
               if(health2 < (double)health1.getMaxHealth() && health2 < minHealth) {
                  minHealth = health2;
                  entity = health1;
               }
            }
         }

         EntityHumanBase var10 = shooter.party.getLeader();
         double var11 = (double)var10.getHealth();
         if(var11 < (double)var10.getMaxHealth() && var11 < minHealth) {
            entity = var10;
         }
      } else {
         double var9 = (double)shooter.getHealth();
         if(var9 < (double)shooter.getMaxHealth()) {
            return shooter;
         }
      }

      return entity;
   }

   public float getCost(ItemStack itemstack) {
      return 12.0F;
   }

   public int getRange(ItemStack itemstack) {
      return 64;
   }

   public int getCoolDown() {
      return 15;
   }

   public boolean isSupportSpell() {
      return true;
   }
}
