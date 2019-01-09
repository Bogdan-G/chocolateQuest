package com.chocolate.chocolateQuest.items;

import com.chocolate.chocolateQuest.API.ITwoHandedItem;
import com.chocolate.chocolateQuest.entity.EntityDecoration;
import com.chocolate.chocolateQuest.items.ItemEntityPlacer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemBanner extends ItemEntityPlacer implements ITwoHandedItem {

   public final String TAG_SIZE = "size";


   public Entity getEntity(ItemStack stack, EntityPlayer player, World world, int x, int y, int z) {
      EntityDecoration e = new EntityDecoration(world);
      e.rotationYaw = player.rotationYawHead - 180.0F;
      e.type = stack.getItemDamage();
      if(stack.hasTagCompound() && stack.stackTagCompound.hasKey("size")) {
         e.size = stack.stackTagCompound.getFloat("size");
      }

      return e;
   }

   public void addInformation(ItemStack is, EntityPlayer player, List list, boolean par4) {
      if(is.stackTagCompound != null && is.stackTagCompound.hasKey("size")) {
         list.add(StatCollector.translateToLocal("strings.size") + ": " + is.stackTagCompound.getFloat("size"));
      }

      if(player.capabilities.isCreativeMode) {
         list.add("Tags: size (float)");
      }

   }

   public boolean getHasSubtypes() {
      return true;
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {
      super.itemIcon = iconRegister.registerIcon("chocolatequest:bannerHolder");
   }

   public String getItemStackDisplayName(ItemStack itemstack) {
      return super.getItemStackDisplayName(itemstack);
   }

   public boolean requiresMultipleRenderPasses() {
      return false;
   }

   public IIcon getIconFromDamageForRenderPass(int par1, int par2) {
      return super.itemIcon;
   }

   @SideOnly(Side.CLIENT)
   public void getSubItems(Item item, CreativeTabs par2CreativeTabs, List itemList) {
      for(int i = 0; i <= 16; ++i) {
         itemList.add(new ItemStack(item, 1, i));
      }

   }
}
