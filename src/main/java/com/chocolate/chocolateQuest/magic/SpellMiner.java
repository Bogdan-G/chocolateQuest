package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.magic.SpellBase;
import com.chocolate.chocolateQuest.utils.HelperPlayer;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class SpellMiner extends SpellBase {

   private static Map timers = new HashMap(4, 0.75F);


   public void onCastStart(EntityLivingBase shooter, Elements element, ItemStack is) {
      if(shooter instanceof EntityPlayer) {
         int dist = this.getRange(is);
         MovingObjectPosition mop = HelperPlayer.getBlockMovingObjectPositionFromPlayer(shooter.worldObj, shooter, (double)dist, true);
         if(mop != null) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger("x", mop.blockX);
            tag.setInteger("y", mop.blockY);
            tag.setInteger("z", mop.blockZ);
            tag.setInteger("s", mop.sideHit);
            if(shooter.worldObj.isRemote) {
               timers.put(shooter.getCommandSenderName(), Integer.valueOf(0));
            }

            is.stackTagCompound.setTag("miningBlock", tag);
         } else {
            is.stackTagCompound.removeTag("miningBlock");
         }
      }

   }

   public void onUpdate(EntityLivingBase shooter, Elements element, ItemStack is, int angle) {
      if(is.stackTagCompound.hasKey("miningBlock")) {
         EntityPlayer player = (EntityPlayer)shooter;
         World world = player.worldObj;
         NBTTagCompound tag = is.stackTagCompound.getCompoundTag("miningBlock");
         int x = tag.getInteger("x");
         int y = tag.getInteger("y");
         int z = tag.getInteger("z");
         int s = tag.getInteger("s");
         int time = ((Integer)timers.get(shooter.getCommandSenderName())).intValue();
         if(!world.isRemote) {
            Map var10000 = timers;
            String var10001 = shooter.getCommandSenderName();
            ++time;
            var10000.put(var10001, Integer.valueOf(time));
         }

         float mineSpeed = this.getDamage(is) * 5.0F;
         this.blockInteraction(world, player, x, y, z, time, mineSpeed);
         if(this.isAreaMiner()) {
            int expansion = this.getExpansion(is);
            byte dx0;
            byte dz0;
            byte dy0;
            byte dy1;
            byte dz1;
            if(expansion > 0) {
               dx0 = 0;
               dy0 = 0;
               dz0 = 0;
               byte dx1 = 0;
               dy1 = 0;
               dz1 = 0;
               switch(s) {
               case 0:
               case 1:
                  dz0 = 1;
                  dz1 = -1;
                  break;
               case 2:
               case 3:
                  dy0 = 1;
                  dy1 = -1;
                  break;
               case 4:
               case 5:
                  dy0 = 1;
                  dy1 = -1;
               }

               this.blockInteraction(world, player, x + dx0, y + dy0, z + dz0, time, mineSpeed);
               this.blockInteraction(world, player, x + dx1, y + dy1, z + dz1, time, mineSpeed);
            }

            byte var22;
            byte var24;
            if(expansion > 2) {
               dx0 = 0;
               dy0 = 0;
               dz0 = 0;
               var22 = 0;
               var24 = 0;
               dz1 = 0;
               switch(s) {
               case 0:
               case 1:
                  dx0 = 1;
                  var22 = -1;
                  break;
               case 2:
               case 3:
                  dx0 = 1;
                  var22 = -1;
                  break;
               case 4:
               case 5:
                  dz0 = 1;
                  dz1 = -1;
               }

               this.blockInteraction(world, player, x + dx0, y + dy0, z + dz0, time, mineSpeed);
               if(expansion > 2) {
                  this.blockInteraction(world, player, x + var22, y + var24, z + dz1, time, mineSpeed);
               }
            }

            if(expansion > 3) {
               dx0 = 0;
               byte var21 = 0;
               dz0 = 0;
               var22 = 0;
               dy1 = 0;
               dz1 = 0;
               switch(s) {
               case 0:
                  dx0 = 1;
                  var22 = 1;
                  dz0 = 1;
                  dz1 = -1;
                  break;
               case 1:
                  dx0 = 1;
                  var22 = -1;
                  dz0 = 1;
                  dz1 = 1;
                  break;
               case 2:
               case 3:
                  dx0 = 1;
                  var22 = -1;
                  var21 = -1;
                  dy1 = -1;
                  break;
               case 4:
               case 5:
                  dz0 = 1;
                  dz1 = -1;
                  var21 = -1;
                  dy1 = -1;
               }

               this.blockInteraction(world, player, x + dx0, y + var21, z + dz0, time, mineSpeed);
               this.blockInteraction(world, player, x + var22, y + dy1, z + dz1, time, mineSpeed);
            }

            if(expansion > 4) {
               dx0 = 0;
               dy0 = 0;
               byte var23 = 0;
               var22 = 0;
               var24 = 0;
               dz1 = 0;
               switch(s) {
               case 0:
                  dx0 = 1;
                  var22 = 1;
                  var23 = -1;
                  dz1 = 1;
                  break;
               case 1:
                  dx0 = 1;
                  var22 = -1;
                  var23 = -1;
                  dz1 = -1;
                  break;
               case 2:
               case 3:
                  dx0 = 1;
                  var22 = -1;
                  dy0 = 1;
                  var24 = 1;
                  break;
               case 4:
               case 5:
                  var23 = 1;
                  dz1 = -1;
                  dy0 = 1;
                  var24 = 1;
               }

               this.blockInteraction(world, player, x + dx0, y + dy0, z + var23, time, mineSpeed);
               this.blockInteraction(world, player, x + var22, y + var24, z + dz1, time, mineSpeed);
            }
         }
      }

   }

   public void blockInteraction(World world, EntityPlayer player, int x, int y, int z, int useTime, float mineSpeed) {
      Block block = world.getBlock(x, y, z);
      if(block != Blocks.air) {
         if(world.isRemote) {
            int blockID = Block.getIdFromBlock(block);
            world.spawnParticle("blockcrack_" + blockID + "_" + world.getBlockMetadata(x, y, z), (double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, 0.0D, 0.0D, 0.0D);

            for(int i = 0; i < 5; ++i) {
               double dist = (double)player.getRNG().nextFloat();
               world.spawnParticle("blockcrack_" + blockID + "_" + world.getBlockMetadata(x, y, z), (double)x + 0.5D - ((double)x - player.posX) * dist, (double)y + 0.5D - ((double)y - player.posY) * dist, (double)z + 0.5D - ((double)z - player.posZ) * dist, 0.0D, 0.0D, 0.0D);
            }
         } else if(world.canMineBlock(player, x, z, y) && block != Blocks.bedrock && block != Blocks.end_portal && block != Blocks.end_portal_frame && block != Blocks.command_block && block != Blocks.stonebrick && block != ChocolateQuest.dungeonBrick && (float)useTime > block.getBlockHardness(world, x, y, z) * mineSpeed) {
            world.setBlockToAir(x, y, z);
         }
      }

   }

   public void onShoot(EntityLivingBase shooter, Elements element, ItemStack is, int chargeTime) {
      timers.remove(shooter.getCommandSenderName());
   }

   public boolean isProjectile() {
      return super.isProjectile();
   }

   public boolean shouldStartCasting(ItemStack is, EntityLivingBase shooter, Entity target) {
      return false;
   }

   public boolean shouldUpdate() {
      return true;
   }

   public int getCoolDown() {
      return 20;
   }

   public float getCost(ItemStack itemstack) {
      return 0.5F;
   }

   public boolean isAreaMiner() {
      return false;
   }

   public int getRange(ItemStack itemstack) {
      return 10 + this.getExpansion(itemstack) * 4;
   }

}
