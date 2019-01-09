package com.chocolate.chocolateQuest.items.swords;

import com.chocolate.chocolateQuest.API.IRangedWeapon;
import com.chocolate.chocolateQuest.client.itemsRender.RenderItemBase;
import com.chocolate.chocolateQuest.entity.projectile.EntityBaseBall;
import com.chocolate.chocolateQuest.items.ILoadBar;
import com.chocolate.chocolateQuest.items.swords.ItemBaseSpear;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.world.World;

public class ItemSpearSpider extends ItemBaseSpear implements IRangedWeapon, ILoadBar {

   public ItemSpearSpider() {
      super(ToolMaterial.EMERALD, 3.0F);
      this.setMaxDamage(2048);
      super.cooldown = 40;
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {
      super.itemIcon = iconRegister.registerIcon("chocolatequest:spearSpider");
      RenderItemBase.registerIcons(iconRegister);
   }

   public void doSpecialSkill(ItemStack itemstack, World world, EntityLivingBase entityPlayer) {
      this.shootFromEntity(entityPlayer, itemstack, 0, (Entity)null);
      itemstack.damageItem(1, entityPlayer);
   }

   public EnumRarity getRarity(ItemStack itemstack) {
      return EnumRarity.epic;
   }

   public int getEntityLifespan(ItemStack itemStack, World world) {
      return 24000;
   }

   public void shootFromEntity(EntityLivingBase shooter, ItemStack is, int angle, Entity target) {
      World world = shooter.worldObj;
      if(!world.isRemote) {
         world.spawnEntityInWorld(new EntityBaseBall(world, shooter, 0, 0));
      }

   }

   public float getRange(EntityLivingBase shooter, ItemStack is) {
      return 256.0F;
   }

   public int getCooldown(EntityLivingBase shooter, ItemStack is) {
      return super.cooldown + 20;
   }

   public boolean canBeUsedByEntity(Entity entity) {
      return true;
   }

   public boolean isMeleeWeapon(EntityLivingBase shooter, ItemStack is) {
      return true;
   }

   public boolean shouldUpdate(EntityLivingBase shooter) {
      return false;
   }

   public int startAiming(ItemStack is, EntityLivingBase shooter, Entity target) {
      return 80;
   }

   public int getMaxCharge() {
      return super.cooldown;
   }

   public boolean shouldBarShine(EntityPlayer player, ItemStack is) {
      return player.getItemInUseDuration() > super.cooldown;
   }
}
