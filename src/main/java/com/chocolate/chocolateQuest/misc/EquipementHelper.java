package com.chocolate.chocolateQuest.misc;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanMob;
import com.chocolate.chocolateQuest.utils.BDHelper;
import java.util.Random;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class EquipementHelper {

   public static final int swordsman = 0;
   public static final int defender = 1;
   public static final int ninja = 2;
   public static final int berserk = 3;
   public static final int spearman = 4;
   public static final int gladiator = 5;
   public static final int ranged = 6;
   public static final int healer = 7;
   public static final int bannerman = 8;
   public static final int music = 9;


   public static void equipHumanRandomly(EntityHumanBase e, int lvl, int type) {
      equipEntity(e, lvl);
      ItemStack is;
      switch(type) {
      case 0:
         e.setCurrentItemOrArmor(0, getSword(e.getRNG(), lvl));
         e.setLeftHandItem((ItemStack)null);
         break;
      case 1:
         e.setCurrentItemOrArmor(0, getSword(e.getRNG(), lvl));
         e.setLeftHandItem(new ItemStack(ChocolateQuest.shield, 1, e.getTeamID()));
         break;
      case 2:
         if(lvl < 4) {
            is = new ItemStack(ChocolateQuest.ironDagger);
         } else {
            is = new ItemStack(ChocolateQuest.diamondDagger);
         }

         e.setCurrentItemOrArmor(0, is);
         e.setLeftHandItem((ItemStack)null);
         break;
      case 3:
         if(lvl < 4) {
            is = new ItemStack(ChocolateQuest.ironBigsword);
         } else {
            is = new ItemStack(ChocolateQuest.diamondBigsword);
         }

         e.setCurrentItemOrArmor(0, is);
         e.setLeftHandItem((ItemStack)null);
         break;
      case 4:
         if(lvl < 4) {
            is = new ItemStack(ChocolateQuest.ironSpear);
         } else {
            is = new ItemStack(ChocolateQuest.diamondSpear);
         }

         e.setCurrentItemOrArmor(0, is);
         e.setLeftHandItem((ItemStack)null);
         break;
      case 5:
         e.setCurrentItemOrArmor(0, getSword(e.getRNG(), lvl));
         e.setLeftHandItem(getSword(e.getRNG(), lvl));
         break;
      case 6:
         e.setCurrentItemOrArmor(0, e.getRangedWeapon(lvl));
         e.setLeftHandItem(e.getRangedWeaponLeft(lvl));
         break;
      case 7:
         e.setCurrentItemOrArmor(0, new ItemStack(ChocolateQuest.staffHeal));
         e.setLeftHandItem((ItemStack)null);
         break;
      case 8:
         e.setCurrentItemOrArmor(0, new ItemStack(ChocolateQuest.banner, 1, e.getTeamID()));
         e.setLeftHandItem((ItemStack)null);
      }

   }

   public static int getRandomType(EntityHumanBase e, int rareRatio) {
      Random random = e.getRNG();
      return random.nextInt(50 * rareRatio) == 0?8:(random.nextInt(4 * rareRatio) == 0?6:(random.nextInt(5 * rareRatio) == 0?3:(random.nextInt(4 * rareRatio) == 0?4:(random.nextInt(4 * rareRatio) == 0?2:(random.nextInt(5 * rareRatio) == 0?5:(random.nextInt(3 * rareRatio) == 0?7:(random.nextInt(2 * rareRatio) == 0?1:0)))))));
   }

   public static int getRandomLevel(EntityHumanBase e) {
      Random random = e.getRNG();
      return random.nextInt(5);
   }

   public static void equipEntity(EntityHumanBase e, int lvl) {
      int i;
      if(lvl >= 3) {
         if(lvl == 3) {
            for(i = 1; i <= 4; ++i) {
               e.setCurrentItemOrArmor(i, e.getIronArmorForSlot(i));
            }
         } else {
            for(i = 1; i <= 4; ++i) {
               e.setCurrentItemOrArmor(i, e.getDiamondArmorForSlot(i));
            }
         }
      } else {
         for(i = 1; i <= 4; ++i) {
            ItemStack is = getArmor(e.getRNG(), i, lvl);
            e.setCurrentItemOrArmor(i, is);
            if(lvl == 0 && e instanceof EntityHumanMob) {
               BDHelper.colorArmor(is, ((EntityHumanMob)e).getMonsterType().getColor());
            }
         }
      }

      e.setCurrentItemOrArmor(0, getSword(e.getRNG(), lvl));
   }

   public static ItemStack getSword(Random rand, int lvl) {
      switch(lvl) {
      case 0:
         return new ItemStack(Items.wooden_sword);
      case 1:
         return new ItemStack(Items.stone_sword);
      case 2:
         return new ItemStack(Items.golden_sword);
      case 3:
         return new ItemStack(Items.iron_sword);
      default:
         return new ItemStack(Items.diamond_sword);
      }
   }

   public static ItemStack getArmor(Random rand, int stack, int lvl) {
      if(1 == stack) {
         switch(lvl) {
         case 0:
            return new ItemStack(Items.leather_boots);
         case 1:
            return new ItemStack(Items.chainmail_boots);
         case 2:
            return new ItemStack(Items.golden_boots);
         case 3:
            return new ItemStack(Items.iron_boots);
         case 4:
            return new ItemStack(Items.diamond_boots);
         }
      }

      if(2 == stack) {
         switch(lvl) {
         case 0:
            return new ItemStack(Items.leather_leggings);
         case 1:
            return new ItemStack(Items.chainmail_leggings);
         case 2:
            return new ItemStack(Items.golden_leggings);
         case 3:
            return new ItemStack(Items.iron_leggings);
         case 4:
            return new ItemStack(Items.diamond_leggings);
         }
      }

      if(3 == stack) {
         switch(lvl) {
         case 0:
            return new ItemStack(Items.leather_chestplate);
         case 1:
            return new ItemStack(Items.chainmail_chestplate);
         case 2:
            return new ItemStack(Items.golden_chestplate);
         case 3:
            return new ItemStack(Items.iron_chestplate);
         case 4:
            return new ItemStack(Items.diamond_chestplate);
         }
      }

      if(4 == stack) {
         switch(lvl) {
         case 0:
            return new ItemStack(Items.leather_helmet);
         case 1:
            return new ItemStack(Items.chainmail_helmet);
         case 2:
            return new ItemStack(Items.golden_helmet);
         case 3:
            return new ItemStack(Items.iron_helmet);
         case 4:
            return new ItemStack(Items.diamond_helmet);
         }
      }

      return null;
   }
}
