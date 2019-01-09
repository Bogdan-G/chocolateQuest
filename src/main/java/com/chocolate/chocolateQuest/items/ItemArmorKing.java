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
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.world.World;

public class ItemArmorKing extends ItemArmorBase {

   public ItemArmorKing() {
      super(ArmorMaterial.DIAMOND, 1);
      this.setMaxDamage(8588);
      super.isColoreable = true;
   }

   public Multimap getItemAttributeModifiers() {
      HashMultimap multimap = HashMultimap.create();
      multimap.put(SharedMonsterAttributes.knockbackResistance.getAttributeUnlocalizedName(), new AttributeModifier("KingArmorModifier", 0.6D, 0));
      return multimap;
   }

   public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
      return "chocolatequest:textures/armor/king_1.png";
   }

   public boolean getIsRepairable(ItemStack itemToRepair, ItemStack itemMaterial) {
      return super.getIsRepairable(itemToRepair, itemMaterial);
   }

   @SideOnly(Side.CLIENT)
   public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
      return ClientProxy.kingArmor;
   }

   public EnumRarity getRarity(ItemStack itemstack) {
      return EnumRarity.epic;
   }

   public int getEntityLifespan(ItemStack itemStack, World world) {
      return 24000;
   }
}
