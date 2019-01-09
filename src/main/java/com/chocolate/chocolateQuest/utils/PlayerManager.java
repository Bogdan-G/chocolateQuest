package com.chocolate.chocolateQuest.utils;

import com.chocolate.chocolateQuest.magic.Awakements;
import com.chocolate.chocolateQuest.utils.PlayerInfo;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.FoodStats;

public class PlayerManager {

   static PlayerInfo clientPlayerInfo;
   static Map serverPlayersInfo = new HashMap();


   public static void setTarget(EntityPlayer player, EntityLivingBase target) {
      PlayerInfo info = getPlayerInfo(player);
   }

   public static EntityLivingBase getTarget(EntityPlayer player) {
      return getPlayerInfo(player).target;
   }

   public static boolean useStamina(EntityPlayer player, float ammount, boolean forceConsumition) {
      if(useStamina(player, ammount)) {
         return true;
      } else {
         if(!player.capabilities.isCreativeMode) {
            FoodStats food = player.getFoodStats();
            if(food.getFoodLevel() < 1) {
               return false;
            }

            food.addExhaustion(ammount);
         }

         return true;
      }
   }

   public static boolean useStamina(EntityPlayer player, float ammount) {
      if(getStamina(player) - ammount < 0.0F) {
         if(!player.capabilities.isCreativeMode) {
            FoodStats food = player.getFoodStats();
            if(food.getFoodLevel() <= 2) {
               return false;
            }

            food.addExhaustion(ammount);
         }
      } else {
         addStamina(player, -ammount);
      }

      return true;
   }

   public static void addStamina(EntityPlayer player, float ammount) {
      float newStamina = getStamina(player);
      newStamina = Math.min(getMaxStamina(player), Math.max(0.0F, newStamina + ammount));
      if(!player.worldObj.isRemote) {
         getClientPlayerInfo(player).stamina = newStamina;
      } else {
         PlayerInfo info = getPlayerInfo(player);
         info.stamina = newStamina;
      }

   }

   public static float getStamina(EntityPlayer player) {
      if(!player.worldObj.isRemote) {
         return getClientPlayerInfo(player).stamina;
      } else {
         String name = getPlayerName(player);
         return !serverPlayersInfo.containsKey(name)?getMaxStamina(player):getPlayerInfo(player).stamina;
      }
   }

   public static PlayerInfo getPlayerInfo(EntityPlayer player) {
      if(!player.worldObj.isRemote) {
         return getClientPlayerInfo(player);
      } else {
         String name = getPlayerName(player);
         if(!serverPlayersInfo.containsKey(name)) {
            serverPlayersInfo.put(name, new PlayerInfo(player));
         }

         return (PlayerInfo)serverPlayersInfo.get(name);
      }
   }

   public static PlayerInfo getClientPlayerInfo(EntityPlayer player) {
      if(clientPlayerInfo == null) {
         clientPlayerInfo = new PlayerInfo(player);
      }

      return clientPlayerInfo;
   }

   private static String getPlayerName(EntityPlayer player) {
      return player.getCommandSenderName();
   }

   public static float getMaxStamina(EntityPlayer player) {
      float stamina = 50.0F;
      int staminaLevel = 0;

      for(int i = 0; i < 4; ++i) {
         ItemStack is = player.getCurrentArmor(i);
         if(is != null) {
            staminaLevel += Awakements.getEnchantLevel(is, Awakements.staminaMax);
         }
      }

      return stamina + (float)staminaLevel * 2.5F;
   }

   public static void reset() {
      clientPlayerInfo = null;
      serverPlayersInfo = new HashMap();
   }

}
