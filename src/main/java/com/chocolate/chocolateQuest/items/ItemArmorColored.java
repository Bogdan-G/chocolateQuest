package com.chocolate.chocolateQuest.items;

import com.chocolate.chocolateQuest.client.ClientProxy;
import com.chocolate.chocolateQuest.items.ItemArmorBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;

public class ItemArmorColored extends ItemArmorBase {

   Item repairItem;


   public ItemArmorColored(int type, String name, Item repairItem, int defaultColor) {
      this(type, name, repairItem, ItemArmorBase.DRAGON, defaultColor);
   }

   public ItemArmorColored(ArmorMaterial material, int renderIndex, String name, int defaultColor) {
      super(material, renderIndex, name);
      this.repairItem = Items.diamond;
      super.name = name;
      super.defaultColor = defaultColor;
      super.isColoreable = true;
   }

   public ItemArmorColored(int type, String name, Item repairItem, ArmorMaterial mat, int defaultColor) {
      this(mat, type, name, defaultColor);
      this.repairItem = repairItem;
   }

   @SideOnly(Side.CLIENT)
   public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
      return ClientProxy.coloredArmor[armorSlot];
   }

   public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
      return slot == 2?"chocolatequest:textures/armor/armor_" + super.name + "_2_overlay.png":"chocolatequest:textures/armor/armor_" + super.name + "_1_overlay.png";
   }

   public boolean getIsRepairable(ItemStack itemToRepair, ItemStack itemMaterial) {
      return itemMaterial.getItem() == this.repairItem || super.getIsRepairable(itemToRepair, itemMaterial);
   }
}
