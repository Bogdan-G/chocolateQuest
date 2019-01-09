package com.chocolate.chocolateQuest.entity.mob.registry;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public abstract class DungeonMonstersBase {

   int id;


   public void setID(int id) {
      this.id = id;
   }

   public int getID() {
      return this.id;
   }

   public abstract Entity getBoss(World var1, int var2, int var3, int var4);

   public abstract Entity getEntity(World var1, int var2, int var3, int var4);

   public String getSpawnerName(int x, int y, int z, Random random) {
      return null;
   }

   public abstract String getEntityName();

   public abstract String getRegisteredEntityName();

   public String getTeamName() {
      return "npc";
   }

   public double getHealth() {
      return 20.0D;
   }

   public double getAttack() {
      return 1.0D;
   }

   public double getRange() {
      return 20.0D;
   }

   public int getWeight() {
      return 100;
   }

   public int getFlagId() {
      return 0;
   }

   public DungeonMonstersBase getDungeonMonster(World world, int x, int y, int z) {
      return this;
   }

   public int getColor() {
      return 16777215;
   }

   public int getDifficulty(World world, int posX, int posY, int posZ) {
      return 2;
   }
}
