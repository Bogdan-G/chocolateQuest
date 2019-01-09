package com.chocolate.chocolateQuest.utils;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.items.ItemArmorBull;
import com.chocolate.chocolateQuest.items.ItemArmorMage;
import com.chocolate.chocolateQuest.items.ItemArmorSlime;
import com.chocolate.chocolateQuest.items.ItemArmorSpider;
import com.chocolate.chocolateQuest.items.ItemArmorTurtle;
import com.chocolate.chocolateQuest.items.ItemHookShoot;
import com.chocolate.chocolateQuest.items.ItemStaffBase;
import com.chocolate.chocolateQuest.items.gun.ItemPistol;
import com.chocolate.chocolateQuest.items.swords.ItemCQBlade;
import com.chocolate.chocolateQuest.magic.Awakements;
import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.magic.IElementWeak;
import com.google.common.collect.Multimap;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class BDHelper {

   public static final ResourceLocation texture = new ResourceLocation("chocolateQuest:textures/entity/items.png");
   public static final ResourceLocation guiButtonsTexture = new ResourceLocation("chocolateQuest:textures/entity/gui.png");
   public static final ResourceLocation guiParticleTexture = new ResourceLocation("chocolateQuest:textures/entity/particles.png");


   public static Elements getElementFromDamageSource(DamageSource ds) {
      Elements element = Elements.physic;
      if(ds.isFireDamage()) {
         element = Elements.fire;
      } else if(ds.isMagicDamage()) {
         element = Elements.magic;
      } else if(ds.isExplosion()) {
         element = Elements.blast;
      }

      return element;
   }

   public static double getDamageReductionForElement(EntityLivingBase entity, Elements element, boolean checkEnchantments) {
      double damageReduction = 1.0D;
      if(checkEnchantments) {
         int enchantType = -1;
         if(element == Elements.fire) {
            enchantType = Enchantment.fireProtection.effectId;
         } else if(element == Elements.blast) {
            enchantType = Enchantment.blastProtection.effectId;
         }

         if(enchantType != -1) {
            damageReduction = getEnchantmentDamageReduction(entity, enchantType);
         }
      }

      damageReduction *= getNaturalDamageReduction(entity, element);
      damageReduction *= getAwakementDamageReduction(entity, element);
      return damageReduction;
   }

   public static double getEnchantmentDamageReduction(EntityLivingBase entity, int enchantType) {
      double epf = enchantType == Enchantment.protection.effectId?0.75D:(enchantType == Enchantment.fireProtection.effectId?1.25D:1.5D);
      int totalEnchantProtection = 0;

      for(int avgDamage = 0; avgDamage < 4; ++avgDamage) {
         ItemStack is = entity.getEquipmentInSlot(1 + avgDamage);
         if(is != null) {
            int enchantLevel = EnchantmentHelper.getEnchantmentLevel(enchantType, is);
            if(enchantLevel > 0) {
               totalEnchantProtection = (int)((double)totalEnchantProtection + Math.floor((double)(6 + enchantLevel * enchantLevel) * epf / 3.0D));
            }
         }
      }

      totalEnchantProtection = Math.min(totalEnchantProtection, 20);
      double var8 = 0.75D;
      return 1.0D - (double)totalEnchantProtection * 0.04D * var8;
   }

   public static double getNaturalDamageReduction(EntityLivingBase entity, Elements element) {
      double damageReduction = 1.0D;
      if(entity instanceof IElementWeak) {
         double naturalDefense = 0.0D;
         if(element == Elements.fire) {
            naturalDefense = (double)((IElementWeak)entity).getFireDefense() * 0.01D;
         } else if(element == Elements.physic) {
            naturalDefense = (double)((IElementWeak)entity).getPhysicDefense() * 0.01D;
         } else if(element == Elements.magic) {
            naturalDefense = (double)((IElementWeak)entity).getMagicDefense() * 0.01D;
         } else if(element == Elements.blast) {
            naturalDefense = (double)((IElementWeak)entity).getBlastDefense() * 0.01D;
         }

         damageReduction = 1.0D - naturalDefense;
      }

      return damageReduction;
   }

   public static double getAwakementDamageReduction(EntityLivingBase entity, Elements element) {
      double damageReduction = 1.0D;

      for(int i = 0; i < 4; ++i) {
         ItemStack armor = entity.getEquipmentInSlot(1 + i);
         if(armor != null && Awakements.getEnchantLevel(armor, Awakements.elementProtection) > 0) {
            damageReduction -= 0.05D + (double)Elements.getElementValue(armor, element) * 0.0375D;
         }
      }

      return damageReduction;
   }

   public static double getRandomValue(Random random) {
      byte it = 3;
      double d = 0.0D;

      for(int i = 0; i < it; ++i) {
         if(random.nextInt(it) >= i) {
            d += random.nextDouble();
         }
      }

      return d / (double)it;
   }

   public static void addAttribute(ItemStack is, String attributeName, AttributeModifier attribute) {
      if(is.stackTagCompound == null) {
         is.stackTagCompound = new NBTTagCompound();
      }

      NBTTagList tagList;
      if(is.stackTagCompound.hasKey("AttributeModifiers")) {
         tagList = is.stackTagCompound.getTagList("AttributeModifiers", 10);
      } else {
         tagList = new NBTTagList();
      }

      NBTTagCompound tag = new NBTTagCompound();
      tag.setString("AttributeName", attributeName);
      tag.setString("Name", attribute.getName());
      tag.setDouble("Amount", attribute.getAmount());
      tag.setInteger("Operation", attribute.getOperation());
      tag.setLong("UUIDLeast", attribute.getID().getLeastSignificantBits());
      tag.setLong("UUIDMost", attribute.getID().getMostSignificantBits());
      tagList.appendTag(tag);
      is.stackTagCompound.setTag("AttributeModifiers", tagList);
   }

   public static float getEntityValue(EntityHumanBase entity) {
      float entityValue = 0.0F;
      float healthValue = entity.getMaxHealth() / 4.0F;
      float armorValue = 0.0F;

      for(int is = 0; is < 4; ++is) {
         ItemStack is1 = entity.getEquipmentInSlot(is + 1);
         if(is1 != null && is1.getItem() instanceof ItemArmor) {
            armorValue += (float)((ItemArmor)is1.getItem()).getArmorMaterial().getDamageReductionAmount(is);
            if(is1.getItem() instanceof ItemArmorMage) {
               armorValue += 4.0F;
            }

            if(is1.getItem() instanceof ItemArmorTurtle) {
               armorValue += 2.0F;
            }

            if(is1.getItem() instanceof ItemArmorSpider) {
               ++armorValue;
            }

            if(is1.getItem() instanceof ItemArmorSlime) {
               armorValue += 2.0F;
            }

            if(is1.getItem() instanceof ItemArmorBull) {
               armorValue += 2.0F;
            }
         }
      }

      armorValue /= 10.0F;
      entityValue = healthValue * (1.0F + armorValue);
      if(entity.isCaptain()) {
         ++entityValue;
      }

      ItemStack var6 = entity.getEquipmentInSlot(0);
      entityValue += getWeaponValue(var6) / 3.0F;
      var6 = entity.getLeftHandItem();
      entityValue += getWeaponValue(var6) / 3.0F;
      return entityValue;
   }

   public static float getWeaponValue(ItemStack is) {
      float weaponDamage = 0.0F;
      if(is != null) {
         if(is.getItem() instanceof ItemCQBlade) {
            weaponDamage = ((ItemCQBlade)is.getItem()).getWeaponDamage();
         }

         if(is.getItem() instanceof ItemSword) {
            weaponDamage = 1.0F + ((ItemSword)is.getItem()).func_150931_i();
         }

         if(is.getItem() instanceof ItemBow) {
            weaponDamage = 4.0F;
         }

         if(is.getItem() instanceof ItemStaffBase) {
            weaponDamage = 1.0F + ItemStaffBase.getMagicDamage(is) + (float)Awakements.getEnchantLevel(is, Awakements.spellExpansion);
         }

         if(is.getItem() instanceof ItemPistol) {
            weaponDamage = 5.0F;
         }

         if(is.getItem() instanceof ItemHookShoot) {
            weaponDamage += 5.0F;
         }
      }

      return weaponDamage;
   }

   public static void colorArmor(ItemStack is, int color) {
      NBTTagCompound tag = is.stackTagCompound == null?new NBTTagCompound():is.stackTagCompound;
      NBTTagCompound tagDisplay = new NBTTagCompound();
      tagDisplay.setInteger("color", color);
      tag.setTag("display", tagDisplay);
      is.stackTagCompound = tag;
   }

   public static ResourceLocation getItemTexture() {
      return texture;
   }

   public static ResourceLocation getParticleTexture() {
      return guiParticleTexture;
   }

   public static void println(String msg) {
      FMLLog.getLogger().info(msg);
   }

   public static void printWarn(String msg) {
      FMLLog.getLogger().warn(msg);
   }

   public static String getAppDir() {
      String s = Loader.instance().getConfigDir().getAbsolutePath();
      return s;
   }

   public static String getInfoDir() {
      return "/Chocolate/";
   }

   public static String getChocolateDir() {
      return getAppDir() + getInfoDir();
   }

   public static String getQuestDir() {
      return getInfoDir() + "Quest/";
   }

   public static boolean getBooleanProperty(Properties prop, String name, boolean defaultValue) {
      String s = prop.getProperty(name);
      if(s == null) {
         return defaultValue;
      } else {
         s = s.trim();
         return s.equals("true");
      }
   }

   public static int getIntegerProperty(Properties prop, String name, int defaultValue) {
      String s = prop.getProperty(name);
      if(s == null) {
         return defaultValue;
      } else {
         try {
            s = s.trim();
            int ret = Integer.parseInt(s);
            return ret;
         } finally {
            ;
         }
      }
   }

   public static int getIntegerProperty(Properties prop, String name, int defaultValue, int minValue, int maxValue) {
      int i = getIntegerProperty(prop, name, defaultValue);
      i = Math.max(minValue, i);
      i = Math.min(maxValue, i);
      return i;
   }

   public static ItemStack EnchantItemRandomly(ItemStack itemstack, Random random) {
      if(itemstack.getItem() instanceof ItemArmor) {
         ItemArmor cant = (ItemArmor)itemstack.getItem();
         byte cant1;
         if(cant.armorType == 0) {
            cant1 = 7;
            if(random.nextInt(cant1) == 0) {
               itemstack.addEnchantment(Enchantment.protection, random.nextInt(4) + 1);
            }

            if(random.nextInt(cant1) == 0) {
               itemstack.addEnchantment(Enchantment.fireProtection, random.nextInt(4) + 1);
            }

            if(random.nextInt(cant1) == 0) {
               itemstack.addEnchantment(Enchantment.blastProtection, random.nextInt(4) + 1);
            }

            if(random.nextInt(cant1) == 0) {
               itemstack.addEnchantment(Enchantment.projectileProtection, random.nextInt(4) + 1);
            }

            if(random.nextInt(cant1) == 0) {
               itemstack.addEnchantment(Enchantment.respiration, random.nextInt(3) + 1);
            }

            if(random.nextInt(cant1) == 0) {
               itemstack.addEnchantment(Enchantment.aquaAffinity, 1);
            }
         } else if(cant.armorType == 3) {
            cant1 = 5;
            if(random.nextInt(cant1) == 0) {
               itemstack.addEnchantment(Enchantment.protection, random.nextInt(4) + 1);
            }

            if(random.nextInt(cant1) == 0) {
               itemstack.addEnchantment(Enchantment.fireProtection, random.nextInt(4) + 1);
            }

            if(random.nextInt(cant1) == 0) {
               itemstack.addEnchantment(Enchantment.blastProtection, random.nextInt(4) + 1);
            }

            if(random.nextInt(cant1) == 0) {
               itemstack.addEnchantment(Enchantment.projectileProtection, random.nextInt(4) + 1);
            }

            if(random.nextInt(cant1) == 0) {
               itemstack.addEnchantment(Enchantment.featherFalling, random.nextInt(4) + 1);
            }
         } else {
            cant1 = 6;
            if(random.nextInt(cant1) == 0) {
               itemstack.addEnchantment(Enchantment.protection, random.nextInt(4) + 1);
            }

            if(random.nextInt(cant1) == 0) {
               itemstack.addEnchantment(Enchantment.fireProtection, random.nextInt(4) + 1);
            }

            if(random.nextInt(cant1) == 0) {
               itemstack.addEnchantment(Enchantment.blastProtection, random.nextInt(4) + 1);
            }

            if(random.nextInt(cant1) == 0) {
               itemstack.addEnchantment(Enchantment.projectileProtection, random.nextInt(4) + 1);
            }

            if(random.nextInt(cant1) == 0) {
               itemstack.addEnchantment(Enchantment.thorns, random.nextInt(3) + 1);
            }
         }
      } else if(itemstack.getItem() instanceof ItemSword) {
         byte cant2 = 7;
         if(random.nextInt(cant2) == 0) {
            itemstack.addEnchantment(Enchantment.sharpness, random.nextInt(2) + 1);
         }

         if(random.nextInt(cant2) == 0) {
            itemstack.addEnchantment(Enchantment.fireAspect, random.nextInt(2) + 1);
         }

         if(random.nextInt(cant2) == 0) {
            itemstack.addEnchantment(Enchantment.smite, random.nextInt(5) + 1);
         }

         if(random.nextInt(cant2) == 0) {
            itemstack.addEnchantment(Enchantment.baneOfArthropods, random.nextInt(5) + 1);
         }

         if(random.nextInt(cant2) == 0) {
            itemstack.addEnchantment(Enchantment.knockback, 1);
         }
      }

      return itemstack;
   }

   public static int getRandomIndex(int[] weights, Random random) {
      int maxNum = 0;
      int[] randomNum = weights;
      int index = weights.length;

      int weightSum;
      for(weightSum = 0; weightSum < index; ++weightSum) {
         int i = randomNum[weightSum];
         maxNum += i;
      }

      int var7 = random.nextInt(weights.length);
      index = 0;

      for(weightSum = weights[0]; weightSum <= var7; weightSum += weights[index]) {
         ++index;
      }

      return index;
   }

   public static double getRotationDiffBetweenEntity(Entity entity, Entity target) {
      double angle;
      for(angle = (double)(entity.rotationYaw - target.rotationYaw); angle > 360.0D; angle -= 360.0D) {
         ;
      }

      while(angle < 0.0D) {
         angle += 360.0D;
      }

      angle = Math.abs(angle - 180.0D);
      return angle;
   }

   public static double getAngleBetweenEntities(Entity entity, Entity target) {
      double d = entity.posX - target.posX;
      double d2 = entity.posZ - target.posZ;
      double angle = Math.atan2(d, d2);
      angle = angle * 180.0D / 3.141592D;
      angle = -MathHelper.wrapAngleTo180_double(angle - 180.0D);
      return angle;
   }

   @Deprecated
   public static String StringColor(String color) {
      return '\u00a7' + color;
   }

   public static double getWeaponDamage(ItemStack weapon) {
      double damage = 0.0D;
      Multimap map = weapon.getAttributeModifiers();
      if(map.containsKey("generic.attackDamage")) {
         AttributeModifier a = (AttributeModifier)map.get("generic.attackDamage").toArray()[0];
         damage += (double)((float)a.getAmount());
      }

      return damage;
   }

   public static void writeCompressed(NBTTagCompound tagCompound, File file) throws IOException {
      CompressedStreamTools.writeCompressed(tagCompound, new FileOutputStream(file));
   }

   public static NBTTagCompound readCompressed(File file) {
      try {
         return CompressedStreamTools.readCompressed(new FileInputStream(file));
      } catch (Exception var2) {
         printWarn("Error reading: " + file.getPath());
         return null;
      }
   }

   public static float getColorRed(int color) {
      return (float)(color >> 16 & 255) / 256.0F;
   }

   public static float getColorGreen(int color) {
      return (float)(color >> 8 & 255) / 256.0F;
   }

   public static float getColorBlue(int color) {
      return (float)(color >> 0 & 255) / 256.0F;
   }

   public static ItemStack getStackFromString(String itemText) {
      String[] textArray = itemText.split(" ");
      ItemStack stack = null;
      if(textArray.length > 0) {
         String name = textArray[0];
         Item currentItem = (Item)Item.itemRegistry.getObject(name);
         int e;
         if(currentItem != null) {
            int json = 1;
            e = 0;

            try {
               if(textArray.length > 1) {
                  json = Integer.parseInt(textArray[1]);
               }

               if(textArray.length > 2) {
                  e = Integer.parseInt(textArray[2]);
               }
            } catch (NumberFormatException var9) {
               ;
            }

            stack = new ItemStack(currentItem, json, e);
         }

         if(textArray.length > 3 && stack != null) {
            String var10 = "";

            for(e = 3; e < textArray.length; ++e) {
               var10 = var10 + textArray[3];
            }

            try {
               NBTBase var11 = JSONToNBT(var10);
               if(var11 instanceof NBTTagCompound) {
                  stack.stackTagCompound = (NBTTagCompound)var11;
               }
            } catch (NBTException var8) {
               var8.printStackTrace();
            }
         }
      }

      return stack;
   }

   public static NBTBase JSONToNBT(String value) throws NBTException {
      NBTBase nbt = JsonToNBT.func_150315_a(value);
      return nbt;
   }

   public static int getIntegerFromString(String value) {
      int i = 0;

      try {
         i = Integer.valueOf(value).intValue();
      } catch (NumberFormatException var3) {
         var3.printStackTrace();
      }

      return i;
   }

   public static boolean compareTags(NBTTagCompound tagWithKeys, NBTTagCompound tagToCheck) {
      if(tagWithKeys == null && tagToCheck == null) {
         return true;
      } else if(tagWithKeys != null && tagToCheck != null) {
         Set set = tagWithKeys.func_150296_c();
         Iterator iterator = set.iterator();

         String name;
         do {
            if(!iterator.hasNext()) {
               return true;
            }

            name = (String)iterator.next();
         } while(tagWithKeys.getTag(name).equals(tagToCheck.getTag(name)));

         return false;
      } else {
         return false;
      }
   }

   public static String formatNumberToDisplay(int ammount) {
      return formatNumberToDisplay(ammount, false);
   }

   public static String formatNumberToDisplay(int ammount, boolean invertColors) {
      String damage = "";
      if((ammount < 0 || invertColors) && (!invertColors || ammount > 0)) {
         damage = EnumChatFormatting.RED + " ";
      } else {
         damage = EnumChatFormatting.BLUE + " ";
      }

      damage = damage + ammount;
      return damage;
   }

   public static String formatNumberToDisplay(float ammount) {
      return formatNumberToDisplay(ammount, false);
   }

   public static String formatNumberToDisplay(float ammount, boolean invertColors) {
      String damage = "";
      if((ammount < 0.0F || invertColors) && (!invertColors || ammount > 0.0F)) {
         damage = EnumChatFormatting.RED + " ";
         if(invertColors) {
            damage = damage + "+";
         }
      } else {
         damage = EnumChatFormatting.BLUE + " ";
         if(!invertColors) {
            damage = damage + "";
         }
      }

      damage = damage + floatToString((double)ammount, 2);
      return damage;
   }

   public static String floatToString(double d, int decimals) {
      String value = Double.toString(d);
      int index = value.indexOf(".");
      if(index <= decimals) {
         index += decimals;
      }

      return index > value.length()?value:value.substring(0, index);
   }

   public String toRomanNumber(int i) {
      String s = " ";

      while(i > 0) {
         if(i - 1000 >= 0) {
            s = s + "M";
            i -= 1000;
         } else if(i - 900 >= 0) {
            s = s + "CM";
            i -= 900;
         } else if(i - 500 >= 0) {
            s = s + "D";
            i -= 500;
         } else if(i - 400 >= 0) {
            s = s + "CD";
            i -= 400;
         } else if(i - 100 >= 0) {
            s = s + "C";
            i -= 100;
         } else if(i - 90 >= 0) {
            s = s + "XC";
            i -= 90;
         } else if(i - 50 >= 0) {
            s = s + "L";
            i -= 50;
         } else if(i - 40 >= 0) {
            s = s + "XL";
            i -= 40;
         } else if(i - 10 >= 0) {
            s = s + "X";
            i -= 10;
         } else if(i - 9 >= 0) {
            s = s + "IX";
            i -= 9;
         } else if(i - 5 >= 0) {
            s = s + "V";
            i -= 5;
         } else if(i - 4 >= 0) {
            s = s + "IV";
            i -= 4;
         } else if(i - 1 >= 0) {
            s = s + "I";
            --i;
         }
      }

      return s;
   }

}
