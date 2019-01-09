package com.chocolate.chocolateQuest.items;

import com.chocolate.chocolateQuest.client.ClientProxy;
import com.chocolate.chocolateQuest.items.ItemArmorBase;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.world.World;

public class ItemArmorHeavy extends ItemArmorBase {

   int type;
   Item repairItem;
   AttributeModifier speed;
   AttributeModifier knockBack;


   public ItemArmorHeavy(int type, String name, Item repairItem) {
      this(type, name, repairItem, ItemArmorBase.DRAGON);
   }

   public ItemArmorHeavy(int type, String name, Item repairItem, ArmorMaterial mat) {
      super(mat, type, name);
      this.repairItem = Items.diamond;
      this.speed = new AttributeModifier("HA Speed modifier", -0.07D, 2);
      this.knockBack = new AttributeModifier("HA Speed modifier", 0.25D, 2);
      this.type = type;
      this.repairItem = repairItem;
      this.setMaxDamage(mat.getDurability(type) + 1500);
      this.speed = new AttributeModifier("HA Speed modifier" + type, -0.06D, 2);
      this.knockBack = new AttributeModifier("HA Speed modifier", 0.25D, 0);
      super.isColoreable = type == 1;
   }

   public Multimap getItemAttributeModifiers() {
      HashMultimap multimap = HashMultimap.create();
      multimap.put(SharedMonsterAttributes.knockbackResistance.getAttributeUnlocalizedName(), this.knockBack);
      multimap.put(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), this.speed);
      return multimap;
   }

   public void onUpdateEquiped(ItemStack par1ItemStack, World world, EntityLivingBase entity) {
      if(!entity.onGround) {
         entity.jumpMovementFactor = (float)Math.max(0.015D, (double)(entity.jumpMovementFactor - 0.01F));
      }

   }

   @SideOnly(Side.CLIENT)
   public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
      return armorSlot == 1?ClientProxy.heavyArmorPlate:(armorSlot == 2?ClientProxy.heavyArmorLegs:null);
   }

   public boolean getIsRepairable(ItemStack itemToRepair, ItemStack itemMaterial) {
      return itemMaterial.getItem() == this.repairItem || super.getIsRepairable(itemToRepair, itemMaterial);
   }
}
