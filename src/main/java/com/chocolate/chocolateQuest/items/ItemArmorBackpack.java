package com.chocolate.chocolateQuest.items;

import com.chocolate.chocolateQuest.client.ClientProxy;
import com.chocolate.chocolateQuest.items.ItemArmorBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.world.World;

public class ItemArmorBackpack extends ItemArmorBase {

   public ItemArmorBackpack() {
      super(ArmorMaterial.CLOTH, 1);
      this.setMaxDamage(1850);
      super.isColoreable = true;
      super.defaultColor = 16777215;
      super.isEpic = true;
   }

   public void addInformation(ItemStack is, EntityPlayer player, List list, boolean par4) {
      super.addInformation(is, player, list, par4);
      if(player.capabilities.isCreativeMode) {
         list.add("Tags: tier (integer)");
      }

   }

   public void onUpdateEquiped(ItemStack par1ItemStack, World world, EntityLivingBase entity) {}

   public boolean requiresMultipleRenderPasses() {
      return false;
   }

   public int getColorFromItemStack(ItemStack is, int i) {
      return super.getColorFromItemStack(is, 1);
   }

   public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
      return "chocolatequest:textures/armor/bag.png";
   }

   public boolean getIsRepairable(ItemStack itemToRepair, ItemStack itemMaterial) {
      return super.getIsRepairable(itemToRepair, itemMaterial);
   }

   @SideOnly(Side.CLIENT)
   public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
      return ClientProxy.armorBag;
   }

   public EnumRarity getRarity(ItemStack itemstack) {
      return EnumRarity.uncommon;
   }
}
