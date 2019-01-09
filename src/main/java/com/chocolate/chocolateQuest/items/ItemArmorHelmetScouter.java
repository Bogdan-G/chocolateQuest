package com.chocolate.chocolateQuest.items;

import com.chocolate.chocolateQuest.items.ItemArmorBase;
import com.chocolate.chocolateQuest.utils.BDHelper;
import com.chocolate.chocolateQuest.utils.HelperPlayer;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;

public class ItemArmorHelmetScouter extends ItemArmorBase {

   public static EntityLivingBase target;
   public static int targetTimer;
   final int reachDistance = 40;


   public ItemArmorHelmetScouter() {
      super(ArmorMaterial.CLOTH, 0);
      this.setMaxDamage(650);
   }

   public void onUpdateEquiped(ItemStack par1ItemStack, World world, EntityLivingBase entity) {
      if(entity instanceof EntityPlayer) {
         EntityPlayer ep = (EntityPlayer)entity;
         ep.addPotionEffect(new PotionEffect(Potion.nightVision.id, 220, 0, true));
         if(world.isRemote) {
            Entity mop = HelperPlayer.getTarget(ep, world, 40.0D);
            if(mop != null && mop instanceof EntityLivingBase) {
               EntityLivingBase el = (EntityLivingBase)mop;
               target = el;
               targetTimer = 80;
            }
         }
      }

   }

   public String getItemStackDisplayName(ItemStack itemstack) {
      return super.getItemStackDisplayName(itemstack);
   }

   public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
      return "chocolatequest:textures/armor/cloud_1.png";
   }

   public EnumRarity getRarity(ItemStack itemstack) {
      return EnumRarity.rare;
   }

   public WeightedRandomChestContent getChestGenBase(ChestGenHooks chest, Random rnd, WeightedRandomChestContent original) {
      super.getChestGenBase(chest, rnd, original);
      ItemStack is = original.theItemId;
      if(rnd.nextInt(5) == 0) {
         double health = BDHelper.getRandomValue(rnd) * 20.0D;
         BDHelper.addAttribute(is, SharedMonsterAttributes.maxHealth.getAttributeUnlocalizedName(), new AttributeModifier(Item.field_111210_e, "helmetHealth", health, 0));
      }

      return original;
   }
}
