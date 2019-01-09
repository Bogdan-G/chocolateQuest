package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.magic.AwakementArmor;
import com.chocolate.chocolateQuest.magic.AwakementAutoRepair;
import com.chocolate.chocolateQuest.magic.AwakementBigSword;
import com.chocolate.chocolateQuest.magic.AwakementDagger;
import com.chocolate.chocolateQuest.magic.AwakementPistol;
import com.chocolate.chocolateQuest.magic.AwakementProperty;
import com.chocolate.chocolateQuest.magic.AwakementSpear;
import com.chocolate.chocolateQuest.magic.AwakementStaff;
import com.chocolate.chocolateQuest.magic.AwakementStaminaMax;
import com.chocolate.chocolateQuest.magic.AwakementStaminaUp;
import com.chocolate.chocolateQuest.magic.AwakementSword;
import com.chocolate.chocolateQuest.misc.EnumEnchantType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class Awakements {

   public int id = 0;
   private static int lastID = 0;
   public static Awakements property = new AwakementProperty("property", 0);
   public static Awakements autoRepair = new AwakementAutoRepair("autoRepair", 0);
   public static Awakements backStab = new AwakementDagger("backstab", 0);
   public static Awakements dodgeStamina = new AwakementDagger("dodge", 0);
   public static Awakements backDodge = new AwakementBigSword("backwardsJump", 0);
   public static Awakements berserk = new AwakementBigSword("berserk", 0);
   public static Awakements range = new AwakementSpear("range", 0);
   public static Awakements blockStamina = new AwakementSword("shieldBlock", 0);
   public static Awakements parryDamage = new AwakementSword("parryDamage", 0);
   public static Awakements ammoCapacity = new AwakementPistol("ammoCapacity", 0, 8);
   public static Awakements power = new AwakementPistol("power", 0);
   public static Awakements ammoSaver = new AwakementPistol("ammoSaver", 0);
   public static Awakements spellPower = new AwakementStaff("power", 0);
   public static Awakements spellExpansion = new AwakementStaff("spellExpansion", 0);
   public static Awakements staminaUP = new AwakementStaminaUp("stamina", 0);
   public static Awakements staminaMax = new AwakementStaminaMax("staminaMax", 0);
   public static Awakements elementProtection = new AwakementArmor("elementProtection", 0);
   static final String NBT_NAME = "awk";
   private final String name;
   private final int icon;
   public static Awakements[] awekements = new Awakements[]{property, autoRepair, backStab, dodgeStamina, backDodge, berserk, range, blockStamina, parryDamage, ammoCapacity, power, ammoSaver, spellPower, spellExpansion, staminaUP, staminaMax, elementProtection};


   public Awakements(String name, int icon) {
      this.id = lastID++;
      this.name = name;
      this.icon = icon;
   }

   public static void addEnchant(ItemStack is, Awakements awekement, int lvl) {
      if(is.stackTagCompound == null) {
         is.stackTagCompound = new NBTTagCompound();
      }

      NBTTagCompound tag = is.stackTagCompound.getCompoundTag("awk");
      tag.setShort(awekement.id + "", (short)lvl);
      is.stackTagCompound.setTag("awk", tag);
   }

   public static boolean isAwakened(ItemStack is) {
      return is.stackTagCompound == null?false:is.stackTagCompound.hasKey("awk");
   }

   public static boolean hasEnchant(ItemStack is, Awakements awekement) {
      if(is.stackTagCompound == null) {
         return false;
      } else {
         NBTTagCompound tag = is.stackTagCompound.getCompoundTag("awk");
         short lvl = tag.getShort(awekement.id + "");
         return lvl > 0;
      }
   }

   public static int getEnchantLevel(ItemStack is, Awakements awekement) {
      if(is.stackTagCompound == null) {
         return 0;
      } else {
         NBTTagCompound tag = is.stackTagCompound.getCompoundTag("awk");
         short lvl = tag.getShort(awekement.id + "");
         return lvl;
      }
   }

   public float getValueModifier(ItemStack is) {
      return 1.0F;
   }

   public void onUpdate(Entity entity, ItemStack is) {}

   public String getDescription(ItemStack is) {
      int lvl = getEnchantLevel(is, this);
      return StatCollector.translateToLocal("enchantment." + this.getName() + ".name") + " " + StatCollector.translateToLocal("enchantment.level." + lvl);
   }

   public int getMaxLevel() {
      return 4;
   }

   public void onEntityItemUpdate(EntityItem entityItem) {}

   public String getName() {
      return this.name;
   }

   public int getIconIndex() {
      return this.icon;
   }

   public int getLevelCost() {
      return 2;
   }

   public boolean canBeUsedOnItem(ItemStack is) {
      return false;
   }

   public boolean canBeAddedByNPC(int type) {
      return type == EnumEnchantType.BLACKSMITH.ordinal();
   }

   public static int getExperienceForNextLevel(ItemStack is) {
      int expRequired = 1;
      Awakements[] arr$ = awekements;
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         Awakements aw = arr$[i$];
         if(aw.canBeUsedOnItem(is)) {
            int lvl = getEnchantLevel(is, aw);
            expRequired += lvl * aw.getLevelCost();
         }
      }

      return Math.min(expRequired * expRequired / 2, 104);
   }

}
