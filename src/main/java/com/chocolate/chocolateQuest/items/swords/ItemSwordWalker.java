package com.chocolate.chocolateQuest.items.swords;

import com.chocolate.chocolateQuest.items.swords.ItemSwordAndShieldBase;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemSwordWalker extends ItemSwordAndShieldBase {

   public ItemSwordWalker() {
      super(ToolMaterial.EMERALD, "swordEnd");
      this.setMaxDamage(2024);
   }

   public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
      return super.onItemRightClick(itemStack, world, entityPlayer);
   }

   public boolean getIsRepairable(ItemStack itemToRepair, ItemStack itemMaterial) {
      return super.getIsRepairable(itemToRepair, itemMaterial);
   }

   public EnumRarity getRarity(ItemStack itemstack) {
      return EnumRarity.epic;
   }

   public void onBlock(EntityLivingBase blockingEntity, EntityLivingBase attackerEntity, DamageSource ds) {
      if(blockingEntity.getDistanceSqToEntity(attackerEntity) >= 25.0D && !blockingEntity.isSneaking()) {
         World worldObj = blockingEntity.worldObj;
         Random rand = blockingEntity.getRNG();
         if(blockingEntity.ridingEntity == null) {
            double d = attackerEntity.posX + (rand.nextDouble() - 0.5D) * 4.0D;
            double d1 = attackerEntity.posY;
            double d2 = attackerEntity.posZ + (rand.nextDouble() - 0.5D) * 4.0D;
            double d3 = blockingEntity.posX;
            double d4 = blockingEntity.posY;
            double d5 = blockingEntity.posZ;
            blockingEntity.posX = d;
            blockingEntity.posY = d1;
            blockingEntity.posZ = d2;
            boolean succesfulTeleport = false;
            int i = MathHelper.floor_double(blockingEntity.posX);
            int j = MathHelper.floor_double(blockingEntity.posY);
            int k = MathHelper.floor_double(blockingEntity.posZ);
            int var36;
            if(worldObj.blockExists(i, j, k)) {
               boolean l = false;

               while(!l && j > 0) {
                  Block j1 = worldObj.getBlock(i, j - 1, k);
                  if(j1 != Blocks.air && j1.getMaterial().isSolid()) {
                     l = true;
                  } else {
                     --blockingEntity.posY;
                     --j;
                  }
               }

               if(l) {
                  for(var36 = 0; var36 < 10; ++var36) {
                     worldObj.spawnParticle("largesmoke", d3 + (double)rand.nextFloat() - 0.5D, d4, d5 + (double)rand.nextFloat() - 0.5D, 1.0D, 1.0D, 1.0D);
                  }

                  blockingEntity.setPosition(blockingEntity.posX, blockingEntity.posY, blockingEntity.posZ);
                  if(worldObj.getCollidingBoundingBoxes(blockingEntity, blockingEntity.boundingBox).size() == 0 && !worldObj.isAnyLiquid(blockingEntity.boundingBox)) {
                     succesfulTeleport = true;
                  }
               }
            }

            if(!succesfulTeleport) {
               blockingEntity.setPosition(d3, d4, d5);
            } else {
               if(blockingEntity instanceof EntityPlayerMP) {
                  ((EntityPlayerMP)blockingEntity).playerNetServerHandler.setPlayerLocation(blockingEntity.posX, blockingEntity.posY, blockingEntity.posZ, blockingEntity.rotationYaw, blockingEntity.rotationPitch);
               } else {
                  blockingEntity.prevPosX = blockingEntity.posX;
                  blockingEntity.prevPosY = blockingEntity.posY;
                  blockingEntity.prevPosZ = blockingEntity.posX;
                  if(blockingEntity instanceof EntityLiving) {
                     ((EntityLiving)blockingEntity).setAttackTarget(attackerEntity);
                  }
               }

               short var35 = 128;

               for(var36 = 0; var36 < var35; ++var36) {
                  double d6 = (double)var36 / ((double)var35 - 1.0D);
                  float f = (rand.nextFloat() - 0.5F) * 0.2F;
                  float f1 = (rand.nextFloat() - 0.5F) * 0.2F;
                  float f2 = (rand.nextFloat() - 0.5F) * 0.2F;
                  double d7 = d3 + (blockingEntity.posX - d3) * d6 + (rand.nextDouble() - 0.5D) * (double)blockingEntity.width * 2.0D;
                  double var10000 = d4 + (blockingEntity.posY - d4) * d6 + rand.nextDouble() * (double)blockingEntity.height;
                  double d9 = d5 + (blockingEntity.posZ - d5) * d6 + (rand.nextDouble() - 0.5D) * (double)blockingEntity.width * 2.0D;
               }

               worldObj.playSoundEffect(d3, d4, d5, "mob.endermen.portal", 1.0F, 1.0F);
               worldObj.playSoundAtEntity(blockingEntity, "mob.endermen.portal", 1.0F, 1.0F);
            }
         }
      }
   }

   public void addPotionEffectsOnBlockInformation(List list) {
      list.add(EnumChatFormatting.GOLD + StatCollector.translateToLocal("strings.on_block"));
   }
}
