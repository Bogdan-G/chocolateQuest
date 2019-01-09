package com.chocolate.chocolateQuest.config;

import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class HelperWeightedItems {

   public Item[] items;
   public int[] number;
   public int[] damage;
   public int[] weight;


   public HelperWeightedItems(Item[] items, int[] number, int[] damage, int[] weight) {
      this.items = new Item[]{Items.apple};
      this.number = new int[]{1};
      this.damage = new int[]{0};
      this.weight = new int[]{1};
      this.items = items;
      this.number = number;
      this.damage = damage;
      this.weight = weight;
   }
}
