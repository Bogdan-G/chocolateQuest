package com.chocolate.chocolateQuest.items;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

public class ItemMedal extends Item {

   @SideOnly(Side.CLIENT)
   protected IIcon overlayIcon;


   public void addInformation(ItemStack is, EntityPlayer player, List list, boolean par4) {
      super.addInformation(is, player, list, par4);
      if(is.stackTagCompound != null) {
         if(is.stackTagCompound.hasKey("display")) {
            NBTTagCompound set = is.stackTagCompound.getCompoundTag("display");
            if(set.hasKey("mob")) {
               list.add(StatCollector.translateToLocal(set.getString("mob")));
            }

            if(set.hasKey("size")) {
               list.add(StatCollector.translateToLocal("strings.size") + ": " + set.getFloat("size"));
            }
         }

         Set set1 = is.stackTagCompound.func_150296_c();
         Iterator i$ = set1.iterator();

         while(i$.hasNext()) {
            String o = (String)i$.next();
            if(!o.equals("id") && !o.equals("display")) {
               list.add("" + is.stackTagCompound.getTag(o));
            }
         }
      }

      if(player.capabilities.isCreativeMode) {
         list.add("Left click on a monster to open drops inventory");
      }

   }

   public String getItemStackDisplayName(ItemStack itemstack) {
      String name = this.getUnlocalizedNameInefficiently(itemstack);
      name = ("" + StatCollector.translateToLocal(name + ".name")).trim();
      return name;
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {
      super.registerIcons(iconRegister);
      this.overlayIcon = iconRegister.registerIcon("chocolatequest:" + (this.getUnlocalizedName() + "_overlay").replace("item.", ""));
   }

   public boolean requiresMultipleRenderPasses() {
      return true;
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIcon(ItemStack stack, int pass) {
      return pass == 1?this.overlayIcon:super.itemIcon;
   }

   public int getColorFromItemStack(ItemStack is, int i) {
      if(i == 1 && is.stackTagCompound != null && is.stackTagCompound.hasKey("display")) {
         NBTBase tagbase = is.stackTagCompound.getTag("display");
         if(tagbase instanceof NBTTagCompound) {
            NBTTagCompound tag = (NBTTagCompound)tagbase;
            if(tag.hasKey("color")) {
               return tag.getInteger("color");
            }
         }
      }

      return super.getColorFromItemStack(is, i);
   }

   public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
      if(player.capabilities.isCreativeMode) {
         player.openGui(ChocolateQuest.instance, 10, player.worldObj, entity.getEntityId(), 0, 0);
      }

      return true;
   }

   public static ItemStack[] getEntityInventory(Entity e) {
      int sizeInventory = 9;
      if(e instanceof EntityHumanBase) {
         ItemStack is = ((EntityHumanBase)e).getEquipmentInSlot(3);
         if(is != null && is.getItem() == ChocolateQuest.backpack) {
            sizeInventory += 9 + ChocolateQuest.backpack.getTier(is) * 9;
         }
      }

      return readFromNBTWithMapping(e.getEntityData(), sizeInventory);
   }

   public static ItemStack[] readFromNBTWithMapping(NBTTagCompound nbt, int sizeInventory) {
      if(nbt.hasKey("items")) {
         NBTTagList list = (NBTTagList)nbt.getTag("items");

         for(int i = 0; i < list.tagCount(); ++i) {
            String id = list.getCompoundTagAt(i).getString("name");
            Item item = (Item)Item.itemRegistry.getObject(id);
            if(item != null) {
               short newID = (short)Item.getIdFromItem(item);
               list.getCompoundTagAt(i).setShort("id", newID);
            }
         }
      }

      return readFromNBT(nbt, sizeInventory);
   }

   public static void writeToNBTWithMapping(NBTTagCompound nbt, ItemStack[] items) {
      writeToNBT(nbt, items);
      NBTTagList list = (NBTTagList)nbt.getTag("items");

      for(int i = 0; i < list.tagCount(); ++i) {
         if(items[i] != null) {
            String id = "";
            id = Item.itemRegistry.getNameForObject(items[i].getItem());
            list.getCompoundTagAt(i).setString("name", id);
         }
      }

   }

   public static ItemStack[] readFromNBT(NBTTagCompound nbt, int sizeInventory) {
      ItemStack[] costItems = new ItemStack[sizeInventory];
      if(nbt.hasKey("items")) {
         NBTTagList itemsTag = (NBTTagList)nbt.getTag("items");
         int tagCount = itemsTag.tagCount();
         if(tagCount > sizeInventory) {
            costItems = new ItemStack[tagCount];
         }

         for(int i = 0; i < sizeInventory && i != tagCount; ++i) {
            costItems[i] = ItemStack.loadItemStackFromNBT(itemsTag.getCompoundTagAt(i));
         }
      }

      return costItems;
   }

   public static void writeToNBT(NBTTagCompound nbt, ItemStack[] items) {
      NBTTagList priceListTag = new NBTTagList();

      for(int i = 0; i < items.length; ++i) {
         if(items[i] != null) {
            NBTTagCompound priceItemTag = new NBTTagCompound();
            items[i].writeToNBT(priceItemTag);
            priceListTag.appendTag(priceItemTag);
         }
      }

      nbt.setTag("items", priceListTag);
   }
}
