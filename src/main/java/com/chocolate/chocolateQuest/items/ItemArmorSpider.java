package com.chocolate.chocolateQuest.items;

import com.chocolate.chocolateQuest.ChocolateQuest;
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
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemArmorSpider extends ItemArmorBase {

   int type;
   String name;
   AttributeModifier speed;


   public ItemArmorSpider(int type, String name) {
      super(ItemArmorBase.MONSTER_FUR, type);
      this.type = type;
      this.name = name;
      this.speed = new AttributeModifier("SpiderArmodifier" + type, 0.05D, 2);
      this.setEpic();
   }

   public Multimap getItemAttributeModifiers() {
      HashMultimap multimap = HashMultimap.create();
      multimap.put(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), this.speed);
      return multimap;
   }

   public boolean hasFullSetBonus() {
      return true;
   }

   public String getFullSetBonus() {
      return "climb";
   }

   public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
      return slot == 2?"chocolatequest:textures/armor/armorSpider_2.png":"chocolatequest:textures/armor/armorSpider.png";
   }

   public void onUpdateEquiped(ItemStack par1ItemStack, World world, EntityLivingBase entity) {
      boolean isCollidedVertically = false;
      if(this.isFullSet(entity, par1ItemStack)) {
         if(entity.isCollidedHorizontally || isCollidedVertically) {
            if(!entity.isSneaking()) {
               entity.motionY = 0.0D;
               if(entity.moveForward > 0.0F) {
                  entity.motionY = 0.2D;
               }
            }

            entity.onGround = true;
         }

         entity.fallDistance = 0.0F;
      }

   }

   public boolean getIsRepairable(ItemStack itemToRepair, ItemStack itemMaterial) {
      return itemMaterial.getItem() == ChocolateQuest.material && itemMaterial.getItemDamage() == 3 || super.getIsRepairable(itemToRepair, itemMaterial);
   }

   @SideOnly(Side.CLIENT)
   public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
      return armorSlot == 0?ClientProxy.turtleHelmetModel:(armorSlot == 1?ClientProxy.armorSpider:(armorSlot == 2?ClientProxy.armorSpiderLegs:null));
   }
}
