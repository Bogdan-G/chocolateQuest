package com.chocolate.chocolateQuest.misc;

import com.chocolate.chocolateQuest.ChocolateQuest;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.potion.Potion;

public class PotionCQ extends Potion {

   public static Potion minePrevention;


   public PotionCQ(int par1, boolean par2, int par3) {
      super(par1, par2, par3);
   }

   public Potion setIconIndex(int par1, int par2) {
      super.setIconIndex(par1, par2);
      return this;
   }

   public static void registerPotions(FMLPreInitializationEvent event) {
      minePrevention = (new PotionCQ(ChocolateQuest.config.potionMinePreventionID, false, 0)).setIconIndex(3, 0).setPotionName("potion.minePrevention");
   }
}
