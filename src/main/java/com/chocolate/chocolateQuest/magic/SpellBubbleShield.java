package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.EntityTracker;
import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.magic.SpellBase;
import com.chocolate.chocolateQuest.packets.PacketSpawnParticlesAround;
import cpw.mods.fml.common.registry.IThrowableEntity;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SpellBubbleShield extends SpellBase {

   public void onCastStart(EntityLivingBase shooter, Elements element, ItemStack is) {
      if(!shooter.worldObj.isRemote) {
         EntityTracker e = new EntityTracker(shooter.worldObj, shooter, this.getBubbleRange(is));
         e.setPosition(shooter.posX, shooter.posY, shooter.posZ);
         shooter.worldObj.spawnEntityInWorld(e);
      }

   }

   public void onUpdate(EntityLivingBase shooter, Elements element, ItemStack is, int angle) {
      applyShieldEffect(shooter, (double)this.getBubbleRange(is));
   }

   public static int applyShieldEffect(EntityLivingBase shooter, double expand) {
      World worldObj = shooter.worldObj;
      int shield = 0;
      List list = worldObj.getEntitiesWithinAABBExcludingEntity(shooter, shooter.boundingBox.expand(expand, expand, expand));

      for(int j = 0; j < list.size(); ++j) {
         Entity entity1 = (Entity)list.get(j);
         if(shooter.getDistanceSq(entity1.posX, entity1.posY - 1.0D, entity1.posZ) < expand * expand && shooter.getDistanceSq(entity1.prevPosX, entity1.prevPosY - 1.0D, entity1.prevPosZ) >= expand * expand) {
            boolean isProjectile = false;
            if(entity1 instanceof IThrowableEntity) {
               IThrowableEntity packet = (IThrowableEntity)entity1;
               shield += 20;
               isProjectile = true;
            }

            if(entity1 instanceof EntityArrow) {
               EntityArrow var10 = (EntityArrow)entity1;
               shield += 30;
               isProjectile = true;
            }

            if(isProjectile && !worldObj.isRemote) {
               entity1.setDead();
               PacketSpawnParticlesAround var11 = new PacketSpawnParticlesAround((byte)5, entity1.posX, entity1.posY, entity1.posZ);
               ChocolateQuest.channel.sendToAllAround(shooter, var11, 64);
            }
         }
      }

      return shield;
   }

   public boolean isSupportSpell() {
      return true;
   }

   public int getRange(ItemStack itemstack) {
      return 32;
   }

   public boolean shouldUpdate() {
      return true;
   }

   public int getCastingTime() {
      return 200;
   }

   public int getCoolDown() {
      return 0;
   }

   public float getCost(ItemStack itemstack) {
      return 0.5F;
   }

   public int getBubbleRange(ItemStack is) {
      return 3 + this.getExpansion(is);
   }

   public boolean shouldStartCasting(ItemStack is, EntityLivingBase shooter, Entity target) {
      return true;
   }
}
