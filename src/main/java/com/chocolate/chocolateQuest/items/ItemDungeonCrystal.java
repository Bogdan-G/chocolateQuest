package com.chocolate.chocolateQuest.items;

import com.chocolate.chocolateQuest.entity.EntityDungeonCrystal;
import com.chocolate.chocolateQuest.items.ItemEntityPlacer;
import com.chocolate.chocolateQuest.items.ItemStaffHeal;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDungeonCrystal extends ItemEntityPlacer {

   static final String TAG_EXCLUDE_TEAM = "excludeTeam";
   static final String TAG_PLACE_POSITION = "placePosition";


   public void addInformation(ItemStack is, EntityPlayer player, List list, boolean par4) {
      if(is.stackTagCompound != null) {
         if(is.stackTagCompound.hasKey("excludeTeam")) {
            list.add(is.stackTagCompound.getString("excludeTeam"));
         }

         ItemStaffHeal.addPotionEffectInformation(is, list, false);
      }

      if(player.capabilities.isCreativeMode) {
         list.add("Tags: excludeTeam(String), CustomPotionEffects(Tag)");
      }

   }

   public Entity getEntity(ItemStack stack, EntityPlayer player, World world, int x, int y, int z) {
      EntityDungeonCrystal entity = new EntityDungeonCrystal(world);
      if(stack.stackTagCompound != null) {
         entity.crystalTag = ItemStaffHeal.getPotionEffectsTag(stack);
         if(stack.stackTagCompound.hasKey("excludeTeam")) {
            entity.excludeTeam = stack.stackTagCompound.getString("excludeTeam");
         }
      }

      return entity;
   }
}
