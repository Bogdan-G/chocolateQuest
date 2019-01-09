package com.chocolate.chocolateQuest.items;

import com.chocolate.chocolateQuest.API.IRangedWeapon;
import com.chocolate.chocolateQuest.entity.EntitySummonedUndead;
import com.chocolate.chocolateQuest.items.ItemEntityPlacer;
import com.chocolate.chocolateQuest.items.ItemStaffBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;

public class ItemCursedBone extends ItemEntityPlacer implements IRangedWeapon {

   public static final String TAG_WITHER = "wither";
   public static final String TAG_HOUND = "hound";
   public static final String TAG_LEVEL = "level";


   public ItemCursedBone() {
      this.setMaxStackSize(1);
      this.setMaxDamage(8);
   }

   @SideOnly(Side.CLIENT)
   public void addInformation(ItemStack is, EntityPlayer player, List list, boolean par4) {
      if(is.stackTagCompound != null) {
         if(is.stackTagCompound.hasKey("wither")) {
            list.add("Wither");
         }

         if(is.stackTagCompound.hasKey("hound")) {
            list.add("Hound");
         }
      }

      if(player.capabilities.isCreativeMode) {
         list.add("Tags: wither(boolean), hound(boolean) ");
      }

   }

   @SideOnly(Side.CLIENT)
   public void getSubItems(Item item, CreativeTabs par2CreativeTabs, List itemList) {
      ItemStack is = new ItemStack(this);
      itemList.add(is);
      is = new ItemStack(this);
      is.stackTagCompound = new NBTTagCompound();
      is.stackTagCompound.setBoolean("hound", true);
      itemList.add(is);
      is = new ItemStack(this);
      is.stackTagCompound = new NBTTagCompound();
      is.stackTagCompound.setBoolean("wither", true);
      itemList.add(is);
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {
      super.itemIcon = iconRegister.registerIcon("chocolatequest:cursedBone");
   }

   public EnumRarity getRarity(ItemStack itemstack) {
      return EnumRarity.uncommon;
   }

   public Entity getEntity(ItemStack stack, EntityPlayer entityPlayer, World world, int x, int y, int z) {
      return this.getEntity(stack, entityPlayer, world);
   }

   public EntitySummonedUndead getEntity(ItemStack stack, EntityLivingBase entityPlayer, World world) {
      byte type = 0;
      int lvl = 0;
      if(stack.stackTagCompound != null) {
         if(stack.stackTagCompound.hasKey("wither")) {
            type = 3;
         }

         if(stack.stackTagCompound.hasKey("hound")) {
            type = 2;
         }

         if(stack.stackTagCompound.hasKey("level")) {
            lvl = stack.stackTagCompound.getInteger("level");
         }
      }

      EntitySummonedUndead e = new EntitySummonedUndead(world, entityPlayer, lvl, type);
      double dist = 2.0D;
      Vec3 dest = Vec3.createVectorHelper(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ);
      Vec3 look = entityPlayer.getLookVec();
      dest = dest.addVector(look.xCoord * dist, look.yCoord * dist, look.zCoord * dist);
      e.setPosition(dest.xCoord, dest.yCoord + 1.0D, dest.zCoord);
      entityPlayer.attackEntityFrom(DamageSource.wither, 2.0F);
      return e;
   }

   public WeightedRandomChestContent getChestGenBase(ChestGenHooks chest, Random rnd, WeightedRandomChestContent original) {
      ItemStack is = original.theItemId;
      if(rnd.nextInt(5) == 0) {
         is.stackTagCompound = new NBTTagCompound();
         if(rnd.nextInt(5) == 0) {
            is.stackTagCompound.setBoolean("wither", true);
         } else {
            is.stackTagCompound.setBoolean("hound", true);
         }
      }

      return original;
   }

   public float getRange(EntityLivingBase shooter, ItemStack is) {
      return 256.0F;
   }

   public int getCooldown(EntityLivingBase shooter, ItemStack is) {
      return (int)(800.0F * ItemStaffBase.getEntityCoolDownReduction(shooter));
   }

   public void shootFromEntity(EntityLivingBase shooter, ItemStack is, int angle, Entity target) {
      World world = shooter.worldObj;
      if(!world.isRemote) {
         EntitySummonedUndead e = this.getEntity(is, shooter, shooter.worldObj);
         world.spawnEntityInWorld(e);
      }

   }

   public boolean canBeUsedByEntity(Entity shooter) {
      return true;
   }

   public boolean isMeleeWeapon(EntityLivingBase shooter, ItemStack is) {
      return false;
   }

   public boolean shouldUpdate(EntityLivingBase shooter) {
      return false;
   }

   public int startAiming(ItemStack is, EntityLivingBase shooter, Entity target) {
      return 30;
   }
}
