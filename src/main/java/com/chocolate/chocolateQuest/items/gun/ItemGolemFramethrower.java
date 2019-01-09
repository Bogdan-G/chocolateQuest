package com.chocolate.chocolateQuest.items.gun;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.npc.EntityGolemMecha;
import com.chocolate.chocolateQuest.items.gun.ItemGolemWeapon;
import com.chocolate.chocolateQuest.particles.EffectManager;
import com.chocolate.chocolateQuest.utils.HelperDamageSource;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemGolemFramethrower extends ItemGolemWeapon {

   public ItemGolemFramethrower() {
      super.usesStats = false;
   }

   public void shootFromEntity(EntityLivingBase entity, ItemStack is, int angle, Entity target) {
      double armDist = 1.0D;
      float rotationYaw = (float)MathHelper.wrapAngleTo180_double((double)entity.rotationYawHead);
      this.shootFlames(entity, is, angle, target, (double)entity.height * 0.75D, 1.0D, rotationYaw);
   }

   public void shootFlames(EntityLivingBase entity, ItemStack is, int angle, Entity target, double yOff, double armDist, float rotationYaw) {
      World world = entity.worldObj;
      double posX = entity.posX - Math.sin(Math.toRadians((double)(rotationYaw + (float)angle))) * armDist;
      double posY = entity.posY + yOff;
      double posZ = entity.posZ + Math.cos(Math.toRadians((double)(rotationYaw + (float)angle))) * armDist;
      float x = (float)(-Math.sin(Math.toRadians((double)rotationYaw)));
      float z = (float)Math.cos(Math.toRadians((double)rotationYaw));
      double y = -Math.sin(Math.toRadians((double)entity.rotationPitch));
      x = (float)((double)x * (1.0D - Math.abs(y)));
      z = (float)((double)z * (1.0D - Math.abs(y)));
      if(world.isRemote) {
         for(int dist = 0; dist < 8; ++dist) {
            EffectManager.spawnParticle(3, world, posX, posY, posZ, ((double)(x + Item.itemRand.nextFloat()) - 0.5D) / 3.0D, (y + (double)Item.itemRand.nextFloat() - 0.5D) / 8.0D, ((double)(z + Item.itemRand.nextFloat()) - 0.5D) / 3.0D);
         }
      } else {
         byte var31 = 5;
         List list = world.getEntitiesWithinAABBExcludingEntity(entity, entity.boundingBox.addCoord(entity.getLookVec().xCoord * (double)var31, entity.getLookVec().yCoord * (double)var31, entity.getLookVec().zCoord * (double)var31).expand(1.0D, 1.0D, 1.0D));
         Iterator i$ = list.iterator();

         while(i$.hasNext()) {
            Entity e = (Entity)i$.next();
            if(e instanceof EntityLivingBase && !e.isWet() && e != entity.riddenByEntity) {
               double d = posX - e.posX;
               double d2 = posZ - e.posZ;
               double rotDiff = Math.atan2(d, d2);
               rotDiff = rotDiff * 180.0D / 3.141592D;
               rotDiff = -MathHelper.wrapAngleTo180_double(rotDiff - 180.0D);
               rotDiff -= (double)rotationYaw;
               if(Math.abs(rotDiff) < 30.0D) {
                  e.setFire(2);
                  e.attackEntityFrom(HelperDamageSource.causeFireDamage(entity), 1.0F);
               }
            }
         }
      }

   }

   public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityPlayer) {
      entityPlayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
      return itemstack;
   }

   public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityPlayer, int useTime) {}

   public void onUpdate(ItemStack is, World world, Entity entity, int par4, boolean par5) {
      if(entity instanceof EntityGolemMecha) {
         this.shootFromEntity((EntityLivingBase)entity, is, par4, (Entity)null);
      } else if(entity instanceof EntityHumanBase) {
         this.shootFromEntity((EntityLivingBase)entity, is, par4, (Entity)null);
      } else if(entity instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)entity;
         if(player.isUsingItem() && player.getItemInUse() == is) {
            float rotationYaw = (float)MathHelper.wrapAngleTo180_double((double)player.rotationYawHead);
            this.shootFlames((EntityLivingBase)entity, is, par4, (Entity)null, -0.3D, 0.0D, rotationYaw);
         }
      }

   }

   public int getMaxItemUseDuration(ItemStack par1ItemStack) {
      return 72000;
   }

   public EnumAction getItemUseAction(ItemStack par1ItemStack) {
      return EnumAction.bow;
   }

   public boolean isDamageable() {
      return false;
   }

   public float getRange(EntityLivingBase shooter, ItemStack is) {
      return 36.0F;
   }

   public int getCooldown(EntityLivingBase shooter, ItemStack is) {
      return 0;
   }

   public boolean shouldUpdate(EntityLivingBase shooter) {
      return true;
   }
}
