package com.chocolate.chocolateQuest.builder.decorator.rooms;

import com.chocolate.chocolateQuest.builder.BuilderHelper;
import com.chocolate.chocolateQuest.builder.decorator.RoomBase;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RoomKitchen extends RoomBase {

   public void placeDecorationBlock(Random random, World world, int x, int y, int z, int side) {
      if(random.nextInt(100) == 0) {
         this.placePainting(random, world, x, y + 1, z, side);
      }

      if(random.nextInt(250) == 0) {
         world.setBlock(x, y, z, Blocks.brewing_stand, 0, 3);
      } else if(random.nextInt(30) == 0) {
         this.placeCake(random, world, x, y, z);
      } else if(random.nextInt(50) == 0) {
         world.setBlock(x, y, z, Blocks.cauldron);
      } else if(random.nextInt(50) == 0) {
         world.setBlock(x, y, z, Blocks.melon_block);
      } else if(random.nextInt(50) == 0) {
         world.setBlock(x, y, z, Blocks.pumpkin);
      } else if(random.nextInt(8) == 0) {
         this.placeFoodFurnace(random, world, x, y, z, side);
      } else if(random.nextInt(10) == 0) {
         world.setBlock(x, y, z, Blocks.crafting_table);
      } else if(random.nextInt(8) == 0) {
         ItemStack is = null;
         if(random.nextInt(3) == 0) {
            is = new ItemStack(Items.cooked_fished);
         } else if(random.nextInt(3) == 0) {
            is = new ItemStack(Items.cooked_beef);
         } else if(random.nextInt(3) == 0) {
            is = new ItemStack(Items.milk_bucket);
         } else if(random.nextInt(3) == 0) {
            is = new ItemStack(Items.cookie);
         } else if(random.nextInt(3) == 0) {
            is = new ItemStack(Items.cooked_chicken);
         } else if(random.nextInt(3) == 0) {
            is = new ItemStack(Items.egg);
         } else if(random.nextInt(3) == 0) {
            is = new ItemStack(Items.golden_apple);
         }

         this.placeTable(random, world, x, y, z, is);
      } else if(random.nextInt(20) == 0) {
         BuilderHelper.addFoodChest(random, world, x, y, z, 2);
      }

   }

   public int getType() {
      return 0;
   }
}
