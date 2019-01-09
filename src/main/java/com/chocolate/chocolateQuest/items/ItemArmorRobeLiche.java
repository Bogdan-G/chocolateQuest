package com.chocolate.chocolateQuest.items;

import com.chocolate.chocolateQuest.client.model.ModelArmorMageRobe;
import com.chocolate.chocolateQuest.items.ItemArmorBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.world.World;

public class ItemArmorRobeLiche extends ItemArmorBase {

   public ItemArmorRobeLiche() {
      super(ArmorMaterial.GOLD, 1);
      this.setMaxDamage(950);
   }

   public void onUpdateEquiped(ItemStack par1ItemStack, World world, EntityLivingBase entity) {}

   public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
      return "chocolatequest:textures/entity/licheRobe.png";
   }

   public boolean getIsRepairable(ItemStack itemToRepair, ItemStack itemMaterial) {
      return super.getIsRepairable(itemToRepair, itemMaterial);
   }

   @SideOnly(Side.CLIENT)
   public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
      return new ModelArmorMageRobe();
   }

   public EnumRarity getRarity(ItemStack itemstack) {
      return EnumRarity.rare;
   }

   public int getEntityLifespan(ItemStack itemStack, World world) {
      return 24000;
   }
}
