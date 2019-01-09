package com.chocolate.chocolateQuest.entity.mob;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanZombie;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import com.chocolate.chocolateQuest.gui.InventoryBag;
import com.chocolate.chocolateQuest.magic.SpellBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class EntityHumanPigZombie extends EntityHumanZombie {

   EntityPigZombie pig;


   public EntityHumanPigZombie(World world) {
      super(world);
      super.isImmuneToFire = true;
   }

   public DungeonMonstersBase getMonsterType() {
      return ChocolateQuest.pigZombie;
   }

   public EnumCreatureAttribute getCreatureAttribute() {
      return EnumCreatureAttribute.UNDEAD;
   }

   protected String getLivingSound() {
      return "mob.zombiepig.zpig";
   }

   protected String getHurtSound() {
      return "mob.zombiepig.zpighurt";
   }

   protected String getDeathSound() {
      return "mob.zombiepig.zpigdeath";
   }

   public boolean attackEntityFrom(DamageSource damagesource, float damage) {
      if(super.attackEntityFrom(damagesource, damage)) {
         if(this.pig == null) {
            this.pig = new EntityPigZombie(super.worldObj);
         }

         this.pig.setPosition(super.posX, super.posY, super.posZ);
         this.pig.attackEntityFrom(damagesource, damage);
         return true;
      } else {
         return false;
      }
   }

   public ItemStack getSecondaryDrop() {
      return new ItemStack(Items.gold_nugget);
   }

   public String getCommandSenderName() {
      return this.isBoss()?StatCollector.translateToLocal(this.getEntityName()):super.getCommandSenderName();
   }

   public String getEntityName() {
      return this.isBoss()?"entity.chocolateQuest.pigZombieBoss.name":super.getEntityName();
   }

   public ItemStack getRangedWeapon(int lvl) {
      ItemStack is = new ItemStack(ChocolateQuest.staffFire);
      is.stackTagCompound = new NBTTagCompound();
      ItemStack[] cargo = new ItemStack[]{new ItemStack(ChocolateQuest.spell, 1, SpellBase.getSpellID("spell_tracker")), new ItemStack(ChocolateQuest.spell, 1, SpellBase.getSpellID("spell_projectile"))};
      InventoryBag.saveCargo(is, cargo);
      is.stackTagCompound.setInteger("damage", 6);
      is.stackTagCompound.setBoolean("wand", true);
      return is;
   }
}
