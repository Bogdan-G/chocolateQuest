package com.chocolate.chocolateQuest.items.swords;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.API.IRangedWeapon;
import com.chocolate.chocolateQuest.items.ILoadBar;
import com.chocolate.chocolateQuest.items.swords.ItemBaseSpear;
import com.chocolate.chocolateQuest.packets.PacketSpawnParticlesAround;
import com.chocolate.chocolateQuest.utils.BDHelper;
import com.chocolate.chocolateQuest.utils.HelperDamageSource;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemSpearFire extends ItemBaseSpear implements IRangedWeapon, ILoadBar {

   public ItemSpearFire() {
      super(ToolMaterial.EMERALD, 3.0F);
      this.setMaxDamage(2048);
      super.cooldown = 25;
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {
      super.itemIcon = iconRegister.registerIcon("chocolatequest:spearDwarf");
   }

   public void doSpecialSkill(ItemStack itemstack, World world, EntityLivingBase shooter) {
      this.shootFromEntity(shooter, itemstack, 0, (Entity)null);
      itemstack.damageItem(1, shooter);
   }

   public EnumRarity getRarity(ItemStack itemstack) {
      return EnumRarity.epic;
   }

   public int getEntityLifespan(ItemStack itemStack, World world) {
      return 24000;
   }

   public float getRange(EntityLivingBase shooter, ItemStack is) {
      return 64.0F;
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

   public void shootFromEntity(EntityLivingBase shooter, ItemStack is, int angle, Entity target) {
      World world = shooter.worldObj;
      world.playSoundEffect((double)((int)shooter.posX), (double)((int)shooter.posY), (double)((int)shooter.posZ), "fire.fire", 4.0F, (1.0F + (Item.itemRand.nextFloat() - Item.itemRand.nextFloat()) * 0.2F) * 0.7F);
      float x = (float)(-Math.sin(Math.toRadians((double)shooter.rotationYaw)));
      float z = (float)Math.cos(Math.toRadians((double)shooter.rotationYaw));
      float y = (float)(-Math.sin(Math.toRadians((double)shooter.rotationPitch)));
      float var10000 = x * (1.0F - Math.abs(y));
      var10000 = z * (1.0F - Math.abs(y));
      if(!world.isRemote) {
         PacketSpawnParticlesAround dist = new PacketSpawnParticlesAround((byte)2, (double)shooter.getEntityId(), 0.0D, 0.0D);
         ChocolateQuest.channel.sendToAllAround(shooter, dist, 64);
      }

      byte dist1 = 15;
      List list = world.getEntitiesWithinAABBExcludingEntity(shooter, shooter.boundingBox.addCoord(shooter.getLookVec().xCoord * (double)dist1, shooter.getLookVec().yCoord * (double)dist1, shooter.getLookVec().zCoord * (double)dist1).expand(1.0D, 1.0D, 1.0D));
      Iterator i$ = list.iterator();

      while(i$.hasNext()) {
         Entity e = (Entity)i$.next();
         if(e instanceof EntityLiving) {
            double rotDiff = Math.abs(BDHelper.getAngleBetweenEntities(shooter, e));
            double rot = rotDiff - Math.abs(MathHelper.wrapAngleTo180_double((double)shooter.rotationYaw));
            rot = Math.abs(rot);
            if(rot < 10.0D && shooter.canEntityBeSeen(e)) {
               e.setFire(6);
               e.attackEntityFrom(HelperDamageSource.causeFireDamage(shooter), 4.0F);
            }
         }
      }

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
