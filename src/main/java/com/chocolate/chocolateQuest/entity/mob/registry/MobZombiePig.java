package com.chocolate.chocolateQuest.entity.mob.registry;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanPigZombie;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import com.chocolate.chocolateQuest.gui.InventoryBag;
import com.chocolate.chocolateQuest.magic.Awakements;
import com.chocolate.chocolateQuest.magic.SpellBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class MobZombiePig extends DungeonMonstersBase {

   public String getEntityName() {
      return "pigzombie";
   }

   public int getFlagId() {
      return 3;
   }

   public String getRegisteredEntityName() {
      return "chocolateQuest.pigzombie";
   }

   public Entity getBoss(World world, int x, int y, int z) {
      EntityHumanPigZombie p = new EntityHumanPigZombie(world);
      p.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(500.0D);
      ItemStack is = new ItemStack(ChocolateQuest.staffFire);
      is.stackTagCompound = new NBTTagCompound();
      ItemStack[] cargo = new ItemStack[]{new ItemStack(ChocolateQuest.spell, 1, SpellBase.getSpellID("spell_teleport")), new ItemStack(ChocolateQuest.spell, 1, SpellBase.getSpellID("spell_shield")), new ItemStack(ChocolateQuest.spell, 1, SpellBase.getSpellID("spell_vampiric")), new ItemStack(ChocolateQuest.spell, 1, SpellBase.getSpellID("spell_tracker")), new ItemStack(ChocolateQuest.spell, 1, SpellBase.getSpellID("spell_projectile"))};
      InventoryBag.saveCargo(is, cargo);
      Awakements.addEnchant(is, Awakements.power, 2);
      is.stackTagCompound.setInteger("damage", 8);
      p.setCurrentItemOrArmor(0, is);
      ItemStack bone = new ItemStack(ChocolateQuest.cursedBone);
      bone.stackTagCompound = new NBTTagCompound();
      bone.stackTagCompound.setBoolean("wither", true);
      bone.stackTagCompound.setInteger("level", 4);
      p.setLeftHandItem(bone);
      p.setCurrentItemOrArmor(4, this.setItemColor(new ItemStack(ChocolateQuest.mageArmorHelmet), 6684672));
      p.setCurrentItemOrArmor(3, this.setItemColor(new ItemStack(ChocolateQuest.mageArmorPlate), 6684672));
      p.setCurrentItemOrArmor(2, this.setItemColor(new ItemStack(ChocolateQuest.mageArmorPants), 6684672));
      p.setCurrentItemOrArmor(1, this.setItemColor(new ItemStack(ChocolateQuest.mageArmorBoots), 6684672));
      p.setBoss();
      p.heal(500.0F);
      return p;
   }

   public ItemStack setItemColor(ItemStack is, int color) {
      if(is.stackTagCompound == null) {
         is.stackTagCompound = new NBTTagCompound();
      }

      NBTTagCompound tag = is.stackTagCompound;
      NBTTagCompound tagDisplay = new NBTTagCompound();
      tag.setTag("display", tagDisplay);
      tagDisplay.setInteger("color", color);
      System.out.println(is.stackTagCompound);
      return is;
   }

   public Entity getEntity(World world, int x, int y, int z) {
      return new EntityHumanPigZombie(world);
   }

   public String getTeamName() {
      return "mob_undead";
   }

   public double getHealth() {
      return 35.0D;
   }

   public double getRange() {
      return 30.0D;
   }

   public double getAttack() {
      return 2.0D;
   }

   public int getColor() {
      return 11149858;
   }

   public int getDifficulty(World world, int posX, int posY, int posZ) {
      return 4;
   }
}
