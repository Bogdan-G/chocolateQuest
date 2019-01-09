package com.chocolate.chocolateQuest.misc;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class DungeonsItemsTab extends CreativeTabs {

   Item item;


   public DungeonsItemsTab(String label) {
      super(label);
   }

   public void setItemIcon(Item parItem) {
      this.item = parItem;
   }

   public Item getTabIconItem() {
      return this.item;
   }
}
