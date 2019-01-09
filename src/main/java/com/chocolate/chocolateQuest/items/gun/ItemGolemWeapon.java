package com.chocolate.chocolateQuest.items.gun;

import com.chocolate.chocolateQuest.entity.npc.EntityGolemMecha;
import com.chocolate.chocolateQuest.items.gun.ItemGun;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGolemWeapon extends ItemGun {

   public static final String TAG_GOLEM = "golem_use";
   final int ammoCapacity;


   public ItemGolemWeapon() {
      this(10, 16.0F, 5.0F);
   }

   public ItemGolemWeapon(int cooldown, float range, float accuracy) {
      this(cooldown, range, accuracy, 1.0F, 8);
   }

   public ItemGolemWeapon(int cooldown, float range, float accuracy, float damage, int capacity) {
      super(cooldown, range * range, accuracy, damage);
      this.ammoCapacity = capacity;
   }

   public ItemGolemWeapon(int cooldown, float range, float accuracy, float damage, int capacity, int rounds) {
      this(cooldown, range * range, accuracy, damage, capacity);
      super.fireRounds = rounds;
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {}

   public void shootFromEntity(EntityLivingBase shooter, ItemStack is, int angle, Entity target) {
      super.shootFromEntity(shooter, is, angle, target);
   }

   public void onUpdate(ItemStack is, World world, Entity entity, int par4, boolean par5) {
      super.onUpdate(is, world, entity, par4, par5);
   }

   public int getCooldown(EntityLivingBase shooter, ItemStack is) {
      return shooter instanceof EntityGolemMecha?Math.max(super.cooldownBase - 10, 0):super.cooldownBase;
   }

   public int getAmmoLoaderStackSize(ItemStack is) {
      return this.ammoCapacity;
   }
}
