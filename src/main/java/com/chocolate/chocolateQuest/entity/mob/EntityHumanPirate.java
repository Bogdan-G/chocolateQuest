package com.chocolate.chocolateQuest.entity.mob;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.ai.AIFirefighter;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanMob;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import com.chocolate.chocolateQuest.misc.EnumVoice;
import com.chocolate.chocolateQuest.misc.EquipementHelper;
import com.chocolate.chocolateQuest.utils.BDHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityHumanPirate extends EntityHumanMob {

   public EntityHumanPirate(World world) {
      super(world);
   }

   protected void addAITasks() {
      super.tasks.addTask(4, new AIFirefighter(this, 1.0F, false));
      super.addAITasks();
   }

   public DungeonMonstersBase getMonsterType() {
      return ChocolateQuest.pirate;
   }

   protected String getLivingSound() {
      return EnumVoice.PIRATE.say;
   }

   protected String getHurtSound() {
      return EnumVoice.PIRATE.hurt;
   }

   protected String getDeathSound() {
      return EnumVoice.PIRATE.death;
   }

   public ItemStack getDiamondArmorForSlot(int slot) {
      ItemStack is;
      switch(slot) {
      case 1:
         is = new ItemStack(ChocolateQuest.diamondBoots);
         break;
      case 2:
         is = new ItemStack(ChocolateQuest.diamondPants);
         break;
      case 3:
         is = new ItemStack(ChocolateQuest.diamondPlate);
         break;
      default:
         is = new ItemStack(ChocolateQuest.diamondHelmet);
      }

      BDHelper.colorArmor(is, this.getMonsterType().getColor());
      return is;
   }

   public ItemStack getIronArmorForSlot(int slot) {
      ItemStack is;
      switch(slot) {
      case 1:
         is = new ItemStack(ChocolateQuest.ironBoots);
         break;
      case 2:
         is = new ItemStack(ChocolateQuest.ironPants);
         break;
      case 3:
         is = new ItemStack(ChocolateQuest.ironPlate);
         break;
      default:
         is = new ItemStack(ChocolateQuest.ironHelmet);
      }

      BDHelper.colorArmor(is, this.getMonsterType().getColor());
      return is;
   }

   protected ItemStack getMainDrop() {
      return new ItemStack(Items.gunpowder);
   }

   public ItemStack getSecondaryDrop() {
      return new ItemStack(Items.gold_nugget);
   }

   public ItemStack getRangedWeapon(int lvl) {
      int randon = super.rand.nextInt(20);
      return randon == 0?new ItemStack(ChocolateQuest.revolver):(randon <= 5?new ItemStack(ChocolateQuest.musket):EquipementHelper.getSword(super.rand, lvl));
   }

   public ItemStack getRangedWeaponLeft(int lvl) {
      return this.getEquipmentInSlot(0) != null && this.getEquipmentInSlot(0).getItem() == ChocolateQuest.musket?null:new ItemStack(ChocolateQuest.revolver);
   }
}
