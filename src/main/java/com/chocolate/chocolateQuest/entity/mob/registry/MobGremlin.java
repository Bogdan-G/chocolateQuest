package com.chocolate.chocolateQuest.entity.mob.registry;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanGremlin;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import com.chocolate.chocolateQuest.gui.InventoryBag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class MobGremlin extends DungeonMonstersBase {

   public String getEntityName() {
      return "gremlin";
   }

   public int getFlagId() {
      return 11;
   }

   public String getRegisteredEntityName() {
      return "chocolateQuest.gremlin";
   }

   public Entity getEntity(World world, int x, int y, int z) {
      return new EntityHumanGremlin(world);
   }

   public String getTeamName() {
      return "mob_gremlin";
   }

   public Entity getBoss(World world, int x, int y, int z) {
      EntityHumanGremlin p = new EntityHumanGremlin(world);
      p.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(250.0D);
      p.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(0.0D);
      ItemStack is = new ItemStack(ChocolateQuest.hookSword);
      p.setCurrentItemOrArmor(0, is);
      p.setCurrentItemOrArmor(0, is);
      is = new ItemStack(ChocolateQuest.staffFire);
      is.stackTagCompound = new NBTTagCompound();
      ItemStack[] cargo = new ItemStack[]{new ItemStack(ChocolateQuest.spell, 1, 5)};
      InventoryBag.saveCargo(is, cargo);
      is.stackTagCompound.setBoolean("melee", true);
      is.stackTagCompound.setBoolean("rosary", true);
      is.stackTagCompound.setInteger("cooldown", 75);
      p.setLeftHandItem(is);
      p.setCurrentItemOrArmor(4, new ItemStack(Items.golden_helmet));
      p.setCurrentItemOrArmor(1, new ItemStack(ChocolateQuest.cloudBoots));
      p.setBoss();
      p.heal(500.0F);
      return p;
   }

   public int getColor() {
      return 3777355;
   }
}
