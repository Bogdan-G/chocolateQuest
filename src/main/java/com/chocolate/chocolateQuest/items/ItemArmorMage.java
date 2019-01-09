package com.chocolate.chocolateQuest.items;

import com.chocolate.chocolateQuest.client.ClientProxy;
import com.chocolate.chocolateQuest.items.ItemArmorBase;
import com.chocolate.chocolateQuest.utils.BDHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.StatCollector;

public class ItemArmorMage extends ItemArmorBase {

   int cooldown;
   int cost;


   public ItemArmorMage(ArmorMaterial mat, int type, int cooldown, int manaCost) {
      super(mat, type);
      this.cooldown = cooldown;
      this.cost = manaCost;
      super.isColoreable = true;
      super.defaultColor = 16777215;
      super.isEpic = true;
   }

   public void addInformation(ItemStack is, EntityPlayer player, List list, boolean par4) {
      super.addInformation(is, player, list, par4);
      list.add("");
      list.add(BDHelper.formatNumberToDisplay(this.getCooldownReduction(is), true) + "% " + StatCollector.translateToLocal("armorbonus.cast_speed.name"));
      list.add(BDHelper.formatNumberToDisplay(this.getCostReduction(is), true) + "% " + StatCollector.translateToLocal("armorbonus.spell_cost.name"));
   }

   public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
      return "chocolatequest:textures/armor/mage_tunic.png";
   }

   @SideOnly(Side.CLIENT)
   public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
      return ClientProxy.armorMagePlate[armorSlot];
   }

   public EnumRarity getRarity(ItemStack itemstack) {
      return EnumRarity.rare;
   }

   public boolean requiresMultipleRenderPasses() {
      return false;
   }

   public int getColorFromItemStack(ItemStack is, int i) {
      return super.isColoreable?super.getColorFromItemStack(is, 1):super.getColorFromItemStack(is, i);
   }

   public boolean getIsRepairable(ItemStack itemToRepair, ItemStack itemMaterial) {
      return itemMaterial.getItem() == Items.leather;
   }

   public int getCooldownReduction(ItemStack is) {
      return this.cooldown;
   }

   public int getCostReduction(ItemStack is) {
      return this.cost;
   }
}
