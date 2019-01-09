package com.chocolate.chocolateQuest.entity.mob;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanMob;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import com.chocolate.chocolateQuest.misc.EnumVoice;
import com.chocolate.chocolateQuest.particles.EffectManager;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityHumanWalker extends EntityHumanMob {

   public EntityHumanWalker(World world) {
      super(world);
      super.maxStamina = 60 + super.worldObj.difficultySetting.getDifficultyId() * 20;
   }

   public DungeonMonstersBase getMonsterType() {
      return ChocolateQuest.walker;
   }

   public void onLivingUpdate() {
      super.onLivingUpdate();
      if(super.worldObj.isRemote && super.ticksExisted % 10 == 0) {
         byte eyeOff = 25;
         double eyeDist = 0.38D;
         double yLook = 1.0D - Math.abs(this.getLookVec().yCoord);
         double x = super.posX - Math.sin(Math.toRadians((double)(super.rotationYawHead + (float)eyeOff))) * eyeDist * yLook + super.motionX;
         double z = super.posZ + Math.cos(Math.toRadians((double)(super.rotationYawHead + (float)eyeOff))) * eyeDist * yLook + super.motionZ;
         double y = super.posY + ((double)this.getEyeHeight() + 0.2D) * (double)this.getSizeModifier() + this.getLookVec().yCoord;
         EffectManager.spawnParticle(7, super.worldObj, x, y, z, 0.4D, 0.8D, 1.0D);
         byte eyeOff1 = -25;
         x = super.posX - Math.sin(Math.toRadians((double)(super.rotationYawHead + (float)eyeOff1))) * eyeDist * yLook + super.motionX;
         z = super.posZ + Math.cos(Math.toRadians((double)(super.rotationYawHead + (float)eyeOff1))) * eyeDist * yLook + super.motionZ;
         EffectManager.spawnParticle(7, super.worldObj, x, y, z, 0.4D, 0.8D, 1.0D);
      }

   }

   public ItemStack getDiamondArmorForSlot(int slot) {
      ItemStack is;
      switch(slot) {
      case 1:
         is = new ItemStack(ChocolateQuest.diamondBoots);
         break;
      case 2:
         is = new ItemStack(ChocolateQuest.diamondPants);
         break;
      case 3:
         is = new ItemStack(ChocolateQuest.diamondPlate);
         break;
      default:
         is = new ItemStack(ChocolateQuest.diamondHelmet);
      }

      BDHelper.colorArmor(is, this.getMonsterType().getColor());
      return is;
   }

   public ItemStack getIronArmorForSlot(int slot) {
      ItemStack is;
      switch(slot) {
      case 1:
         is = new ItemStack(ChocolateQuest.ironBoots);
         break;
      case 2:
         is = new ItemStack(ChocolateQuest.ironPants);
         break;
      case 3:
         is = new ItemStack(ChocolateQuest.ironPlate);
         break;
      default:
         is = new ItemStack(ChocolateQuest.ironHelmet);
      }

      BDHelper.colorArmor(is, this.getMonsterType().getColor());
      return is;
   }

   protected String getLivingSound() {
      return EnumVoice.ENDERMEN.say;
   }

   protected String getHurtSound() {
      return EnumVoice.ENDERMEN.hurt;
   }

   protected String getDeathSound() {
      return EnumVoice.ENDERMEN.death;
   }

   public int getInteligence() {
      return 2;
   }

   protected ItemStack getMainDrop() {
      return new ItemStack(Items.ender_pearl);
   }

   public ItemStack getSecondaryDrop() {
      return new ItemStack(Items.dye, 1, 4);
   }
}
