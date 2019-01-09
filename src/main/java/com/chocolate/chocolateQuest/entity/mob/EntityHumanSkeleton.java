package com.chocolate.chocolateQuest.entity.mob;

import com.chocolate.chocolateQuest.ChocolateQuest;
import com.chocolate.chocolateQuest.entity.mob.EntityHumanMob;
import com.chocolate.chocolateQuest.entity.mob.registry.DungeonMonstersBase;
import com.chocolate.chocolateQuest.misc.EnumVoice;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class EntityHumanSkeleton extends EntityHumanMob {

   public EntityHumanSkeleton(World world) {
      super(world);
      if(world.provider.dimensionId == -1) {
         this.setWither(true);
      }

   }

   public DungeonMonstersBase getMonsterType() {
      return ChocolateQuest.skeleton;
   }

   public EnumCreatureAttribute getCreatureAttribute() {
      return EnumCreatureAttribute.UNDEAD;
   }

   protected String getLivingSound() {
      return EnumVoice.SKELETON.say;
   }

   protected String getHurtSound() {
      return EnumVoice.SKELETON.hurt;
   }

   protected String getDeathSound() {
      return EnumVoice.SKELETON.death;
   }

   protected ItemStack getMainDrop() {
      return new ItemStack(Items.bone);
   }

   public boolean attackEntityAsMob(Entity entity, ItemStack weapon) {
      if(super.attackEntityAsMob(entity, weapon)) {
         if(this.isWither() && entity instanceof EntityLivingBase) {
            ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.wither.id, 200));
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean canSprint() {
      return this.isWither();
   }

   protected void dropRareDrop(int p_70600_1_) {
      if(this.isWither()) {
         this.entityDropItem(new ItemStack(Items.skull, 1, 1), 0.0F);
      }

   }

   public float getSizeModifier() {
      return this.isWither()?super.getSizeModifier() * 1.2F:super.getSizeModifier();
   }

   public int getInteligence() {
      return 4;
   }

   public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
      super.writeEntityToNBT(nbttagcompound);
      nbttagcompound.setBoolean("isWither", this.isWither());
   }

   public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
      super.readEntityFromNBT(nbttagcompound);
      this.setWither(nbttagcompound.getBoolean("isWither"));
   }

   protected void entityInit() {
      super.entityInit();
      super.dataWatcher.addObject(21, Byte.valueOf((byte)0));
   }

   public boolean isWither() {
      return super.dataWatcher.getWatchableObjectByte(21) == 1;
   }

   public void setWither(boolean wither) {
      super.dataWatcher.updateObject(20, Byte.valueOf((byte)(wither?1:0)));
   }
}
