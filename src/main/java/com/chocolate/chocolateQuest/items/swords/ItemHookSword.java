package com.chocolate.chocolateQuest.items.swords;

import com.chocolate.chocolateQuest.entity.projectile.EntityHookShoot;
import com.chocolate.chocolateQuest.items.IHookLauncher;
import com.chocolate.chocolateQuest.items.ItemHookShoot;
import com.chocolate.chocolateQuest.items.swords.ItemCQBlade;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemHookSword extends ItemCQBlade implements IHookLauncher {

   public ItemHookSword(ToolMaterial material) {
      super(material);
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {
      super.itemIcon = iconRegister.registerIcon("chocolatequest:hookSword");
   }

   public IIcon getIconFromDamageForRenderPass(int par1, int par2) {
      return par2 == 1?ItemHookShoot.hook:super.itemIcon;
   }

   @SideOnly(Side.CLIENT)
   public boolean requiresMultipleRenderPasses() {
      return true;
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entity, int par4, boolean flag) {
      int id = this.getHookID(itemstack);
      if(id != 0) {
         Entity e = world.getEntityByID(id);
         if(e != null) {
            if(e.isDead) {
               this.setHookID(itemstack, 0);
            }
         } else {
            this.setHookID(itemstack, 0);
         }
      }

      super.onUpdate(itemstack, world, entity, par4, flag);
   }

   public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityPlayer) {
      if(this.getHookID(itemstack) == 0) {
         if(!world.isRemote) {
            EntityHookShoot e = new EntityHookShoot(world, entityPlayer, 4, itemstack);
            world.spawnEntityInWorld(e);
            this.setHookID(itemstack, e.getEntityId());
         }
      } else {
         Entity e1 = world.getEntityByID(this.getHookID(itemstack));
         if(e1 instanceof EntityHookShoot) {
            e1.setDead();
         }

         this.setHookID(itemstack, 0);
      }

      return super.onItemRightClick(itemstack, world, entityPlayer);
   }

   public void setHookID(ItemStack is, int id) {
      if(is.stackTagCompound == null) {
         is.stackTagCompound = new NBTTagCompound();
      }

      is.stackTagCompound.setInteger("hook", id);
   }

   public int getHookID(ItemStack is) {
      return is.stackTagCompound == null?0:is.stackTagCompound.getInteger("hook");
   }
}
