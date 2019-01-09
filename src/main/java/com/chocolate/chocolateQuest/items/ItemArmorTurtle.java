package com.chocolate.chocolateQuest.items;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.client.ClientProxy;
import com.chocolate.chocolateQuest.items.ItemArmorBase;
import com.chocolate.chocolateQuest.utils.BDHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ItemArmorTurtle extends ItemArmorBase {

   int type;
   String name;


   public ItemArmorTurtle(int type, String name) {
      super(ItemArmorBase.TURTLE, type);
      this.type = type;
      this.name = name;
      this.setEpic();
   }

   public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
      return ((ItemArmorTurtle)stack.getItem()).type == 2?"chocolatequest:textures/armor/turtle_2.png":"chocolatequest:textures/armor/turtle_1.png";
   }

   public void addInformation(ItemStack is, EntityPlayer player, List list, boolean par4) {
      super.addInformation(is, player, list, par4);
      list.add("");
      if(((ItemArmorTurtle)is.getItem()).type == 1) {
         list.add(BDHelper.formatNumberToDisplay(-50, true) + "% " + StatCollector.translateToLocal("weaponbonus.rear_damage.name").trim());
      }

      list.add(BDHelper.StringColor("9") + "+1" + StatCollector.translateToLocal("armorbonus.regeneration.name"));
      if(is.hasTagCompound()) {
         int cd = is.stackTagCompound.getInteger("CD");
         if(cd != 0) {
            list.add(BDHelper.StringColor("4") + cd / 20 / 60 + ":" + cd / 20 % 60);
         }
      }

   }

   public boolean hasFullSetBonus() {
      return true;
   }

   public String getFullSetBonus() {
      return "emergency_regeneration";
   }

   public EnumRarity getRarity(ItemStack itemstack) {
      return EnumRarity.epic;
   }

   @SideOnly(Side.CLIENT)
   public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
      return armorSlot == 1?ClientProxy.turtleArmorModel:(armorSlot == 0?ClientProxy.turtleHelmetModel:(armorSlot == 2?ClientProxy.heavyArmorLegs:null));
   }

   public boolean getIsRepairable(ItemStack itemToRepair, ItemStack itemMaterial) {
      return itemMaterial.getItem() == ChocolateQuest.material && itemMaterial.getItemDamage() == 1 || super.getIsRepairable(itemToRepair, itemMaterial);
   }

   public void onUpdateEquiped(ItemStack itemStack, World world, EntityLivingBase entity) {
      super.onUpdateEquiped(itemStack, world, entity);
      if(entity.ticksExisted % 800 == this.type * 200) {
         entity.heal(1.0F);
         if(world.isRemote) {
            entity.worldObj.spawnParticle("heart", entity.posX + Item.itemRand.nextDouble() - 0.5D, entity.posY + Item.itemRand.nextDouble(), entity.posZ + Item.itemRand.nextDouble() - 0.5D, 0.0D, 1.0D, 0.0D);
         }
      }

      if(itemStack.stackTagCompound != null) {
         if(itemStack.stackTagCompound.hasKey("CD")) {
            this.setCooldown(itemStack, this.getCoolDown(itemStack) - 1);
         }

         if(itemStack.stackTagCompound.hasKey("ON")) {
            if(entity.getHealth() < entity.getMaxHealth()) {
               entity.heal(1.0F);
               if(world.isRemote) {
                  entity.worldObj.spawnParticle("heart", entity.posX + Item.itemRand.nextDouble() - 0.5D, entity.posY + Item.itemRand.nextDouble(), entity.posZ + Item.itemRand.nextDouble() - 0.5D, 0.0D, 1.0D, 0.0D);
               }
            } else {
               itemStack.stackTagCompound.removeTag("ON");
            }
         }
      }

   }

   public void onHit(LivingHurtEvent event, ItemStack is, EntityLivingBase entity) {
      super.onHit(event, is, entity);
      if(super.armorType == 1) {
         if(event.source.getEntity() instanceof EntityLivingBase) {
            EntityLivingBase ammount = (EntityLivingBase)event.source.getEntity();

            double angle;
            for(angle = (double)(entity.rotationYaw - ammount.rotationYaw); angle > 360.0D; angle -= 360.0D) {
               ;
            }

            while(angle < 0.0D) {
               angle += 360.0D;
            }

            angle = Math.abs(angle - 180.0D);
            if(angle > 130.0D) {
               event.ammount /= 2.0F;
            }
         }

         if(this.isFullSet(entity, is)) {
            float ammount1 = event.ammount + 4.0F;
            if(!event.source.isUnblockable()) {
               ammount1 *= 1.0F / (float)entity.getTotalArmorValue() * 4.0F;
            }

            if(entity.getHealth() - ammount1 <= 0.0F && this.getCoolDown(is) == 0) {
               entity.setHealth(0.1F);
               event.setCanceled(true);
               this.setCooldown(is, '\u8ca0');
               is.stackTagCompound.setBoolean("ON", true);
            }
         }
      }

   }

   public void setCooldown(ItemStack is, int cooldown) {
      if(is.stackTagCompound == null) {
         is.stackTagCompound = new NBTTagCompound();
      }

      if(cooldown == 0) {
         is.stackTagCompound.removeTag("CD");
      } else {
         is.stackTagCompound.setInteger("CD", cooldown);
      }
   }

   public int getCoolDown(ItemStack is) {
      return is.stackTagCompound == null?0:is.stackTagCompound.getInteger("CD");
   }
}
