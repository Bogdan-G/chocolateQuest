package com.chocolate.chocolateQuest.items.swords;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.API.ITwoHandedItem;
import com.chocolate.chocolateQuest.items.ILoadBar;
import com.chocolate.chocolateQuest.items.swords.ItemCQBlade;
import com.chocolate.chocolateQuest.magic.Awakements;
import com.chocolate.chocolateQuest.packets.PacketSpawnParticlesAround;
import com.chocolate.chocolateQuest.utils.BDHelper;
import com.google.common.collect.Multimap;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemBaseBroadSword extends ItemCQBlade implements ITwoHandedItem, ILoadBar {

   public static final int BASE_DAMAGE = 6;
   public static final float ELEMENT_MODIFIER = 1.4F;
   public static final int cooldown = 50;
   String texture;
   protected AttributeModifier speedInrease;
   protected AttributeModifier speedDecreaseSwing;


   public ItemBaseBroadSword(ToolMaterial mat, String texture) {
      this(mat, texture, 6.0F);
      this.texture = texture;
   }

   public ItemBaseBroadSword(ToolMaterial mat, String texture, float baseDamage) {
      super(mat, baseDamage);
      this.texture = texture;
      super.elementModifier = 1.4F;
      this.speedInrease = (new AttributeModifier(Item.field_111210_e, "Weapon modifier", 0.24D, 0)).setSaved(false);
   }

   public ItemBaseBroadSword(ToolMaterial mat, String texture, float baseDamage, float elementModifier) {
      this(mat, texture, baseDamage);
      super.elementModifier = elementModifier;
   }

   public Multimap getItemAttributeModifiers() {
      Multimap multimap = super.getItemAttributeModifiers();
      return multimap;
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {
      super.itemIcon = iconRegister.registerIcon("chocolatequest:" + this.texture);
   }

   public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityPlayer) {
      entityPlayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
      return super.onItemRightClick(itemstack, world, entityPlayer);
   }

   public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityPlayer, int useTime) {
      useTime = this.getMaxItemUseDuration(itemstack) - useTime;
      useTime = Math.min(useTime + 1, this.getMaxCharge());
      super.cachedDamage = super.weaponAttackDamage * (float)useTime / (float)this.getMaxCharge() * 2.0F + 1.0F;
      boolean crit = false;
      if(this.isReady(entityPlayer, itemstack, useTime)) {
         super.cachedDamage *= 2.0F;
         crit = true;
      }

      entityPlayer.swingItem();
      boolean hitEntity = false;
      if(!world.isRemote) {
         byte j = 1;
         List jumpLevel = world.getEntitiesWithinAABBExcludingEntity(entityPlayer, entityPlayer.boundingBox.addCoord(entityPlayer.getLookVec().xCoord * (double)j, entityPlayer.getLookVec().yCoord * (double)j, entityPlayer.getLookVec().zCoord * (double)j).expand(2.0D, 2.0D, 2.0D));
         Iterator rot = jumpLevel.iterator();

         while(rot.hasNext()) {
            Entity mx = (Entity)rot.next();
            if(mx instanceof EntityLivingBase) {
               double rotDiff = Math.abs(BDHelper.getAngleBetweenEntities(entityPlayer, mx));
               double rot1 = rotDiff - Math.abs(MathHelper.wrapAngleTo180_double((double)entityPlayer.rotationYaw));
               rot1 = Math.abs(rot1);
               if(rot1 < 40.0D) {
                  this.attackEntityWithItem(entityPlayer, mx);
                  itemstack.damageItem(1, entityPlayer);
                  hitEntity = true;
                  if(crit) {
                     PacketSpawnParticlesAround packet = new PacketSpawnParticlesAround((byte)1, mx.posX, mx.posY + 1.0D + Item.itemRand.nextDouble(), mx.posZ);
                     ChocolateQuest.channel.sendToAllAround(entityPlayer, packet, 64);
                  }
               }
            }
         }
      }

      super.cachedDamage = 0.0F;
      float rot2;
      if(world.isRemote) {
         float j1 = (float)(-Math.sin(Math.toRadians((double)entityPlayer.rotationYaw)));
         float jumpLevel1 = (float)Math.cos(Math.toRadians((double)entityPlayer.rotationYaw));
         rot2 = (float)(-Math.sin(Math.toRadians((double)entityPlayer.rotationPitch)));
         j1 *= 1.0F - Math.abs(rot2);
         jumpLevel1 *= 1.0F - Math.abs(rot2);
         world.spawnParticle("largeexplode", entityPlayer.posX + (double)j1, entityPlayer.posY + (double)rot2, entityPlayer.posZ + (double)jumpLevel1, 0.0D, 0.0D, 0.0D);
      }

      int j2 = this.getMaxItemUseDuration(itemstack) - useTime;
      if(j2 > 50) {
         this.doSpecialSkill(itemstack, world, entityPlayer);
      }

      int jumpLevel2 = Awakements.getEnchantLevel(itemstack, Awakements.backDodge);
      if(entityPlayer.onGround && (useTime > 30 || hitEntity)) {
         entityPlayer.onGround = false;
         rot2 = entityPlayer.rotationYawHead * 3.1416F / 180.0F;
         double mx1 = -Math.sin((double)rot2);
         double mz = Math.cos((double)rot2);
         double jumpSpeed = (double)(1 + jumpLevel2);
         double backSpeed = (double)(-Math.abs(entityPlayer.moveForward)) * jumpSpeed;
         entityPlayer.motionX += mx1 * backSpeed;
         entityPlayer.motionZ += mz * backSpeed;
         rot2 = (float)((double)rot2 - 1.57D);
         mx1 = -Math.sin((double)rot2);
         mz = Math.cos((double)rot2);
         entityPlayer.motionX += mx1 * (double)entityPlayer.moveStrafing * jumpSpeed;
         entityPlayer.motionZ += mz * (double)entityPlayer.moveStrafing * jumpSpeed;
         entityPlayer.motionY = 0.3D;
      }

   }

   public void onUpdate(ItemStack itemstack, World world, Entity entity, int par4, boolean par5) {
      EntityLivingBase e = (EntityLivingBase)entity;
      ItemStack entityWeapon = null;
      if(e instanceof EntityPlayer) {
         entityWeapon = ((EntityPlayer)entity).getHeldItem();
      } else if(e instanceof EntityLivingBase) {
         entityWeapon = ((EntityLivingBase)entity).getEquipmentInSlot(0);
      }

      if(entityWeapon == itemstack) {
         e.jumpMovementFactor = (float)((double)e.jumpMovementFactor - 0.01D);
         if(e.isSwingInProgress && e.onGround) {
            e.motionX *= 0.4D;
            e.motionZ *= 0.4D;
         }
      }

      if(entity instanceof EntityPlayer) {
         int berserk = Awakements.getEnchantLevel(itemstack, Awakements.berserk);
         if(berserk > 0) {
            EntityPlayer ep = (EntityPlayer)entity;
            double duration = (double)(Math.min(ep.getItemInUseDuration(), 30) * berserk / 4);
            AttributeModifier speedInrease = (new AttributeModifier(Item.field_111210_e, "Weapon modifier", duration / 100.0D, 0)).setSaved(false);
            AttributeModifier knockBackResist = (new AttributeModifier(Item.field_111210_e, "Weapon modifier", 0.25D * (double)berserk, 0)).setSaved(false);
            if(ep.getCurrentEquippedItem() == itemstack) {
               ep.getEntityAttribute(SharedMonsterAttributes.movementSpeed).removeModifier(speedInrease);
               ep.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).removeModifier(knockBackResist);
               if(ep.isUsingItem()) {
                  ep.getEntityAttribute(SharedMonsterAttributes.movementSpeed).applyModifier(speedInrease);
                  ep.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).applyModifier(knockBackResist);
               }
            } else if(ep.getCurrentEquippedItem() == null || !(ep.getCurrentEquippedItem().getItem() instanceof ItemBaseBroadSword)) {
               ep.getEntityAttribute(SharedMonsterAttributes.movementSpeed).removeModifier(speedInrease);
               ep.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).removeModifier(knockBackResist);
            }
         }
      }

      super.onUpdate(itemstack, world, entity, par4, par5);
   }

   public int getMaxItemUseDuration(ItemStack par1ItemStack) {
      return 72000;
   }

   public EnumAction getItemUseAction(ItemStack par1ItemStack) {
      return EnumAction.bow;
   }

   public void doSpecialSkill(ItemStack itemstack, World world, EntityPlayer entityPlayer) {}

   public int getMaxCharge() {
      return 60;
   }

   public boolean shouldBarShine(EntityPlayer player, ItemStack is) {
      return this.isReady(player, is, player.getItemInUseDuration());
   }

   private boolean isReady(EntityPlayer player, ItemStack is, int useTime) {
      int i = this.getMaxCharge() / 2;
      return useTime >= i - 2 && useTime <= i + 2?true:useTime >= this.getMaxCharge() - 3 && useTime < this.getMaxCharge();
   }
}
