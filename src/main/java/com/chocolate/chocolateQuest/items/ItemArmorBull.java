package com.chocolate.chocolateQuest.items;

import com.chocolate.chocolateQuest.client.ClientProxy;
import com.chocolate.chocolateQuest.items.ItemArmorBase;
import com.chocolate.chocolateQuest.utils.PlayerManager;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;

public class ItemArmorBull extends ItemArmorBase {

   int type;
   String name;
   AttributeModifier strength;


   public ItemArmorBull(int type, String name) {
      super(ItemArmorBase.MONSTER_FUR, type);
      this.type = type;
      this.name = name;
      this.strength = new AttributeModifier("BullArmodifier" + type, 1.0D, 0);
      this.setEpic();
   }

   public Multimap getItemAttributeModifiers() {
      HashMultimap multimap = HashMultimap.create();
      multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), this.strength);
      return multimap;
   }

   public boolean hasFullSetBonus() {
      return true;
   }

   public String getFullSetBonus() {
      return "stamina_master";
   }

   public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
      return slot == 2?"chocolatequest:textures/armor/bull_2.png":"chocolatequest:textures/armor/bull_1.png";
   }

   @SideOnly(Side.CLIENT)
   public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
      return (ModelBiped)(armorSlot == 0?ClientProxy.bullHead:(armorSlot == 1?ClientProxy.bullPlate:(armorSlot == 2?ClientProxy.armorSpiderLegs:super.getArmorModel(entityLiving, itemStack, armorSlot))));
   }

   public EnumRarity getRarity(ItemStack itemstack) {
      return EnumRarity.epic;
   }

   public void onUpdateEquiped(ItemStack itemStack, World world, EntityLivingBase entityliving) {
      if(super.armorType == 1 && this.isFullSet(entityliving, itemStack)) {
         if(entityliving instanceof EntityPlayer) {
            EntityPlayer list = (EntityPlayer)entityliving;
            PlayerManager.addStamina(list, 0.1F);
         }

         if(entityliving.isSprinting()) {
            entityliving.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 0, 1, true));
            List var7 = world.getEntitiesWithinAABBExcludingEntity(entityliving, entityliving.boundingBox.expand(1.1D, 1.1D, 1.1D));

            for(int k2 = 0; k2 < var7.size(); ++k2) {
               Entity entity = (Entity)var7.get(k2);
               if(entity instanceof EntityLivingBase && !entityliving.isOnSameTeam((EntityLivingBase)entity)) {
                  entity.attackEntityFrom(new EntityDamageSource(DamageSource.generic.damageType, entityliving), 4.0F);
               }
            }
         }
      }

   }
}
