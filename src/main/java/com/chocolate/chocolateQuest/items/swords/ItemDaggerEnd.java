package com.chocolate.chocolateQuest.items.swords;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.items.swords.ItemBaseDagger;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemDaggerEnd extends ItemBaseDagger {

   public ItemDaggerEnd() {
      super(ToolMaterial.EMERALD, 3);
      this.setMaxDamage(2048);
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {
      super.itemIcon = iconRegister.registerIcon("chocolatequest:daggerNinja");
   }

   public int getMaxDamage() {
      return 2048;
   }

   public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
      if(entityPlayer.isSneaking()) {
         world.playSoundAtEntity(entityPlayer, "mob.endermen.portal", 1.0F, 1.0F);

         for(int x = 0; x < 6; ++x) {
            world.spawnParticle("portal", entityPlayer.posX + (double)Item.itemRand.nextFloat() - 0.5D, entityPlayer.posY + (double)Item.itemRand.nextFloat() - 0.5D, entityPlayer.posZ + (double)Item.itemRand.nextFloat() - 0.5D, (double)(Item.itemRand.nextFloat() - 0.5F), (double)(Item.itemRand.nextFloat() - 0.5F), (double)(Item.itemRand.nextFloat() - 0.5F));
         }

         double var12 = -Math.sin(Math.toRadians((double)entityPlayer.rotationYaw));
         double z = Math.cos(Math.toRadians((double)entityPlayer.rotationYaw));
         double y = -Math.sin(Math.toRadians((double)entityPlayer.rotationPitch));
         var12 *= 1.0D - Math.abs(y);
         z *= 1.0D - Math.abs(y);
         byte dist = 4;
         entityPlayer.setPosition(entityPlayer.posX + var12 * (double)dist, entityPlayer.posY + y * (double)dist, entityPlayer.posZ + z * (double)dist);
         itemStack.damageItem(1, entityPlayer);

         for(int i = 0; i < 6; ++i) {
            world.spawnParticle("portal", entityPlayer.posX + (double)Item.itemRand.nextFloat() - 0.5D, entityPlayer.posY + (double)Item.itemRand.nextFloat() - 0.5D, entityPlayer.posZ + (double)Item.itemRand.nextFloat() - 0.5D, (double)(Item.itemRand.nextFloat() - 0.5F), (double)(Item.itemRand.nextFloat() - 0.5F), (double)(Item.itemRand.nextFloat() - 0.5F));
         }

         itemStack.damageItem(1, entityPlayer);
      } else {
         itemStack.damageItem(1, entityPlayer);
      }

      entityPlayer.addPotionEffect(new PotionEffect(Potion.invisibility.id, 100, 5));
      return super.onItemRightClick(itemStack, world, entityPlayer);
   }

   public boolean getIsRepairable(ItemStack itemToRepair, ItemStack itemMaterial) {
      return super.getIsRepairable(itemToRepair, itemMaterial);
   }

   public EnumRarity getRarity(ItemStack itemstack) {
      return EnumRarity.epic;
   }

   public int getEntityLifespan(ItemStack itemStack, World world) {
      return 24000;
   }

   public boolean hasSkill() {
      return true;
   }

   public int doSkill(EntityHumanBase human) {
      World worldObj = human.worldObj;
      EntityLivingBase entity = human.getAttackTarget();
      Random rand = human.getRNG();
      double d = entity.posX + (rand.nextDouble() - 0.5D) * 8.0D;
      double d1 = entity.posY;
      double d2 = entity.posZ + (rand.nextDouble() - 0.5D) * 8.0D;
      double d3 = human.posX;
      double d4 = human.posY;
      double d5 = human.posZ;
      human.posX = d;
      human.posY = d1;
      human.posZ = d2;
      boolean flag = false;
      int i = MathHelper.floor_double(human.posX);
      int j = MathHelper.floor_double(human.posY);
      int k = MathHelper.floor_double(human.posZ);
      int var34;
      if(worldObj.blockExists(i, j, k)) {
         boolean l = false;

         while(!l && j > 0) {
            Block j1 = worldObj.getBlock(i, j - 1, k);
            if(j1 != Blocks.air && j1.getMaterial().isSolid()) {
               l = true;
            } else {
               --human.posY;
               --j;
            }
         }

         if(l) {
            for(var34 = 0; var34 < 10; ++var34) {
               worldObj.spawnParticle("largesmoke", d3 + (double)rand.nextFloat() - 0.5D, d4, d5 + (double)rand.nextFloat() - 0.5D, 1.0D, 1.0D, 1.0D);
            }

            human.setPosition(human.posX, human.posY, human.posZ);
            if(worldObj.getCollidingBoundingBoxes(human, human.boundingBox).size() == 0 && !worldObj.isAnyLiquid(human.boundingBox)) {
               flag = true;
            }
         }
      }

      if(!flag) {
         human.setPosition(d3, d4, d5);
         return 0;
      } else {
         short var35 = 128;

         for(var34 = 0; var34 < var35; ++var34) {
            double d6 = (double)var34 / ((double)var35 - 1.0D);
            float f = (rand.nextFloat() - 0.5F) * 0.2F;
            float f1 = (rand.nextFloat() - 0.5F) * 0.2F;
            float f2 = (rand.nextFloat() - 0.5F) * 0.2F;
            double d7 = d3 + (human.posX - d3) * d6 + (rand.nextDouble() - 0.5D) * (double)human.width * 2.0D;
            double var10000 = d4 + (human.posY - d4) * d6 + rand.nextDouble() * (double)human.height;
            double d9 = d5 + (human.posZ - d5) * d6 + (rand.nextDouble() - 0.5D) * (double)human.width * 2.0D;
         }

         worldObj.playSoundEffect(d3, d4, d5, "mob.endermen.portal", 1.0F, 1.0F);
         worldObj.playSoundAtEntity(human, "mob.endermen.portal", 1.0F, 1.0F);
         return 100;
      }
   }
}
