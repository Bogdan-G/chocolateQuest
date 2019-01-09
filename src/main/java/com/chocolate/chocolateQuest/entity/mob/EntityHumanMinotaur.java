package com.chocolate.chocolateQuest.entity.mob;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanMob;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import com.chocolate.chocolateQuest.gui.InventoryBag;
import com.chocolate.chocolateQuest.magic.Awakements;
import com.chocolate.chocolateQuest.magic.SpellBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class EntityHumanMinotaur extends EntityHumanMob {

   public EntityHumanMinotaur(World world) {
      super(world);
      super.maxStamina = 80 + super.worldObj.difficultySetting.getDifficultyId() * 20;
   }

   public DungeonMonstersBase getMonsterType() {
      return ChocolateQuest.minotaur;
   }

   public EnumCreatureAttribute getCreatureAttribute() {
      return EnumCreatureAttribute.UNDEAD;
   }

   protected String getLivingSound() {
      return "mob.cow.say";
   }

   protected String getHurtSound() {
      return "mob.cow.hurt";
   }

   protected String getDeathSound() {
      return "mob.cow.death";
   }

   public String getCommandSenderName() {
      return this.isBoss()?StatCollector.translateToLocal(this.getEntityName()):super.getCommandSenderName();
   }

   public String getEntityName() {
      return this.isBoss()?"entity.chocolateQuest.minotaurBoss.name":super.getEntityName();
   }

   protected ItemStack getMainDrop() {
      return new ItemStack(Items.leather);
   }

   public ItemStack getSecondaryDrop() {
      return new ItemStack(Items.dye, 1, 4);
   }

   public ItemStack getRangedWeapon(int lvl) {
      ItemStack is = new ItemStack(ChocolateQuest.staffMagic);
      is.stackTagCompound = new NBTTagCompound();
      ItemStack[] cargo = new ItemStack[]{new ItemStack(ChocolateQuest.spell, 1, SpellBase.getSpellID("spell_tracker")), new ItemStack(ChocolateQuest.spell, 1, SpellBase.getSpellID("spell_projectile"))};
      InventoryBag.saveCargo(is, cargo);
      Awakements.addEnchant(is, Awakements.spellExpansion, 2);
      is.stackTagCompound.setInteger("damage", 6);
      is.stackTagCompound.setBoolean("rosary", true);
      return is;
   }
}
