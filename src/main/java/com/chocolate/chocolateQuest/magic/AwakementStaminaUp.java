package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.magic.AwakementArmor;
import com.chocolate.chocolateQuest.misc.EnumEnchantType;
import com.chocolate.chocolateQuest.utils.PlayerManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class AwakementStaminaUp extends AwakementArmor {

   public AwakementStaminaUp(String name, int icon) {
      super(name, icon);
   }

   public void onUpdate(Entity entity, ItemStack itemStack) {
      if(entity instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)entity;
         int level = getEnchantLevel(itemStack, this);
         PlayerManager.addStamina(player, 0.005F * (float)level);
      }

   }

   public boolean canBeAddedByNPC(int type) {
      return super.canBeAddedByNPC(type) || type == EnumEnchantType.STAVES.ordinal();
   }

   public int getLevelCost() {
      return 2;
   }

   public int getMaxLevel() {
      return 5;
   }
}
