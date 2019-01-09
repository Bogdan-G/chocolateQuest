package com.chocolate.chocolateQuest.entity.mob;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanMob;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import com.chocolate.chocolateQuest.misc.EnumVoice;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityHumanZombie extends EntityHumanMob {

   public EntityHumanZombie(World world) {
      super(world);
      if(super.rand.nextInt(30) == 0) {
         this.setDwarf(true);
      }

   }

   public DungeonMonstersBase getMonsterType() {
      return ChocolateQuest.zombie;
   }

   public EnumCreatureAttribute getCreatureAttribute() {
      return EnumCreatureAttribute.UNDEAD;
   }

   protected String getLivingSound() {
      return EnumVoice.ZOMBIE.say;
   }

   protected String getHurtSound() {
      return EnumVoice.ZOMBIE.hurt;
   }

   protected String getDeathSound() {
      return EnumVoice.ZOMBIE.death;
   }

   public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
      super.writeEntityToNBT(nbttagcompound);
      nbttagcompound.setBoolean("isDwarf", this.isDwarf());
   }

   public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
      super.readEntityFromNBT(nbttagcompound);
      this.setDwarf(nbttagcompound.getBoolean("isDwarf"));
   }

   public void onLivingUpdate() {
      if(super.worldObj.isRemote && this.isDwarf()) {
         this.setSize(0.6F, 0.9F);
      }

      super.onLivingUpdate();
   }

   protected ItemStack getMainDrop() {
      return new ItemStack(Items.rotten_flesh);
   }

   protected void entityInit() {
      super.entityInit();
      super.dataWatcher.addObject(21, Byte.valueOf((byte)0));
   }

   public boolean isDwarf() {
      return super.dataWatcher.getWatchableObjectByte(21) == 1;
   }

   public void setDwarf(boolean dwarf) {
      super.dataWatcher.updateObject(21, Byte.valueOf((byte)(dwarf?1:0)));
      if(dwarf) {
         this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.33D);
         this.setSize(0.6F, 0.9F);
      } else {
         this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23D);
      }

   }

   public int getInteligence() {
      return 4;
   }
}
