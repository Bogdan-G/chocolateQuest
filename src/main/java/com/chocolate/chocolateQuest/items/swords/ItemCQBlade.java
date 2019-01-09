package com.chocolate.chocolateQuest.items.swords;

import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.items.ItemStaffHeal;
import com.chocolate.chocolateQuest.magic.Awakements;
import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.magic.IElementHolder;
import com.chocolate.chocolateQuest.utils.BDHelper;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.world.World;

public class ItemCQBlade extends ItemSword implements IElementHolder {

   protected float weaponAttackDamage;
   protected AttributeModifier damageModifier;
   protected float elementModifier;
   protected float cachedDamage;


   public ItemCQBlade(ToolMaterial material) {
      this(material, 4.0F);
   }

   public ItemCQBlade(ToolMaterial material, float baseDamage) {
      super(material);
      this.elementModifier = 1.0F;
      this.cachedDamage = 0.0F;
      this.weaponAttackDamage = baseDamage + material.getDamageVsEntity();
   }

   public Multimap getItemAttributeModifiers() {
      HashMultimap multimap = HashMultimap.create();
      this.damageModifier = new AttributeModifier(Item.field_111210_e, "Weapon modifier", (double)this.weaponAttackDamage, 0);
      multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), this.damageModifier);
      return multimap;
   }

   public float getWeaponDamage() {
      return this.weaponAttackDamage;
   }

   public void addInformation(ItemStack is, EntityPlayer player, List list, boolean par4) {
      int len$;
      int i$;
      if(Elements.hasElements(is)) {
         Elements[] arr$ = Elements.values();
         len$ = arr$.length;

         for(i$ = 0; i$ < len$; ++i$) {
            Elements a = arr$[i$];
            float value = Elements.getElementDamage(is, a);
            if(value > 0.0F) {
               list.add(BDHelper.StringColor(a.getStringColor()) + this.getElementString(a, value));
            }
         }
      }

      ItemStaffHeal.addPotionEffectInformation(is, list, true);
      this.addPotionEffectsOnBlockInformation(list);
      super.addInformation(is, player, list, par4);
      Awakements[] var10 = Awakements.awekements;
      len$ = var10.length;

      for(i$ = 0; i$ < len$; ++i$) {
         Awakements var11 = var10[i$];
         if(Awakements.hasEnchant(is, var11)) {
            list.add(var11.getDescription(is));
         }
      }

   }

   public void addPotionEffectsOnBlockInformation(List list) {}

   protected String getElementString(Elements element, float value) {
      return "+" + BDHelper.floatToString((double)value, 2) + " " + element.getTranslatedName();
   }

   public boolean hitEntity(ItemStack is, EntityLivingBase target, EntityLivingBase entity) {
      return entity instanceof EntityPlayer?super.hitEntity(is, target, entity):true;
   }

   public void attackEntityWithItem(EntityPlayer player, Entity e) {
      AttributeModifier cacheDamageModifier = new AttributeModifier(Item.field_111210_e, "Weapon modifier", (double)this.cachedDamage, 0);
      player.getEntityAttribute(SharedMonsterAttributes.attackDamage).removeModifier(this.damageModifier);
      player.getEntityAttribute(SharedMonsterAttributes.attackDamage).applyModifier(cacheDamageModifier);
      player.attackTargetEntityWithCurrentItem(e);
      player.getEntityAttribute(SharedMonsterAttributes.attackDamage).removeModifier(cacheDamageModifier);
      player.getEntityAttribute(SharedMonsterAttributes.attackDamage).applyModifier(this.damageModifier);
   }

   public void onUpdate(ItemStack itemStack, World world, Entity entity, int par4, boolean par5) {
      if(Awakements.hasEnchant(itemStack, Awakements.property)) {
         Awakements.property.onUpdate(entity, itemStack);
      }

      if(Awakements.hasEnchant(itemStack, Awakements.autoRepair)) {
         Awakements.autoRepair.onUpdate(entity, itemStack);
      }

   }

   public boolean onEntityItemUpdate(EntityItem entityItem) {
      Awakements.property.onEntityItemUpdate(entityItem);
      return super.onEntityItemUpdate(entityItem);
   }

   public float getElementModifier(ItemStack is, Elements element) {
      return this.elementModifier;
   }

   public boolean hasSkill() {
      return false;
   }

   public int doSkill(EntityHumanBase human) {
      return 0;
   }
}
