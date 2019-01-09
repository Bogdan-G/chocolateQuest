package com.chocolate.chocolateQuest.items.gun;

import com.chocolate.chocolateQuest.entity.npc.EntityGolemMecha;
import com.chocolate.chocolateQuest.entity.npc.EntityGolemMechaHeavy;
import com.chocolate.chocolateQuest.items.gun.ItemGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ItemGolemHeavy extends ItemGolem {

   public ItemGolemHeavy() {
      super("mechaElite");
   }

   public EntityGolemMecha getGolem(World world, EntityPlayer entityPlayer) {
      return new EntityGolemMechaHeavy(world, entityPlayer);
   }
}
