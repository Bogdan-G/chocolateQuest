package com.chocolate.chocolateQuest.items;

import com.chocolate.chocolateQuest.client.ClientProxy;
import com.chocolate.chocolateQuest.items.ItemArmorBase;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemArmorHelmetDragon extends ItemArmorBase {

   AttributeModifier attackDamage = new AttributeModifier("ArmorDamage0", 1.0D, 1);
   AttributeModifier maxHealth = new AttributeModifier("ArmorHealth0", 10.0D, 0);


   public ItemArmorHelmetDragon() {
      super(ItemArmorBase.DRAGON, 0);
      this.setMaxDamage(2850);
   }

   public Multimap getItemAttributeModifiers() {
      HashMultimap multimap = HashMultimap.create();
      multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), this.attackDamage);
      multimap.put(SharedMonsterAttributes.maxHealth.getAttributeUnlocalizedName(), this.maxHealth);
      return multimap;
   }

   public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
      return "chocolatequest:textures/entity/dragonbd.png";
   }

   @SideOnly(Side.CLIENT)
   public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
      return ClientProxy.dragonHead;
   }

   public EnumRarity getRarity(ItemStack itemstack) {
      return EnumRarity.epic;
   }
}
