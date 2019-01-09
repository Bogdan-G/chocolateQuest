package com.chocolate.chocolateQuest.block;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.block.BlockArmorStandTileEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockArmorStand extends BlockContainer {

   int armorSlots = 4;


   public BlockArmorStand() {
      super(Material.wood);
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister iconRegister) {
      super.blockIcon = iconRegister.registerIcon("chocolatequest:armorStand");
   }

   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
      ItemStack currentItem = player.getCurrentEquippedItem();
      BlockArmorStandTileEntity stand = (BlockArmorStandTileEntity)world.getTileEntity(x, y, z);
      if(player.isSneaking()) {
         player.openGui(ChocolateQuest.instance, 2, world, x, y, z);
         return true;
      } else {
         int i;
         ItemStack stack;
         if(currentItem != null && currentItem.getItem() instanceof ItemArmor) {
            i = 3 - ((ItemArmor)currentItem.getItem()).armorType;
            stack = stand.cargoItems[i];
            stand.cargoItems[i] = currentItem;
            player.inventory.setInventorySlotContents(player.inventory.currentItem, stack);
            return true;
         } else if(stand.cargoItems == null) {
            return super.onBlockActivated(world, x, y + 1, z, player, par6, par7, par8, par9);
         } else {
            for(i = 0; i < this.armorSlots; ++i) {
               stack = stand.cargoItems[i];
               ItemStack currentItemArmor = player.inventory.armorInventory[i];
               if(currentItem != null || !player.capabilities.isCreativeMode) {
                  stand.cargoItems[i] = currentItemArmor;
               }

               player.inventory.armorInventory[i] = stack;
            }

            return true;
         }
      }
   }

   public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemstack) {
      super.onBlockPlacedBy(world, x, y, z, entity, itemstack);
      BlockArmorStandTileEntity te = (BlockArmorStandTileEntity)this.createNewTileEntity(world, itemstack.getItemDamage());
      te.rotation = (int)(entity.rotationYaw - 180.0F);
      world.setTileEntity(x, y, z, te);
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {
      super.blockIcon = iconRegister.registerIcon("chocolatequest:armorStand");
   }

   public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
      this.setBlockBounds(0.2F, 0.0F, 0.2F, 0.8F, 1.8F, 0.8F);
   }

   public boolean isOpaqueCube() {
      return false;
   }

   @SideOnly(Side.CLIENT)
   public int getRenderType() {
      return -1;
   }

   @SideOnly(Side.CLIENT)
   public boolean renderAsNormalBlock() {
      return false;
   }

   public void breakBlock(World world, int x, int y, int z, Block par5, int par6) {
      BlockArmorStandTileEntity stand = (BlockArmorStandTileEntity)world.getTileEntity(x, y, z);
      if(stand != null) {
         for(int i = 0; i < stand.cargoItems.length; ++i) {
            if(stand.cargoItems[i] != null && !world.isRemote) {
               EntityItem e = new EntityItem(world, (double)x + 0.5D, (double)(y + 1), (double)z + 0.5D, stand.cargoItems[i]);
               world.spawnEntityInWorld(e);
            }
         }
      }

      super.breakBlock(world, x, y, z, par5, par6);
   }

   public TileEntity createNewTileEntity(World var1, int var2) {
      return new BlockArmorStandTileEntity();
   }
}
