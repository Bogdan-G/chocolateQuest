package com.chocolate.chocolateQuest.items;

import com.chocolate.chocolateQuest.items.ItemMulti;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemShied extends ItemMulti {

   public ItemShied() {
      super(new String[]{"Chocolate", "Terra", "Aigua", "Ignis", "Orc", "Dwarf", "Triton", "Zombie", "Skeleton", "Pirate", "Walker", "Wood", "Specter", "Diurna", "Nocturna", "Turtle", "Rusted", "Monking", "Spider", "bull", "mummy"}, "s");
   }

   public String getItemStackDisplayName(ItemStack itemstack) {
      return ("" + StatCollector.translateToLocal("item.shield.name")).trim();
   }

   public void onUpdate(ItemStack itemStack, World world, Entity entity, int par4, boolean par5) {
      if(entity instanceof EntityPlayer) {
         EntityPlayer ep = (EntityPlayer)entity;
         if(ep.isBlocking() && ep.getCurrentEquippedItem() == itemStack) {
            ep.addPotionEffect(new PotionEffect(Potion.resistance.id, 1, 3));
            List list = world.getEntitiesWithinAABBExcludingEntity(entity, entity.boundingBox.expand(1.2D, 1.2D, 1.2D));
            Iterator i$ = list.iterator();

            while(i$.hasNext()) {
               Entity e = (Entity)i$.next();
               if(e instanceof EntityArrow) {
                  e.motionX = entity.getLookVec().xCoord;
                  e.motionZ = entity.getLookVec().zCoord;
               }
            }
         }
      }

   }

   public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityPlayer) {
      entityPlayer.setItemInUse(itemstack, 72000);
      return itemstack;
   }

   public int getMaxItemUseDuration(ItemStack par1ItemStack) {
      return 72000;
   }

   public EnumAction getItemUseAction(ItemStack par1ItemStack) {
      return EnumAction.block;
   }
}
