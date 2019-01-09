package com.chocolate.chocolateQuest.items;

import com.chocolate.chocolateQuest.utils.BDHelper;
import com.google.common.collect.Multimap;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemPickaxeMagic extends Item {

   public ItemPickaxeMagic() {
      this.setMaxStackSize(1);
      this.setMaxDamage(2024);
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {
      super.itemIcon = iconRegister.registerIcon("chocolatequest:pickaxeMagic");
   }

   public boolean canHarvestBlock(Block par1Block) {
      return par1Block != Blocks.bedrock;
   }

   public Multimap getItemAttributeModifiers() {
      Multimap multimap = super.getItemAttributeModifiers();
      multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(Item.field_111210_e, "Weapon modifier", 10000.0D, 0));
      return multimap;
   }

   public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block) {
      return par2Block != null && (par2Block.getMaterial() == Material.iron || par2Block.getMaterial() == Material.anvil || par2Block.getMaterial() == Material.rock)?15.0F:8.0F;
   }

   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer player) {
      if(player.isSneaking()) {
         int mode = this.getMode(par1ItemStack) + 1;
         if(mode >= 3) {
            mode = 0;
         }

         this.setMode(par1ItemStack, mode);
         if(!par2World.isRemote) {
            if(mode == 2) {
               player.addChatMessage(new ChatComponentText(BDHelper.StringColor("f") + "Pickaxe mode: " + BDHelper.StringColor("3") + " build"));
            } else if(mode == 1) {
               player.addChatMessage(new ChatComponentText(BDHelper.StringColor("f") + "Pickaxe mode: " + BDHelper.StringColor("3") + " fill"));
            } else {
               player.addChatMessage(new ChatComponentText(BDHelper.StringColor("f") + "Pickaxe mode: " + BDHelper.StringColor("3") + " mine"));
            }
         }
      }

      return super.onItemRightClick(par1ItemStack, par2World, player);
   }

   public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
      if(player.isSneaking() && this.getMode(stack) != 0) {
         this.setBlockAndMetadata(stack, world.getBlock(x, y, z), world.getBlockMetadata(x, y, z));
         return true;
      } else {
         byte cont = 0;
         byte size = 2;
         int t;
         int v;
         if(side == 0) {
            for(t = -size + 1; t < size; ++t) {
               for(v = -size + 1; v < size; ++v) {
                  this.destroyAndDropItem(world, x + t, y, z + v, 0, stack, player);
               }
            }
         }

         if(side == 1) {
            for(t = -size + 1; t < size; ++t) {
               for(v = -size + 1; v < size; ++v) {
                  this.destroyAndDropItem(world, x + t, y, z + v, 0, stack, player);
               }
            }
         }

         if(side == 2) {
            for(t = -size + 1; t < size; ++t) {
               for(v = -size + 1; v < size; ++v) {
                  this.destroyAndDropItem(world, x + t, y + v, z, 0, stack, player);
               }
            }
         }

         if(side == 3) {
            for(t = -size + 1; t < size; ++t) {
               for(v = -size + 1; v < size; ++v) {
                  this.destroyAndDropItem(world, x + t, y + v, z, 0, stack, player);
               }
            }
         }

         if(side == 4) {
            for(t = -size + 1; t < size; ++t) {
               for(v = -size + 1; v < size; ++v) {
                  this.destroyAndDropItem(world, x, y + t, z + v, 0, stack, player);
               }
            }
         }

         if(side == 5) {
            for(t = -size + 1; t < size; ++t) {
               for(v = -size + 1; v < size; ++v) {
                  this.destroyAndDropItem(world, x, y + t, z + v, 0, stack, player);
               }
            }
         }

         stack.damageItem(cont, player);
         return true;
      }
   }

   private void destroyAndDropItem(World world, int i, int j, int k, int power, ItemStack is, EntityPlayer player) {
      Block id = world.getBlock(i, j, k);
      if(this.getMode(is) == 2) {
         world.setBlock(i, j, k, this.getBlock(is), this.getMetadata(is), 3);
         is.damageItem(1, player);
      } else if(id != Blocks.air && id != Blocks.bedrock) {
         if(world.isRemote) {
            int blockID = Block.getIdFromBlock(world.getBlock(i, j, k));
            world.getBlockMetadata(i, j, k);

            for(int c = 0; c < 8; ++c) {
               world.spawnParticle("blockcrack_" + blockID + "_" + 0, (double)i + ((double)Item.itemRand.nextFloat() - 0.5D), (double)((float)j + Item.itemRand.nextFloat() - 0.5F), (double)((float)k + Item.itemRand.nextFloat()) - 0.5D, 0.0D, 0.0D, 0.0D);
            }
         }

         is.damageItem(1, player);
         if(this.getMode(is) == 1) {
            world.setBlock(i, j, k, this.getBlock(is), this.getMetadata(is), 3);
         } else {
            world.setBlockToAir(i, j, k);
         }
      }

   }

   public String getItemStackDisplayName(ItemStack itemstack) {
      return (new String("Super Tool!(Creative only)")).concat(this.getMode(itemstack) == 2?"Build mode":(this.getMode(itemstack) == 1?"Fill mode":""));
   }

   public int getMode(ItemStack is) {
      return is.stackTagCompound != null?is.stackTagCompound.getByte("mode"):0;
   }

   public void setMode(ItemStack is, int i) {
      if(is.stackTagCompound == null) {
         is.stackTagCompound = new NBTTagCompound();
      }

      is.stackTagCompound.setByte("mode", (byte)i);
   }

   public void setBlockAndMetadata(ItemStack is, Block block, int md) {
      if(is.stackTagCompound == null) {
         is.stackTagCompound = new NBTTagCompound();
      }

      is.stackTagCompound.setInteger("bl", Block.getIdFromBlock(block));
      is.stackTagCompound.setInteger("md", md);
   }

   public Block getBlock(ItemStack is) {
      if(is.stackTagCompound == null) {
         return Blocks.stone;
      } else {
         int id = is.stackTagCompound.getInteger("bl");
         return id < 1?Blocks.stone:Block.getBlockById(id);
      }
   }

   public int getMetadata(ItemStack is) {
      return is.stackTagCompound == null?0:is.stackTagCompound.getInteger("md");
   }
}
