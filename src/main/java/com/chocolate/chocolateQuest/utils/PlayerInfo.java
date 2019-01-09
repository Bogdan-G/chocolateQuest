package com.chocolate.chocolateQuest.utils;

import com.chocolate.chocolateQuest.utils.PlayerManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

class PlayerInfo {

   public float stamina;
   public EntityLivingBase target;


   public PlayerInfo(EntityPlayer player) {
      this.stamina = PlayerManager.getMaxStamina(player);
   }
}
