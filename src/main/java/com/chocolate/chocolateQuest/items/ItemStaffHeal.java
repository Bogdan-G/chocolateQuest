package com.chocolate.chocolateQuest.items;

import com.chocolate.chocolateQuest.items.ItemStaffBase;
import com.chocolate.chocolateQuest.utils.PlayerManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

public class ItemStaffHeal extends Item {

   public ItemStaffHeal() {
      this.setMaxStackSize(1);
      this.setFull3D();
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {
      super.itemIcon = iconRegister.registerIcon("chocolatequest:" + this.getUnlocalizedName().replace("item.", ""));
   }

   public void addInformation(ItemStack is, EntityPlayer player, List list, boolean par4) {
      super.addInformation(is, player, list, par4);
      addPotionEffectInformation(is, list, true);
      list.add(" " + StatCollector.translateToLocal(Potion.heal.getName()).trim());
      if(is.stackTagCompound != null && is.stackTagCompound.hasKey("cost")) {
         int ammount = is.stackTagCompound.getInteger("cost");
         list.add(EnumChatFormatting.BLUE + "-" + ammount + "%" + StatCollector.translateToLocal("armorbonus.spell_damage.name"));
      }

   }

   public static void addPotionEffectInformation(ItemStack is, List list, boolean addOnHitIndicator) {
      List list1 = getPotionEffects(is);
      if(list1 != null && !list1.isEmpty()) {
         if(addOnHitIndicator) {
            list.add(EnumChatFormatting.GOLD + StatCollector.translateToLocal("strings.on_hit"));
         }

         Iterator iterator = list1.iterator();

         while(iterator.hasNext()) {
            list.add(" " + getPotionName((PotionEffect)iterator.next()));
         }
      }

   }

   public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
      if(entity instanceof EntityLivingBase) {
         applyPotionEffects(stack, (EntityLivingBase)entity);
         if(PlayerManager.useStamina(player, 8.0F * ItemStaffBase.getEntityManaCost(player))) {
            player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
            ((EntityLivingBase)entity).heal(2.0F);
            if(entity.worldObj.isRemote) {
               for(int i = 0; i < 5; ++i) {
                  entity.worldObj.spawnParticle("heart", entity.posX + (double)Item.itemRand.nextFloat() - 0.5D, entity.posY + 1.0D + (double)Item.itemRand.nextFloat(), entity.posZ + (double)Item.itemRand.nextFloat() - 0.5D, (double)(Item.itemRand.nextFloat() - 0.5F), (double)(Item.itemRand.nextFloat() - 0.5F), (double)(Item.itemRand.nextFloat() - 0.5F));
               }
            }

            entity.worldObj.playSoundEffect((double)((int)entity.posX), (double)((int)entity.posY), (double)((int)entity.posZ), "chocolatequest:magic", 0.5F, (1.0F + (entity.worldObj.rand.nextFloat() - entity.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
            return true;
         }
      }

      return false;
   }

   public static boolean applyPotionEffects(ItemStack is, EntityLivingBase entity) {
      return applyPotionEffects(getPotionEffectsTag(is), entity);
   }

   public static boolean applyPotionEffects(NBTTagList nbttaglist, EntityLivingBase entity) {
      List list = getPotionEffects(nbttaglist);
      if(list != null && !list.isEmpty()) {
         Iterator iterator = list.iterator();

         while(iterator.hasNext()) {
            entity.addPotionEffect((PotionEffect)iterator.next());
         }

         return true;
      } else {
         return false;
      }
   }

   public static String getPotionName(PotionEffect effect) {
      String s1 = StatCollector.translateToLocal(effect.getEffectName()).trim();
      if(effect.getAmplifier() > 0) {
         s1 = s1 + " " + StatCollector.translateToLocal("potion.potency." + effect.getAmplifier()).trim();
      }

      s1 = s1 + " (" + Potion.getDurationString(effect) + ")";
      Potion potion = Potion.potionTypes[effect.getPotionID()];
      if(potion.isBadEffect()) {
         s1 = EnumChatFormatting.RED + s1;
      } else {
         s1 = EnumChatFormatting.GRAY + s1;
      }

      return s1;
   }

   public static List getPotionEffects(ItemStack is) {
      return getPotionEffects(getPotionEffectsTag(is));
   }

   public static List getPotionEffects(NBTTagList nbttaglist) {
      if(nbttaglist != null) {
         ArrayList arraylist = new ArrayList();

         for(int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            PotionEffect potioneffect = PotionEffect.readCustomPotionEffectFromNBT(nbttagcompound);
            if(potioneffect != null) {
               arraylist.add(potioneffect);
            }
         }

         return arraylist;
      } else {
         return null;
      }
   }

   public static NBTTagList getPotionEffectsTag(ItemStack is) {
      if(is.hasTagCompound() && is.getTagCompound().hasKey("CustomPotionEffects", 9)) {
         new ArrayList();
         return is.getTagCompound().getTagList("CustomPotionEffects", 10);
      } else {
         return null;
      }
   }
}
