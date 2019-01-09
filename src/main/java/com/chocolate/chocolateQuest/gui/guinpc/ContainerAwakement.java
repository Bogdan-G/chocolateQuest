package com.chocolate.chocolateQuest.gui.guinpc;

import com.chocolate.chocolateQuest.gui.ContainerBDChest;
import com.chocolate.chocolateQuest.gui.guinpc.InventoryAwakement;
import com.chocolate.chocolateQuest.gui.slot.SlotLockedToClass;
import com.chocolate.chocolateQuest.magic.Awakements;
import com.chocolate.chocolateQuest.misc.EnumEnchantType;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAwakement extends ContainerBDChest {

   static final int COLUMS = 9;
   static final int ICON_DESP = 17;
   static final int MARGIN = 10;
   InventoryAwakement inventory;
   public boolean mode = true;
   public static final boolean MODE_ENCHANTMENT = true;
   int type;
   int maxLevel;


   public ContainerAwakement(IInventory playerInventory, InventoryAwakement chestInventory, int type, int level) {
      super(playerInventory, chestInventory);
      this.inventory = chestInventory;
      this.mode = type == EnumEnchantType.ENCHANT.ordinal();
      this.maxLevel = level;
   }

   public void layoutInventory(IInventory chestInventory) {
      byte x = 10;
      byte y = 60;
      this.addSlotToContainer(new Slot(chestInventory, 0, x, y));
      this.addSlotToContainer(new SlotLockedToClass(chestInventory, 1, x + 32, y - 42, Items.dye, 4));
   }

   public int getPlayerInventoryY() {
      return super.getPlayerInventoryY();
   }

   public void onContainerClosed(EntityPlayer player) {
      ItemStack is = this.inventory.getStackInSlot(0);
      if(is != null) {
         player.dropPlayerItemWithRandomChoice(is, false);
      }

      is = this.inventory.getStackInSlot(1);
      if(is != null) {
         player.dropPlayerItemWithRandomChoice(is, false);
      }

   }

   public void enchantItem(int enchantment) {
      ItemStack is = this.inventory.getStackInSlot(0);
      boolean lvl = false;
      int lvl1;
      if(this.mode) {
         lvl1 = EnchantmentHelper.getEnchantmentLevel(enchantment, is);
      } else {
         lvl1 = Awakements.getEnchantLevel(is, Awakements.awekements[enchantment]);
      }

      int expRequired = this.getXPRequiredToEnchantItem() + this.getXPRequiredForEnchantment(enchantment, lvl1);
      expRequired -= this.getCatalystRebate(expRequired);
      if(!super.player.capabilities.isCreativeMode) {
         super.player.experienceLevel -= expRequired;
      }

      this.inventory.setInventorySlotContents(1, (ItemStack)null);
      if(this.mode) {
         Map aw = EnchantmentHelper.getEnchantments(is);
         if(aw.containsKey(Integer.valueOf(enchantment))) {
            aw.put(Integer.valueOf(enchantment), Integer.valueOf(((Integer)aw.get(Integer.valueOf(enchantment))).intValue() + 1));
         } else {
            aw.put(Integer.valueOf(enchantment), Integer.valueOf(1));
         }

         EnchantmentHelper.setEnchantments(aw, is);
      } else {
         Awakements aw1 = Awakements.awekements[enchantment];
         Awakements.addEnchant(is, aw1, Awakements.getEnchantLevel(is, aw1) + 1);
      }

   }

   public int getXPRequiredToEnchantItem() {
      ItemStack is = this.inventory.getStackInSlot(0);
      int expRequired = 0;
      if(this.mode) {
         int itemEnchantability = is.getItem().getItemEnchantability();
         Map map = EnchantmentHelper.getEnchantments(is);
         Iterator iterator = map.entrySet().iterator();
         Iterator i$ = map.entrySet().iterator();

         while(i$.hasNext()) {
            Object e = i$.next();
            if(e != null) {
               int key = ((Integer)((Entry)e).getKey()).intValue();
               int value = ((Integer)((Entry)e).getValue()).intValue();
               Enchantment enchant = Enchantment.enchantmentsList[((Integer)((Entry)e).getKey()).intValue()];
               int enchantability = Enchantment.enchantmentsList[key].getMinEnchantability(8) * value;
               expRequired += enchantability;
            }
         }

         expRequired = Math.min(104, expRequired / 12);
      } else {
         expRequired = Awakements.getExperienceForNextLevel(is);
      }

      expRequired = Math.max(1, expRequired);
      return expRequired;
   }

   public int getXPRequiredForEnchantment(int enchant, int level) {
      return this.mode?5 + Enchantment.enchantmentsList[enchant].getMaxEnchantability(0) / 4:4;
   }

   public int getCatalystRebate(int expRequired) {
      ItemStack catalyst = this.inventory.getStackInSlot(1);
      if(catalyst != null) {
         int ret = catalyst.stackSize;
         if(ret > expRequired) {
            ret = expRequired - 1;
         }

         return ret;
      } else {
         return 0;
      }
   }
}
