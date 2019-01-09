package com.chocolate.chocolateQuest.entity.mob;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.ai.EnumAiCombat;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanSkeleton;
import com.chocolate.chocolateQuest.gui.InventoryBag;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityNecromancer extends EntityHumanSkeleton {

   public EntityNecromancer(World world) {
      super(world);
      super.AICombatMode = EnumAiCombat.EVASIVE.ordinal();
      super.potionCount = 0;
      this.setCurrentItemOrArmor(0, this.getEquipedWeapon());
      this.setLeftHandItem(new ItemStack(ChocolateQuest.cursedBone));
      ItemStack is = new ItemStack(ChocolateQuest.armorMage);
      NBTTagCompound tag = new NBTTagCompound();
      NBTTagCompound tagDisplay = new NBTTagCompound();
      tag.setTag("display", tagDisplay);
      is.stackTagCompound = tag;
      tagDisplay.setInteger("color", 3355443);
      this.setCurrentItemOrArmor(3, is);
      this.setCurrentItemOrArmor(2, new ItemStack(Items.leather_leggings));
      this.setCurrentItemOrArmor(1, new ItemStack(Items.leather_boots));
      this.setAIForCurrentMode();
      this.setBoss();
   }

   public ItemStack getEquipedWeapon() {
      ItemStack is = new ItemStack(ChocolateQuest.staffPhysic);
      is.stackTagCompound = new NBTTagCompound();
      ItemStack[] cargo = new ItemStack[]{new ItemStack(ChocolateQuest.spell, 1, 16), new ItemStack(ChocolateQuest.spell, 1, 7), new ItemStack(ChocolateQuest.spell, 1, 0)};
      InventoryBag.saveCargo(is, cargo);
      return is;
   }

   protected void updateEntityAttributes() {
      super.updateEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D);
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(300.0D);
      this.setHealth(300.0F);
   }
}
