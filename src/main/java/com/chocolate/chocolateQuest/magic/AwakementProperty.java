package com.chocolate.chocolateQuest.magic;

import com.chocolate.chocolateQuest.items.ItemArmorBase;
import com.chocolate.chocolateQuest.items.ItemStaffBase;
import com.chocolate.chocolateQuest.items.swords.ItemCQBlade;
import com.chocolate.chocolateQuest.magic.Awakements;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class AwakementProperty extends Awakements {

   public AwakementProperty(String name, int icon) {
      super(name, icon);
   }

   public boolean canBeUsedOnItem(ItemStack is) {
      return is.getItem() instanceof ItemArmorBase?((ItemArmorBase)is.getItem()).isEpic():is.getItem() instanceof ItemCQBlade || is.getItem() instanceof ItemStaffBase;
   }

   public int getMaxLevel() {
      return 2;
   }

   public void onEntityItemUpdate(EntityItem entityItem) {
      ItemStack is = entityItem.getEntityItem();
      if(hasEnchant(is, this)) {
         if(entityItem.age > entityItem.lifespan - 100) {
            entityItem.age = 0;
         }

         if(!entityItem.isEntityInvulnerable()) {
            double owner = entityItem.posX;
            double y = entityItem.posY;
            double z = entityItem.posZ;
            double mx = entityItem.motionX;
            double my = entityItem.motionY;
            double mz = entityItem.motionZ;
            NBTTagCompound tag = new NBTTagCompound();
            entityItem.writeEntityToNBT(tag);
            tag.setBoolean("Invulnerable", true);
            entityItem.readFromNBT(tag);
            entityItem.setPosition(owner, y, z);
            entityItem.motionX = mx;
            entityItem.motionY = my;
            entityItem.motionZ = mz;
         }

         if(entityItem.isBurning()) {
            entityItem.extinguish();
            entityItem.motionY = 0.2D;
         }

         if(getEnchantLevel(is, this) > 1) {
            String owner1 = this.getOwner(is);
            if(owner1 != null && entityItem.age % 20 == 0) {
               EntityPlayer player = entityItem.worldObj.getPlayerEntityByName(owner1);
               if(player != null) {
                  entityItem.setPosition(player.posX, player.posY, player.posZ);
               }
            }
         }
      }

   }

   public void onUpdate(Entity entity, ItemStack itemStack) {
      if(entity.ticksExisted % 400 == 0 && this.getOwner(itemStack) == null && entity instanceof EntityPlayer) {
         EntityPlayer ep = (EntityPlayer)entity;
         this.setOwner(itemStack, ep.getCommandSenderName());
      }

   }

   public String getOwner(ItemStack is) {
      if(is.getTagCompound() == null) {
         return null;
      } else {
         String ownerName = is.stackTagCompound.getString("OriginalOwner");
         return ownerName == ""?null:ownerName;
      }
   }

   public void setOwner(ItemStack is, String name) {
      if(is.stackTagCompound == null) {
         is.stackTagCompound = new NBTTagCompound();
      }

      is.stackTagCompound.setString("OriginalOwner", name);
   }

   public String getDescription(ItemStack is) {
      return super.getDescription(is) + " ( " + this.getOwner(is) + " )";
   }

   public int getLevelCost() {
      return 6;
   }
}
