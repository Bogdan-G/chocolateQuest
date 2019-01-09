package com.chocolate.chocolateQuest.gui;

import com.chocolate.chocolateQuest.gui.InventoryCargo;
import com.chocolate.chocolateQuest.items.ItemMedal;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;

public class InventoryMedal extends InventoryCargo {

   Entity entity;


   public InventoryMedal(Entity entity) {
      this.entity = entity;
      super.cargoItems = ItemMedal.getEntityInventory(entity);
   }

   public void closeInventory() {
      super.closeInventory();
      NBTTagCompound tag = this.entity.getEntityData();
      ItemMedal.writeToNBTWithMapping(tag, super.cargoItems);
   }

   public int getSizeInventory() {
      return super.cargoItems.length;
   }

   public String getInventoryName() {
      return "Drops";
   }
}
