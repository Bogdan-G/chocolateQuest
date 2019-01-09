package com.chocolate.chocolateQuest.items.swords;

import com.chocolate.chocolateQuest.entity.EntityBaiter;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import com.chocolate.chocolateQuest.items.swords.ItemBaseDagger;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemDaggerNinja extends ItemBaseDagger {

   public ItemDaggerNinja() {
      super(ToolMaterial.EMERALD, 3);
      this.setMaxDamage(2048);
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {
      super.itemIcon = iconRegister.registerIcon("chocolatequest:daggerPirate");
   }

   public int getMaxDamage() {
      return 2048;
   }

   public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
      if(entityPlayer.isSneaking()) {
         if(!world.isRemote) {
            EntityBaiter e = new EntityBaiter(world, entityPlayer);
            world.spawnEntityInWorld(e);
         }

         entityPlayer.addPotionEffect(new PotionEffect(Potion.invisibility.id, 200, 5));
         itemStack.damageItem(20, entityPlayer);
      } else {
         entityPlayer.addPotionEffect(new PotionEffect(Potion.invisibility.id, 20, 5));
      }

      return super.onItemRightClick(itemStack, world, entityPlayer);
   }

   public boolean getIsRepairable(ItemStack itemToRepair, ItemStack itemMaterial) {
      return super.getIsRepairable(itemToRepair, itemMaterial);
   }

   public EnumRarity getRarity(ItemStack itemstack) {
      return EnumRarity.epic;
   }

   public int getEntityLifespan(ItemStack itemStack, World world) {
      return 24000;
   }

   public boolean hasSkill() {
      return true;
   }

   public int doSkill(EntityHumanBase human) {
      if(human.worldObj.isRemote) {
         Random e = human.getRNG();

         for(int n = 0; n < 5; ++n) {
            human.worldObj.spawnParticle("mobSpell", human.posX + (double)e.nextFloat() - 0.5D, human.posY + 1.0D, human.posZ + (double)e.nextFloat() - 0.5D, 1.0D, 1.0D, 1.0D);
         }
      } else {
         EntityBaiter var4 = new EntityBaiter(human.worldObj, human);
         var4.setPosition(human.posX, human.posY, human.posZ);
         human.worldObj.spawnEntityInWorld(var4);
      }

      return 200;
   }
}
