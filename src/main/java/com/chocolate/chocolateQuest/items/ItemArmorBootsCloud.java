package com.chocolate.chocolateQuest.items;

import com.chocolate.chocolateQuest.items.ItemArmorBase;
import com.chocolate.chocolateQuest.utils.BDHelper;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
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
import net.minecraft.util.DamageSource;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ItemArmorBootsCloud extends ItemArmorBase {

   public ItemArmorBootsCloud() {
      super(ArmorMaterial.CLOTH, 3);
      this.setMaxDamage(450);
   }

   public void onHit(LivingHurtEvent event, ItemStack is, EntityLivingBase entity) {
      super.onHit(event, is, entity);
      if(event.source == DamageSource.fall) {
         event.setCanceled(true);
      }

   }

   public ArmorMaterial getArmorMaterial() {
      return ArmorMaterial.CLOTH;
   }

   public Multimap getItemAttributeModifiers() {
      HashMultimap multimap = HashMultimap.create();
      multimap.put(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), new AttributeModifier(Item.field_111210_e, "Boots modifier", 0.15D, 2));
      return multimap;
   }

   public void onUpdateEquiped(ItemStack par1ItemStack, World world, EntityLivingBase entity) {
      boolean onGround = false;
      if(entity.fallDistance >= 3.0F) {
         entity.fallDistance = 0.0F;
         if(world.isRemote) {
            for(int i = 0; i < 3; ++i) {
               world.spawnParticle("cloud", entity.posX, entity.posY - 2.0D, entity.posZ, (double)((Item.itemRand.nextFloat() - 0.5F) / 2.0F), -0.5D, (double)((Item.itemRand.nextFloat() - 0.5F) / 2.0F));
            }
         }
      }

      if(entity.isSprinting() && world.isRemote) {
         world.spawnParticle("cloud", entity.posX, entity.posY - 1.5D, entity.posZ, (double)((Item.itemRand.nextFloat() - 0.5F) / 2.0F), 0.1D, (double)((Item.itemRand.nextFloat() - 0.5F) / 2.0F));
      }

      if(!entity.onGround && entity instanceof EntityPlayer) {
         entity.jumpMovementFactor += 0.03F;
      }

      if(entity.isCollidedHorizontally) {
         entity.stepHeight = 1.0F;
      } else {
         entity.stepHeight = 0.5F;
      }

   }

   public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
      return "chocolatequest:textures/armor/cloud_1.png";
   }

   public boolean getIsRepairable(ItemStack itemToRepair, ItemStack itemMaterial) {
      return false;
   }

   public EnumRarity getRarity(ItemStack itemstack) {
      return EnumRarity.rare;
   }

   public WeightedRandomChestContent getChestGenBase(ChestGenHooks chest, Random rnd, WeightedRandomChestContent original) {
      super.getChestGenBase(chest, rnd, original);
      ItemStack is = original.theItemId;
      double speed = BDHelper.getRandomValue(rnd) * 0.3D;
      BDHelper.addAttribute(is, SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), new AttributeModifier(Item.field_111210_e, "bootsSpeed", speed, 2));
      return original;
   }
}
