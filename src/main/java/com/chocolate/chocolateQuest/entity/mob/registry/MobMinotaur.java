package com.chocolate.chocolateQuest.entity.mob.registry;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanMinotaur;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import com.chocolate.chocolateQuest.magic.Elements;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class MobMinotaur extends DungeonMonstersBase {

   public String getEntityName() {
      return "minotaur";
   }

   public int getFlagId() {
      return 19;
   }

   public String getRegisteredEntityName() {
      return "chocolateQuest.minotaur";
   }

   public Entity getBoss(World world, int x, int y, int z) {
      EntityHumanMinotaur m = new EntityHumanMinotaur(world);
      m.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(500.0D);
      ItemStack is = new ItemStack(ChocolateQuest.bigSwordBull);
      Elements.setElementValue(is, Elements.magic, 2);
      m.setCurrentItemOrArmor(0, is);
      ItemStack plate = new ItemStack(ChocolateQuest.bullPlate);
      plate.stackTagCompound = new NBTTagCompound();
      plate.stackTagCompound.setInteger("cape", this.getFlagId());
      m.setCurrentItemOrArmor(3, plate);
      m.setCurrentItemOrArmor(2, new ItemStack(ChocolateQuest.bullPants));
      m.setCurrentItemOrArmor(1, new ItemStack(ChocolateQuest.bullBoots));
      m.heal(500.0F);
      m.setBoss();
      return m;
   }

   public Entity getEntity(World world, int x, int y, int z) {
      return new EntityHumanMinotaur(world);
   }

   public String getTeamName() {
      return "mob_minotaur";
   }

   public double getHealth() {
      return 40.0D;
   }

   public double getRange() {
      return 35.0D;
   }

   public double getAttack() {
      return 2.0D;
   }

   public int getColor() {
      return 8014111;
   }

   public int getDifficulty(World world, int posX, int posY, int posZ) {
      return 5;
   }
}
