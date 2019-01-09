package com.chocolate.chocolateQuest.gui.guinpc;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ShopRecipe {

   public ItemStack tradedItem;
   public ItemStack[] costItems;


   public ShopRecipe(ItemStack tradedItem, ItemStack[] costItems) {
      this.tradedItem = tradedItem;
      this.costItems = costItems;
   }

   public ShopRecipe(NBTTagCompound nbt) {
      this.readFromNBT(nbt);
   }

   public ShopRecipe(NBTTagCompound nbt, boolean readWithMapping) {
      if(readWithMapping) {
         this.readFromNBTWithMapping(nbt);
      } else {
         this.readFromNBT(nbt);
      }

   }

   public void readFromNBT(NBTTagCompound nbt) {
      NBTTagList priceListTag = (NBTTagList)nbt.getTag("items");
      this.tradedItem = ItemStack.loadItemStackFromNBT(priceListTag.getCompoundTagAt(0));
      this.costItems = new ItemStack[priceListTag.tagCount() - 1];

      for(int i = 1; i < priceListTag.tagCount(); ++i) {
         this.costItems[i - 1] = ItemStack.loadItemStackFromNBT(priceListTag.getCompoundTagAt(i));
      }

   }

   public void writeToNBT(NBTTagCompound nbt) {
      NBTTagList priceListTag = new NBTTagList();
      NBTTagCompound tradedItemTag = new NBTTagCompound();
      this.tradedItem.writeToNBT(tradedItemTag);
      priceListTag.appendTag(tradedItemTag);

      for(int i = 1; i < this.costItems.length + 1; ++i) {
         NBTTagCompound priceItemTag = new NBTTagCompound();
         this.costItems[i - 1].writeToNBT(priceItemTag);
         priceListTag.appendTag(priceItemTag);
      }

      nbt.setTag("items", priceListTag);
   }

   public void readFromNBTWithMapping(NBTTagCompound nbt) {
      NBTTagList list = (NBTTagList)nbt.getTag("items");

      for(int i = 0; i < list.tagCount(); ++i) {
         String id = list.getCompoundTagAt(i).getString("name");
         Item item = (Item)Item.itemRegistry.getObject(id);
         if(item != null) {
            short newID = (short)Item.getIdFromItem(item);
            list.getCompoundTagAt(i).setShort("id", newID);
         }
      }

      this.readFromNBT(nbt);
   }

   public void writeToNBTWithMapping(NBTTagCompound nbt) {
      this.writeToNBT(nbt);
      NBTTagList list = (NBTTagList)nbt.getTag("items");

      for(int i = 0; i < list.tagCount(); ++i) {
         String id = "";
         if(i == 0) {
            id = Item.itemRegistry.getNameForObject(this.tradedItem.getItem());
         } else {
            id = Item.itemRegistry.getNameForObject(this.costItems[i - 1].getItem());
         }

         list.getCompoundTagAt(i).setString("name", id);
      }

   }
}
