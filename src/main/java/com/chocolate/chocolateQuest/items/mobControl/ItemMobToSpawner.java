package com.chocolate.chocolateQuest.items.mobControl;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.block.BlockMobSpawnerTileEntity;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.ai.EntityParty;
import com.chocolate.chocolateQuest.entity.npc.EntityGolemMecha;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemMobToSpawner extends Item {

   public ItemMobToSpawner() {
      this.setMaxStackSize(1);
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {
      super.itemIcon = iconRegister.registerIcon("chocolatequest:" + this.getUnlocalizedName().replace("item.", ""));
   }

   public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
      if(!entity.worldObj.isRemote) {
         if(entity instanceof EntityGolemMecha && entity.riddenByEntity instanceof EntityHumanBase) {
            entity = entity.riddenByEntity;
         }

         int x = MathHelper.floor_double(entity.posX);
         int y = MathHelper.floor_double(entity.posY);
         int z = MathHelper.floor_double(entity.posZ);
         if(player.isSneaking()) {
            player.worldObj.setBlock(x, y, z, Blocks.mob_spawner);
            TileEntityMobSpawner saved1 = new TileEntityMobSpawner();
            NBTTagCompound mobData = new NBTTagCompound();
            if(entity.writeToNBTOptional(mobData)) {
               MobSpawnerBaseLogic logic = saved1.func_145881_a();
               logic.setEntityName(mobData.getString("id"));
               mobData = new NBTTagCompound();
               entity.writeToNBT(mobData);
               mobData.removeTag("Age");
               mobData.removeTag("UUIDMost");
               mobData.removeTag("UUIDLeast");
               mobData.removeTag("Dimension");
               mobData.removeTag("Pos");
               NBTTagCompound logicTag = new NBTTagCompound();
               logic.writeToNBT(logicTag);
               logicTag.setTag("SpawnData", mobData);
               logic.readFromNBT(logicTag);
               player.worldObj.setTileEntity(x, y, z, saved1);
            }

            return true;
         }

         if(entity instanceof EntityHumanBase) {
            return saveToSpawner(x, y, z, (EntityHumanBase)entity);
         }

         boolean saved = this.saveEntityToSpawner(x, y, z, entity);
         if(saved) {
            entity.setDead();
            return true;
         }
      }

      return false;
   }

   public boolean saveEntityToSpawner(int x, int y, int z, Entity entity) {
      World world = entity.worldObj;
      if(world.getBlock(x, y, z) != Blocks.air) {
         ++y;
      }

      world.setBlock(x, y, z, ChocolateQuest.spawner);
      BlockMobSpawnerTileEntity te = new BlockMobSpawnerTileEntity();
      NBTTagCompound tag = new NBTTagCompound();
      boolean wrote = entity.writeToNBTOptional(tag);
      if(wrote) {
         te.mobNBT = tag;
         te.mob = -1;
         world.setTileEntity(x, y, z, te);
         return true;
      } else {
         return false;
      }
   }

   public static boolean saveToSpawner(int x, int y, int z, EntityHumanBase human) {
      World world = human.worldObj;
      if(world.getBlock(x, y, z) != Blocks.air) {
         ++y;
      }

      world.setBlock(x, y, z, ChocolateQuest.spawner);
      BlockMobSpawnerTileEntity te = new BlockMobSpawnerTileEntity();
      NBTTagCompound tag = getHumanSaveTag(x, y, z, human);
      te.mobNBT = tag;
      te.mob = -1;
      world.setTileEntity(x, y, z, te);
      return true;
   }

   public static NBTTagCompound getHumanSaveTagAndKillIt(int x, int y, int z, EntityHumanBase human) {
      NBTTagCompound tag = getHumanSaveTag(x, y, z, human);
      EntityParty p = human.party;
      if(p != null) {
         for(int i = 0; i < p.getMembersLength(); ++i) {
            if(p.getMember(i) != null) {
               EntityHumanBase member = p.getMember(i);
               if(member.ridingEntity != null) {
                  member.ridingEntity.setDead();
               }

               member.setDead();
            }
         }
      }

      if(human.ridingEntity != null) {
         human.ridingEntity.setDead();
      }

      human.setDead();
      return tag;
   }

   public static NBTTagCompound getHumanSaveTag(int x, int y, int z, EntityHumanBase human) {
      NBTTagCompound tag = new NBTTagCompound();
      human.writeToNBTOptional(tag);
      human.writeEntityToSpawnerNBT(tag, x, y, z);
      return tag;
   }

   public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10) {
      if(!world.isRemote) {
         TileEntity te = world.getTileEntity(x, y, z);
         if(te instanceof BlockMobSpawnerTileEntity) {
            BlockMobSpawnerTileEntity teSpawner = (BlockMobSpawnerTileEntity)te;
            teSpawner.spawnEntity();
         }
      }

      return super.onItemUse(is, player, world, x, y, z, par7, par8, par9, par10);
   }

   public boolean onBlockStartBreak(ItemStack itemstack, int X, int Y, int Z, EntityPlayer player) {
      this.onItemUse(itemstack, player, player.worldObj, X, Y, Z, 0, 0.0F, 0.0F, 0.0F);
      return super.onBlockStartBreak(itemstack, X, Y, Z, player);
   }
}
