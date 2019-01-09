package com.chocolate.chocolateQuest.items;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.client.ClientProxy;
import com.chocolate.chocolateQuest.entity.boss.EntitySlimePart;
import com.chocolate.chocolateQuest.items.ItemArmorBase;
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ItemArmorSlime extends ItemArmorBase {

   int type;
   String name;
   AttributeModifier health;
   AttributeModifier knockBack;


   public ItemArmorSlime(int type, String name) {
      super(ItemArmorBase.TURTLE, type);
      this.type = type;
      this.name = name;
      this.health = new AttributeModifier("SlimeHmodifier" + type, 2.5D, 0);
      this.knockBack = new AttributeModifier("SlimeKBmodifier" + type, 0.25D, 0);
      this.setEpic();
   }

   public Multimap getItemAttributeModifiers() {
      HashMultimap multimap = HashMultimap.create();
      multimap.put(SharedMonsterAttributes.knockbackResistance.getAttributeUnlocalizedName(), this.knockBack);
      multimap.put(SharedMonsterAttributes.maxHealth.getAttributeUnlocalizedName(), this.health);
      return multimap;
   }

   public void addInformation(ItemStack is, EntityPlayer player, List list, boolean par4) {
      super.addInformation(is, player, list, par4);
   }

   public boolean hasFullSetBonus() {
      return true;
   }

   public String getFullSetBonus() {
      return "slime";
   }

   public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
      return slot == 2?"chocolatequest:textures/armor/armorSlime_1.png":"chocolatequest:textures/armor/armorSlime.png";
   }

   @SideOnly(Side.CLIENT)
   public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
      return ClientProxy.slimeArmor[armorSlot];
   }

   public void onHit(LivingHurtEvent event, ItemStack is, EntityLivingBase entity) {
      super.onHit(event, is, entity);
      if(!entity.worldObj.isRemote && entity.hurtTime == 0 && super.armorType == 1 && this.isFullSet(entity, is)) {
         float ammount = event.ammount * 1.0F / (float)entity.getTotalArmorValue() * 4.0F;
         if(event.ammount > 0.0F) {
            EntitySlimePart part = new EntitySlimePart(entity.worldObj, entity, ammount);
            part.setPosition(entity.posX, entity.posY + 1.0D, entity.posZ);
            part.motionX = Item.itemRand.nextGaussian();
            part.motionY = Item.itemRand.nextGaussian();
            part.motionZ = Item.itemRand.nextGaussian();
            entity.worldObj.spawnEntityInWorld(part);
         }
      }

   }

   public boolean getIsRepairable(ItemStack itemToRepair, ItemStack itemMaterial) {
      return itemMaterial.getItem() == ChocolateQuest.material && itemMaterial.getItemDamage() == 3 || super.getIsRepairable(itemToRepair, itemMaterial);
   }
}
