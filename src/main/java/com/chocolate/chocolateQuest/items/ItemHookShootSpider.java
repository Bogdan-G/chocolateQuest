package com.chocolate.chocolateQuest.items;

import com.chocolate.chocolateQuest.items.ItemHookShoot;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemHookShootSpider extends ItemHookShoot {

   public IIcon hook;


   public ItemHookShootSpider(int lvl) {
      super(lvl, "");
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {
      this.hook = iconRegister.registerIcon("chocolatequest:hookWeb");
      super.itemIcon = iconRegister.registerIcon("chocolatequest:hookSpider");
   }

   public IIcon getIconFromDamageForRenderPass(int par1, int par2) {
      return par2 == 0 && par1 == 0?this.hook:super.itemIcon;
   }

   public EnumRarity getRarity(ItemStack itemstack) {
      return EnumRarity.epic;
   }

   public void onUpdate(ItemStack itemStack, World world, Entity entity, int par4, boolean par5) {
      if(entity.ticksExisted % 400 == 0 && entity instanceof EntityPlayer) {
         EntityPlayer ep = (EntityPlayer)entity;
         if(itemStack.stackTagCompound == null) {
            itemStack.stackTagCompound = new NBTTagCompound();
         }

         itemStack.stackTagCompound.setString("OriginalOwner", ep.getCommandSenderName());
      }

   }

   public boolean onEntityItemUpdate(EntityItem entityItem) {
      if(entityItem.getEntityItem().stackTagCompound != null && entityItem.age > entityItem.lifespan - 100) {
         entityItem.age = 0;
         EntityPlayer player = entityItem.worldObj.getPlayerEntityByName(entityItem.getEntityItem().stackTagCompound.getString("OriginalOwner"));
         if(player != null) {
            entityItem.setPosition(player.posX, player.posY, player.posZ);
         }
      }

      if(entityItem.isBurning()) {
         entityItem.extinguish();
         entityItem.motionY = 0.5D;
      }

      return super.onEntityItemUpdate(entityItem);
   }
}
