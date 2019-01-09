package com.chocolate.chocolateQuest.items;

import com.chocolate.chocolateQuest.items.ItemArmorBase;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.world.World;

public class ItemArmorSpikedGloves extends ItemArmorBase {

   AttributeModifier strength = new AttributeModifier("ArmorStrenght1", 6.0D, 0);


   public ItemArmorSpikedGloves(int id) {
      super(ArmorMaterial.CLOTH, 1);
      this.setMaxDamage(450);
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {
      super.itemIcon = iconRegister.registerIcon("chocolatequest:spikedGloves");
   }

   public Multimap getItemAttributeModifiers() {
      HashMultimap multimap = HashMultimap.create();
      multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), this.strength);
      return multimap;
   }

   public void onUpdateEquiped(ItemStack par1ItemStack, World world, EntityLivingBase entity) {
      boolean isCollidedVertically = false;
      if((entity.isCollidedHorizontally || isCollidedVertically) && !entity.isSneaking()) {
         entity.motionY = 0.0D;
         if(entity.moveForward > 0.0F) {
            entity.motionY = 0.2D;
            if(!entity.isSwingInProgress) {
               entity.swingItem();
            }
         }

         entity.onGround = true;
      }

   }

   public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
      return "chocolatequest:textures/entity/cloud_1.png";
   }

   public boolean getIsRepairable(ItemStack itemToRepair, ItemStack item) {
      Item itemMaterial = item.getItem();
      return Items.diamond == itemMaterial || Items.iron_ingot == itemMaterial || super.getIsRepairable(itemToRepair, item);
   }

   public EnumRarity getRarity(ItemStack itemstack) {
      return EnumRarity.rare;
   }
}
