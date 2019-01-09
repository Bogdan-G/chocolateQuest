package com.chocolate.chocolateQuest.entity.mob.registry;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanMummy;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import com.chocolate.chocolateQuest.magic.Elements;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MobMummy extends DungeonMonstersBase {

   public String getEntityName() {
      return "mummy";
   }

   public int getFlagId() {
      return 20;
   }

   public String getRegisteredEntityName() {
      return "chocolateQuest.mummy";
   }

   public Entity getBoss(World world, int x, int y, int z) {
      EntityHumanMummy zombie = new EntityHumanMummy(world);
      zombie.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(200.0D);
      zombie.setBoss();
      ItemStack is = new ItemStack(ChocolateQuest.bullAxe);
      Elements.setElementValue(is, Elements.fire, 1);
      zombie.setCurrentItemOrArmor(0, is);
      zombie.setLeftHandItem(is);
      zombie.setCurrentItemOrArmor(4, new ItemStack(Items.golden_helmet));
      ItemStack plate = new ItemStack(ChocolateQuest.kingArmor);
      BDHelper.colorArmor(plate, this.getColor());
      plate.stackTagCompound.setInteger("cape", this.getFlagId());
      zombie.setCurrentItemOrArmor(3, plate);
      zombie.heal(500.0F);
      return zombie;
   }

   public Entity getEntity(World world, int x, int y, int z) {
      EntityHumanMummy zombie = new EntityHumanMummy(world);
      return zombie;
   }

   public String getTeamName() {
      return "mob_undead";
   }

   public double getHealth() {
      return 30.0D;
   }

   public double getRange() {
      return 25.0D;
   }

   public int getColor() {
      return 14664789;
   }
}
