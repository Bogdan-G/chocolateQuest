package com.chocolate.chocolateQuest.API;

import com.chocolate.chocolateQuest.API.WeightedItemStack;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.item.ItemStack;

public class RegisterChestItem {

   public static ArrayList chestList = new ArrayList();
   public static ArrayList weaponList = new ArrayList();
   public static ArrayList mineralList = new ArrayList();
   public static ArrayList foodList = new ArrayList();
   public static ArrayList treasureList = new ArrayList();


   public static void addChestItem(ItemStack stack, int weight, ArrayList list) {
      list.add(new WeightedItemStack(stack, weight));
   }

   public static void addChestItem(ItemStack stack, int weight) {
      chestList.add(new WeightedItemStack(stack, weight));
   }

   public static void addToolsChestItem(ItemStack stack, int weight) {
      weaponList.add(new WeightedItemStack(stack, weight));
   }

   public static void addMineralsChestItem(ItemStack stack, int weight) {
      mineralList.add(new WeightedItemStack(stack, weight));
   }

   public static void addFoodChestItem(ItemStack stack, int weight) {
      foodList.add(new WeightedItemStack(stack, weight));
   }

   public static void addTreasureItem(ItemStack stack, int weight) {
      treasureList.add(new WeightedItemStack(stack, weight));
   }

   public static ItemStack getRandomItemStack(ArrayList chestList, Random random) {
      int[] weights = new int[chestList.size()];
      int maxNum = 0;

      int randomNum;
      for(randomNum = 0; randomNum < chestList.size(); ++randomNum) {
         weights[randomNum] = ((WeightedItemStack)chestList.get(randomNum)).weight;
         maxNum += weights[randomNum];
      }

      randomNum = random.nextInt(maxNum);
      int index = 0;

      for(int weightSum = weights[0]; weightSum <= randomNum; weightSum += weights[index]) {
         ++index;
      }

      return ((WeightedItemStack)chestList.get(index)).stack.copy();
   }

}
