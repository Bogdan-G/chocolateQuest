package com.chocolate.chocolateQuest.builder.decorator.rooms;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.block.BlockBannerStandTileEntity;
import com.chocolate.chocolateQuest.builder.decorator.RoomBase;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import com.chocolate.chocolateQuest.entity.mob.registry.RegisterDungeonMobs;
import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RoomFlag extends RoomBase {

   public void placeDecorationBlock(Random random, World world, int x, int y, int z, int side) {
      if(side == 5) {
         world.setBlock(x, y, z, ChocolateQuest.bannerStand);
         BlockBannerStandTileEntity rotation = (BlockBannerStandTileEntity)ChocolateQuest.bannerStand.createTileEntity(world, 0);
         if(rotation != null) {
            DungeonMonstersBase stand = null;
            rotation.hasFlag = true;
            stand = (DungeonMonstersBase)RegisterDungeonMobs.mobList.get(super.properties.mobID);
            if(stand != null) {
               rotation.item = new ItemStack(ChocolateQuest.banner, 1, stand.getFlagId());
            }

            this.setTileEntity(world, x, y, z, rotation);
         }
      } else if(random.nextInt(5) == 0) {
         short rotation1 = 0;
         switch(side) {
         case 1:
            rotation1 = -90;
            break;
         case 2:
            rotation1 = 90;
         case 3:
         default:
            break;
         case 4:
            rotation1 = 180;
         }

         world.setBlock(x, y, z, ChocolateQuest.bannerStand);
         BlockBannerStandTileEntity stand1 = (BlockBannerStandTileEntity)ChocolateQuest.bannerStand.createTileEntity(world, 0);
         if(stand1 != null) {
            DungeonMonstersBase mobType = null;
            stand1.hasFlag = true;
            stand1.rotation = rotation1;
            mobType = (DungeonMonstersBase)RegisterDungeonMobs.mobList.get(super.properties.mobID);
            if(mobType != null) {
               stand1.item = new ItemStack(ChocolateQuest.banner, 1, mobType.getFlagId());
            }

            this.setTileEntity(world, x, y, z, stand1);
         }
      } else if(random.nextInt(10) == 0) {
         this.placeShied(random, world, x, y + 1, z, side);
      } else {
         this.decorateFullMonsterRoom(random, world, x, y, z, side);
      }

   }

   public int getType() {
      return 204;
   }
}
